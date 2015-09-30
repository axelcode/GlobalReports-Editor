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
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;







import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.resources.GRColor;
import com.globalreports.editor.graphics.tablelist.GRTableListCell;
import com.globalreports.editor.graphics.tablelist.GRTableListText;
import com.globalreports.editor.graphics.text.GRTextFormatted;
import com.globalreports.editor.graphics.text.GRTextFormattedElement;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDialogPropertyCell extends JDialog implements ActionListener, ItemListener, FocusListener {

	private JTabbedPane tabbedPane;
	
	private JPanel panelMargin;
	private JPanel panelText;
	private JPanel panelBackground;
	
	/* PanelMargin */
	private JTextField txtLeft;
	private JTextField txtRight;
	private JTextField txtTop;
	private JTextField txtBottom;
	
	/* PanelText */
	private SimpleAttributeSet attributi;
	private JTextPane textArea;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField lineSpacing;
	private JComboBox alignment;
	
	/* PanelBackground */
	private JPanel panelAnteprima;
	private JButton buttonBordo;
	private JButton buttonSfondo;
	private Color colorBorder;
	private Color colorFill;
	
	private JButton okButton;
	private JButton closeButton;
	
	private GRTableListCell refCell;
	private GRTableListText grtext;
	
	
	public GRDialogPropertyCell(GRTableListCell grcell) {
		setTitle("Proprietà cella");
		setModal(true);
		
		this.refCell = grcell;
		
		if(grcell.getText() == null)
			this.grtext = null;
		else 
			this.grtext = grcell.getText();
					
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		Font f = new Font("Lucida Grande",Font.PLAIN,10);
		setFont(f);
		
		createPaneMargin();
		createPaneText();
		createPaneBackground();
		
		this.setData();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Margini", panelMargin);
		tabbedPane.addTab("Testo", panelText);
		tabbedPane.addTab("Sfondo", panelBackground);
		c.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		closeButton = new JButton("Chiudi");
		closeButton.addActionListener(this);
		buttonPane.add(closeButton);
			
		/* Riempie i campi con i valori presi dall'oggetto cella */
		txtLeft.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginLeft()));
		txtRight.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginRight()));
		txtTop.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginTop()));
		txtBottom.setText(""+GRLibrary.fromPixelsToMillimeters(refCell.getMarginBottom()));
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 320);
		setResizable(false);
		setVisible(true);
	}

	private void createPaneMargin() {
		panelMargin = new JPanel();
		panelMargin.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelMargin.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(33, 30, 327, 183);
		panelMargin.add(panel);
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
	}
	private void createPaneText() {
		String[] lblAlign = {"Left","Center","Right","Justify"};
		
		attributi = new SimpleAttributeSet();
		
		panelText = new JPanel();
		panelText.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelText.setLayout(null);
		
		textArea = new JTextPane();
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(10,10,372,80);
		textArea.setBorder(new LineBorder(Color.BLACK));
		textArea.setEditable(false);
		textArea.setCharacterAttributes(attributi,false);
		panelText.add(textArea);
		
		/*
		JScrollPane scroll = new JScrollPane(textArea);
		panelText.add(scroll);
		*/
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 100, 372, 100);
		panelText.add(panel);
		panel.setLayout(null);
		
		JLabel lb1 = new JLabel("Alignment");
		lb1.setBounds(10,10,80,30);
		panel.add(lb1);
		
		alignment = new JComboBox(lblAlign);
		alignment.setBounds(90,10,80,30);
		alignment.addItemListener(this);
		panel.add(alignment);
		
		JLabel lb2 = new JLabel("Line spacing");
		lb2.setBounds(180,10,80,30);
		panel.add(lb2);
		
		lineSpacing = new JTextField();
		lineSpacing.setBounds(280,10,80,30);
		lineSpacing.addFocusListener(this);
		panel.add(lineSpacing);
		
		JLabel lb3 = new JLabel("Left");
		lb3.setBounds(10,50,80,30);
		panel.add(lb3);
		
		txtX = new JTextField();
		txtX.setBounds(90,50,80,30);
		txtX.addFocusListener(this);
		panel.add(txtX);
		
		JLabel lb4 = new JLabel("Top");
		lb4.setBounds(180,50,80,30);
		panel.add(lb4);
		
		txtY = new JTextField();
		txtY.setBounds(280,50,80,30);
		txtY.addFocusListener(this);
		panel.add(txtY);
	}
	private void createPaneBackground() {
		panelBackground = new JPanel();
		panelBackground.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelBackground.setLayout(null);
		
		panelAnteprima = new JPanel();
		panelAnteprima.setBorder(new LineBorder(refCell.getColorStroke()));
		if(refCell.getColorFill() == null)
			panelAnteprima.setBackground(GRSetting.COLORTRANSPARENT);
		else
			panelAnteprima.setBackground(refCell.getColorFill());
		panelAnteprima.setBounds(96,10,200,80);
		panelBackground.add(panelAnteprima);
		
		JCheckBox chkPersonalizza = new JCheckBox();
		chkPersonalizza.setText("Colore personalizzato");
		chkPersonalizza.setBounds(20,100,280,26);
		panelBackground.add(chkPersonalizza);
		
		JPanel panelColori = new JPanel();
		panelColori.setBorder(new TitledBorder(null, "Colore cella personalizzato", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelColori.setBounds(20,135,362,90);
		panelColori.setLayout(null);
		panelBackground.add(panelColori);
		
		JLabel lblBordo = new JLabel("Bordo");
		lblBordo.setBounds(20,17,60,30);
		panelColori.add(lblBordo);
		
		buttonBordo = new JButton("Modifica...");
		buttonBordo.setBounds(70,17,120,24);
		buttonBordo.addActionListener(this);
		panelColori.add(buttonBordo);
		
		JLabel lblSfondo = new JLabel("Sfondo");
		lblSfondo.setBounds(20,50,60,30);
		panelColori.add(lblSfondo);
		
		buttonSfondo = new JButton("Modifica...");
		buttonSfondo.setBounds(70,50,120,24);
		buttonSfondo.addActionListener(this);
		panelColori.add(buttonSfondo);
	}
	public void setData() {
		if(grtext == null)
			return;
		
		GRTextFormatted grtextFormatted = grtext.getTextFormatted();
		
		/* Caselle di testo */
		lineSpacing.setText(""+grtext.getLineSpacing());
		txtX.setText(""+GRLibrary.fromPixelsToMillimeters(grtext.getLeft()));
		txtY.setText(""+GRLibrary.fromPixelsToMillimeters(grtext.getTop()));
		
		StyledDocument doc = textArea.getStyledDocument();
		StyleConstants.setAlignment(attributi,grtextFormatted.getAlignment());
		textArea.setParagraphAttributes(attributi,true);
		
		//textArea.setAlignmentX(CENTER_ALIGNMENT);
		for(int i = 0;i < grtextFormatted.getTotaleElement();i++) {
			GRTextFormattedElement grelem = grtextFormatted.getElement(i);
						
			StyleConstants.setFontFamily(attributi,grelem.getFontName());
			StyleConstants.setFontSize(attributi,grelem.getFontSize());
			StyleConstants.setItalic(attributi,grelem.isItalic());
			StyleConstants.setBold(attributi,grelem.isBold());
			StyleConstants.setForeground(attributi,grelem.getFontColor());
			
			textArea.setCharacterAttributes(attributi,false);
			
			try {
				doc.insertString(doc.getLength(), grelem.getValue(), attributi);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//textArea.setFont(new Font(fontName,Font.PLAIN,fontSize));
		}
		
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			refreshCell();
			
			refCell.refresh();
		} else if(e.getSource() == closeButton) {
			this.dispose();
		} else if(e.getSource() == buttonBordo) {
			GRColor c = GRDialogColorChooser.showDialog(refCell.getColorStroke(),false);
		
			if(c != null) {
				panelAnteprima.setBorder(new LineBorder(new Color(c.getRed(),c.getGreen(),c.getBlue())));
				refCell.setColorStroke(c.getColor());
			} else {
				
			}
		} else if(e.getSource() == buttonSfondo) {
			GRColor c = GRDialogColorChooser.showDialog(refCell.getColorFill(),true);
			
			if(c != null) {
				if(c.isTransparent()) {
					panelAnteprima.setBackground(GRSetting.COLORTRANSPARENT);
					refCell.setColorFill(null);
				} else {
					panelAnteprima.setBackground(c.getColor());
					refCell.setColorFill(c.getColor());
				}
			}
		}
	}
	private void refreshCell() {
		/* PanelMargin */
		if(this.isMarginNumeric()) {
			
			refCell.setMarginLeft(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtLeft.getText())));
			refCell.setMarginTop(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtTop.getText())));
			refCell.setMarginRight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtRight.getText())));
			refCell.setMarginBottom(GRLibrary.fromMillimetersToPixels(Double.parseDouble(txtBottom.getText())));
			
		} else {
			JOptionPane.showMessageDialog(this,"Il valore specificato per i margini deve essere un valore intero numerico!","Errore",JOptionPane.ERROR_MESSAGE);
		
			return;
		}
		
		/* PanelText */
		if(refCell.getText() != null) {
			refCell.getText().setLineSpacing(Float.parseFloat(lineSpacing.getText()));
			refCell.getText().setFontAlignment(alignment.getSelectedItem().toString());
			refCell.getText().setLeft(GRLibrary.fromMillimetersToPixels(txtX.getText()));
			refCell.getText().setTop(GRLibrary.fromMillimetersToPixels(txtY.getText()));
			
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

	public void focusGained(FocusEvent e) {
		JTextField t = (JTextField)e.getSource();
		t.setSelectionStart(0);
		t.setSelectionEnd(t.getText().length());
	}
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(e.getSource() == alignment) {
				StyledDocument doc = textArea.getStyledDocument();
				
				if(e.getItem().equals("Left"))
					StyleConstants.setAlignment(attributi,StyleConstants.ALIGN_LEFT);
				else if(e.getItem().equals("Center"))
					StyleConstants.setAlignment(attributi,StyleConstants.ALIGN_CENTER);
				else if(e.getItem().equals("Right"))
					StyleConstants.setAlignment(attributi, StyleConstants.ALIGN_RIGHT);
				else if(e.getItem().equals("Justify"))
					StyleConstants.setAlignment(attributi, StyleConstants.ALIGN_JUSTIFIED);
				
				doc.setParagraphAttributes(0,doc.getLength(),attributi,true);
				//textArea.setParagraphAttributes(attributi,true);
			} 
		}
		
		textArea.requestFocusInWindow();	
	}
}
