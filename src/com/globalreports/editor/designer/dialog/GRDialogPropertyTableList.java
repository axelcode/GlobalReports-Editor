/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogPropertyTableList
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
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

import com.globalreports.editor.graphics.GRTableList;
import com.globalreports.editor.tools.GRLibrary;

import javax.swing.border.TitledBorder;

public class GRDialogPropertyTableList extends JDialog implements ActionListener, FocusListener {

	private final JPanel contentPanel = new JPanel();
	private JTabbedPane tabbedPane;
	private JButton okButton;
	private JButton closeButton;
	
	private JTextField[] textColumnDimension;
	
	private GRTableList grtableList;
	private JScrollPane panelColumn;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GRDialogPropertyTableList dialog = new GRDialogPropertyTableList(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GRDialogPropertyTableList(GRTableList grtableList) {
		this.grtableList = grtableList;
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		this.createPaneColumn();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Generale", this.createPaneGeneral());
		tabbedPane.addTab("Colonne",panelColumn);
		
		c.add(tabbedPane,BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		closeButton = new JButton("Chiudi");
		closeButton.addActionListener(this);
		buttonPane.add(closeButton);
		
		c.add(buttonPane, BorderLayout.SOUTH);
		
		setBounds(100, 100, 620, 400);
		setVisible(true);
	}

	private JPanel createPaneGeneral() {
		JPanel panel = new JPanel();
		
		return panel;
	}
	private void createPaneColumn() {
		textColumnDimension = new JTextField[grtableList.getNumColumns()];
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new TitledBorder(null, "Dimensione colonne", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelColumn = new JScrollPane();
		panelColumn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelColumn.setViewportView(panel);
		
		JPanel columnpanel = new JPanel();
		panel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);
        
		for(int i = 0;i < grtableList.getNumColumns();i++) {
			JPanel rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(300,30));
            columnpanel.add(rowPanel);
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label1 = new JLabel("Colonna "+(i+1));
            label1.setBounds(20, 5, 100, 24);
            rowPanel.add(label1);
            
            textColumnDimension[i] = new JTextField();
            textColumnDimension[i].setPreferredSize(new Dimension(100,24));
            textColumnDimension[i].setBounds(120,5,100,24);
            textColumnDimension[i].setText(""+GRLibrary.fromPixelsToMillimeters(grtableList.getColDimension(i)));
            textColumnDimension[i].addFocusListener(this);
            rowPanel.add(textColumnDimension[i]);
            
            if(i % 2 == 0)
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
		}
		
	}

	private void refreshTableList() {
		/* Tab Colonne */
		for(int i = 0;i < grtableList.getNumColumns();i++)
			grtableList.setColDimension(i,GRLibrary.fromMillimetersToPixels(textColumnDimension[i].getText()));
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			refreshTableList();
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
