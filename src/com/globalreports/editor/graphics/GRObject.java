/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRObject
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

import java.awt.Graphics;
import java.awt.Rectangle;

import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.swing.table.GRTable;

public abstract class GRObject {
	public static final int DIM_ANCHOR			= 4;
	
	public static final int ANCHOR_TSX			= 1;
	public static final int ANCHOR_TDX			= 2;
	public static final int ANCHOR_BSX			= 3;
	public static final int ANCHOR_BDX			= 4;
	
	public static final int TYPEOBJ_NOTHING		= 0;
	public static final int TYPEOBJ_TEXT		= 1;
	public static final int TYPEOBJ_LINE		= 2;
	public static final int TYPEOBJ_RECTANGLE	= 3;
	public static final int TYPEOBJ_IMAGE		= 4;
	public static final int TYPEOBJ_LIST		= 5;
	public static final int TYPEOBJ_TABLELIST	= 6;
	public static final int TYPEOBJ_CIRCLE		= 7;
	public static final int TYPEOBJ_GROUP		= 8;
	
	public static final int TYPEOBJ_CHART		= 100;
	
	public static final int SECTION_HEADER		= 1;
	public static final int SECTION_BODY		= 2;
	public static final int SECTION_FOOTER		= 3;
	
	protected long id;
	protected int typeModel;
	
	protected int x1Original;
	protected int y1Original;
	protected int widthOriginal;
	protected int heightOriginal;
	protected int y1Parent;
	protected int x1;
	protected int y1;
	protected int width;
	protected int height;
	protected int gapH;
	
	protected boolean hPosition;
	protected int section;			// Definisce in quale delle tre sezioni è inserito l'oggetto
	
	// Coordinate di selezione dell'oggetto
	protected int xSel;
	protected int ySel;
	
	protected int type;
	protected boolean selected;
	
	// Ancore
	protected Rectangle tsx;
	protected Rectangle tdx;
	protected Rectangle bsx;
	protected Rectangle bdx;
	
	protected float zoom;
	
	// La pagina ove è inserito l'oggetto
	protected GRPage grpage;
	
	// Puntatore all'oggetto GRGroup
	protected GRGroup grgroup;
	
	// Puntatore all'oggetto GRList
	protected GRList grlistFather;
	
	public GRObject(int type, long id, GRPage grpage) {
		this.type = type;
		this.id = id;
		this.grpage = grpage;
		
		x1 = 0;
		y1 = 0;
		width = 0;
		height = 0;
		hPosition = false;
		
		zoom = 1.0f;
		y1Parent = 0;
		
		selected = false;
	}
	public GRObject(int type, long id) {
		this(type, id, null);
	}
	
