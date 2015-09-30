/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRText
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
package com.globalreports.editor.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.text.Document;
import javax.swing.text.Element;

import com.globalreports.compiler.gr.tools.GRDimension;
import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.font.GRFont;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModel;
import com.globalreports.editor.designer.property.GRTableModelRectangle;
import com.globalreports.editor.designer.property.GRTableModelText;
import com.globalreports.editor.designer.resources.GRResFonts;
import com.globalreports.editor.designer.resources.GRFontResource;
import com.globalreports.editor.graphics.text.*;
import com.globalreports.editor.tools.GRCharCodeSet;
import com.globalreports.editor.tools.GRLibrary;

public class GRText extends GRObject {
	
	private final String REG_TEXT = "\\[([a-zA-Z0-9\\-]+):([0-9]+)(:([0-9]+),([0-9]+),([0-9]+)){0,}\\]([a-zA-Z0-9 ,!£$%&;:\\\\\"\'\\?\\^\\.\\{\\}\\-\\/\\(\\)]+)";
	private final String REG_VARIABLE = "[{]([a-zA-Z0-9_]+)(:{0,1})([a-zA-Z0-9, =!$%&;\\\\\"\'\\?\\^\\.\\-\\/\\(\\)]{0,})[}]";
	
	public static final int ALIGNTEXT_LEFT			= 0;
	public static final int ALIGNTEXT_CENTER		= 1;
	public static final int ALIGNTEXT_RIGHT			= 2;
	public static final int ALIGNTEXT_JUSTIFY		= 3;
	public static final int ALIGNTEXT_SINGLELINE	= 4;
	
	public static final int STYLETEXT_NONE			= -1;
	public static final int STYLETEXT_NORMAL		= 0;
	public static final int STYLETEXT_BOLD			= 1;
	public static final int STYLETEXT_ITALIC		= 2;
	public static final int STYLETEXT_BOLDITALIC	= 3;
	
	private String value;
	
	private String idFont;
	private String fontName;
	private int fontSize;
	private int fontStyle;
	private int alignment;
	private float lineSpacing;
	private float lineSpacingOriginal;
	
	private Font font;
	private FontMetrics metrics;
	private Graphics gdc;	// Graphics Device Context
	
	private GRTextFormatted grtextFormatted;
	private GRParagraph gpar;
	
	private GRTableModelText modelTable;
	
