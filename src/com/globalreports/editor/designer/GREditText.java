/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GREditText
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
package com.globalreports.editor.designer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.graphics.text.GRTextFormatted;
import com.globalreports.editor.graphics.text.GRTextFormattedElement;

@SuppressWarnings("serial")
public class GREditText extends JDialog implements ActionListener, ItemListener, MouseListener, FocusListener {
	public static final int 	NEWTEXT				= 1;
	public static final int 	MODIFYTEXT			= 2;
	
	public static final int		CONTEXT_PAGE		= 1;
	public static final int		CONTEXT_TABLELIST	= 2;
	private int typeDialog;
	private GRPage grpage;		// Pagina in cui andr� inserito il testo
	
	private JToolBar toolbar;
	@SuppressWarnings("rawtypes")
	private JComboBox selFontName;
	@SuppressWarnings("rawtypes")
	private JComboBox selFontSize;
	private JToggleButton bFontBold;
	private JToggleButton bFontItalic;
	private JButton bFontColor;
	
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
	private Rectangle areaClip;	// l'area ove comparir� il testo
	
	private int pointerCaret;
	private int context;
	
	public GREditText(GRPage page, Rectangle r) {
		this(page, r, GREditText.CONTEXT_PAGE);
	}
	public GREditText(GRPage page, Rectangle r, int context) {
		//super(null,"Edit Text",true);
		
		typeDialog = GREditText.NEWTEXT;
		this.context = context;
		
		grpage = page;
		areaClip = r;
		
		flagButtonChanged = false;
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		this.defaultValue();
		
		toolbar = this.createToolBar();
		c.add(toolbar,BorderLayout.NORTH);
		
		textArea = new JTextPane();
		textArea.setBackground(Color.WHITE);
		textArea.setCharacterAttributes(attributi,false);
		textArea.addMouseListener(this);
		textArea.addFocusListener(this);
		JScrollPane scroll = new JScrollPane(textArea);
		c.add(scroll,BorderLayout.CENTER);
		
		panelButton = new JPanel();
		panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		bConfirm = new JButton("Conferma");
		bConfirm.addActionListener(this);
		panelButton.add(bConfirm);
		
		bExit = new JButton("Chiudi");
		bExit.addActionListener(this);
		panelButton.add(bExit);
		
		c.add(panelButton,BorderLayout.SOUTH);
		
		setTitle("Edit Text");
		setSize(600,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		
		tb.setFloatable(false);
		
		return tb;
	}
	
	private void defaultValue() {
		fontName = GRSetting.FONTNAME;
		fontSize = GRSetting.FONTSIZE;
		fontStyle = GRText.STYLETEXT_NORMAL;
		alignment = GRText.ALIGNTEXT_LEFT;
		
		attributi = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attributi,fontName);
		StyleConstants.setFontSize(attributi,fontSize);
		StyleConstants.setAlignment(attributi,StyleConstants.ALIGN_RIGHT);
		
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
			
			
			if(typeDialog == GREditText.NEWTEXT) {
				if(context == GREditText.CONTEXT_PAGE) 
					grpage.insertText(dc, textArea.getText(), areaClip);
				else if(context == GREditText.CONTEXT_TABLELIST)
					grpage.insertTextCell(dc, textArea.getText(), areaClip);
			} else {
				grpage.modifyText(id, dc, textArea.getText());
			}
			this.dispose();
		} else if(e.getSource() == bExit) {
			grpage.annullaInsertText();
			this.dispose();
		} else if(e.getSource() == bFontBold) {
			this.changeFontStyle();
			flagButtonChanged = true;
		} else if(e.getSource() == bFontItalic) {
			this.changeFontStyle();
			flagButtonChanged = true;
		} else if(e.getSource() == bFontColor) {
			Color c = null;
			AttributeSet a = textArea.getCharacterAttributes();
			
			c = JColorChooser.showDialog(this, "Color Chooser", (Color)a.getAttribute(StyleConstants.Foreground));
		
			if(c == null) 
				return;
				StyleConstants.setForeground(attributi, c);
			//StyleConstants.setForeground(attributi,true);
			textArea.setCharacterAttributes(attributi,false);
			//panelColor.setColorStroke(c);
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

			fontName = grelem.getFontName();
			fontSize = grelem.getFontSize();
			
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
		this.showDialog(GREditText.NEWTEXT);
		
		textArea.requestFocusInWindow();
	}

	public void mouseClicked(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		/*
		
		if(flagButtonChanged) {
			this.changeFontStyle();
		} else {
			if(textArea.getCaretPosition() == textArea.getText().length())
				System.out.println("ULTIMA");
			else {
				System.out.println(textArea.getCaretPosition());
				AttributeSet a = textArea.getCharacterAttributes();
				
				bFontBold.setSelected((Boolean)a.getAttribute(StyleConstants.Bold));
				bFontItalic.setSelected((Boolean)a.getAttribute(StyleConstants.Italic));
				selFontName.setSelectedItem((String)a.getAttribute(StyleConstants.FontFamily));
				selFontSize.setSelectedItem((Integer)a.getAttribute(StyleConstants.FontSize));
				
			}
		}
		
		flagButtonChanged = false;
		*/
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