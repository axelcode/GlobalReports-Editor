/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRLine
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModel;
import com.globalreports.editor.designer.property.GRTableModelLine;
import com.globalreports.editor.designer.property.GRTableModelRectangle;
import com.globalreports.editor.tools.GRLibrary;

public class GRLine extends GRShape {
	private int x2;
	private int y2;
	private int x2Original;
	private int y2Original;
	
	private GRTableModelLine modelTable;
	
	public GRLine(GRPage grpage, long id) {
		this(grpage,id,0,0,0,0,Color.BLACK);
	}
	public GRLine(GRPage grpage, long id, int x1, int y1, int x2, int y2, Color colorStroke) {
		super(GRObject.TYPEOBJ_LINE,id,grpage);
		
		this.grpage = grpage;
		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		hPosition = false;
	
		x1Original = x1;
		y1Original = y1;
		x2Original = x2;
		y2Original = y2;
		
		// Crea le ancore
		tsx = new Rectangle(x1-4,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(x2,y2-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		
		cStroke = colorStroke;
		
		this.refreshReferenceSection();	
	}
	public void refreshReferenceSection() {
		if(grpage != null) {
			if(y1 <= grpage.getHeaderSize() || y2 <= grpage.getHeaderSize()) {
				this.setSection(GRObject.SECTION_HEADER);
			} else {
				this.setSection(GRObject.SECTION_BODY);
			}
		}
	}
	public boolean isIntersect(int coordX, int coordY) {
		double m = ((double)(y2 - y1) / (double)(x2 - x1));
		double denom = 1 + (m * m);
		double d = (coordY - (m * coordX) - y1 + (m * x1)) / Math.sqrt(denom);
		
		if(d >= -5.0 && d <= 5.0)
			return true;
		
		return false;
	}
	public GRLine clone(long id) {
		int newX1 = x1 + 15;
		int newY1 = y1 + 15;
		int newX2 = x2 + 15;
		int newY2 = y2 + 15;
		
		GRLine grclone = new GRLine(grpage,id,newX1,newY1,newX2,newY2,cStroke);
		
		return grclone;
	}
	
	public void setZoom(float value) {
		super.setZoom(value);
		
		x2 = (int)(x2Original * value);
		y2 = (int)(y2Original * value);
		
		setWidthStroke(widthStrokeOriginal);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Color oldColor = g2d.getColor();
		Stroke s = g2d.getStroke();
		
		g2d.setColor(cStroke);
		g2d.setStroke(typeStroke);
		g2d.drawLine(x1,y1,x2,y2);
		
		g2d.setStroke(s);
		g2d.setColor(oldColor);
		
		if(selected) {
			if(x2 > x1) {
				if(y2 > y1) {
					g.fillRect(x1-4,y1-4,4,4);
					g.fillRect(x2,y2,4,4);
				} else {
					g.fillRect(x1-4,y1,4,4);
					g.fillRect(x2,y2-4,4,4);
				}
			} else {
				if(y2 > y1) {
					g.fillRect(x1,y1-4,4,4);
					g.fillRect(x2-4,y2,4,4);
				} else {
					g.fillRect(x1,y1,4,4);
					g.fillRect(x2-4,y2-4,4,4);
				}
			}
			
		}
	}

	public void setProperty(GRTableModel model) {
		this.modelTable = (GRTableModelLine)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return;
		
		modelTable.setX1(this.getOriginalX());
		modelTable.setY1(this.getOriginalY());
		modelTable.setX2(this.getOriginalXEnd());
		modelTable.setY2(this.getOriginalYEnd());
		modelTable.setWidthStroke(this.getWidthStroke());
		modelTable.setColorStroke(this.getColorStroke().getRed(), this.getColorStroke().getGreen(),this.getColorStroke().getBlue());
		
	}
	public void setXEnd(int value) {
		x2 = (int)(value * zoom);
		this.setOriginalXEnd(x2);	
		
	}
	public int getXEnd() {
		return x2;
	}
	public void setOriginalXEnd(int x) {
		x2Original = (int)(x / zoom);
	}
	public int getOriginalXEnd() {
		return x2Original;
	}
	public void setYEnd(int value) {
		y2 = (int)(value * zoom);
		this.setOriginalYEnd(y2);
		
		this.refreshReferenceSection();
	}
	public int getYEnd() {
		return y2;
	}
	public void setOriginalYEnd(int y) {
		y2Original = (int)(y / zoom);
	}
	public int getOriginalYEnd() {
		return y2Original;
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1;
		int y2 = this.y2;
		
		if(section == GRObject.SECTION_BODY) {
			y1 = y1 - grpage.getHeaderSize();
			y2 = y2 - grpage.getHeaderSize();
		}
		
		buff.append("<shape>\n");
		buff.append("<type>line</type>\n");
		buff.append("<x1>"+GRLibrary.fromPixelsToMillimeters(x1Original)+"</x1>\n");
		buff.append("<y1>"+GRLibrary.fromPixelsToMillimeters(y1Original)+"</y1>\n");
		buff.append("<x2>"+GRLibrary.fromPixelsToMillimeters(x2Original)+"</x2>\n");
		buff.append("<y2>"+GRLibrary.fromPixelsToMillimeters(y2Original)+"</y2>\n");
		buff.append("<colorstroke>"+colorStrokeRED+" "+colorStrokeGREEN+" "+colorStrokeBLUE+"</colorstroke>\n");
		buff.append("<widthstroke>"+widthStrokeOriginal+"</widthstroke>\n");
		buff.append("</shape>");
		
		return buff.toString();
	}
	
}
