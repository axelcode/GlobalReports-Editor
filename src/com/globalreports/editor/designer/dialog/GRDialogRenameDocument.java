/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogRenameDocument
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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

public class GRDialogRenameDocument extends JDialog implements ActionListener {
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField txtNuovoDocumento;
	private String value;
	/**
	 * Create the dialog.
	 */
	public GRDialogRenameDocument() {
		setModal(true);
		setTitle("Rename Project");
		setResizable(false);
		Container c = getContentPane();
		
		value = null;
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(buttonPane, BorderLayout.SOUTH);
			
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		panel = new JPanel();
		
		panel_1 = new JPanel();
		c.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Nuovo nome", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(21, 30, 400, 67);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtNuovoDocumento = new JTextField();
		txtNuovoDocumento.setBounds(10, 32, 380, 24);
		panel_2.add(txtNuovoDocumento);
		txtNuovoDocumento.setColumns(10);
		
		setBounds(100, 100, 448, 182);
		setVisible(true);
	}
	
	public String getValue() {
		return value;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton) {
			this.dispose();
		} else if(e.getSource() == okButton) {
			if(!txtNuovoDocumento.getText().trim().equals("")) {
				value = txtNuovoDocumento.getText();
			}
			
			this.dispose();
		}
	}
}
