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
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.globalreports.editor.designer.GRPage;

public class GRList extends GRObject {
	private GRPage grpage;
	private AlphaComposite composite;	// Canale Alpha per la trasparenza degli oggetti
	
	public GRList(GRPage grpage, long id) {
		super(GRObject.TYPEOBJ_LIST,id);
		
		x1 = 0;
		y1 = 0;
		width = 630;
		height = 0;
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
	}

	public GRList(GRPage grpage, long id, int yStart, int yEnd) {
		super(GRObject.TYPEOBJ_LIST,id);
		
		x1 = 0;
		width = 630;
		
		if(yStart < yEnd) {
			y1 = yStart;
			height = yEnd - yStart;
		} else {
			y1 = yEnd;
			height = yStart - yEnd;
		}
		
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
		
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Color oldC = g.getColor();
		g.setColor(Color.BLUE);
		Composite compositeOld = g2d.getComposite();
		
		g.drawRect(0,y1,630,height);
		
		g2d.setComposite(composite);
		g2d.setPaint(Color.BLUE);
		g2d.fill(new Rectangle(0,y1,630,height));
		
		g2d.setComposite(compositeOld);
		g.setColor(oldC);
		
	}

	@Override
	public String createCodeGRS() {
		// TODO Auto-generated method stub
		return null;
	}
}
