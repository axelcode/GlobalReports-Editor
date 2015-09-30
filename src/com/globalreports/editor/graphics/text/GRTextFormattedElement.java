/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.text.GRTextFormattedElement
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

import com.globalreports.editor.graphics.GRText;

public class GRTextFormattedElement {
	private String fId;
	private String fontName;
	private int fontSize;
	private int fontSizeOriginal;
	private int fontStyle;
	private Color fontColor;
	private String value;
	private float zoom;
	
	public GRTextFormattedElement(String fName, int fSize, boolean fBold, boolean fItalic, Color fColor, String value) {
		this.fontName = fName;
		this.fontSize = fSize;
		this.fontSizeOriginal = fSize;
		this.fontColor = fColor;
		this.value = value;
		this.fId = fName;
		
		zoom = 1.0f;
		
		fontStyle = GRText.STYLETEXT_NORMAL;
		if(fBold) {
			fontStyle = fontStyle + GRText.STYLETEXT_BOLD;
			this.fId = this.fId + "-Bold";
		}
		if(fItalic) {
			fontStyle = fontStyle + GRText.STYLETEXT_ITALIC;
			
			if(fBold)
				this.fId = this.fId + "Italic";
			else
				this.fId = this.fId + "-Italic";
		}
		
	}
	
	public void setZoom(float value) {
		zoom = value;
		
		this.setFontSize(fontSizeOriginal);
	}
	public Color getFontColor() {
		return fontColor;
	}
	public void setFontColor(Color c) {
		this.fontColor = c;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fName) {
		this.fontName = fName;
	}
	public String getValue() {
		return value;
	}
	public void setFontSize(int fSize) {
		this.fontSize = (int)(fSize * zoom);
		this.setOriginalFontSize(fontSize);
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setOriginalFontSize(int fSize) {
		this.fontSizeOriginal = (int)(fSize / zoom);
	}
	public int getOriginalFontSize() {
		return fontSizeOriginal;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fStyle) {
		this.fontStyle = fStyle;
		
		this.fId = this.fontName;
		
		switch(fStyle) {
			case GRText.STYLETEXT_BOLD:
				this.fId = this.fId + "-Bold";
				break;
				
			case GRText.STYLETEXT_ITALIC:
				this.fId = this.fId + "-Italic";
				break;
				
			case GRText.STYLETEXT_BOLDITALIC:
				this.fId = this.fId + "-BoldItalic";
				break;
			
		}
		
	}
	public void setFontId(String value) {
		fId = value;
	}
	public String getFontId() {
		return fId;
	}
	public boolean isBold() {
		if(fontStyle == GRText.STYLETEXT_BOLD || fontStyle == GRText.STYLETEXT_BOLDITALIC)
			return true;
		
		return false;
	}
	public boolean isItalic() {
		if(fontStyle == GRText.STYLETEXT_ITALIC || fontStyle == GRText.STYLETEXT_BOLDITALIC)
			return true;
		
		return false;
	}
}
