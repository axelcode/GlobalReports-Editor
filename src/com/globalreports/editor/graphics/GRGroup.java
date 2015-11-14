/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRGroup
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
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.Vector;

import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModelGroup;
import com.globalreports.editor.designer.property.GRTableModelList;
import com.globalreports.editor.designer.property.GRTableProperty;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.tools.GRLibrary;

public class GRGroup extends GRObject {
	private AlphaComposite composite;	// Canale Alpha per la trasparenza degli oggetti
	private Color areaColor = new Color(140,9,9);
	private Font fontLabel = new Font("Tahoma",Font.BOLD,11);
	
	private Vector<GRObject> groupObject;
	private int yRelative;
	private String condition;
	
	private GRTableModelGroup modelTable;
	
	public GRGroup(GRPage grpage, long id) {
		this(grpage, id, 0, 0);
	}
	public GRGroup(GRPage grpage, long id, int yStart, int yEnd) {
		super(GRObject.TYPEOBJ_GROUP,id,grpage);
		
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
		
		groupObject = new Vector<GRObject>();
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f);
		
		typeModel = GRTableProperty.TYPEMODEL_GROUP;
		this.refreshReferenceSection();		
		
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		GeneralPath gp = new GeneralPath();
		
		int hGap = 0;
		int y1 = this.y1;
		
		if(hPosition) {
			hGap = hGap + grpage.hPosition;
			gapH = hGap;
		} else {
			gapH = 0;
		}
		
		y1 = y1 + hGap;
		
		Color oldC = g2d.getColor();
		Stroke oldStroke = g2d.getStroke();
		Font oldF = g2d.getFont();
		
		g2d.setColor(areaColor);
		Composite compositeOld = g2d.getComposite();
		
		if(selected) {
			g2d.setStroke(new BasicStroke(2.0f));
		}
		
		// Crea il path
		gp.moveTo(0,y1);
		gp.lineTo((double)(width-90), (double)(y1));
		
		//gp.lineTo((double)(width-75), (double)(y1-15));
		gp.curveTo((double)(width-65), (double)y1, (double)(width-85), (double)(y1-15), (double)(width-60), (double)(y1-15));
		
		gp.lineTo((double)(width), (double)(y1-15));
		gp.lineTo((double)(width), (double)(y1+height));
		gp.lineTo(0.0, (double)(y1+height));
		gp.closePath();
		g2d.draw(gp);
		
		g2d.setFont(fontLabel);
		g2d.drawString("GROUP", (float)(width-60), (float)(y1));
		
		
		g2d.setComposite(composite);
		g2d.fill(gp);
		
		// Ripristina ambiente
		g2d.setComposite(compositeOld);
		g2d.setColor(oldC);
		g2d.setStroke(oldStroke);
		g2d.setFont(oldF);
		
		// Cicla per tutti gli oggetti associati e li disegna
		for(int i = 0;i < groupObject.size();i++) {
			groupObject.get(i).draw(g);
		}
			
		yRelative = y1;
		grpage.hPosition = y1 + height;
	}
	public void addObject(GRObject grobj) {
		grobj.setGroup(this);
		
		// Allinea Y dell'oggetto a quello del gruppo
		//setY(y1 - grgroup.getY() - grgroup.gapH);
		grobj.setY(grobj.getY() - this.getY() - this.gapH);
			
			/* Allinea la dimensione verticale
			if(grgroup.getHeight() < (this.getY() + this.getHeight()))
				grgroup.setHeight(this.getY() + this.getHeight());
			*/

		groupObject.add(grobj);
	}
	public void addObjectFromGRS(GRObject grobj) {
		grobj.setGroup(this);
		
		groupObject.add(grobj);
	}
	public int getTotaleObject() {
		return groupObject.size();
	}
	public void setProperty(GRTable model) {
		this.modelTable = (GRTableModelGroup)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return ;
		
		modelTable.setTop(this.getOriginalY());
		modelTable.setCondition(condition);
		
	}
	public void setCondition(String value) {
		this.condition = value;
	}
	public String getCondition() {
		return condition;
	}
	public void setSelected(boolean value) {
		this.selected = value;
		
		if(!value) {
			// Prima cicla per tutti gli oggetti interni al gruppo
			for(int i = (groupObject.size()-1);i >= 0;i--) {
				groupObject.get(i).setSelected(false);
					
			}
		}
	}
	/* Permette la selezione degli oggetti interni al gruppo
	 * In questa versione gli oggetti possono essere modificati se separati dal gruppo
	 
	public boolean isIntersect(int coordX, int coordY) {
		int y1 = this.y1;
		boolean flagSelected = false;
		
		int hGap = 0;
		if(hPosition)
			hGap = gapH;
		
		y1 = y1 + hGap;
		
		// Prima cicla per tutti gli oggetti interni al gruppo
		for(int i = (groupObject.size()-1);i >= 0;i--) {
			if(groupObject.get(i).isIntersect(coordX, (coordY - y1))) {
				grpage.selectObject(groupObject.get(i));
				
				flagSelected = true;

				return false;
			}
		}
		
		if(coordX >= x1 && coordX <= (x1 + width)) 
			if(coordY >= y1 && coordY <= (y1 + height))
				return true;
				
		return false;
	}
	*/
	public GRGroup clone(long id) {
		return null;
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1Original;
		
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		if(section == GRObject.SECTION_FOOTER) {
			y1 = y1 - (grpage.getHeight() - grpage.getFooterSize());
		}
		buff.append("<group>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<height>"+GRLibrary.fromPixelsToMillimeters(heightOriginal)+"</height>\n");
		buff.append("<hposition>"+getHPositionToString()+"</hposition>\n");
		
		buff.append("<content\n>");
		for(int i = 0;i < groupObject.size();i++) {
			buff.append(groupObject.get(i).createCodeGRS());
			
		}
		buff.append("</content>\n");
		buff.append("</group>");
		
		return buff.toString();
	}

	@Override
	public String getNameObject() {
		return GRLanguageMessage.messages.getString("tlbgrgroup");
	}

	@Override
	public int getTypeModel() {
		return typeModel;
	}
}
