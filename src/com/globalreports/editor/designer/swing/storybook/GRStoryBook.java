/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.storybook.GRStoryBook
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
package com.globalreports.editor.designer.swing.storybook;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.dialog.GRDialogStoryBook;
import com.globalreports.editor.graphics.GRObject;

public class GRStoryBook extends JPanel implements MouseListener {
	private JPanel panelContainer;
	private GRDialogStoryBook sb;
	private GRStoryBookPage pageSelected;
	private Vector<GRObject> grobj;
	
	public static Image icoLink;
	public static Image icoNext;
	public GRStoryBook(GRDialogStoryBook sb, Vector<GRObject> grobj) {
		this.sb = sb;
		this.grobj = grobj;
		
		pageSelected = null;
		
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panelContainer = new JPanel();
		panelContainer.setLayout(new GridLayout(0,1));
		
		panel.add(panelContainer, BorderLayout.NORTH);
		
		JScrollPane scroll = new JScrollPane(panel);
		add(scroll, BorderLayout.CENTER);
		
		MediaTracker tracker = new MediaTracker(this);
		icoLink = Toolkit.getDefaultToolkit().getImage(GRSetting.PATHIMAGE+"ico_linksb.png");
		icoNext = Toolkit.getDefaultToolkit().getImage(GRSetting.PATHIMAGE+"ico_nextsb.png");
		tracker.addImage(icoLink, 0);
		tracker.addImage(icoNext, 1);
		try {
			tracker.waitForAll();
		} catch(InterruptedException e) {}
		
		if(grobj != null)
			this.init();
		
	}
	
	private void init() {
		GRObject refObj;
		int type;
		String value;
		boolean hpos;
		
		for(int i = 0;i < grobj.size();i++) {
			GRStoryBookPage page = new GRStoryBookPage(grobj.get(i));
			
			if((i + 1) == grobj.size())
				page.setLastPage();
			
			panelContainer.add(page);
			
			page.addMouseListener(this);
		}
	}
	public GRObject getSelected() {
		if(pageSelected == null)
			return null;
		
		return pageSelected.getObject();
	}
	public void up() {
		int index = -1;
		
		if(pageSelected == null)
			return;
		
		/* Sposta l'oggetto all'interno del Vector */
		GRObject objTemp = pageSelected.getObject();
		index = grobj.indexOf(objTemp);
		if(index == 0)
			return;
		
		grobj.removeElementAt(index);
		index--;
		grobj.insertElementAt(objTemp, index);
		
		/* Procede allo spostamento dell'oggetto nel Container */
		for(int i = 0;i < panelContainer.getComponentCount();i++) {
			if(panelContainer.getComponent(i) == pageSelected) {
				index = i;
		
				break;
			}
		}
		
		panelContainer.remove(pageSelected);
		index--;
		panelContainer.add(pageSelected,index);
		
	}
	public void down() {
		int index = -1;
		
		if(pageSelected == null)
			return;
		
		/* Sposta l'oggetto all'interno del Vector */
		GRObject objTemp = pageSelected.getObject();
		index = grobj.indexOf(objTemp);
		if(index == (grobj.size()-1))
			return;
		
		grobj.removeElementAt(index);
		index++;
		grobj.insertElementAt(objTemp, index);
		
		/* Procede allo spostamento dell'oggetto nel Container */
		for(int i = 0;i < panelContainer.getComponentCount();i++) {
			if(panelContainer.getComponent(i) == pageSelected) {
				index = i;
		
				break;
			}
		}
		
		panelContainer.remove(pageSelected);
		index++;
		
		if(index == panelContainer.getComponentCount())
			panelContainer.add(pageSelected);
		else
			panelContainer.add(pageSelected,index);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		GRStoryBookPage page = (GRStoryBookPage)e.getSource();
		
		if(pageSelected == page) {
			page.setSelected(false);
			
			pageSelected = null;
		} else {
			if(pageSelected != null)
				pageSelected.setSelected(false);
			
			page.setSelected(true);
			
			pageSelected = page;
		}
		
		if(pageSelected == null)
			sb.selectObject(null);
		else
			sb.selectObject(pageSelected.getObject());
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
