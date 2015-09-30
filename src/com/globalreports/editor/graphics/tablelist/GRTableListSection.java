/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.tablelist.GRTableListSection
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
package com.globalreports.editor.graphics.tablelist;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRTableList;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.tools.GRLibrary;
import com.globalreports.editor.GRSetting;

public class GRTableListSection {
	protected int colorStrokeRED;
	protected int colorStrokeGREEN;
	protected int colorStrokeBLUE;
	protected Color cStroke;
	protected int colorFillRED;
	protected int colorFillGREEN;
	protected int colorFillBLUE;
	protected Color cFill;
	protected Color colorSection;
	protected double widthStroke;

	protected int top;
	protected int height;
	protected int minHeight;
	protected int column;
	
	protected Vector<GRTableListCell> grtablelistcell;
	protected GRTableListCell cellSelected;
	protected GRTableList grfather;
	protected int indexCellSelected;
	
	protected BasicStroke typeStroke;
	
	public GRTableListSection(int top, int height, GRTableList grtablelist) {
		widthStroke = GRSetting.WIDTHSTROKE;
		cStroke = GRSetting.COLORSTROKE;
		cFill = GRSetting.COLORFILL;
		
		typeStroke = new BasicStroke((float)(widthStroke * 2));
		
		colorStrokeRED = 0;
		colorStrokeGREEN = 0;
		colorStrokeBLUE = 0;
		colorFillRED = -1;
		colorFillGREEN = -1;
		colorFillBLUE = -1;
		
		this.top = top;
		this.height = height;
		this.minHeight = height;
		this.grfather = grtablelist;
		
		this.column = 0;
		indexCellSelected = -1;
		cellSelected = null;
		colorSection = null;
	}
	public GRTableListSection(int top, int height, int numColumns, GRTableList grtablelist) {
		widthStroke = GRSetting.WIDTHSTROKE;
		cStroke = GRSetting.COLORSTROKE;
		cFill = GRSetting.COLORFILL;
		
		typeStroke = new BasicStroke((float)(widthStroke * 2));
		
		colorStrokeRED = 0;
		colorStrokeGREEN = 0;
		colorStrokeBLUE = 0;
		colorFillRED = -1;
		colorFillGREEN = -1;
		colorFillBLUE = -1;
		
		this.top = top;
		this.height = height;
		this.minHeight = height;
		this.column = numColumns;
		this.grfather = grtablelist;
		
		// Crea le celle
		int xLeft = 0;
		
		grtablelistcell = new Vector<GRTableListCell>();
		for(int i = 0;i < numColumns;i++) {
			int wCell = grfather.getColDimension(i);
			
			grtablelistcell.add(new GRTableListCell(i,xLeft,0,wCell,height,this));
			xLeft = xLeft + wCell;
		}
		
		indexCellSelected = -1;
		cellSelected = null;
		colorSection = null;
	}
	public void refresh() {
		grfather.refresh();
	}
	public void addCell(int wCell) {
		int xLeft = 0;
		int index = 0;
		
		if(grtablelistcell == null)
			grtablelistcell = new Vector<GRTableListCell>();
		else {
			xLeft = grtablelistcell.lastElement().getLeft() + grtablelistcell.lastElement().getWidth();
			index = grtablelistcell.size();
		}
		
		grtablelistcell.add(new GRTableListCell(index,xLeft,0,wCell,this.height,this));
		column++;
	}
	public GRTableList getTableList() {
		return grfather;
	}
	public Vector<GRTableListCell> getCell() {
		return grtablelistcell;
	}
	public GRTableListCell getCellColumn(int index) {
		/* Ritorna la cella presente nella sezione con l'indice collegato alla colonna (se presente) */
		for(int i = 0;i < grtablelistcell.size();i++) {
			if(grtablelistcell.get(i).getIndexColumn() == index) {
				
				return grtablelistcell.get(i);
			}
		}
		
		return null;
	}
	public int getLeft() {
		return grfather.getX();
	}
	public void setTop(int value) {
		top = value;
	}
	public int getTop() {
		return grfather.getY()+top;
	}
	public void mergeCell(GRTableListCell refCell) {
		if(refCell.getIndex() + 1 == column)
			return;	// Se è l'ultima cella della colonna non fa nulla
		
		// Recupera la cella successiva con cui effettuare il merge
		GRTableListCell cellDX = grtablelistcell.get(refCell.getIndex() + 1);
		
		refCell.setWidth(refCell.getWidth() + cellDX.getWidth());
		refCell.addColumns();
		
		grtablelistcell.remove(cellDX.getIndex());
		
		// Aggiorna gli indici di tutte le celle
		for(int i = 0;i < grtablelistcell.size();i++) {
			grtablelistcell.get(i).setIndex(i);
		}
		
		column--;
		
	}
	public int getColumnLeft(int index) {
		return grfather.getColLeft(index);
	}
	public int getColumnWidth(int index) {
		return grfather.getColDimension(index);
	}
	public void setWidth(int value) {
		/* Cicla per tutte le celle. Per ogni cella:
		 * 1) Ridimensiona la cella
		 * 2) Ne modifica il left
		 */
		int left = 0;
		for(int i = 0;i < grtablelistcell.size();i++) {
			GRTableListCell refCell = grtablelistcell.get(i);
			int wCell = refCell.getWidth();
			
			int newWCell = (wCell * value) / grfather.getWidth();
			
			refCell.setWidth(newWCell);
			refCell.setLeft(left);
			
			left = left + newWCell;
		}
	}
	public AlphaComposite getComposite() {
		return grfather.getComposite();
	}
	public void setWidthStroke(double value) {
		widthStroke = value;
		
		typeStroke = new BasicStroke((float)(widthStroke * 2));
	}
	public double getWidthStroke() {
		return widthStroke;
	}
	public BasicStroke getStroke() {
		return typeStroke;
	}
	public void setColorStroke(Color c) {
		cStroke = c;
	}
	public Color getColorStroke() {
		return cStroke;
	}
	public void setColorFill(Color c) {
		cFill = c;
	}
	public Color getColorFill() {
		return cFill;
	}
	public void setHeight(int value) {
		if(value > height)
			height = value;
		
		grfather.refreshSection();
	}
	public int getHeight() {
		return height;
	}
	public void setMinHeight(int value) {
		boolean trovato = false;
		//if(value > height)
		//	height = value;
		
		minHeight = value;
		
		/* Cicla per tutte le celle per vedere se è possibile riadattare l'altezza della sezione */
		for(int i = 0;i < grtablelistcell.size();i++)
			if( grtablelistcell.get(i).getHeight() > value) {// Se anche solo una cella è più grande del valore non modifica nulla
				trovato = true;
				
				break;
			}
		
		if(!trovato) {
			height = value;
		}
		grfather.refreshSection();
	}
	public int getMinHeight() {
		return minHeight;
	}
	public void draw(Graphics2D g2d) {
		for(int i = 0;i < grtablelistcell.size();i++) {
			grtablelistcell.get(i).draw(g2d);
		}
	}
	public boolean isCellIntersect(int x, int y) {
		if(cellSelected != null)
			cellSelected.setSelected(false);
		
		for(int i = 0;i < grtablelistcell.size();i++)
			if(grtablelistcell.get(i).isEntry(x,y)) {
				
				cellSelected = grtablelistcell.get(i);
				cellSelected.setSelected(true);
				indexCellSelected = i;
				
				return true;
			}

		indexCellSelected = -1;
		cellSelected = null;
		return false;
	}
	public int getIndexCellSelected() {
		return indexCellSelected;
	}
	public boolean isFirstCellSelected() {
		if(indexCellSelected == 0)
			return true;
		
		return false;
	}
	public boolean isLastCellSelected() {
		if((indexCellSelected + 1) == column)
			return true;
		
		return false;
	}
	public GRTableListCell getCellSelected() {
		return cellSelected;
	}
	public void deselect() {
		if(cellSelected != null) 
			cellSelected.setSelected(false);
		
		cellSelected = null;
	}
	public GRTableListText getTextCellSelected() {
		if(cellSelected == null)
			return null;
		
		return cellSelected.getText();
	}
	public Color getColorCell() {
		return colorSection;
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("<widthstroke>"+widthStroke+"</widthstroke>\n");
		buff.append("<colorstroke>"+cStroke.getRed()+" "+cStroke.getGreen()+" "+cStroke.getBlue()+"</colorstroke>\n");
		buff.append("<colorfill>");
		if(cFill == null)
			buff.append("transparent");
		else
			buff.append(cFill.getRed()+" "+cFill.getGreen()+" "+cFill.getBlue());
		buff.append("</colorfill>\n");
		buff.append("<minheight>"+GRLibrary.fromPixelsToMillimeters(minHeight)+"</minheight>\n");
		buff.append("<column>"+column+"</column>\n");
		
		for(int i = 0;i < grtablelistcell.size();i++) {
			GRTableListCell grcell = grtablelistcell.get(i);
			
			buff.append(grcell.createCodeGRS());
		}
		
		return buff.toString();
	}
}
