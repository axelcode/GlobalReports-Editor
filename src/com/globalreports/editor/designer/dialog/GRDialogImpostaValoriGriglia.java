/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogImpostaValoriGriglia
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
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDialogImpostaValoriGriglia extends JDialog implements ActionListener, FocusListener {
	private GREditor greditor;
	
	private JTextField txtAsseX;
	private JTextField txtAsseY;
	private JButton okButton;
	private JButton closeButton;
	
	public GRDialogImpostaValoriGriglia(GREditor frame, Point gap) {
		super(frame,"Imposta valori relativi alla griglia",true);
		
		this.greditor = frame;
		setBounds(100, 100, 450, 300);
		setLayout(new BorderLayout());
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		add(contentPane, BorderLayout.CENTER);
		
		JLabel lblAsseX = new JLabel("Asse X:");
		lblAsseX.setBounds(120, 63, 46, 14);
		contentPane.add(lblAsseX);
		
		JLabel lblAsseY = new JLabel("Asse Y");
		lblAsseY.setBounds(120, 122, 46, 14);
		contentPane.add(lblAsseY);
		
		txtAsseX = new JTextField();
		txtAsseX.setFont(GRSetting.DEFAULTFONT);
		txtAsseX.setBounds(198, 60, 86, 24);
		txtAsseX.setColumns(10);
		txtAsseX.setText(""+Math.round(GRLibrary.fromPixelsToMillimeters(gap.x)));
		txtAsseX.addFocusListener(this);
		contentPane.add(txtAsseX);
		
		txtAsseY = new JTextField();
		txtAsseY.setFont(GRSetting.DEFAULTFONT);
		txtAsseY.setBounds(198, 119, 86, 24);
		txtAsseY.setText(""+Math.round(GRLibrary.fromPixelsToMillimeters(gap.y)));
		txtAsseY.setColumns(10);
		txtAsseY.addFocusListener(this);
		contentPane.add(txtAsseY);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		
		//getRootPane().setDefaultButton(okButton);
		closeButton = new JButton("Chiudi");
		closeButton.addActionListener(this);
		buttonPane.add(closeButton);
		
		setResizable(false);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			greditor.setGapGrid(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtAsseX.getText())),GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtAsseY.getText())));
		} else if(e.getSource() == closeButton) {
			this.dispose();
		}
			
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		JTextField t = (JTextField)e.getSource();
		t.setSelectionStart(0);
		t.setSelectionEnd(t.getText().length());
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
