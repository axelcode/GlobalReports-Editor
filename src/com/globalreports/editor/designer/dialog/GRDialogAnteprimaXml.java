/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogAnteprimaXml
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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GRDialogAnteprimaXml extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();

	private JTextField[] textDati;
	private JButton okButton;
	private JButton cancelButton;
	
	Vector<String> nodiXml;
	private String xml;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GRDialogAnteprimaXml dialog = new GRDialogAnteprimaXml(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GRDialogAnteprimaXml(Vector<String> nodi) {
		this.nodiXml = nodi;
		setMinimumSize(new Dimension(620,400));
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(scroll,BorderLayout.CENTER);
		
		JPanel borderlaoutpanel = new JPanel();
        scroll.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        JPanel columnpanel = new JPanel();
        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        textDati = new JTextField[nodi.size()];
        
        for(int i=0;i < nodi.size();i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(300,30));
            columnpanel.add(rowPanel);
            rowPanel.setLayout(new FlowLayout());

            JLabel label1 = new JLabel("<"+nodi.get(i)+">");
            label1.setBounds(20, 5, 100, 24);
            rowPanel.add(label1);

            textDati[i] = new JTextField();
            textDati[i].setPreferredSize(new Dimension(100,24));
            textDati[i].setBounds(120,5,100,24);
            rowPanel.add(textDati[i]);
            
            JLabel label2 = new JLabel("</"+nodi.get(i)+">");
            label2.setBounds(220, 5, 100, 24);
            rowPanel.add(label2);
            if(i % 2 == 0)
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
        }
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		c.add(buttonPane, BorderLayout.SOUTH);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.WHITE);
		
		setBounds(100, 100, 620, 400);
		setModal(true);
		setVisible(true);		
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			this.setXml();
			this.dispose();
		} else if(e.getSource() == cancelButton) {
			xml = null;
			
			this.dispose();
		}
	}
	private void setXml() {
		if(nodiXml.size() == 0) {
			xml = null;
			
			return;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<globalreports>\n");
		buffer.append("<data>\n");
		for(int i = 0;i < nodiXml.size();i++) {
			buffer.append("<"+nodiXml.get(i)+">"+textDati[i].getText()+"</"+nodiXml.get(i)+">\n");
		}
		buffer.append("</data>\n");
		buffer.append("</globalreports>\n");
		
		xml = buffer.toString();
	}
	public String getXml() {
		return xml;
	}
}
