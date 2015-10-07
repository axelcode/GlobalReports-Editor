/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.tablelist.GRTableList
 * Begin       : 
 * Last Update : 
 *
 * Author      : Alessandro Baldini - alex.baldini72@gmail.com
 * License     : GNU-GPL v2 (http://www.gnu.org/licenses/)
 * ==========================================================================
 * 
 * GlobalReports Editor
 * Copyright (C) 2015 Alessandro Baldini
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Linking GlobalReports Editor(C) statically or dynamically with other 
 * modules is making a combined work based on GlobalReports Editor(C). 
 * Thus, the terms and conditions of the GNU General Public License cover 
 * the whole combination.
 *
 * In addition, as a special exception, the copyright holders 
 * of GlobalReports Editor(C) give you permission to combine 
 * GlobalReports Editor(C) program with free software programs or libraries 
 * that are released under the GNU LGPL and with code included 
 * in the standard release of GlobalReports Engine(C) under the CC license 
 * (or modified versions of such code, with unchanged license) and
 * GlobalReports Compiler(C) under the CC license. 
 * You may copy and distribute such a system following the terms of the GNU GPL 
 * for GlobalReports Editor(C) and the licenses of the other code concerned, 
 * provided that you include the source code of that other code 
 * when and as the GNU GPL requires distribution of source code.
 *
 * Note that people who make modified versions of GlobalReports Editor(C) 
 * are not obligated to grant this special exception for their modified versions; 
 * it is their choice whether to do so. The GNU General Public License 
 * gives permission to release a modified version without this exception; 
 * this exception also makes it possible to release a modified version 
 * which carries forward this exception.
 * 
 */
package com.globalreports.editor.graphics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModelList;
import com.globalreports.editor.designer.property.GRTableModelTableList;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.graphics.tablelist.GRTableListBody;
import com.globalreports.editor.graphics.tablelist.GRTableListCell;
import com.globalreports.editor.graphics.tablelist.GRTableListColumn;
import com.globalreports.editor.graphics.tablelist.GRTableListFooter;
import com.globalreports.editor.graphics.tablelist.GRTableListHeader;
import com.globalreports.editor.graphics.tablelist.GRTableListSection;
import com.globalreports.editor.tools.GRLibrary;

public class GRTableList extends GRObject {
	private int numColumns;
	private int dimHeader;
	private int dimBody;
	private int dimFooter;
	private Vector<Integer> dimCell;
	
	private Vector<GRTableListColumn> grtablelistColumns;
	
	private GRTableListSection grtablelistHeader;
	private GRTableListSection grtablelistBody;
	private GRTableListSection grtablelistFooter;
	
	private AlphaComposite composite;	// Canale Alpha per la trasparenza degli oggetti
	private BasicStroke bordoEsterno;
	private Color cBordoEsterno;
	
	private String nameXml;
	
	private GRTableModelTableList modelTable;
	
	public GRTableList(GRPage grpage, long id, int xStart, int yStart, int xEnd, int yEnd, int totColumn) {
		super(GRObject.TYPEOBJ_TABLELIST,id,grpage);
		
		this.numColumns = totColumn;
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f);
		
		nameXml = "tablelist";
		
		if(xStart < xEnd) {
			x1 = xStart;
			width = xEnd - xStart;
		} else {
			x1 = xEnd;
			width = xStart - xEnd;
		}
		if(yStart < yEnd) {
			y1 = yStart;
			height = yEnd - yStart;
		} else {
			y1 = yEnd;
			height = yStart - yEnd;
		}
		
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		heightOriginal = height;
		
		bordoEsterno = new BasicStroke(2.0f);
		cBordoEsterno = new Color(57,105,138);
		
		this.setDimension();
		this.refreshReferenceSection();	
	}
	
	private void setDimension() {
		// Imposta le dimensioni di default di:
		// -->HEADER
		// -->BODY
		// -->Width di ogni colonna
		int wCell = 0;
		int leftCell = 0;
		int totCell = 0;
		dimCell = new Vector<Integer>();
		grtablelistColumns = new Vector<GRTableListColumn>();
		
		/* Genera le tre sezioni della tabella */
		dimHeader = height / 3;
		dimBody = dimHeader;
		dimFooter = height - (dimHeader + dimHeader);
		/*
		grtablelistHeader = new GRTableListHeader(0, dimHeader, numColumns, this);
		grtablelistBody = new GRTableListBody(dimHeader, dimBody, numColumns, this);
		grtablelistFooter = new GRTableListFooter(dimHeader+dimBody, dimFooter, numColumns, this);
		*/
		grtablelistHeader = new GRTableListHeader(0, dimHeader, this);
		grtablelistBody = new GRTableListBody(dimHeader, dimBody, this);
		grtablelistFooter = new GRTableListFooter(dimHeader+dimBody, dimFooter,this);
	
		/* Genera il Vector che conterrà i riferimenti alle colonne */
		wCell = width / numColumns;
		for(int i = 0;i < numColumns;i++) {
			if((i+1) == numColumns) {
				dimCell.add(width-totCell);	// Arrotondamento ultima colonna
				grtablelistColumns.add(new GRTableListColumn(i,totCell,(width-totCell),grtablelistHeader,grtablelistBody,grtablelistFooter));
			} else {
				dimCell.add(wCell);
				grtablelistColumns.add(new GRTableListColumn(i,totCell,wCell,grtablelistHeader,grtablelistBody,grtablelistFooter));
			}
			
			totCell = totCell + wCell;
		}
	}
	public AlphaComposite getComposite() {
		return composite;
	}
	public Color getColorTableList() {
		return cBordoEsterno;
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Composite compositeOld = g2d.getComposite();
		Color oldColorStroke = g.getColor();
		Stroke oldStroke = g2d.getStroke();
		
		grtablelistHeader.draw(g2d);
		grtablelistBody.draw(g2d);
		grtablelistFooter.draw(g2d);
		
		// Dopo che ha disegnato le varie sezioni disegna il bordo unico
		g2d.setStroke(bordoEsterno);
		g2d.setColor(cBordoEsterno);
		g2d.drawRect(x1-3, y1-3, width+6, height+6);
		
		g2d.setStroke(oldStroke);
		g2d.setColor(oldColorStroke);
		
		if(selected) {
			Font oldFont = g.getFont();
			g.setFont(new Font("Verdana",Font.PLAIN,8));
			
			g.fillRect(x1-7,y1-7,4,4);
			g.fillRect(x1-7,y1+height+3,4,4);
			g.fillRect(x1+width+3,y1-7,4,4);
			g.fillRect(x1+width+3,y1+height+3,4,4);
			
			//g.drawString("HEADER", x1+2, y1);
			//g.drawString("BODY",x1+2,y1+height);
			g.setFont(oldFont);
		}
	}
	public void setProperty(GRTable model) {
		this.modelTable = (GRTableModelTableList)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return ;
		/*
		modelTable.setNameXml(this.getNameXml());
		modelTable.setTop(this.getOriginalY());
		modelTable.setHeight(this.getOriginalHeight());
		
		modelTableList.setGRObject(grtablelist);
			modelTableList.setLeft(grtablelist.getX());
			modelTableList.setTop(grtablelist.getY());
			modelTableList.setWidth(grtablelist.getWidth());
			modelTableList.setNameXml(grtablelist.getNameXml());
			
			// HEADER
			if(grtablelist.isHeader()) {
				modelTableList.setHeaderWidthStroke(grtablelist.getHeaderWidthStroke());
				modelTableList.setHeaderColorStroke(grtablelist.getHeaderColorStroke());
				modelTableList.setHeaderColorFill(grtablelist.getHeaderColorFill());
				modelTableList.setHeaderMinHeight(grtablelist.getHeaderMinHeight());
				
			}
						
			// BODY
			modelTableList.setBodyWidthStroke(grtablelist.getBodyWidthStroke());
			modelTableList.setBodyColorStroke(grtablelist.getBodyColorStroke());
			modelTableList.setBodyColorFill(grtablelist.getBodyColorFill());
			modelTableList.setBodyMinHeight(grtablelist.getBodyMinHeight());
			
			// FOOTER
			if(grtablelist.isFooter()) {
				modelTableList.setFooterWidthStroke(grtablelist.getFooterWidthStroke());
				modelTableList.setFooterColorStroke(grtablelist.getFooterColorStroke());
				modelTableList.setFooterColorFill(grtablelist.getFooterColorFill());
				modelTableList.setFooterMinHeight(grtablelist.getFooterMinHeight());
				
			}
		*/
	}
	public int getColLeft(int index) {
		if(index < 0 || index >= grtablelistColumns.size())
			return -1;
		
		return grtablelistColumns.get(index).getLeft();
	}
	public void setColDimension(int index, int value) {
		if(index < 0 || index >= grtablelistColumns.size())
			return;
		
		int gap = value - grtablelistColumns.get(index).getWidth();
		
		grtablelistColumns.get(index).setWidth(value);
		
		/* Dopo aver ridimensionato la colonna, aggiorna il left di tutte le colonne restanti */
		for(int i = (index+1);i < grtablelistColumns.size();i++) {
			grtablelistColumns.get(i).setLeft(grtablelistColumns.get(i).getLeft() + gap);
		}
		
		/* Aggiorna il width totale */
		width = width + gap;
		
		this.refresh();
	}
	public int getColDimension(int i) {
		if(i < 0 || i >= grtablelistColumns.size())
			return 0;
		
		return grtablelistColumns.get(i).getWidth();
	}
	public void setNameXml(String nameXml) {
		this.nameXml = nameXml;
	}
	public String getNameXml() {
		return nameXml;
	}
	public boolean isCellIntersect(int x, int y) {
		boolean ris;
		
		ris = grtablelistHeader.isCellIntersect(x,y);
		if(ris) {
			grtablelistBody.deselect();
			
			if(grtablelistFooter != null)
				grtablelistFooter.deselect();
			
			return true;
		}

		ris = grtablelistBody.isCellIntersect(x,y);
		if(ris) {
			if(grtablelistHeader != null)
				grtablelistHeader.deselect();
			if(grtablelistFooter != null)
				grtablelistFooter.deselect();
			
			return true;
		}
		
		ris = grtablelistFooter.isCellIntersect(x,y);
		if(ris) {
			if(grtablelistHeader != null)
				grtablelistHeader.deselect();
			
			grtablelistBody.deselect();
						
			return true;
		}
		return false;
	}
	public void refreshSection() {
		grtablelistBody.setTop(grtablelistHeader.getHeight());
		if(grtablelistFooter != null)
			grtablelistFooter.setTop(grtablelistHeader.getHeight()+grtablelistBody.getHeight());
		
		height = grtablelistHeader.getHeight() + grtablelistBody.getHeight() + grtablelistFooter.getHeight();
	}
	public GRTableListCell getCellSelected() {
		GRTableListCell cell = null;
		
		cell = grtablelistHeader.getCellSelected();
		if(cell != null)
			return cell;
		
		cell = grtablelistBody.getCellSelected();
		if(cell != null)
			return cell;
		
		cell = grtablelistFooter.getCellSelected();
		if(cell != null)
			return cell;
		
		return null;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public boolean isHeader() {
		if(grtablelistHeader == null)
			return false;
		
		return true;
	}
	public boolean isFooter() {
		if(grtablelistFooter == null)
			return false;
		
		return true;
	}
	public void setWidth(int value) {
		// Ridimensiona le sezioni
		if(grtablelistHeader != null)
			grtablelistHeader.setWidth(value);
		
		grtablelistBody.setWidth(value);
		
		if(grtablelistFooter != null)
			grtablelistFooter.setWidth(value);
		
		width = value;
		
	}
	public void cellMerge(int index) {
		System.out.println("Indice colonna: "+index);
	}
	// HEAD
	public void setHeaderWidthStroke(double value) {
		if(grtablelistHeader == null)
			return;
		
		grtablelistHeader.setWidthStroke(value);
	}
	public double getHeaderWidthStroke() {
		if(grtablelistHeader == null)
			return -1.0;
		
		return grtablelistHeader.getWidthStroke();
	}
	public void setHeaderColorStroke(Color c) {
		if(grtablelistHeader == null)
			return;
		
		grtablelistHeader.setColorStroke(c);
	}
	public Color getHeaderColorStroke() {
		if(grtablelistHeader == null)
			return null;
		
		return grtablelistHeader.getColorStroke();
	}
	public void setHeaderColorFill(Color c) {
		if(grtablelistHeader == null)
			return;
		
		grtablelistHeader.setColorFill(c);
	}
	public Color getHeaderColorFill() {
		if(grtablelistHeader == null)
			return null;
		
		return grtablelistHeader.getColorFill();
	}
	public void setHeaderMinHeight(int value) {
		if(grtablelistHeader == null)
			return;
		
		grtablelistHeader.setMinHeight(value);
	}
	public int getHeaderMinHeight() {
		if(grtablelistHeader == null)
			return -1;
		
		return grtablelistHeader.getMinHeight();
	}
	
	// BODY
	public void setBodyWidthStroke(double value) {
		grtablelistBody.setWidthStroke(value);
	}
	public double getBodyWidthStroke() {
		return grtablelistBody.getWidthStroke();
	}
	public void setBodyColorStroke(Color c) {
		grtablelistBody.setColorStroke(c);
	}
	public Color getBodyColorStroke() {
		return grtablelistBody.getColorStroke();
	}
	public void setBodyColorFill(Color c) {
		grtablelistBody.setColorFill(c);
	}
	public Color getBodyColorFill() {
		return grtablelistBody.getColorFill();
	}
	public void setBodyMinHeight(int value) {
		grtablelistBody.setMinHeight(value);
	}
	public int getBodyMinHeight() {
		return grtablelistBody.getMinHeight();
	}
	
	// FOOTER
	public void setFooterWidthStroke(double value) {
		if(grtablelistFooter == null)
			return;
			
		grtablelistFooter.setWidthStroke(value);
	}
	public double getFooterWidthStroke() {
		if(grtablelistFooter == null)
			return -1.0;
		
		return grtablelistFooter.getWidthStroke();
	}
	public void setFooterColorStroke(Color c) {
		if(grtablelistFooter == null)
			return;
		
		grtablelistFooter.setColorStroke(c);
	}
	public Color getFooterColorStroke() {
		if(grtablelistFooter == null)
			return null;
		
		return grtablelistFooter.getColorStroke();
	}
	public void setFooterColorFill(Color c) {
		if(grtablelistFooter == null)
			return;
		
		grtablelistFooter.setColorFill(c);
	}
	public Color getFooterColorFill() {
		if(grtablelistFooter == null)
			return null;
		
		return grtablelistFooter.getColorFill();
	}
	public void setFooterMinHeight(int value) {
		if(grtablelistFooter == null)
			return;
		
		grtablelistFooter.setMinHeight(value);
	}
	public int getFooterMinHeight() {
		if(grtablelistFooter == null)
			return -1;
		
		return grtablelistFooter.getMinHeight();
	}
	public GRTableList clone(long id) {
		return null;
	}
		
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1;
		
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		
		buff.append("<tablelist>\n");
		buff.append("<id>"+nameXml+"</id>\n");
		buff.append("<left>"+GRLibrary.fromPixelsToMillimeters(x1)+"</left>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<cols>\n");
		
		for(int i = 0;i < grtablelistColumns.size();i++) {
			buff.append("<cell>\n");
			buff.append("<width>"+GRLibrary.fromPixelsToMillimeters(grtablelistColumns.get(i).getWidth())+"</width>\n");
			buff.append("</cell>\n");
		}
		buff.append("</cols>\n");
		
		buff.append(grtablelistHeader.createCodeGRS());
		buff.append(grtablelistBody.createCodeGRS());
		buff.append(grtablelistFooter.createCodeGRS());
		
		buff.append("</tablelist>");
		
		return buff.toString();
	}
}
