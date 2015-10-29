/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRPanelFooter
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

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.swing.toolbar.GRToolBar;
import com.globalreports.editor.graphics.GRObject;

public class GRPanelFooter extends JPanel {
	private JLabel lblObject;
	
	public GRPanelFooter() {
		setLayout(null);
		setPreferredSize(new Dimension(0,40));
		
		lblObject = new JLabel("");
		lblObject.setBounds(10,4,320,32);
		//lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_rectangle.png"));
		
		add(lblObject);
		
	}
	
	public void setObject(int typeObject) {
		switch(typeObject) {
			case GRToolBar.TYPEBUTTON_SELECTED:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbnothing"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_selected.png"));
			
				break;
			
			case GRToolBar.TYPEBUTTON_LINE:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrline"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_line.png"));
			
				break;
				
			case GRToolBar.TYPEBUTTON_RECTANGLE:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrrectangle"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_rectangle.png"));
			
				break;
			
			case GRToolBar.TYPEBUTTON_CIRCLE:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrcircle"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_circle.png"));
			
				break;
				
			case GRToolBar.TYPEBUTTON_IMAGE:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrimage"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_image.png"));
			
				break;
			
			case GRToolBar.TYPEBUTTON_CHART:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrchart"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_chart.png"));
			
				break;
				
			case GRToolBar.TYPEBUTTON_LIST:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrlist"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_list.png"));
			
				break;
				
			case GRToolBar.TYPEBUTTON_TABLELIST:
				lblObject.setText(GRLanguageMessage.messages.getString("tlbgrtablelist"));
				lblObject.setIcon(new ImageIcon(GRSetting.PATHIMAGE+"ico_table.png"));
			
				break;
		}
	}
}
