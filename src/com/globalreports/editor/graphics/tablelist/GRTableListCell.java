/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.tablelist.GRTableListCell
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
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import com.globalreports.editor.designer.resources.GRColor;
import com.globalreports.editor.graphics.GRRectangle;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.tools.GRLibrary;

public class GRTableListCell {
	private boolean selected;
	
	private int index;		// Indice della cella all'interno della colonna
	private int indexColumn;
	private int x;
	private int y;
	private int width;
	private int height;
	private int totColumn;	// Numero totale di colonne occupate dalla cella
	private int marginLeft;
	private int marginTop;
	private int marginRight;
	private int marginBottom;
	private Color colorStroke;
	private Color colorFill;
	
	private GRTableListSection grsection;
	private GRTableListText grtext;
		
	public GRTableListCell(int index, int x, int y, int width, int height, GRTableListSection grsection) {
		this.index = index;
		this.indexColumn = index;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.colorStroke = null;
		this.colorFill = null;
		
		this.grsection = grsection;
		grtext = null;

		totColumn = 1;
		
		marginLeft = 0;
		marginTop = 0;
		marginRight = 0;
		marginBottom = 0;
		
		selected = false;
	}
	
	public void refresh() {
		grsection.refresh();
	}
	public void setIndex(int value) {
		index = value;
	}
	public int getIndex() {
		return index;
	}
	public int getIndexColumn() {
		return indexColumn;
	}
	public void addColumns() {
		totColumn++;
	}
	public int getNumColumns() {
		return totColumn;
	}
	public void setMarginLeft(int value) {
		marginLeft = value;
	}
	public int getMarginLeft() {
		return marginLeft;
	}
	public void setMarginTop(int value) {
		marginTop = value;
		
		if(grtext == null)
			grsection.setHeight(marginTop+marginBottom);
		else
			grsection.setHeight(grtext.getHeight()+grtext.getTop()+marginTop+marginBottom);
	}
	public int getMarginTop() {
		return marginTop;
	}
	public void setMarginRight(int value) {
		marginRight = value;
	}
	public int getMarginRight() {
		return marginRight;
	}
	public void setMarginBottom(int value) {
		marginBottom = value;
		
		if(grtext == null)
			grsection.setHeight(marginTop+marginBottom);
		else
			grsection.setHeight(grtext.getHeight()+grtext.getTop()+marginTop+marginBottom);
	}
	public int getMarginBottom() {
		return marginBottom;
	}
	public void refreshHeightSection() {
		if(grtext == null)
			grsection.setHeight(marginTop+marginBottom);
		else
			grsection.setHeight(grtext.getHeight()+grtext.getTop()+marginTop+marginBottom);
	}
	public void setWidth(int value) {
		width = value;
		
		if(grtext != null) {
			grtext.setWidth(value);
		}
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		if(grtext == null)
			return marginTop+marginBottom;
		else
			return grtext.getHeight()+grtext.getTop()+marginTop+marginBottom;
		
	}
	
