/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRPanelSection
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
package com.globalreports.editor.designer;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.globalreports.editor.designer.swing.GRHandle;

@SuppressWarnings("serial")
public class GRPanelSection extends JPanel implements MouseListener, MouseMotionListener {
	private GRHandle grHeader;
	private GRPage grpage;
	
	private final int Y_HEAD	= 17;

	
	public GRPanelSection(GRPage grpage, int height) {
		this.grpage = grpage;
		
		setBackground(new Color(233,232,233));
		setBounds(0,0,71,height);
		setLayout(null);
		setBorder(new MatteBorder(0,0,1,1,Color.black));
		
		grHeader = new GRHandle();
		grHeader.setLocation(0,Y_HEAD);
		grHeader.addMouseListener(this);
		grHeader.addMouseMotionListener(this);
		add(grHeader);
	}
	
	public void setHeader(int value) {
		if(value < 0)
			value = 0;
		
		grHeader.setLocation(0,Y_HEAD + value);
		grpage.modifyHeader(value);
	}
	public void setHeight(int value) {
		setSize(71,value);
	}
	private void modifyHeader(int yValue) {
		int newY = grHeader.getLocation().y + yValue;
		
		if(newY < Y_HEAD)
			newY = Y_HEAD;
		grHeader.setLocation(0,newY);
		
		grpage.modifyHeader(newY - Y_HEAD);
		grpage.repaint();
	}
	public void mouseClicked(MouseEvent me) {
	}
	public void mouseEntered(MouseEvent me) {
	}
	public void mouseExited(MouseEvent me) {
	}
	public void mousePressed(MouseEvent me) {
		
	}
	public void mouseReleased(MouseEvent me) {
		grpage.releaseHeader();
	}
	public void mouseMoved(MouseEvent me) {
	}
	public void mouseDragged(MouseEvent me) {
		
		modifyHeader(me.getY());
		
	}
}
