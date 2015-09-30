/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.text.GRTextFormatted
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
package com.globalreports.editor.graphics.text;

import java.awt.Color;
import java.util.Vector;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.graphics.GRText;

public class GRTextFormatted {
	private Vector<GRTextFormattedElement> grelem;
	private Document dc;
	private int textAlignment;
	private String value;
	
	/* Le proprietà che seguono sono relative ai singoli token */
	private String fName;
	private int fSize;
	private int fStyle;
	boolean fontNameMultiplo;
	boolean fontSizeMultiplo;
	boolean fontStyleMultiplo;
	
	public GRTextFormatted() {
		this.dc = new DefaultStyledDocument();
		this.textAlignment = GRSetting.FONTALIGNMENT;
		this.value = "";
		this.fName = "";
		this.fSize = 0;
		this.fStyle = GRText.STYLETEXT_NONE;
		
		fontNameMultiplo = false;
		fontSizeMultiplo = false;
		fontStyleMultiplo = false;
	}
	public GRTextFormatted(Document dc, int alignment, String value) {
		this.dc = dc;
		this.textAlignment = alignment;
		this.value = value;
			
		this.init();
	}
	private void init() {
		grelem = new Vector<GRTextFormattedElement>();
		
		Element e = dc.getDefaultRootElement();
		
		fontNameMultiplo = false;
		fontSizeMultiplo = false;
		fontStyleMultiplo = false;
		int fStyle = GRText.STYLETEXT_NORMAL;
		this.fName = "";
		this.fSize = 0;
		this.fStyle = GRText.STYLETEXT_NONE;
		
		// Cicla per tutti i token formattati
		
		for(int x = 0;x < e.getElementCount();x++) {
			Element et = e.getElement(x);
					
			for(int y = 0;y < et.getElementCount();y++) {
				try {
					Element et2 = et.getElement(y);
					AttributeSet att = et2.getAttributes();
						
					String fName = (String)att.getAttribute(StyleConstants.FontFamily);
					if(!this.fName.equals("") && !this.fName.equals(fName))
						fontNameMultiplo = true;
					this.fName = fName;
					
					int start = et2.getStartOffset();
					int end = et2.getEndOffset() - start;
					String testo = dc.getText(start,end);
					int fSize = (Integer)(att.getAttribute(StyleConstants.FontSize));
					if(this.fSize != 0 && this.fSize != fSize)
						fontSizeMultiplo = true;
					this.fSize = fSize;
					
					Color fColor = (Color)(att.getAttribute(StyleConstants.Foreground));
					boolean fBold = (Boolean)(att.getAttribute(StyleConstants.Bold));
					boolean fItalic = (Boolean)(att.getAttribute(StyleConstants.Italic));
					if(fBold)
						fStyle = fStyle + GRText.STYLETEXT_BOLD;
					if(fItalic)
						fStyle = fStyle + GRText.STYLETEXT_ITALIC;
					if(this.fStyle != GRText.STYLETEXT_NONE && this.fStyle != fStyle)
						fontStyleMultiplo = true;
					this.fStyle = fStyle;
					
					if(testo.codePointAt(0) == 10 && x == (e.getElementCount()-1))
						break;
					
					grelem.add(new GRTextFormattedElement(fName,fSize,fBold,fItalic,fColor,testo));
				} catch(BadLocationException ble) {
					System.out.println("GRTextFormatted::init::BadLocationException: "+ble.getMessage());
				}
			}
		}
		
		if(fontNameMultiplo)
			this.fName = "";
		if(fontSizeMultiplo)
			this.fSize = 0;
		if(fontStyleMultiplo)
			this.fStyle = GRText.STYLETEXT_NONE;
		/*
		System.out.println("DEBUG");
		System.out.println("TOTALE ELEMENTI: "+e.getElementCount());
		for(int x = 0;x < e.getElementCount();x++) {
			Element et = e.getElement(x);
			
			System.out.println("Elemento: "+x);
			System.out.println("Totale sottofigli: "+et.getElementCount());
			
			for(int y = 0;y < et.getElementCount();y++) {
				Element et2 = et.getElement(y);
				System.out.println("Sottoelement: "+y);
				System.out.println("Totale sottoelementi: "+et2.getElementCount());

				int start = et2.getStartOffset();
				int end = et2.getEndOffset();
				int end2 = end - start;
				System.out.println("START: "+start+" - END: "+end);
				//String testo = value.substring(start,end);

				//System.out.println("TESTO: "+testo);
				System.out.println("LEN: "+et2.getDocument().getLength());
				try {
					String testo = et2.getDocument().getText(start,end2);
				System.out.println("TEXT: "+testo);
				for(int q = 0;q < testo.length();q++)
					System.out.println(testo.codePointAt(q));
				} catch(Exception ee) {
					System.out.println("Errore TEXT: "+ee.getMessage());
				}
				
			}
		}
		*/
		
	}
	public void addElement(String fName, int fSize, int fStyle, Color fColor, String testo) {
		if(grelem == null)
			grelem = new Vector<GRTextFormattedElement>();
		
		boolean fBold = false;
		boolean fItalic = false;
		
		if(fStyle == GRText.STYLETEXT_BOLD || fStyle == GRText.STYLETEXT_BOLDITALIC)
			fBold = true;
		
		if(fStyle == GRText.STYLETEXT_ITALIC || fStyle == GRText.STYLETEXT_BOLDITALIC)
			fItalic = true;
		
		if(!this.fName.equals("") && !this.fName.equals(fName))
			fontNameMultiplo = true;
		if(fontNameMultiplo)
			this.fName = "";
		else
			this.fName = fName;
		
		if(this.fSize != 0 && this.fSize != fSize)
			fontSizeMultiplo = true;
		if(fontSizeMultiplo)
			this.fSize = 0;
		else
			this.fSize = fSize;
		
		if(this.fStyle != GRText.STYLETEXT_NONE && this.fStyle != fStyle)
			fontStyleMultiplo = true;
		if(fontStyleMultiplo)
			this.fStyle = GRText.STYLETEXT_NONE;
		else
			this.fStyle = fStyle;
		
		grelem.add(new GRTextFormattedElement(fName,fSize,fBold,fItalic,fColor,testo));
		
		/* Imposta gli attributi al Document */
		
	}
	public Document getDocument() {
		return dc;
	}
	public String getText() {
		return value;
	}
	public int getAlignment() {
		return textAlignment;
	}
	public void setAlignment(int alignment) {
		this.textAlignment = alignment;
	}
	public int getTotaleElement() {
		return grelem.size();
	}
	public GRTextFormattedElement getElement(int i) {
		return grelem.get(i);
	}
	
	/* Proprietà relative ai singoli token */
	public String getFontName() {
		return fName;
	}
	public void setFontFamily(String fName) {
		// Assegna il font a tutti i token
		for(int i = 0;i < grelem.size();i++)
			grelem.get(i).setFontName(fName);
		
		this.fName = fName;
	}
	public void setFontSize(int fSize) {
		// Assegna la dimensione a tutti i token
		for(int i = 0;i < grelem.size();i++)
			grelem.get(i).setFontSize(fSize);
		
		this.fSize = fSize;
	}
	public int getFontSize() {
		return fSize;
	}
	public int getFontStyle() {
		return fStyle;
	}
	public void setFontStyle(int fStyle) {
		// Assegna lo stile a tutti i token
		for(int i = 0;i < grelem.size();i++)
			grelem.get(i).setFontStyle(fStyle);
		
		this.fStyle = fStyle;
	}
}
