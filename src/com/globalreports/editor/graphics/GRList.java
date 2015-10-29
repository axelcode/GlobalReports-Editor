/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRList
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Vector;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModelImage;
import com.globalreports.editor.designer.property.GRTableModelList;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.tools.GRLibrary;

public class GRList extends GRObject {
	private AlphaComposite composite;	// Canale Alpha per la trasparenza degli oggetti
	
	private String nameXml;
	private int yRelative;
	
	private GRTableModelList modelTable;
	
	public GRList(GRPage grpage, long id) {
		super(GRObject.TYPEOBJ_LIST,id,grpage);
		
		nameXml = "list"+id;
		
		y1 = 0;
		width = grpage.getWidth();
		height = 0;
		
		y1Original = y1;
		heightOriginal = height;
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f);
		this.refreshReferenceSection();		
	}

	public GRList(GRPage grpage, long id, int yStart, int yEnd) {
		super(GRObject.TYPEOBJ_LIST,id,grpage);
		
		nameXml = "list"+id;
		
		width = grpage.getWidth();
		
		if(yStart < yEnd) {
			y1 = yStart;
			height = yEnd - yStart;
		} else {
			y1 = yEnd;
			height = yStart - yEnd;
		}
		
		y1Original = y1;
		heightOriginal = height;
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f);
		this.refreshReferenceSection();		
		
	}
	public void setNameXml(String nameXml) {
		this.nameXml = nameXml;
	}
	public String getNameXml() {
		return nameXml;
	}
	public GRList clone(long id) {
		return null;
	}
	public void setZoom(float value) {
		super.setZoom(value);
		
		width = grpage.getWidth();
		x1 = 0;
		
		
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		int hGap = 0;
		int y1 = this.y1;
		
		if(hPosition) {
			hGap = hGap + grpage.hPosition;
			gapH = hGap;
		}
		
		y1 = y1 + hGap;
		
		Color oldC = g2d.getColor();
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setColor(Color.BLUE);
		Composite compositeOld = g2d.getComposite();
		
		if(selected) {
			g2d.setStroke(new BasicStroke(2.0f));
		}
		g2d.drawRect(0,y1,width,height);
		
		g2d.setComposite(composite);
		g2d.setPaint(Color.BLUE);
		g2d.fill(new Rectangle(0,y1,width,height));
		
		g2d.setComposite(compositeOld);
		g2d.setColor(oldC);
		g2d.setStroke(oldStroke);
		
		yRelative = y1;
		grpage.hPosition = y1 + height;
	}
	public void setProperty(GRTable model) {
		this.modelTable = (GRTableModelList)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return ;
		
		modelTable.setNameXml(this.getNameXml());
		modelTable.setTop(this.getOriginalY());
		modelTable.setHeight(this.getOriginalHeight());
		
	}
	public String createCodeGRS() {
		Vector<GRObject> child = grpage.getListChild(nameXml);
		
		if(child == null)
			return "";
		
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1Original;
		
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		
		buff.append("<list>\n");
		buff.append("<id>"+nameXml+"</id>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<height>"+GRLibrary.fromPixelsToMillimeters(heightOriginal)+"</height>\n");
		buff.append("<hposition>");
		if(hPosition)
			buff.append("relative");
		else
			buff.append("absolute");
		buff.append("</hposition>\n");
		
		buff.append("<row>\n");
		
		for(int i = 0;i < child.size();i++)
			buff.append(child.get(i).createCodeGRS()+"\n");
		
		buff.append("</row>\n");
		
		buff.append("</list>");
		
		return buff.toString();
	}
	public int getTopPosition() {
		
		return yRelative;
	}
}
