/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogColorChooser
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.globalreports.editor.designer.resources.GRColor;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class GRDialogColorChooser extends JDialog implements ActionListener, ItemListener, ChangeListener, MouseListener {
	
	private JButton okButton;
	private JButton cancelButton;
	
	private JPanel panelAnteprima;
	private JSlider[] sliderRGB;
	private JSpinner[] spinnerRGB;
	
	private GRColor color;
	private JTextField txtCodiceColore;
	private JCheckBox chkTrasparente;
	
	/* Colori predefiniti */
	private JPanel colorDefault1;
	private JPanel colorDefault2;
	private JPanel colorDefault3;
	
	/**
	 * Create the dialog.
	 */
	public static GRColor showDialog() {
		return new GRDialogColorChooser().getColor();
	}
	public static GRColor showDialog(Color color) {
		return new GRDialogColorChooser(color).getColor();
	}
	public static GRColor showDialog(boolean fTransparent) {
		return new GRDialogColorChooser(fTransparent).getColor();
	}
	public static GRColor showDialog(Color color, boolean fTransparent) {
		return new GRDialogColorChooser(color, fTransparent).getColor();
	}
	public static GRColor showDialog(GRColor color, boolean fTransparent) {
		if(color == null)
			return new GRDialogColorChooser(null,fTransparent).getColor();
		else
			return new GRDialogColorChooser(color.getColor(),fTransparent).getColor();
	}
	public GRDialogColorChooser() {
		this(null);
	}
	public GRDialogColorChooser(Color c) {
		this(c,false);
	}
	public GRDialogColorChooser(boolean fTransparent) {
		this(null,fTransparent);
	}
	public GRDialogColorChooser(Color color,boolean fTransparent) {
		setTitle("Scelta del colore");
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		sliderRGB = new JSlider[3];
		spinnerRGB = new JSpinner[3];
			
		JPanel panelColor = new JPanel();
		panelColor.setLayout(null);
		
		JPanel panelCenter = new JPanel(new FlowLayout());
		c.add(panelColor, BorderLayout.CENTER);
		//panelCenter.add(panelColor);
		
		panelAnteprima = new JPanel();
		panelAnteprima.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelAnteprima.setBackground(Color.BLACK);
		panelAnteprima.setBounds(31, 24, 119, 114);
		panelColor.add(panelAnteprima);
		
		JLabel lblRosso = new JLabel("Rosso");
		lblRosso.setBounds(160, 24, 46, 14);
		panelColor.add(lblRosso);
		
		JLabel lblVerde = new JLabel("Verde");
		lblVerde.setBounds(160, 54, 46, 14);
		panelColor.add(lblVerde);
		
		JLabel lblBlue = new JLabel("Blue");
		lblBlue.setBounds(160, 84, 46, 14);
		panelColor.add(lblBlue);
				
		for(int i = 0;i < spinnerRGB.length;i++) {
			sliderRGB[i] = new JSlider(0,255);
			sliderRGB[i].setBounds(203, (20 + (i* 30)), 88, 23);
			sliderRGB[i].setValue(0);
			sliderRGB[i].addChangeListener(this);
			panelColor.add(sliderRGB[i]);
			
			spinnerRGB[i] = new JSpinner();
			spinnerRGB[i].setModel(new SpinnerNumberModel(0,0,255,1));
			spinnerRGB[i].setBounds(298, (24 + (i*30)), 64, 24);
			spinnerRGB[i].addChangeListener(this);
			
			DefaultEditor editor = (DefaultEditor)spinnerRGB[i].getEditor();
			
			editor.getTextField().addFocusListener(new FocusAdapter() {
				public void focusGained(final FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable()
			        {
			            @Override
			            public void run()
			            {
			                JTextField tf = (JTextField)e.getSource();
			                tf.selectAll();
			            }
			        });
				}
			});
			
			panelColor.add(spinnerRGB[i]);
		}
		
		
		chkTrasparente = new JCheckBox("Trasparente");
		chkTrasparente.setBounds(156, 115, 206, 23);
		chkTrasparente.addItemListener(this);
		panelColor.add(chkTrasparente);
		chkTrasparente.setVisible(fTransparent);
		
		JLabel lblCodiceColore = new JLabel("Codice colore");
		lblCodiceColore.setBounds(160, 150, 92, 14);
		panelColor.add(lblCodiceColore);
		
		txtCodiceColore = new JTextField();
		txtCodiceColore.setBounds(276, 147, 86, 24);
		panelColor.add(txtCodiceColore);
		txtCodiceColore.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Predefiniti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(27, 175, 335, 63);
		panelColor.add(panel);
		panel.setLayout(null);
		
		colorDefault1 = new JPanel();
		colorDefault1.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorDefault1.setBounds(10, 21, 18, 18);
		colorDefault1.setBackground(Color.WHITE);
		colorDefault1.addMouseListener(this);
		panel.add(colorDefault1);
		
		colorDefault2 = new JPanel();
		colorDefault2.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorDefault2.setBounds(37, 21, 18, 18);
		colorDefault2.setBackground(new Color(174,174,210));
		colorDefault2.addMouseListener(this);
		panel.add(colorDefault2);
		
		colorDefault3 = new JPanel();
		colorDefault3.setBorder(new LineBorder(new Color(0, 0, 0)));
		colorDefault3.setBounds(65, 21, 18, 18);
		colorDefault3.setBackground(new Color(205,205,220));
		colorDefault3.addMouseListener(this);
		panel.add(colorDefault3);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton("Annulla");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		this.changeColor();
		this.initColor(color);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 320);
		setModal(true);
		setVisible(true);
		
	}
	
	private void initColor(Color c) {
		if(c == null)
			chkTrasparente.setSelected(true);
		else {
			chkTrasparente.setSelected(false);
		
			sliderRGB[0].setValue(c.getRed());
			sliderRGB[1].setValue(c.getGreen());
			sliderRGB[2].setValue(c.getBlue());
		}
		
	}
	private void changeColor() {
		StringBuffer colorHex = new StringBuffer();
		String valueHex;
		
		color = new GRColor(sliderRGB[0].getValue(),sliderRGB[1].getValue(),sliderRGB[2].getValue());
		if(chkTrasparente.isVisible())
			color.setTransparent(chkTrasparente.isSelected());
		
		/* RED */
		valueHex = Integer.toHexString(color.getRed()).toUpperCase();
		if(valueHex.length() == 1)
			valueHex = "0"+valueHex;
		colorHex.append(valueHex);
		
		/* GREEN */
		valueHex = Integer.toHexString(color.getGreen()).toUpperCase();
		if(valueHex.length() == 1)
			valueHex = "0"+valueHex;
		colorHex.append(valueHex);
		
		/* BLUE */
		valueHex = Integer.toHexString(color.getBlue()).toUpperCase();
		if(valueHex.length() == 1)
			valueHex = "0"+valueHex;
		colorHex.append(valueHex);
		
		txtCodiceColore.setText(colorHex.toString());
		panelAnteprima.setBackground(color.getColor());
	}
	private void setTransparent(boolean value) {
		for(int i = 0;i < 3;i++) {
			sliderRGB[i].setEnabled(!value);
			spinnerRGB[i].setEnabled(!value);
		}
		
		txtCodiceColore.setEnabled(!value);
		
		color.setTransparent(value);
		if(!value)
			changeColor();
	}
	public GRColor getColor() {
		return color;
	}
	private void setColor(Color c) {
		sliderRGB[0].setValue(c.getRed());
		sliderRGB[1].setValue(c.getGreen());
		sliderRGB[2].setValue(c.getBlue());
		
		chkTrasparente.setSelected(false);
		//this.setTransparent(false);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			
			this.dispose();
		} else if(e.getSource() == cancelButton) {
			color = null;
			
			this.dispose();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == sliderRGB[0]) {
			spinnerRGB[0].setValue(new Integer(sliderRGB[0].getValue()));
		} else if(e.getSource() == sliderRGB[1]) {
			spinnerRGB[1].setValue(new Integer(sliderRGB[1].getValue()));
		} else if(e.getSource() == sliderRGB[2]) {
			spinnerRGB[2].setValue(new Integer(sliderRGB[2].getValue()));
		} else if(e.getSource() == spinnerRGB[0]) {
			sliderRGB[0].setValue(Integer.parseInt(""+spinnerRGB[0].getValue()));
		} else if(e.getSource() == spinnerRGB[1]) {
			sliderRGB[1].setValue(Integer.parseInt(""+spinnerRGB[1].getValue()));
		} else if(e.getSource() == spinnerRGB[2]) {
			sliderRGB[2].setValue(Integer.parseInt(""+spinnerRGB[2].getValue()));
		} 
			
		this.changeColor();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == chkTrasparente) {
			this.setTransparent(chkTrasparente.isSelected());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setColor(((JPanel)e.getSource()).getBackground());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
