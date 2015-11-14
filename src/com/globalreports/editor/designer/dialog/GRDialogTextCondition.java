/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogTextCondition
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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GREditText;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.swing.GRPanelCondition;
import com.globalreports.editor.graphics.textcondition.GRDocumentTextCondition;
import javax.swing.border.TitledBorder;

public class GRDialogTextCondition extends JDialog implements ActionListener {
	public static final int 	NEWTEXT				= 1;
	public static final int 	MODIFYTEXT			= 2;
	
	private JButton bAddText;
	private JButton bAddCondition;
	private JButton okButton;
	private JButton cancelButton;
	
	private GRPage grpage;
	private JPanel panelText;
	
	private int typeDialog;
	private Rectangle areaText;
	
	private Vector<GRPanelCondition> listText;	// Paragrafi di testo condizionati
	
	public GRDialogTextCondition(GRPage page, Rectangle r) {
		this.grpage = page;
		this.areaText = r;
		
		listText = null;
		
		this.init();
	}
	private void init() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		panelText = new JPanel();
		panelText.setLayout(new GridLayout(0,1));
		c.add(panelText, BorderLayout.NORTH);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new GridLayout(1,0));
		
		JPanel panelLeft = new JPanel();
		panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		bAddText = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"add.png"));
		bAddText.addActionListener(this);
		panelLeft.add(bAddText);
		
		bAddCondition = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"condition.png"));
		bAddCondition.addActionListener(this);
		panelLeft.add(bAddCondition);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton(GRLanguageMessage.messages.getString("btnconfirm"));
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton(GRLanguageMessage.messages.getString("btncancel"));
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		panelSouth.add(panelLeft);
		panelSouth.add(buttonPane);
		
		c.add(panelSouth, BorderLayout.SOUTH);
		setBounds(100, 100, 620, 500);
		setModal(true);
		setVisible(false);
	}

	public void showDialog(int type) {
		typeDialog = type;
		
		setVisible(true);
		
	}
	public void insertText(Document dc, String value) {
		if(listText == null) 
			listText = new Vector<GRPanelCondition>();
		
		GRPanelCondition panel = new GRPanelCondition(new GRDocumentTextCondition(dc, value));
		panelText.add(panel);
		
		listText.add(panel);
		
		
		
		this.setSize(this.getWidth(),this.getHeight()-1);
		this.setSize(this.getWidth(),this.getHeight()+1);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			
		} else if(e.getSource() == cancelButton) {
			this.dispose();
		} else if(e.getSource() == bAddText) {
			GRDialogEditText et = new GRDialogEditText(this, areaText);
			
			//et.setFont(grtoolbarStrumenti.getFontName(),grtoolbarStrumenti.getFontSize(),grtoolbarStrumenti.getFontStyle());
			et.showDialog(GRDialogEditText.NEWTEXT);
		} else if(e.getSource() == bAddCondition) {
			new GRDialogCreateCondition();
		}
		
	}

}
