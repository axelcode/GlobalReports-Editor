/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogEditText
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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.dialog.GRDialogColorChooser;
import com.globalreports.editor.designer.resources.GRColor;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.graphics.text.GRTextFormatted;
import com.globalreports.editor.graphics.text.GRTextFormattedElement;

@SuppressWarnings("serial")
public class GRDialogEditText extends JDialog implements ActionListener, ItemListener, MouseListener, FocusListener {
	public static final int 	NEWTEXT				= 1;
	public static final int 	MODIFYTEXT			= 2;
	
	public static final int		CONTEXT_PAGE		= 1;
	public static final int		CONTEXT_TABLELIST	= 2;
	
	private static final int	FUNCTIONVALIDATE_NOTEMPTY			= 1;
	private static final int	FUNCTIONVALIDATE_DATE				= 2;
	private static final int	FUNCTIONVALIDATE_NUMBER				= 3;
	private static final int	FUNCTIONVALIDATE_EMAIL				= 4;
	private static final int	FUNCTIONVALIDATE_CODICEFISCALE		= 5;
	
	private static final int	FUNCTIONFORMATTED_LOWERCASE			= 1;
	private static final int	FUNCTIONFORMATTED_UPPERCASE			= 2;
	private static final int 	FUNCTIONFORMATTED_FIRSTUPPERCASE	= 3;
	private static final int	FUNCTIONFORMATTED_FORMATNUMBER		= 4;
	
	String functionValidate[] = {"--Funzioni di validazione--",
								 "Campo obbligatorio",
								 "Campo data",
								 "Campo numerico",
								 "Campo e-mail",
		 						 "Campo codice fiscale"};

	String functionFormatted[] = {"--Funzioni di formattazione--",
								  "Campo in minuscolo",
								  "Campo in maiuscolo",
								  "Prima lettera in maiuscolo",
		  						  "Numero e decimali"};

	private int typeDialog;
	private GRDialogTextCondition father;
	
	private JToolBar toolbar;
	@SuppressWarnings("rawtypes")
	private JComboBox selFontName;
	@SuppressWarnings("rawtypes")
	private JComboBox selFontSize;
	private JToggleButton bFontBold;
	private JToggleButton bFontItalic;
	private JButton bFontColor;
	private JToggleButton bVariable;
	
	private JTextPane textArea;
	private JPanel panelButton;
	private JButton bConfirm;
	private JButton bExit;
	
	private SimpleAttributeSet attributi;
	
	private long id;
	private String fontName;
	private int fontSize;
	private int fontStyle;
	private int alignment;
	
	boolean flagButtonChanged;
	private Rectangle areaClip;	// l'area ove comparirà il testo
	
	private int pointerCaret;
	private int context;
	
	// Sezione variabili
	private JButton bInsertVariabile;
	private JTextField txtNomeVariabile;
	private JComboBox comboFunctionValidate;
	private JComboBox comboFunctionFormatted;
	private JLabel lblNumDecimali;
	private JTextField txtNumDecimali;
	
	private Container c;
	private JPanel panelVariable;
	
	private JComboBox comboCondition;
	private JButton bAddCondition;
	
	public GRDialogEditText(GRDialogTextCondition father, Rectangle r) {
		typeDialog = NEWTEXT;
		
		this.father = father;
		areaClip = r;
		
		flagButtonChanged = false;
		
		c = getContentPane();
		c.setLayout(new BorderLayout());
		
		this.defaultValue();
		
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(2,0));
		
		toolbar = this.createToolBar();
		
		c.add(toolbar,BorderLayout.NORTH);
		
		textArea = new JTextPane();
		textArea.setBackground(Color.WHITE);
		textArea.setCharacterAttributes(attributi,false);
		textArea.addMouseListener(this);
		textArea.addFocusListener(this);
		JScrollPane scroll = new JScrollPane(textArea);
		
		c.add(scroll,BorderLayout.CENTER);
		
		JPanel panelSouth = new JPanel();
		GridBagLayout gbSouth = new GridBagLayout();
		GridBagConstraints gbcSouth = new GridBagConstraints();
		panelSouth.setLayout(gbSouth);
		
