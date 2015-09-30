/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogTemplate
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.template.GRTemplate;
import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.designer.swing.GRImageTemplate;
import com.globalreports.editor.tools.GRLibrary;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class GRDialogTemplate extends JDialog implements ActionListener,ListSelectionListener {
	public static Vector<GRTemplate> grtemplate = new Vector<GRTemplate>();
	
	private String nameTemplate = "Modello vuoto";
	private String descriptionTemplate = "<html><body>Template bianco da disegnare a piacere</body></html>";
	private String fileTemplate = "";
	//private String[] nameTemplate = {"Modello vuoto","Modello Base CNP Vita","Modello Generico CNP Vita"};
	//private String[] descriptionTemplate = {"<html><body>Template bianco da disegnare a piacere</body></html>",
	//										"<html><body>Template con riferimenti di base per tutte le cover relative alle lettere di CNP Vita</body></html>",
	//										"<html><body>Template contenente le informazioni di polizza e prodotto in forma tabellare.<br><br>Utilizzato nella maggior parte delle comunicazioni giornaliere di CNP Vita</body></html>"};
	//private String[] fileTemplate = {"","ModelloBaseCNP.grs","ModelloPolizzaCNP.grs"};
	
	private JList titTemplate;
	private JButton okButton;
	private JButton cancelButton;
	
	private JPanel panelLayout;
	private JLabel lblNomeModello;
	private JLabel lblDescrizioneModello;
	private GRImageTemplate imgTemplate;
	
	private GREditor grfather;
	
	
	/**
	 * Create the dialog.
	 */
	public GRDialogTemplate(GREditor father) {
		super(father,"Template");
		
		this.grfather = father;
		setResizable(false);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		// Aggiunge il modello vuoto SEMPRE
		model.addElement("Modello vuoto");
		for(int i = 0;i < grtemplate.size();i++) {
			model.addElement(grtemplate.get(i).getTitle());
		}
		
		titTemplate = new JList(model);
		titTemplate.addListSelectionListener(this);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) UIManager.getColor("Button.background")));
		panel.setBackground(Color.WHITE);
		panel.add(titTemplate);
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
		
		panelLayout = new JPanel();
		c.add(panelLayout, BorderLayout.CENTER);
		panelLayout.setLayout(null);
		
		lblNomeModello = new JLabel("Nome modello");
		lblNomeModello.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNomeModello.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeModello.setBounds(36, 38, 372, 14);
		panelLayout.add(lblNomeModello);
		
		lblDescrizioneModello = new JLabel("Descrizione modello");
		lblDescrizioneModello.setVerticalAlignment(SwingConstants.TOP);
		lblDescrizioneModello.setBounds(36, 63, 191, 247);
		panelLayout.add(lblDescrizioneModello);
			
		imgTemplate = new GRImageTemplate();
		imgTemplate.setLocation(237,38);
		panelLayout.add(imgTemplate);
		
		titTemplate.setSelectedIndex(0);
		panelLayout.setBackground(Color.WHITE);
		this.changeTemplate(0);
		
		setBounds(100, 100, 620, 400);
		setVisible(true);
		
		for(int i = 0;i < grtemplate.size();i++)
			System.out.println(grtemplate.get(i).getTitle());
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(imgTemplate.getIndexTemplate() == 0) 
				grfather.newDocument((int)GRLibrary.fromPixelsToMillimeters(GRSetting.WIDTHPAGE),(int)GRLibrary.fromPixelsToMillimeters(GRSetting.HEIGHTPAGE));
			else {
				//File f = new File(GRSetting.PATHTEMPLATE+fileTemplate[imgTemplate.getIndexTemplate()]);
				//grfather.openDoc(f);
				
			}
			this.dispose();
		} else if(e.getSource() == cancelButton) {
			this.dispose();
		}
	}
	public void valueChanged(ListSelectionEvent e) {
		changeTemplate(titTemplate.getSelectedIndex());
	}
	private void changeTemplate(int index) {
		if(index == 0) {
			// Modello vuoto predefinito e sempre presente
			lblNomeModello.setText(nameTemplate);
			lblDescrizioneModello.setText(descriptionTemplate);
		} else {
			index--;
			lblNomeModello.setText(grtemplate.get(index).getTitle());
			lblDescrizioneModello.setText(grtemplate.get(index).getDescription());
		}
		
		//lblNomeModello.setText(nameTemplate[index]);
		//lblDescrizioneModello.setText(descriptionTemplate[index]);
		imgTemplate.setTemplate(index);
	}
	
}
