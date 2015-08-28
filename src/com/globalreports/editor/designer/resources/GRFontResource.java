/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.resources.GRFontResource
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
package com.globalreports.editor.designer.resources;

import com.globalreports.editor.graphics.GRText;

public class GRFontResource {
	private String id;
	private String baseName;	// Nome del font PDF
	private String fontName;	// Nome del font
	private int style;
	private String type;
	private int totTextCollegato;
	
	public GRFontResource() {
		this(null,null,null,GRText.STYLETEXT_NORMAL);
	}
	
	public GRFontResource(String id, String baseName, String fontName, int style) {
		this.id = id;
		this.baseName = baseName;
		this.fontName = fontName;
		this.style = style;
		this.type = "TrueType";
		
		totTextCollegato = 1;
	}
	
	public void addTextCollegato() {
		totTextCollegato++;
	}
	public void removeTextCollegato() {
		totTextCollegato--;
	}
	public int getTextCollegato() {
		return totTextCollegato;
	}
	public String getId(String name) {
		if(name.equals(this.baseName))
			return id;
			
		return null;
	}
	public String getId() {
		return id;
	}
	public void setId(String value) {
		id = value;
	}
	public String getBaseName(String fId) {
		if(fId.equals(this.id))
			return baseName;
			
		return null;
	}
	public String getFontName(String fId) {
		if(fId.equals(this.id))
			return fontName;
			
		return null;
	}
	public String getBaseName() {
		return baseName;
	}
	public String getFontName() {
		return fontName;
	}
	public void setBaseName(String value) {
		baseName = value;
		
		String token[] = value.split("-");
		
		if(token.length == 1)
			style = GRText.STYLETEXT_NORMAL;
		else {
			if(token[1].equals("Bold"))
				style = GRText.STYLETEXT_BOLD;
			else if(token[1].equals("Italic"))
				style = GRText.STYLETEXT_ITALIC;
			else if(token[1].equals("BoldItalic"))
				style = GRText.STYLETEXT_BOLDITALIC;
		}
		
		fontName = token[0];
	}
	public void setFontName(String value) {
		fontName = value;
	}
	public int getStyle() {
		return style;
	}
	public String getType() {
		return type;
	}
	public void setType(String value) {
		type = value;
	}
}