	public GRText(GRPage grpage, Graphics g, long id) {
		super(GRObject.TYPEOBJ_TEXT,id,grpage);
		
		this.gdc = g;
		
		// Inizializza l'oggetto con valori predefiniti
		value = "";
		x1 = 0;
		y1 = 0;
		width = 0;
		hPosition = false;
		lineSpacing = GRSetting.LINESPACING;
		lineSpacingOriginal = lineSpacing;
		
		alignment = GRSetting.FONTALIGNMENT;
		
		grtextFormatted = new GRTextFormatted();
		
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		
		tsx = new Rectangle(x1-6,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(x1+width+2,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx = new Rectangle(x1-6,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx = new Rectangle(x1+width+2,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
	
		this.refreshReferenceSection();	
	}
	public GRText(GRPage grpage, Graphics g, long id, Document dc, int alignment, String value, Rectangle area) {
		super(GRObject.TYPEOBJ_TEXT, id,grpage);
		
		grtextFormatted = new GRTextFormatted(dc,alignment,value);
		
		this.gdc = g;
		this.value = value;
		
		x1 = area.x;
		y1 = area.y;
		width = area.width;
		hPosition = false;
		lineSpacing = GRSetting.LINESPACING;
		lineSpacingOriginal = lineSpacing;
		
		this.alignment = alignment;
		
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		
		insertTextFormatted();
		
		this.refreshReferenceSection();	
	}
	
	public void setProperty(GRTableModel model) {
		/*
		this.modelTable = (GRTableModelText)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
		*/
	}
	/*
	public void refreshProperty() {
		modelTable.setLeft(this.getOriginalX());
		modelTable.setTop(this.getOriginalY());
		modelTable.setWidth(this.getOriginalWidth());
		modelTable.setHPosition(this.getHPosition());
		modelTable.setLineSpacing(this.getLineSpacing());
		modelTable.setFont(this.getFontFamily());
		modelTable.setFontStyle(this.getFontStyleToString());
		modelTable.setFontSize(this.getFontSize());
		modelTable.setFontAlignment(this.getFontAlignmentToString());
	}
	*/
	public void setValueFromGRS(String value, GRResFonts resFont) {
		Color fontColor = Color.black;
		int cRED;
		int cGREEN;
		int cBLUE;
		Pattern pattern = Pattern.compile(REG_TEXT);
		Matcher matcher = pattern.matcher(value);
		
		while(matcher.find()) { 
			idFont = matcher.group(1);
			fontSize = Integer.parseInt(matcher.group(2));
			
			if(matcher.group(3) != null) {
				cRED = Integer.parseInt(matcher.group(4));
				cGREEN = Integer.parseInt(matcher.group(5));
				cBLUE = Integer.parseInt(matcher.group(6));
				
				fontColor = new Color(cRED, cGREEN, cBLUE);
			}
			this.value = GRLibrary.lineOctToASCII(matcher.group(7));
			
			if(resFont == null)
				resFont = new GRResFonts();
			
			resFont.addResource();
			resFont.setFontBaseName(idFont);
			
			fontName = resFont.getResource(idFont).getFontName();
			fontStyle = GRLibrary.getFontStyleFromFontPDF(idFont);
								
			grtextFormatted.addElement(fontName, fontSize, fontStyle, fontColor, this.value);
							
		}
		
	}
	public void modifyText(Document dc, String value) {
		grtextFormatted = new GRTextFormatted(dc,alignment,value);
		
		for(int i = 0;i < grtextFormatted.getTotaleElement();i++) {
			GRTextFormattedElement tfe = grtextFormatted.getElement(i);
			
			tfe.setZoom(zoom);
		}
		
		insertTextFormatted();
	}
	public void setValue(String value) {
		this.value = value;
		
	}
	public String getValue() {
		return value;
	}
	public void setFontId(String id) {
		idFont = id;
	}
	public String getFontid() {
		return idFont;
	}
	public void setLineSpacing(float value) {
		lineSpacing = value * zoom;
		
		this.setOriginalLineSpacing(lineSpacing);
	}
	public float getLineSpacing() {
		return lineSpacing;
	}
	public void setOriginalLineSpacing(float value) {
		lineSpacingOriginal = value / zoom;
	}
	public float getOriginalLineSpacing() {
		return lineSpacingOriginal;
	}
	public void setFontFamily(String fName) {
		this.fontName = fName;
		
		grtextFormatted.setFontFamily(fName);
		refresh();
	}
	public String getFontFamily() {
		return grtextFormatted.getFontName();
	}
	public void setFontSize(int fSize) {
		this.fontSize = fSize;
		
		grtextFormatted.setFontSize(fSize);
		refresh();
	}
	public int getFontSize() {
		return grtextFormatted.getFontSize();
	}
	public void setFontAlignment(int align) {
		this.alignment = align;
		
		grtextFormatted.setAlignment(alignment);
	}
	public void setFontAlignment(String align) {
		if(align.equals("Left") || align.equals("left"))
			this.alignment = GRText.ALIGNTEXT_LEFT;
		else if(align.equals("Center") || align.equals("center"))
			this.alignment = GRText.ALIGNTEXT_CENTER;
		else if(align.equals("Right") || align.equals("right"))
			this.alignment = GRText.ALIGNTEXT_RIGHT;
		else if(align.equals("Justify") || align.equals("justify"))
			this.alignment = GRText.ALIGNTEXT_JUSTIFY;
			
		grtextFormatted.setAlignment(alignment);
	}
	public int getFontAlignment() {
		return alignment;
	}
	public String getFontAlignmentToString() {
		switch(alignment) {
			case GRText.ALIGNTEXT_LEFT:
				return "Left";
				
			case GRText.ALIGNTEXT_CENTER:
				return "Center";
				
			case GRText.ALIGNTEXT_RIGHT:
				return "Right";
				
			case GRText.ALIGNTEXT_JUSTIFY:
				return "Justify";
		}
		
		return null;
	}
	public void setFontStyle(String style) {
		if(style.equals("Normale"))
			grtextFormatted.setFontStyle(GRText.STYLETEXT_NORMAL);
		else if(style.equals("Corsivo"))
			grtextFormatted.setFontStyle(GRText.STYLETEXT_ITALIC);
		else if(style.equals("Grassetto"))
			grtextFormatted.setFontStyle(GRText.STYLETEXT_BOLD);
		else if(style.equals("Grassetto Corsivo"))
			grtextFormatted.setFontStyle(GRText.STYLETEXT_BOLDITALIC);
		
		//this.insertText();
	}
	public int getFontStyle() {
		return grtextFormatted.getFontStyle();
	}
	public String getFontStyleToString() {
		switch(grtextFormatted.getFontStyle()) {
			case GRText.STYLETEXT_NONE:
				return "";
				
			case GRText.STYLETEXT_NORMAL:
				return "Normale";
			
			case GRText.STYLETEXT_ITALIC:
				return "Corsivo";
				
			case GRText.STYLETEXT_BOLD:
				return "Grassetto";
				
			case GRText.STYLETEXT_BOLDITALIC:
				return "Grassetto Corsivo";
		}
		
		return null;
	}
	
	public void setFont(String fName, int fSize, int fStyle, int align) {
		this.fontName = fName;
		this.fontSize = fSize;
		this.fontStyle = fStyle;
		this.alignment = align;
		
		font = new Font(fontName,fontStyle,fontSize);
	}
	public GRText clone(long id) {
		int newX = x1Original+15;
		int newY = y1Original+15;
		
		Rectangle r = new Rectangle(newX,newY,widthOriginal,heightOriginal);
		GRText grclone = new GRText(grpage,gdc,id,grtextFormatted.getDocument(),grtextFormatted.getAlignment(),grtextFormatted.getText(),r);
		
		return grclone;
	}
	public void refresh() {
		this.insertTextFormatted();
	}
	public void setZoom(float value) {
		super.setZoom(value);
		
		for(int i = 0;i < grtextFormatted.getTotaleElement();i++) {
			GRTextFormattedElement tfe = grtextFormatted.getElement(i);
			
			tfe.setZoom(value);
			//grtextFormatted.getElement(i).setFontSize((int)(grtextFormatted.getElement(i).getFontSize() * zoom));
		}
		
		this.setLineSpacing(lineSpacingOriginal);
		
		this.insertTextFormatted();
		
	}
	
	private void insertTextFormatted() {
		
		int endStream = 0;
		String tempStream = "";
		
		int dim = 0;
		gpar = new GRParagraph(x1, y1, grtextFormatted.getAlignment(), gdc);
		gpar.newRow();
		
		for(int i = 0;i < grtextFormatted.getTotaleElement();i++) {
			GRTextFormattedElement grtextElement = grtextFormatted.getElement(i);
			String fontName = grtextElement.getFontName();
			int fontSize = grtextElement.getFontSize();
			int fontStyle = grtextElement.getFontStyle();
			Color fontColor = grtextElement.getFontColor();
			
			GRFont f = GRFontProperty.getFont(GRLibrary.getFontPDF(fontName, fontStyle));
			
			String value = grtextElement.getValue();
			int lenStream = value.length();
			int pointerStream = 0;
			int startStream = 0;

			while(pointerStream < lenStream) {
				
				int c = value.codePointAt(pointerStream);
				if(c > 255) {
					c = GRCharCodeSet.CodeASCIILatinExtended(c);
				}
				dim = (int)(dim + Math.round((f.getWidth(c)*1.0588*fontSize)));
				
				if(dim >= (width*1000)) {
					if(tempStream.equals(""))
						break;
					
					gpar.addTextRow(fontName, fontSize, fontStyle, fontColor, tempStream);
					gpar.newRow();
					
					pointerStream = endStream;
					startStream = endStream+1;
					
					dim = 0;
					tempStream = "";
										
				} else {
					if(c == 32) {
						
						tempStream = value.substring(startStream,pointerStream);
						
						endStream = pointerStream;
						//tempBlank++;
					} else if(c == 10) {	// Carriage Return
						if(startStream == lenStream)
							tempStream = "";
						else
							tempStream = value.substring(startStream,pointerStream);
						
						gpar.addTextRow(fontName, fontSize, fontStyle, fontColor, tempStream);
						gpar.setLastRow();
						gpar.newRow();
						
						startStream = pointerStream+1;
						dim = 0;
						tempStream = "";
																	
					}
					
				}
				
				pointerStream++;
			}
			
			if(startStream < lenStream) {
				tempStream = value.substring(startStream);
				
				gpar.addTextRow(fontName, fontSize, fontStyle, fontColor, tempStream);
				startStream = 0;	
			}
			
		}
		
		gpar.setLastRow();
		
		/*
		System.out.println("PARAGRAFO");
		System.out.println("Totale row: "+gpar.getTotaleRow());
		for(int i = 0;i < gpar.getTotaleRow();i++) {
			System.out.println("ROW: "+i);
			
			GRRowParagraph grrow = gpar.getLineParagraph(i);
			for(int z = 0;z < grrow.getTotaleTextRow();z++) {
				System.out.println("TEXT: "+grrow.getTokenTextRow(z).getValue());
			}
			
			System.out.println("\n");
		}
		*/
	}
	
	
	private void drawParagraph(GRRowParagraph grrow, FontRenderContext frc, Graphics2D g2, float y) {
		Color oldColor = g2.getColor();
		
		int widthToken = 0;
		int widthRow = grrow.getWidthRow();
		int widthParziale = 0;
		
		int x = 0;
		
		for(int i = 0;i < grrow.getTotaleTextRow();i++) {
			GRTextRowParagraph grtext = grrow.getTokenTextRow(i);
			String str = grtext.getValue();
			Font f = grtext.getFont();
			
			if(str.length() > 0) {
				TextLayout tl = new TextLayout(str,f,frc);
				
				switch(gpar.getAlignment()) {
					case GRText.ALIGNTEXT_LEFT:
						if(i == 0)
							x = x1;
						else
							x = x + widthToken;
						
						widthToken = grtext.getWidth();
						break;
						
					case GRText.ALIGNTEXT_CENTER:
						if(i == 0)
							x = x1 + ((width - grrow.getWidthRow()) / 2);
						else
							x = x + widthToken;
						
						widthToken = grtext.getWidth();
						break;
						
					case GRText.ALIGNTEXT_RIGHT:
						if(i == 0)
							x = x1 + width - grrow.getWidthRow();
						else
							x = x + widthToken;
						
						widthToken = grtext.getWidth();
						break;
						
					case GRText.ALIGNTEXT_JUSTIFY:
						if(i == 0)
							x = x1;
						else {
							x = x + widthToken;
						}
	
						if(!grrow.isLastRow()) {
							if(grrow.getTotaleTextRow() == 1)
								tl = tl.getJustifiedLayout(width);
							else {
								if((i+1) == grrow.getTotaleTextRow())
									widthToken = width - widthParziale;
								else
									widthToken = (width * grtext.getWidth()) / widthRow;
								
								tl = tl.getJustifiedLayout(widthToken);
							}
						} else {
							widthToken = grtext.getWidth();
						}
						
						widthParziale = widthParziale + widthToken;
						break;
				}
				
				g2.setColor(grtext.getFontColor());
				tl.draw(g2,(float)x,y);
			}
		}
		
		g2.setColor(oldColor);
	}
	public void draw(Graphics g) {
		if(gpar == null) {
			gdc = g;
			this.insertTextFormatted();
		}
		
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext frc = g2.getFontRenderContext();
		Shape oldClip = g2.getClip();
		
		int y1 = this.y1;
		
		float y = 0;
		gapH = -2;
		if(hPosition) {
			gapH = gapH + grpage.hPosition;
		}
		if(grlistFather != null) {
			y1 = y1 + grlistFather.getY();
			g2.setClip(0,grlistFather.getY(),grpage.getWidth(),grlistFather.getHeight()+1);
		}
		
		for(int i = 0;i < gpar.getTotaleRow();i++) {
			GRRowParagraph grrow = gpar.getLineParagraph(i);
			float leading = ((float)grrow.getMaxHeight() + GRLibrary.getLeadingFromMillimeters(lineSpacing)) * 1.0588f;
			
			if(i == 0) {
				y = gapH + y1 + (float)grrow.getMaxHeight();
				
			} else
				y = y + leading;
			
			this.drawParagraph(grrow,frc,g2,y);	
		}
		
		height = (int)(y - (y1+gapH));
		
		grpage.hPosition = (int)y;
		
		g2.setClip(oldClip);
		
		if(selected) {
			g.drawRect(x1-2,y1+gapH,width+4,height+4);
			g.fillRect(x1-6,y1-4+gapH,4,4);
			g.fillRect(x1-6,y1+height+gapH+4,4,4);
			g.fillRect(x1+width+2,y1-4+gapH,4,4);
			g.fillRect(x1+width+2,y1+height+gapH+4,4,4);
		}
		
		refreshReferenceSection();
		
	}
	
	public void setWidth(int width) {
		super.setWidth(width);
		
		//this.insertText();
	}
	
	public GRTextFormatted getTextFormatted() {
		return grtextFormatted;
	}
	public Vector<String> getVariables() {
		Vector<String> listVariables = new Vector<String>();
		
		Pattern pattern = Pattern.compile(REG_VARIABLE);
		Matcher matcher = pattern.matcher(value);
		
		while(matcher.find()) { 
			listVariables.add(matcher.group(1));			
		}
		
		return listVariables;
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		String fontToken = "";
		String fontTokenTemp = "";
		String fontId = "";
		int fontSize = 0;
		Color fontColor = Color.black;
		int y1 = this.y1Original;
		
		if(section == GRObject.SECTION_BODY) {
			y1 = y1 - grpage.getHeaderSize();
		} 
		if(section == GRObject.SECTION_FOOTER) {
			y1 = y1 - (grpage.getHeight() - grpage.getFooterSize());
		}
		buff.append("<text>\n");
		buff.append("<alignment>");
		switch(gpar.getAlignment()) {
			case ALIGNTEXT_LEFT:
				buff.append("left");
				break;
				
			case ALIGNTEXT_CENTER:
				buff.append("center");
				break;
			
			case ALIGNTEXT_RIGHT:
				buff.append("right");
				break;
				
			case ALIGNTEXT_JUSTIFY:
				buff.append("justify");
				break;
		}
		buff.append("</alignment>\n");
		buff.append("<left>"+GRLibrary.fromPixelsToMillimeters(x1Original)+"</left>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<width>"+GRLibrary.fromPixelsToMillimeters(widthOriginal)+"</width>\n");
		buff.append("<linespacing>"+lineSpacingOriginal+"</linespacing>\n");
		buff.append("<hposition>");
		if(hPosition)
			buff.append("relative");
		else
			buff.append("absolute");
		buff.append("</hposition>\n");
		
		buff.append("<value>");
		// Il testo completo è composto da tutti gli elementi di GRTextFormattedElement
		for(int i = 0;i < grtextFormatted.getTotaleElement();i++) {
			GRTextFormattedElement tfe = grtextFormatted.getElement(i);
			
			fontId = tfe.getFontId();
			fontSize = tfe.getOriginalFontSize();
			fontColor = tfe.getFontColor();
				
			fontTokenTemp = "["+fontId+":"+fontSize+":"+fontColor.getRed()+","+fontColor.getGreen()+","+fontColor.getBlue()+"]";
			if(!fontTokenTemp.equals(fontToken)) {
				fontToken = fontTokenTemp;
				
				buff.append(fontToken);
			}
				
			buff.append(GRLibrary.lineASCIIToOct(tfe.getValue()));
			
		}
		buff.append("</value>\n");
		
		buff.append("</text>");
		
		return buff.toString();
	}
	
}