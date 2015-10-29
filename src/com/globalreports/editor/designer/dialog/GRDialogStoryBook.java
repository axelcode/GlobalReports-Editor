/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogStoryBook
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
package com.globalreports.editor.designer.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GRDocument;
import com.globalreports.editor.designer.swing.storybook.GRStoryBook;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRText;

@SuppressWarnings("serial")
public class GRDialogStoryBook extends JDialog implements ActionListener {
	private GRDocument doc;
	private GRStoryBook sb;
	private Vector<GRObject> grobj;
		
	private JButton upButton;
	private JButton downButton;
	private JButton okButton;
	private JButton cancelButton;
	
	public GRDialogStoryBook(GRDocument doc) {
		setTitle(GRLanguageMessage.messages.getString("dlgstorybooktitle"));
		
		this.doc = doc;
		this.grobj = doc.getObjectInThePage();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		sb = new GRStoryBook(this,grobj);
		
		c.add(sb, BorderLayout.CENTER);
		
		/* Pannello bottoni */
		JPanel southPanel = new JPanel(new GridLayout(1,2));
        
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton(GRLanguageMessage.messages.getString("btnconfirm"));
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton(GRLanguageMessage.messages.getString("btncancel"));
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		JPanel arrowPanel = new JPanel();
		arrowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		upButton = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"up.png"));
		upButton.addActionListener(this);
		arrowPanel.add(upButton);
		
		downButton = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"down.png"));
		downButton.addActionListener(this);
		arrowPanel.add(downButton);
		
		JLabel lblTotale = new JLabel(GRLanguageMessage.messages.getString("dlgstorybooktotalobject")+grobj.size());
		arrowPanel.add(lblTotale);
		
		southPanel.add(arrowPanel);
		southPanel.add(buttonPane);
		
		c.add(southPanel, BorderLayout.SOUTH);
		
		
		setBounds(100, 100, 620, 500);
		//setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);	
		
		
	}
	public void selectObject(GRObject grobj) {
		doc.selectObject(grobj);
	}
	private void spostaSu(GRObject refObj) {
		GRObject objTemp;
		
		int index = grobj.indexOf(refObj);
		if(index == 0)
			return;
		
		grobj.removeElementAt(index);
	
		index--;
		grobj.insertElementAt(refObj, index);
		doc.refreshPage();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			
		} else if(e.getSource() == cancelButton) {
			this.dispose();
		} else if(e.getSource() == upButton) {
			sb.up();
			
			this.setSize(new Dimension(this.getWidth(),this.getHeight()+1));
			this.setSize(new Dimension(this.getWidth(),this.getHeight()-1));
			
			doc.refreshPage();
		} else if(e.getSource() == downButton) {
			sb.down();
			
			this.setSize(new Dimension(this.getWidth(),this.getHeight()+1));
			this.setSize(new Dimension(this.getWidth(),this.getHeight()-1));
			
			doc.refreshPage();
		}
			
	}
	
	
}