		gbSouth.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0, 0.0};
		
		JButton bWindowCondition = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"condition.png"));
		gbcSouth.insets = new Insets(2,2,2,2);
		gbcSouth.gridx = 0;
		gbcSouth.gridy = 0;
		panelSouth.add(bWindowCondition,gbcSouth);
		
		JButton bAddCondition = new JButton(new ImageIcon(GRSetting.PATHIMAGE+"add.png"));
		gbcSouth.insets = new Insets(2,2,2,2);
		gbcSouth.gridx = 1;
		gbcSouth.gridy = 0;
		panelSouth.add(bAddCondition,gbcSouth);
		
		JTextField txtCondition = new JTextField();
		gbcSouth.fill = GridBagConstraints.HORIZONTAL;
		gbcSouth.insets = new Insets(2,2,2,2);
		gbcSouth.gridx = 2;
		gbcSouth.gridy = 0;
		panelSouth.add(txtCondition,gbcSouth);
		
		//panelButton = new JPanel();
		//panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		bConfirm = new JButton("Conferma");
		bConfirm.addActionListener(this);
		gbcSouth.insets = new Insets(2,2,2,2);
		gbcSouth.gridx = 3;
		gbcSouth.gridy = 0;
		panelSouth.add(bConfirm,gbcSouth);
		
		//panelButton.add(bConfirm);
		
		bExit = new JButton("Chiudi");
		bExit.addActionListener(this);
		gbcSouth.insets = new Insets(2,2,2,2);
		gbcSouth.gridx = 4;
		gbcSouth.gridy = 0;
		panelSouth.add(bExit,gbcSouth);
		//panelButton.add(bExit);
		
		//panelSouth.add(panelButton);
		
		c.add(panelSouth,BorderLayout.SOUTH);
		
		// Pannello delle variabili
		panelVariable = getPanelVariables();
		//c.add(getPanelVariables(), BorderLayout.EAST);
		
		setTitle("Edit Text");
		setSize(600,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(false);
		
		pointerCaret = 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JToolBar createToolBar() {
		JToolBar tb = new JToolBar();
		//GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//String[] fontnames = e.getAvailableFontFamilyNames();
		// Vengono presentati solamente i Font supportati da Global Reports
		String[] fontsizes = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};
		
		tb.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		selFontName = new JComboBox();
		for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
			selFontName.addItem(GRFontProperty.grfontInstalled.get(i).getBaseName());
		}
		
		selFontName.setSelectedItem(fontName);
		selFontName.addItemListener(this);
		tb.add(selFontName);
		
		selFontSize = new JComboBox(fontsizes);
		selFontSize.setSelectedItem(""+fontSize);
		selFontSize.setEditable(true);
		selFontSize.addItemListener(this);
		tb.add(selFontSize);
		
		tb.addSeparator();
		
		ImageIcon ico_fontbold = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontbold.png");
		bFontBold = new JToggleButton(ico_fontbold);
		bFontBold.setSelected(false);
		bFontBold.addActionListener(this);
		tb.add(bFontBold);
		
		ImageIcon ico_fontitalic = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontitalic.png");
		bFontItalic = new JToggleButton(ico_fontitalic);
		bFontItalic.setSelected(false);
		bFontItalic.addActionListener(this);
		tb.add(bFontItalic);
		
		ImageIcon ico_fontcolor = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontcolor.png");
		bFontColor = new JButton(ico_fontcolor);
		bFontColor.addActionListener(this);
		tb.add(bFontColor);
		
		tb.addSeparator();
		
		ImageIcon ico_variable = new ImageIcon(GRSetting.PATHIMAGE+"ico_variable.png");
		bVariable = new JToggleButton(ico_variable);
		bVariable.addActionListener(this);
		tb.add(bVariable);
		
		tb.setFloatable(false);
		
		return tb;
	}
	
	private JPanel getPanelVariables() {
		JLabel lblNomeVariabile;
		
		JPanel panel = new JPanel();
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		layout.columnWidths = new int[]{100, 0};
		layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(layout);
				
		lblNomeVariabile = new JLabel("Nome Variabile");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5,2,5,2);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(lblNomeVariabile,gbc);
		
		txtNomeVariabile = new JTextField(20);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0,2,5,2);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(txtNomeVariabile,gbc);
		
		comboFunctionValidate = new JComboBox(functionValidate);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,2,5,2);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(comboFunctionValidate,gbc);
		
		comboFunctionFormatted = new JComboBox(functionFormatted);
		comboFunctionFormatted.addItemListener(this);
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,2,5,2);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panel.add(comboFunctionFormatted,gbc);
		
		lblNumDecimali = new JLabel("Decimali");
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0,2,5,2);
		gbc.gridx = 0;
		gbc.gridy = 4;
		panel.add(lblNumDecimali,gbc);
		
		txtNumDecimali = new JTextField(2);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,2,5,2);
		gbc.gridx = 1;
		gbc.gridy = 4;
		panel.add(txtNumDecimali,gbc);
		
		lblNumDecimali.setVisible(false);
		txtNumDecimali.setVisible(false);
		
		bInsertVariabile = new JButton("Inserisci");
		bInsertVariabile.addActionListener(this);
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(10,2,5,2);
		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(bInsertVariabile,gbc);
		
		return panel;
	}
	private void defaultValue() {
		fontName = GRSetting.FONTNAME;
		fontSize = GRSetting.FONTSIZE;
		fontStyle = GRText.STYLETEXT_NORMAL;
		alignment = GRText.ALIGNTEXT_LEFT;
		
		attributi = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attributi,fontName);
		StyleConstants.setFontSize(attributi,fontSize);
		StyleConstants.setAlignment(attributi,StyleConstants.ALIGN_LEFT);
		
	}
	
	private void changeFontStyle() {
		fontStyle = GRText.STYLETEXT_NORMAL;
		
		if(bFontItalic.isSelected()) {
			fontStyle += GRText.STYLETEXT_ITALIC;
	
			StyleConstants.setItalic(attributi,true);
			textArea.setCharacterAttributes(attributi,false);
		} else {
			StyleConstants.setItalic(attributi,false);
			textArea.setCharacterAttributes(attributi,false);
		}
		
		if(bFontBold.isSelected()) {
			fontStyle += GRText.STYLETEXT_BOLD;
			
			StyleConstants.setBold(attributi,true);
			textArea.setCharacterAttributes(attributi,false);
		} else {
			StyleConstants.setBold(attributi,false);
			textArea.setCharacterAttributes(attributi,false);
		}
		
	}
	
	private String getVariable(String name, int valValidate, int valFormatted) {
		String value = "";
		int numDec = 0;
		
		if(name == null || name.equals(""))
			return "";
		
		value = "{" + name.trim();
		
		if(valValidate > 0) {
			value = value + ":";
			
			switch(valValidate) {
				case FUNCTIONVALIDATE_NOTEMPTY:
					value = value + "$NOTEMPTY";
					break;
					
				case FUNCTIONVALIDATE_DATE:
					value = value + "$DATE";
					break;
					
				case FUNCTIONVALIDATE_NUMBER:
					value = value + "$NUMBER";
					break;
				
				case FUNCTIONVALIDATE_EMAIL:
					value = value + "$EMAIL";
					break;
					
				case FUNCTIONVALIDATE_CODICEFISCALE:
					value = value + "$CODICEFISCALE";
					break;
			}
		}
		
		if(valFormatted > 0) {
			if(valValidate > 0)
				value = value + ";";
			else
				value = value + ":";
			
			switch(valFormatted) {
				case FUNCTIONFORMATTED_LOWERCASE:
					value = value + "FUNCTION(LOWERCASE)";
					break;
					
				case FUNCTIONFORMATTED_UPPERCASE:
					value = value + "FUNCTION(UPPERCASE)";
					break;
					
				case FUNCTIONFORMATTED_FIRSTUPPERCASE:
					value = value + "FUNCTION(FIRSTUPPERCASE)";
					break;
					
				case FUNCTIONFORMATTED_FORMATNUMBER:
					value = value + "FUNCTION(FORMATNUMBER(";
					if(!txtNumDecimali.getText().trim().equals("")) {
						numDec = Integer.parseInt(txtNumDecimali.getText());
					}
					value = value + numDec+"))";
					
					break;
			}
		}
		
		value = value + "}";
		
		return value;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bConfirm) {
			Document dc = null;
			
			try {
				dc = textArea.getDocument();
				/*
				System.out.println(epadre.getElementCount());
				
				AttributeSet a1 = epadre.getElement(0).getAttributes();
				System.out.println(a1.getAttribute(StyleConstants.FontSize));
				System.out.println(a1.getAttribute(StyleConstants.FontFamily));
				System.out.println(a1.getAttribute(StyleConstants.Bold));
				
				System.out.println(epadre.getElement(0).getAttributes());
				//System.out.println(epadre.getElement(1).getAttributes());
				//System.out.println(epadre.getElement(2).getAttributes());
				
				int start = epadre.getElement(0).getStartOffset();
				int end = epadre.getElement(0).getEndOffset();
				System.out.println(epadre.getElement(1).getStartOffset()+ " - "+epadre.getElement(1).getEndOffset());
				System.out.println(textArea.getText().substring(start,end));
				*/
			} catch(Exception ex) {}
			
			
			if(typeDialog == NEWTEXT) {
				father.insertText(dc, textArea.getText());
			} else {
				//grpage.modifyText(id, dc, textArea.getText());
			}
			this.dispose();
		} else if(e.getSource() == bExit) {
			this.dispose();
		} else if(e.getSource() == bFontBold) {
			this.changeFontStyle();
			flagButtonChanged = true;
		} else if(e.getSource() == bFontItalic) {
			this.changeFontStyle();
			flagButtonChanged = true;
		} else if(e.getSource() == bFontColor) {
			GRColor c = null;
			AttributeSet a = textArea.getCharacterAttributes();
			
			//c = JColorChooser.showDialog(this, "Color Chooser", (Color)a.getAttribute(StyleConstants.Foreground));
			c = GRDialogColorChooser.showDialog((Color)a.getAttribute(StyleConstants.Foreground),false);
			
			if(c == null) 
				return;
				
			StyleConstants.setForeground(attributi, c.getColor());
			//StyleConstants.setForeground(attributi,true);
			textArea.setCharacterAttributes(attributi,false);
			//panelColor.setColorStroke(c);
		} else if(e.getSource() == bInsertVariabile) {
			AttributeSet a = textArea.getCharacterAttributes();
			Document d = textArea.getDocument();
			
			String var = getVariable(txtNomeVariabile.getText(),comboFunctionValidate.getSelectedIndex(),comboFunctionFormatted.getSelectedIndex());
			
			try {
				d.insertString(textArea.getCaretPosition(), var,a);
			} catch(Exception exc) {
				System.out.println("GREditText::actionPerformed::Exception: "+exc.getMessage());
			}
			//textArea.setText(txtNomeVariabile.getText());
		} else if(e.getSource() == bVariable) {
			
			if(bVariable.isSelected()) {
				c.add(panelVariable, BorderLayout.EAST);
				
				this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));
			} else {
				c.remove(panelVariable);
				
				this.setSize(new Dimension(this.getWidth()-200,this.getHeight()));
			}
			
		} else if(e.getSource() == bAddCondition) {
			
		}
		
		textArea.requestFocusInWindow();
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(e.getSource() == selFontName) {
				fontName = ""+e.getItem();
				
				StyleConstants.setFontFamily(attributi,fontName);
				textArea.setCharacterAttributes(attributi,false);
			} else if(e.getSource() == selFontSize) {
				fontSize = Integer.parseInt(""+e.getItem());
				
				StyleConstants.setFontSize(attributi,fontSize);
				textArea.setCharacterAttributes(attributi,false);
			} else if(e.getSource() == comboFunctionFormatted) {
				if(comboFunctionFormatted.getSelectedIndex() == FUNCTIONFORMATTED_FORMATNUMBER) {
					lblNumDecimali.setVisible(true);
					txtNumDecimali.setVisible(true);	
				} else {
					lblNumDecimali.setVisible(false);
					txtNumDecimali.setVisible(false);
				}
				
				this.setSize(new Dimension(this.getWidth()+1,this.getHeight()));
				this.setSize(new Dimension(this.getWidth()-1,this.getHeight()));
				
			}
		}
		
		textArea.requestFocusInWindow();	
	}
	
	public void setFont(String fontName, int fontSize, int fontStyle) {
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontStyle = fontStyle;
		
		selFontName.setSelectedItem(fontName);
		selFontSize.setSelectedItem(fontSize);
		
		bFontBold.setSelected(false);
		bFontItalic.setSelected(false);
		switch(fontStyle) {
			case GRText.STYLETEXT_BOLD:
				bFontBold.setSelected(true);
				break;
				
			case GRText.STYLETEXT_ITALIC:
				bFontItalic.setSelected(true);
				break;
				
			case GRText.STYLETEXT_BOLDITALIC:
				bFontBold.setSelected(true);
				bFontItalic.setSelected(true);
				break;
		}
		
		StyleConstants.setFontFamily(attributi,fontName);
		StyleConstants.setFontSize(attributi,fontSize);
		StyleConstants.setItalic(attributi,bFontItalic.isSelected());
		StyleConstants.setBold(attributi,bFontBold.isSelected());
		
		textArea.setCharacterAttributes(attributi,false);
		textArea.setFont(new Font(fontName,Font.PLAIN,fontSize));
		
	}
	public void setData(long idObj, GRTextFormatted grtext) {
		String fontName;
		int fontSize;
		
		id = idObj;
		StyledDocument doc = textArea.getStyledDocument();
		
		for(int i = 0;i < grtext.getTotaleElement();i++) {
			GRTextFormattedElement grelem = grtext.getElement(i);
					
			StyleConstants.setFontFamily(attributi,grelem.getFontName());
			StyleConstants.setFontSize(attributi,grelem.getOriginalFontSize());
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

			fontName = grelem.getFontName();
			fontSize = grelem.getOriginalFontSize();
			
			textArea.setFont(new Font(fontName,Font.PLAIN,fontSize));
		}
		
				
	}
	public void setData(long idObj,String fName,int fSize,int fStyle,String value) {
		id = idObj;
		
		fontName = fName;
		fontSize = fSize;
		fontStyle = fStyle;
		
		selFontName.setSelectedItem(fontName);
				
		bFontBold.setSelected(false);
		bFontItalic.setSelected(false);
		switch(fStyle) {
			case GRText.STYLETEXT_BOLD:
				bFontBold.setSelected(true);
				break;
				
			case GRText.STYLETEXT_ITALIC:
				bFontItalic.setSelected(true);
				break;
				
			case GRText.STYLETEXT_BOLDITALIC:
				bFontBold.setSelected(true);
				bFontItalic.setSelected(true);
				break;
		}
		
		textArea.setText(value);
	}
	public void showDialog(int type) {
		typeDialog = type;
		
		setVisible(true);
		textArea.requestFocusInWindow();
		
		
	}
	public void showDialog() {
		this.showDialog(NEWTEXT);
		
		textArea.requestFocusInWindow();
	}

	public void mouseClicked(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		AttributeSet a = textArea.getCharacterAttributes();
		
		bFontBold.setSelected((Boolean)a.getAttribute(StyleConstants.Bold));
		bFontItalic.setSelected((Boolean)a.getAttribute(StyleConstants.Italic));
		selFontName.setSelectedItem((String)a.getAttribute(StyleConstants.FontFamily));
		selFontSize.setSelectedItem((Integer)a.getAttribute(StyleConstants.FontSize));
		textArea.setCharacterAttributes(a,false);
	}
	public void mouseReleased(MouseEvent e) {

	}

	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent arg0) {
		
		//pointerCaret = textArea.getCaretPosition();
		//System.out.println("FOCUS LOST: "+textArea.getCaretPosition());
	}
	
}