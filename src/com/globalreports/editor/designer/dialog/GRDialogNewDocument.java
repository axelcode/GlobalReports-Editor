/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogNewDocument
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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.tools.GRLibrary;

import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GRDialogNewDocument extends JDialog implements ActionListener,ListSelectionListener,ItemListener,FocusListener {
	private final String[] nomeFormato = {"A3","A4","A5","A6","B4","B5","B6","Legal","Letter"};
	private final String[] descFormato = {"297 x 420 mm","210 x 297 mm","148 x 210 mm","105 x 148 mm","257 x 364 mm","182 x 257 mm","128 x 182 mm","215 x 355 mm","215 x 279 mm"};
	private final Point[] dimFormato = {new Point(297,420),
										new Point(210,297),
										new Point(148,210),
										new Point(105,148),
										new Point(257,364),
										new Point(182,257),
										new Point(128,182),
										new Point(215,355),
										new Point(215,279)};
	
	private JList formatoCarta;
	private JButton okButton;
	private JButton cancelButton;
	
	private GREditor grfather;
	
	private JCheckBox chckbxDimensioniPersonalizzate;
	private JTextField txtLarghezza;
	private JTextField txtAltezza;
	private JToggleButton portrait;
	private JToggleButton landscape;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GRDialogNewDocument dialog = new GRDialogNewDocument(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GRDialogNewDocument(GREditor father) {
		super(father,"Nuovo documento");
		
		this.grfather = father;
		setResizable(false);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(int i = 0;i < nomeFormato.length;i++) {
			String el = "<html><body><span style='font-family: Tahoma; font-weight: bold; font-size: 10px;'>"+nomeFormato[i]+"</span><br><span style='font-size: 9px; font-family: Tahoma;'>"+descFormato[i]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br><br></body></html>";
            model.addElement(el);
		}
			
		formatoCarta = new JList(model);
		formatoCarta.setForeground(new Color(57,105,138));
		formatoCarta.addListSelectionListener(this);
		
		JScrollPane scroll = new JScrollPane(formatoCarta);
		scroll.setPreferredSize(new Dimension(200,330));
		scroll.setBorder(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) UIManager.getColor("Button.background")));
		panel.setBackground(Color.WHITE);
		panel.add(scroll);
		
		c.add(panel, BorderLayout.WEST);
				
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton("Cancella");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		c.add(buttonPane, BorderLayout.SOUTH);
		
		JPanel panelDati = new JPanel();
		panelDati.setBackground(Color.WHITE);
		panelDati.setLayout(new GridLayout(2,1));
		c.add(panelDati, BorderLayout.CENTER);
		
		JPanel panelDimension = new JPanel();
		panelDimension.setBackground(Color.WHITE);
		panelDimension.setLayout(null);
		panelDati.add(panelDimension);
		
		JPanel panelDatiDim = new JPanel();
		panelDatiDim.setBackground(Color.WHITE);
		panelDatiDim.setBorder(new TitledBorder(null, "Formato", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDatiDim.setBounds(28, 46, 346, 110);
		panelDimension.add(panelDatiDim);
		panelDatiDim.setLayout(null);
		
		JLabel lblLarghezza = new JLabel("Larghezza (mm):");
		lblLarghezza.setBounds(88, 34, 92, 14);
		panelDatiDim.add(lblLarghezza);
		
		JLabel lblAltezzamm = new JLabel("Altezza (mm):");
		lblAltezzamm.setBounds(88, 59, 103, 14);
		panelDatiDim.add(lblAltezzamm);
		
		txtLarghezza = new JTextField();
		txtLarghezza.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtLarghezza.setBounds(190, 31, 86, 24);
		txtLarghezza.addFocusListener(this);
		panelDatiDim.add(txtLarghezza);
		txtLarghezza.setColumns(10);
				
		txtAltezza = new JTextField();
		txtAltezza.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtAltezza.setBounds(190, 59, 86, 24);
		txtAltezza.addFocusListener(this);
		panelDatiDim.add(txtAltezza);
		txtAltezza.setColumns(10);
		
		chckbxDimensioniPersonalizzate = new JCheckBox("Dimensioni personalizzate");
		chckbxDimensioniPersonalizzate.setBounds(28, 7, 346, 23);
		chckbxDimensioniPersonalizzate.addItemListener(this);
		panelDimension.add(chckbxDimensioniPersonalizzate);
		
		JPanel panelOrientation = new JPanel();
		panelOrientation.setBackground(Color.WHITE);
		panelOrientation.setLayout(new GridLayout(1,2,10,10));
		panelDati.add(panelOrientation);
		
		portrait = new JToggleButton("Verticale",new ImageIcon(GRSetting.PATHIMAGE+"portrait.png"),false);
		portrait.setHorizontalTextPosition(JLabel.CENTER);
		portrait.setVerticalTextPosition(JLabel.BOTTOM);
		portrait.setSize(128,128);
		portrait.setSelected(true);
		portrait.addItemListener(this);
		panelOrientation.add(portrait);
		
		landscape = new JToggleButton("Orizzontale",new ImageIcon(GRSetting.PATHIMAGE+"landscape.png"),false);
		landscape.setHorizontalTextPosition(JLabel.CENTER);
		landscape.setVerticalTextPosition(JLabel.BOTTOM);
		landscape.setSize(128,128);
		landscape.addItemListener(this);
		panelOrientation.add(landscape);
		
		formatoCarta.setSelectedIndex(1);
		enableCustomDimension(false);
		
		setBounds(100, 100, 620, 400);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(portrait.isSelected())
				grfather.newDocument(Integer.parseInt(txtLarghezza.getText()), Integer.parseInt(txtAltezza.getText()));
			else if(landscape.isSelected())
				grfather.newDocument(Integer.parseInt(txtAltezza.getText()), Integer.parseInt(txtLarghezza.getText()));
			
			this.dispose();
		} else if(e.getSource() == cancelButton) {
			this.dispose();
		}
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == chckbxDimensioniPersonalizzate) {
			enableCustomDimension(chckbxDimensioniPersonalizzate.isSelected());
		} else if(e.getSource() == landscape) {
			portrait.setSelected(!landscape.isSelected());
		} else if(e.getSource() == portrait) {
			landscape.setSelected(!portrait.isSelected());
		}
	}
	public void focusGained(FocusEvent e) {
		JTextField t = (JTextField)e.getSource();
		t.setSelectionStart(0);
		t.setSelectionEnd(t.getText().length());
	}
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void valueChanged(ListSelectionEvent e) {
		changeFormato(formatoCarta.getSelectedIndex());
	}
	private void changeFormato(int index) {
		
		txtLarghezza.setText(""+dimFormato[index].x);
		txtAltezza.setText(""+dimFormato[index].y);
		
	}
	public void enableCustomDimension(boolean value) {
		txtLarghezza.setEditable(value);
		txtAltezza.setEditable(value);
		
		formatoCarta.setEnabled(!value);
		if(!value) {
			this.changeFormato(formatoCarta.getSelectedIndex());
		}
	}
}