	public void setLeft(int value) {
		x = value;
		
		if(grtext != null) {
			grtext.setX(value);
		}
					
	}
	public int getLeft() {
		return x;
	}
	public boolean isEntry(int xCoord, int yCoord) {
		int lColumn = grsection.getColumnLeft(indexColumn);
		int wColumn = 0;
		for(int i = 0;i < totColumn;i++)
			wColumn = wColumn + grsection.getColumnWidth(indexColumn+i);
		
		if(xCoord >= (grsection.getLeft()+lColumn) && xCoord <= ((grsection.getLeft()+lColumn) + wColumn))
			if(yCoord >= (grsection.getTop()+y) && yCoord <= ((grsection.getTop()+y) + grsection.getHeight()))
				return true;
				
		return false;
	}
	public void setSelected(boolean value) {
		selected = value;
	}
	public Rectangle getCellArea() {
		int lColumn = grsection.getColumnLeft(indexColumn);
		
		return new Rectangle(lColumn,y,width,height);
	}
	public void setText(GRTableListText grtext) {
		this.grtext = grtext;
		
		this.grtext.setCellFather(this);
		
		grsection.setHeight(grtext.getHeight()+marginTop+marginBottom);
	}
	public GRTableListText getText() {
		return grtext;
	}
	public GRTableListSection getFatherSection() {
		return grsection;
	}
	public int getLeftAbsolute() {
		return grsection.getLeft()+x;
	}
	public int getTopAbsolute() {
		return grsection.getTop()+y;
	}
	public int getLeftSection() {
		return grsection.getLeft();
	}
	public int getTopSection() {
		return grsection.getTop();
	}
	public void setColorStroke(Color c) {
		colorStroke = this.verifyColor(c);
		
	}
	public Color getColorStroke() {
		if(colorStroke == null)
			return grsection.getColorStroke();
		
		return colorStroke;
	}
	public void setColorFill(Color c) {
		colorFill = this.verifyColor(c);
		
	}
	private Color verifyColor(Color c) {
		if(c == null)
			return null;
		
		Color section = grsection.getColorStroke();
		
		if(section == null)
			return c;
		else {
			if(c.getRed() == section.getRed() &&
			   c.getGreen() == section.getGreen() &&
			   c.getBlue() == section.getBlue()) {
				return null;
			} else {
				return c;
			}
		}
	}
	public Color getColorFill() {
		if(colorFill == null)
			return grsection.getColorFill();
		
		return colorFill;
	}
	public void draw(Graphics2D g2d) {
		Stroke oldStroke = g2d.getStroke();
		Color oldColorStroke = g2d.getColor();
		Composite oldComposite = g2d.getComposite();
		
		g2d.setStroke(grsection.getStroke());
		
		int wColumn = 0;
		for(int i = 0;i < totColumn;i++)
			wColumn = wColumn + grsection.getColumnWidth(indexColumn+i);
		
		int lColumn = grsection.getColumnLeft(indexColumn);
			
		// Se è presente un riempimento disegna prima quello
		if(colorFill != null) {
			g2d.setColor(colorFill);
			g2d.fillRect(grsection.getLeft()+lColumn, grsection.getTop()+y, wColumn, grsection.getHeight());
		} else {
			// Se è presente un riempimento disegna prima quello
			if(grsection.getColorFill() != null) {
				g2d.setColor(grsection.getColorFill());
				g2d.fillRect(grsection.getLeft()+lColumn, grsection.getTop()+y, wColumn, grsection.getHeight());
			}
		}
		
		if(colorStroke != null) {
			g2d.setColor(colorStroke);
		} else {
			g2d.setColor(grsection.getColorStroke());
		}
		g2d.drawRect(grsection.getLeft()+lColumn, grsection.getTop()+y, wColumn, grsection.getHeight());
		
		if(selected) {
			g2d.setComposite(grsection.getComposite());
			g2d.setColor(grsection.getColorCell());
			g2d.fillRect(grsection.getLeft()+lColumn, grsection.getTop()+y, wColumn, grsection.getHeight());
			g2d.setComposite(oldComposite);
		}
		// Ripristina le info originali
		g2d.setColor(oldColorStroke);
		g2d.setStroke(oldStroke);
		
		// Se è presente un oggetto GRText lo disegna
		if(grtext != null)
			grtext.draw(g2d);
	}
	
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("<cell>\n");
		buff.append("<column>"+totColumn+"</column>\n");
		buff.append("<margincell>");
		buff.append("" + GRLibrary.fromPixelsToMillimeters(marginLeft) + " ");
		buff.append("" + GRLibrary.fromPixelsToMillimeters(marginTop) + " ");
		buff.append("" + GRLibrary.fromPixelsToMillimeters(marginRight) + " ");
		buff.append("" + GRLibrary.fromPixelsToMillimeters(marginBottom));
		buff.append("</margincell>\n");
		
		if(grtext != null) {
			buff.append(grtext.createCodeGRS());
		}
		
		if(colorStroke != null || colorFill != null) {
			int wColumn = 0;
			Color cStroke = grsection.getColorStroke();
			Color cFill = grsection.getColorFill();
			
			for(int i = 0;i < totColumn;i++)
				wColumn = wColumn + grsection.getColumnWidth(indexColumn+i);
			
			/* Aggiunge un GRRectangle alla cella */
			buff.append("<shape>\n");
			buff.append("<type>rectangle</type>\n");
			buff.append("<left>0.0</left>\n");
			buff.append("<top>0.0</top>\n");
			buff.append("<width>"+GRLibrary.fromPixelsToMillimeters(wColumn)+"</width>\n");
			buff.append("<height>"+GRLibrary.fromPixelsToMillimeters(grsection.getHeight())+"</height>\n");
			
			if(colorStroke != null) 
				cStroke = colorStroke;
			
			buff.append("<colorstroke>"+cStroke.getRed()+" "+cStroke.getGreen()+" "+cStroke.getBlue()+"</colorstroke>\n");
			
			if(colorFill != null)
				cFill = colorFill;
			
			buff.append("<colorfill>");
			if(cFill == null)
				buff.append("transparent");
			else
				buff.append(cFill.getRed()+" "+cFill.getGreen()+" "+cFill.getBlue());
			buff.append("</colorfill>\n");
			buff.append("<widthstroke>"+grsection.getWidthStroke()+"</widthstroke>\n");
			buff.append("</shape>");
		}
		
		buff.append("</cell>\n");
		
		return buff.toString();
	}
}