	public long getId() {
		return id;
	}
	public void setX(int x) {
		x1 = (int)(x * zoom);
		this.setOriginalX(x1);	
	}
	public int getX() {
		return x1;
	}
	public void setOriginalX(int x) {
		x1Original = (int)(x / zoom);
		
		refreshProperty();
	}
	public int getOriginalX() {
		return x1Original;
	}
	public void setY(int y) {
		y1 = (int)(y * zoom);
		this.setOriginalY(y1);
		
		this.refreshReferenceSection();
	}
	public int getY() {
		return y1;
	}
	public void setOriginalY(int y) {
		y1Original =(int)(y / zoom);
		
		refreshProperty();
	}
	public int getOriginalY() {
		return y1Original;
	}
	public void setWidth(int width) {
		this.widthOriginal = width;
		this.width = width;
		
		refreshProperty();
	}
	public int getWidth() {
		return width;
	}
	public int getOriginalWidth() {
		return widthOriginal;
	}
	public void setHeight(int height) {
		this.heightOriginal = height;
		this.height = height;
		
		refreshProperty();
	}
	public int getHeight() {
		return height;
	}
	public int getOriginalHeight() {
		return heightOriginal;
	}
	public void setHPosition(String value) {
		if(value.equals("absolute"))
			this.hPosition = false;
		else if(value.equals("relative"))
			this.hPosition = true;
	}
	public void setHPosition(boolean value) {
		this.hPosition = value;
		
	}
	public boolean getHPosition() {
		return hPosition;
	}
	public String getHPositionToString() {
		if(hPosition)
			return "relative";
		
		return "absolute";
	}
	public void setListFather(GRList grlist) {
		this.grlistFather = grlist;
		
		if(grlistFather != null) {
			y1Parent = y1;
			
			this.setY(0);
			//y1 = this.setY(0);
			grpage.refreshObjectList(grlist, this);
		} else {
			if(y1Parent != 0) {
				//y1 = y1Parent;
				this.setY(y1Parent);
				
				y1Parent = 0;
			}
		}
	}
	public void setListFather(String name) {
		this.setListFather(grpage.getList(name));
	}
	public GRList getListFather() {
		return grlistFather;
	}
	public boolean hasListFather() {
		if(grlistFather == null)
			return false;
		
		return true;
	}
	public void setGroup(GRGroup grgroup) {
		this.grgroup = grgroup;
		
		//setY(y1 - grgroup.getY() - grgroup.gapH);
		
		
	}
	public void resize(int xStart, int yStart, int xEnd, int yEnd) {
		x1 = xStart;
		y1 = yStart;
		width = xEnd - xStart;
		height = yEnd - yStart;
	}
	public void setLocation(int x, int y) {
		x1 = x;
		y1 = y;
		
		this.refreshReferenceSection();			
	}
	public void refreshReferenceSection() {
		if(type == GRObject.TYPEOBJ_LINE) {
			GRLine refObj = (GRLine)this;
			refObj.refreshReferenceSection();
		} else {
			if(grpage != null && grlistFather == null) {
				if(y1 < grpage.getHeaderSize()) {
					this.setSection(GRObject.SECTION_HEADER);
				} else if(y1 > (grpage.getHeight() - grpage.getFooterSize())) {
					this.setSection(GRObject.SECTION_FOOTER);
				} else {
					this.setSection(GRObject.SECTION_BODY);
				}
			}
		}
	}
	public void refresh() {
		grpage.repaint();
	}
	public void setCoordsSelected(int x, int y) {
		xSel = x;
		ySel = y;
	}
	public void setSelected(boolean value) {
		this.selected = value;
	}
	public boolean getSelected() {
		return selected;
	}
	public boolean isIntersect(int coordX, int coordY) {
			
		int hGap = 0;
		if(hPosition)
			hGap = gapH;
		
		if(grlistFather != null)
			hGap = grlistFather.getY();
		
		if(coordX >= x1 && coordX <= (x1 + width)) 
			if(coordY >= (y1+hGap) && coordY <= (y1 + height + hGap))
				return true;
				
		return false;
	}
	public boolean isAreaIntersect(Rectangle r) {
		boolean c = r.intersects(new Rectangle(this.x1,this.y1,this.width,this.height));
		
		return c;

	}
	public int isAnchor(int coordX, int coordY) {
		if(tsx != null && tsx.contains(coordX,coordY))
			return ANCHOR_TSX;
		else if(tdx != null && tdx.contains(coordX,coordY))
			return ANCHOR_TDX;
		else if(bsx != null && bsx.contains(coordX,coordY))
			return ANCHOR_BSX;
		else if(bdx != null && bdx.contains(coordX,coordY))
			return ANCHOR_BDX;
			
		return 0;
		/*
		if(coordX >= (x1+width) && coordX <= (x1+width+4)) 
			if(coordY >= (y1+height) && coordY <= (y1 + height+4))
				return true;
				
		return false;
		*/
	}
	public void translateX(int x) {
		x1 = x1 + x;
		
		if(this instanceof GRLine) {
			GRLine refLine = (GRLine)this;
			refLine.setXEnd(refLine.getXEnd() + x);
		}
		xSel = xSel + x;
		
		this.setOriginalX(x1);
	}	
	public void translateY(int y) {
		
		y1 = y1 + y;
		
		if(this instanceof GRLine) {
			GRLine refLine = (GRLine)this;
			refLine.setYEnd(refLine.getYEnd() + y);
		} 
		ySel = ySel + y;
		
		this.setOriginalY(y1);			
	}
	
	public void moveTo(int x, int y) {
		
		x1 = x1 + (x - xSel);
		y1 = y1 + (y - ySel);
		
		if(this instanceof GRLine) {
			GRLine refLine = (GRLine)this;
			refLine.setXEnd(refLine.getXEnd() + (x - xSel));
			refLine.setYEnd(refLine.getYEnd() + (y - ySel));
		}
		xSel = x;
		ySel = y;
	}
	public int getType() {
		return type;
	}
	public void setSection(int value) {
		this.section = value;
	}
	public int getSection() {
		return section;
	}
	public void setZoom(float value) {
		zoom = value;
		
		x1 = (int)(x1Original * value);
		y1 = (int)(y1Original * value);
		width = (int)(widthOriginal * value);
		height = (int)(heightOriginal * value);
		
	}
	
	/* Metodi astratti */
	public void refreshProperty() {
		//System.out.println("OBJECT::refreshProperty");
	}
	public abstract void setProperty(GRTable model);
	public abstract int getTypeModel();
	public abstract String getNameObject();
	public abstract void draw(Graphics g);
	public abstract GRObject clone(long id);
	public abstract String createCodeGRS();
	
}