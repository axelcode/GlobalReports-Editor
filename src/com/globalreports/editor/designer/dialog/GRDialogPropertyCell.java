/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogPropertyCell
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
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.globalreports.editor.graphics.tablelist.GRTableListCell;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDialogPropertyCell extends JDialog implements ActionListener, FocusListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtLeft;
	private JTextField txtRight;
	private JTextField txtTop;
	private JTextField txtBottom;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private GRTableListCell refCell;
	
	/**
	 * Create the dialog.
	 */
	public GRDialogPropertyCell(GRTableListCell grcell) {
		setTitle("Proprietà cella");
		setModal(true);
		
		this.refCell = grcell;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container c = getContentPane();
		
		setBounds(100, 100, 401, 300);
		
		Font f = new Font("Lucida Grande",Font.PLAIN,10);
		setFont(f);
		
		c.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(33, 30, 327, 183);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblMargineSinistro = new JLabel("Margine sinistro (mm)");
		lblMargineSinistro.setBounds(42, 34, 133, 14);
		panel.add(lblMargineSinistro);
		
		JLabel lblMargineDestro = new JLabel("Margine destro (mm)");
		lblMargineDestro.setBounds(42, 64, 133, 14);
		panel.add(lblMargineDestro);
		
		JLabel lblMargineSuperiore = new JLabel("Margine superiore (mm)");
		lblMargineSuperiore.setBounds(42, 94, 133, 14);
		panel.add(lblMargineSuperiore);
		
		JLabel lblMargineInferiore = new JLabel("Margine inferiore (mm)");
		lblMargineInferiore.setBounds(42, 124, 133, 14);
		panel.add(lblMargineInferiore);
		
		txtLeft = new JTextField();
		txtLeft.setBounds(183, 34, 86, 26);
		txtLeft.addFocusListener(this);
		panel.add(txtLeft);
		txtLeft.setColumns(10);
		
		txtRight = new JTextField();
		txtRight.setBounds(183, 64, 86, 26);
		txtRight.addFocusListener(this);
		panel.add(txtRight);
		txtRight.setColumns(10);
		
		txtTop = new JTextField();
		txtTop.setBounds(183, 94, 86, 26);
		txtTop.addFocusListener(this);
		panel.add(txtTop);
		txtTop.setColumns(10);
		
		txtBottom = new JTextField();
		txtBottom.setBounds(183, 124, 86, 26);
		txtBottom.addFocusListener(this);
		panel.add(txtBottom);
		txtBottom.setColumns(10);
		
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
			
		/* Riempie i campi con i valori presi dall'oggetto cella */
		txtLeft.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginLeft()));
		txtRight.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginRight()));
		txtTop.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginTop()));
		txtBottom.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginBottom()));
		
		setResizable(false);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(this.isMarginNumeric()) {
				
				refCell.setMarginLeft(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtLeft.getText())));
				refCell.setMarginTop(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtTop.getText())));
				refCell.setMarginRight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtRight.getText())));
				refCell.setMarginBottom(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtBottom.getText())));
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this,"Il valore specificato per i margini deve essere un valore intero numerico!","Errore",JOptionPane.ERROR_MESSAGE);
			}
			
		} else if(e.getSource() == cancelButton) {
			this.dispose();
		}
	}
	
	private boolean isMarginNumeric() {
		
		if(txtLeft.getText().trim().equals(""))
			txtLeft.setText("0");
		if(txtRight.getText().trim().equals(""))
			txtRight.setText("0");
		if(txtTop.getText().trim().equals(""))
			txtTop.setText("0");
		if(txtBottom.getText().trim().equals(""))
			txtBottom.setText("0");
		
		/* Decidere se i valori possono essere in virgola mobile o solo interi
		txtLeft.setText(txtLeft.getText().replace(",","."));
		txtRight.setText(txtRight.getText().replace(",","."));
		txtTop.setText(txtTop.getText().replace(",","."));
		txtBottom.setText(txtBottom.getText().replace(",","."));
		*/
		
		try {
			Double.parseDouble(txtLeft.getText());
			Double.parseDouble(txtRight.getText());
			Double.parseDouble(txtTop.getText());
			Double.parseDouble(txtBottom.getText());
			
		} catch(Exception e) {
			return false;
		}
		
		return true;
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
