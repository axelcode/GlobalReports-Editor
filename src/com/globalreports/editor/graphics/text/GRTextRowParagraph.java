/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.text.GRTextRowParagraph
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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class GRTextRowParagraph {
	private String value;
	private String fontName;
	private int fontSize;
	private int fontStyle;
	private Color fontColor;
	private int width;		// Dimensione in pixel del token
	private int widthBlank;	
	private Font font;
	private Graphics gdc;	// Riferimento al contesto grafico
	
	public GRTextRowParagraph(String fontName, int fontSize, int fontStyle, Color fontColor, String value, Graphics g) {
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontStyle = fontStyle;
		this.fontColor = fontColor;
		this.value = value;		
		this.gdc = g;
		
		font = new Font(fontName,fontStyle,fontSize);
		FontMetrics fm = gdc.getFontMetrics(font);
		width = fm.stringWidth(value);
		widthBlank = fm.charWidth(32);
	}
	
	public String getValue() {
		return value;
	}
	public String getFontName() {
		return fontName;
	}
	public int getFontSize() {
		return fontSize;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public Color getFontColor() {
		return fontColor;
	}
	public int getWidth() {
		return width;
	}
	public int getWidthBlank() {
		return widthBlank;
	}
	public Font getFont() {
		return font;
	}
	
}

