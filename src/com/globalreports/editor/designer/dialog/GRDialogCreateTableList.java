/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogCreateTableList
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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.designer.GRPage;

@SuppressWarnings("serial")
public class GRDialogCreateTableList extends JDialog implements ActionListener {
	private final JPanel contentPanel = new JPanel();
	private JTextField txtColumn;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox chkHeader;
	private JCheckBox chkFooter;
	
	private GRPage grpage;
	private int xStart;
	private int yStart;
	private int xEnd;
	private int yEnd;
	
	
	public GRDialogCreateTableList(GREditor frame, GRPage grpage, int x1, int y1, int x2, int y2) {
		super(frame,"Imposta valori relativi alla griglia",true);
		
		this.grpage = grpage;
		this.xStart = x1;
		this.yStart = y1;
		this.xEnd = x2;
		this.yEnd = y2;
		
		setBounds(100, 100, 509, 368);
		Container c = getContentPane();
	
		c.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		c.add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Numero di colonne");
		lblNewLabel.setBounds(135, 62, 125, 14);
		contentPanel.add(lblNewLabel);
		
		txtColumn = new JTextField();
		txtColumn.setBounds(245, 59, 86, 26);
		contentPanel.add(txtColumn);
		txtColumn.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Sezioni", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(27, 128, 439, 146);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		chkHeader = new JCheckBox("Header");
		chkHeader.setSelected(true);
		chkHeader.setBounds(65, 46, 97, 23);
		panel.add(chkHeader);
		
		chkFooter = new JCheckBox("Footer");
		chkFooter.setSelected(true);
		chkFooter.setBounds(65, 90, 97, 23);
		panel.add(chkFooter);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("Conferma");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
		cancelButton = new JButton("Annulla");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			int col = this.isColumnNumeric();
			if(col > 0) {
				grpage.insertTableList(xStart,yStart,xEnd,yEnd, col, true, true);
				this.dispose();
			} else {
				switch(col) {
					case -1:
						JOptionPane.showMessageDialog(this,"Il numero di colonne non può essere minore o uguale a zero!","Errore",JOptionPane.ERROR_MESSAGE);
						break;
						
					case -2:
						JOptionPane.showMessageDialog(this,"Il valore specificato per le colonne deve essere un valore numerico!","Errore",JOptionPane.ERROR_MESSAGE);
						break;
				}
			}
			
		} else if(e.getSource() == cancelButton) {
			grpage.annullaInsertTableList();
			
			this.dispose();
		}
	}
	
	private int isColumnNumeric() {
		// Verifica che il campo relativo al numero di colonne contenga effettivamente
		// un numero
		int n = 0;
		
		try {
			n = Integer.parseInt(txtColumn.getText());
			
			if(n == 0)
				return -1;
		} catch(Exception e) {
			return -2;
		}
		
		return n;
	}
}
