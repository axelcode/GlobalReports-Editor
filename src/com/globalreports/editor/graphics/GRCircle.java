/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRCircle
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
import java.awt.Shape;
import java.awt.Stroke;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModel;
import com.globalreports.editor.designer.property.GRTableModelCircle;
import com.globalreports.editor.designer.property.GRTableModelRectangle;
import com.globalreports.editor.designer.property.GRTableProperty;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.tools.GRLibrary;

public class GRCircle extends GRShape {
	private int colorFillRED;
	private int colorFillGREEN;
	private int colorFillBLUE;
	private Color cFill;
	
	private int raggio;
	
	private GRTableModelCircle modelTable;
	
	public GRCircle(GRPage grpage, long id) {
		this(grpage, id, 0, 0, 0, 0, Color.BLACK, null);
	}
	public GRCircle(GRPage grpage, long id, int xStart, int yStart, int xEnd, int yEnd, Color colorStroke, Color colorFill) {
		super(GRObject.TYPEOBJ_CIRCLE,id,grpage);
		
		int x1,y1,x2,y2;
		int width,height;
		
		if(xStart > xEnd) {
			x1 = xEnd;
			x2 = xStart;
		} else {
			x1 = xStart;
			x2 = xEnd;
		}
		width = x2 - x1;
			
		if(yStart > yEnd) {
			y1 = yEnd;
			y2 = yStart;
		} else {
			y1 = yStart;
			y2 = yEnd;
		}
		height = y2 - y1;
		
		if(width > height) {
			raggio = width;
		} else {
			raggio = height;
		}
		this.x1 = xStart - raggio;
		this.y1 = yStart - raggio;
		this.width = raggio * 2;
		this.height = raggio * 2;
		
		/*
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
		*/
		x1Original = this.x1;
		y1Original = this.y1;
		widthOriginal = this.width;
		heightOriginal = this.height;
		
		// Crea le ancore
		tsx = new Rectangle(this.x1-4,this.y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(this.x1+this.width,this.y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx = new Rectangle(this.x1-4,this.y1+this.height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx = new Rectangle(this.x1+this.width,this.y1+this.height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		
		cStroke = colorStroke;
		cFill = colorFill;
		
		typeModel = GRTableProperty.TYPEMODEL_CIRCLE;
		this.refreshReferenceSection();			
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Color oldStroke = g2d.getColor();
		Shape oldClip = g2d.getClip();
		
		Stroke s = g2d.getStroke();
		int y1 = this.y1;
		
		/* Se è figlio di una lista imposta la clipArea */
		if(grlistFather != null) {
			g2d.setClip(0,grlistFather.getY(),grpage.getWidth(),grlistFather.getHeight()+1);
			y1 = grlistFather.getY() + this.y1;
		}
		
		if(cFill != null) {
			g2d.setColor(cFill);
			g2d.fillOval(x1, y1, raggio * 2, raggio * 2);
		}
		
		// Disegna il contorno esterno
		g2d.setColor(cStroke);
		g2d.setStroke(typeStroke);
		
		g2d.drawOval(x1,y1,raggio * 2, raggio * 2);
		
		// Ripristina le info originali
		g2d.setStroke(s);
		g2d.setColor(oldStroke);
		g2d.setClip(oldClip);
		
		if(selected) {
			g.fillRect(x1-4,y1-4,4,4);
			g.fillRect(x1-4,y1+raggio * 2,4,4);
			g.fillRect(x1+raggio * 2,y1-4,4,4);
			g.fillRect(x1+raggio * 2,y1+raggio * 2,4,4);
		}
		
	}
	
	public void setProperty(GRTable model) {
		this.modelTable = (GRTableModelCircle)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return ;
		
		modelTable.setX(this.getOriginalX());
		modelTable.setY(this.getOriginalY());
		modelTable.setRadius(this.getRadius());
		modelTable.setWidthStroke(this.getWidthStroke());
		modelTable.setColorStroke(this.getColorStroke().getRed(), this.getColorStroke().getGreen(), this.getColorStroke().getBlue());
		
		if(this.getColorFill() == null)
			modelTable.setColorFill(-1, -1, -1);
		else
			modelTable.setColorFill(this.getColorFill().getRed(), this.getColorFill().getGreen(), this.getColorFill().getBlue());
	
	}
	public int getColorFillRED() {
		return colorFillRED;
	}
	public int getColorFillGREEN() {
		return colorFillGREEN;
	}
	public int getColorFillBLUE() {
		return colorFillBLUE;
	}
	public Color getColorFill() {
		return cFill;
	}
	public void setColorFill(int red, int green, int blue) {
		colorFillRED = red;
		colorFillGREEN = green;
		colorFillBLUE = blue;
		
		cFill = new Color(red,green,blue);
	}
	public void setColorFill(Color c) {
		if(c == null) {
			colorFillRED = -1;
			colorFillGREEN = -1;
			colorFillBLUE = -1;
		} else {
			colorFillRED = c.getRed();
			colorFillGREEN = c.getGreen();
			colorFillBLUE = c.getBlue();
		}
		cFill = c;
	}
	public void setColorFill(String c) {
		if(c.equals("transparent")) {
			colorFillRED = -1;
			colorFillGREEN = -1;
			colorFillBLUE = -1;
			
			cFill = null;
		} else {
			String token[] = c.split(" ");
			
			colorFillRED = Integer.parseInt(token[0]);
			colorFillGREEN = Integer.parseInt(token[1]);
			colorFillBLUE = Integer.parseInt(token[2]);
			
			cFill = new Color(colorFillRED,colorFillGREEN,colorFillBLUE);
		}
			
	}
	public void setXRadius(int value) {
		super.setX(value - raggio);
	}
	public void setYRadius(int value) {
		super.setY(value - raggio);
	}
	public void setRadius(int rad) {
		this.raggio = rad;
	}
	public int getRadius() {
		return raggio;
	}
	public void resize(int xStart,int yStart, int xEnd, int yEnd) {
		super.resize(xStart,yStart,xEnd,yEnd);
		
		refreshAnchor();
	}
	public void setLocation(int x, int y) {
		super.setLocation(x,y);
		
		refreshAnchor();
	}
	public boolean isIntersect(int coordX, int coordY) {
		double catetoX = Math.abs((coordX - (x1 + raggio)));
		double catetoY = Math.abs((coordY - (y1 + raggio)));
		double d = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
		
		if(d <= raggio)
			return true;
		
		return false;
	}
	private void refreshAnchor() {
		tsx.setBounds(x1-4,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx.setBounds(x1+width,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx.setBounds(x1-4,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx.setBounds(x1+width,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
	}
	public GRRectangle clone(long id) {
		int newX = x1;
		int newY = y1+height;
		
		GRRectangle grclone = new GRRectangle(grpage,id,newX,newY,newX+width,newY+height,cStroke,cFill);
		
		return grclone;
	}
	public void setZoom(float value) {
		super.setZoom(value);
		
		setWidthStroke(widthStrokeOriginal);
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		
		int y1 = this.y1Original;
		
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		if(section == GRObject.SECTION_FOOTER) {
			y1 = y1 - (grpage.getHeight() - grpage.getFooterSize());
		}
		
		buff.append("<shape>\n");
		buff.append("<type>circle</type>\n");
		buff.append("<x>"+GRLibrary.fromPixelsToMillimeters(x1Original+raggio)+"</x>\n");
		buff.append("<y>"+GRLibrary.fromPixelsToMillimeters(y1+raggio)+"</y>\n");
		buff.append("<radius>"+GRLibrary.fromPixelsToMillimeters(raggio)+"</radius>\n");
		buff.append("<colorstroke>"+cStroke.getRed()+" "+cStroke.getGreen()+" "+cStroke.getBlue()+"</colorstroke>\n");
		buff.append("<colorfill>");
		if(cFill == null)
			buff.append("transparent");
		else
			buff.append(cFill.getRed()+" "+cFill.getGreen()+" "+cFill.getBlue());
		buff.append("</colorfill>\n");
		buff.append("<widthstroke>"+widthStrokeOriginal+"</widthstroke>\n");
		buff.append("</shape>");
		
		return buff.toString();
	}
	@Override
	public String getNameObject() {
		return GRLanguageMessage.messages.getString("tlbgrcircle");
	}
	public int getTypeModel() {
		return typeModel;
	}
	
}