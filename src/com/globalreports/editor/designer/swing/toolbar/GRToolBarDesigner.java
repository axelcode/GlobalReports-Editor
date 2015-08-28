/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.toolbar.GRToolBarDesigner
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
package com.globalreports.editor.designer.swing.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.GREditor;

@SuppressWarnings("serial")
public class GRToolBarDesigner extends JToolBar implements ActionListener {
	private GREditor greditor;
	
	private JToggleButton bSel;
	private JToggleButton bText;
	private JToggleButton bRectangle;
	private JToggleButton bLine;
	private JToggleButton bImage;
	private JToggleButton bList;
	private JToggleButton bTableList;
	
	public GRToolBarDesigner(GREditor greditor) {
		this.greditor = greditor;
		
		ImageIcon ico_selected = new ImageIcon(GRSetting.PATHIMAGE+"ico_selected.png");
		ImageIcon ico_text = new ImageIcon(GRSetting.PATHIMAGE+"ico_text.png");
		ImageIcon ico_line = new ImageIcon(GRSetting.PATHIMAGE+"ico_line.png");
		ImageIcon ico_rectangle = new ImageIcon(GRSetting.PATHIMAGE+"ico_rectangle.png");
		ImageIcon ico_image = new ImageIcon(GRSetting.PATHIMAGE+"ico_image.png");
		ImageIcon ico_list = new ImageIcon(GRSetting.PATHIMAGE+"ico_list.png");
		ImageIcon ico_tablelist = new ImageIcon(GRSetting.PATHIMAGE+"ico_table.png");
		
		bSel = new JToggleButton(ico_selected);
		bText = new JToggleButton(ico_text);
		bLine = new JToggleButton(ico_line);
		bRectangle = new JToggleButton(ico_rectangle);
		bImage = new JToggleButton(ico_image);
		bList = new JToggleButton(ico_list);
		bTableList = new JToggleButton(ico_tablelist);
		
		bSel.addActionListener(this);
		add(bSel);
		
		addSeparator();
		
		bText.addActionListener(this);
		add(bText);
		bLine.addActionListener(this);
		add(bLine);
		bRectangle.addActionListener(this);
		add(bRectangle);
		bImage.addActionListener(this);
		add(bImage);
		bList.addActionListener(this);
		add(bList);
		bTableList.addActionListener(this);
		add(bTableList);
		
		setFloatable(false);
	}
	
	private void clearButton(int type) {
		if(type != GRToolBar.TYPEBUTTON_SELECTED)
			bSel.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_TEXT)
			bText.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_LINE)
			bLine.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_RECTANGLE)
			bRectangle.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_IMAGE)
			bImage.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_LIST)
			bList.setSelected(false);
		if(type != GRToolBar.TYPEBUTTON_TABLELIST)
			bTableList.setSelected(false);
	}
	
	public void restore() {
		clearButton(GRToolBar.TYPEBUTTON_SELECTED);
		bSel.setSelected(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bSel) {
			clearButton(GRToolBar.TYPEBUTTON_SELECTED);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_SELECTED);
		} else if(e.getSource() == bText) {
			clearButton(GRToolBar.TYPEBUTTON_TEXT);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_TEXT);
		} else if(e.getSource() == bLine) {
			clearButton(GRToolBar.TYPEBUTTON_LINE);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_LINE);
		} else if(e.getSource() == bRectangle) {
			clearButton(GRToolBar.TYPEBUTTON_RECTANGLE);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_RECTANGLE);
		} else if(e.getSource() == bImage) {
			clearButton(GRToolBar.TYPEBUTTON_IMAGE);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_IMAGE);
		} else if(e.getSource() == bList) {
			clearButton(GRToolBar.TYPEBUTTON_LIST);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_LIST);
		} else if(e.getSource() == bTableList) {
			clearButton(GRToolBar.TYPEBUTTON_TABLELIST);
			greditor.actionToolBar(GRToolBar.TYPEBUTTON_TABLELIST);
		}
	}
}
