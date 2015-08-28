/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.resources.GRResFonts
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

import java.util.Vector;

import com.globalreports.editor.tools.GRLibrary;

public class GRResFonts {
	private Vector<GRFontResource> res;
	private int progressivo;
	
	public GRResFonts() {
		progressivo = 1;
		
		res = new Vector<GRFontResource>();
	}
	
	public void addResource() {
		res.add(new GRFontResource());
		
		progressivo++;
	}
	
	public String addResource(String fName, int fStyle) {
		// Verifica che il path non sia già censito
		String id = "";
		String fNamePDF = GRLibrary.getFontPDF(fName, fStyle);
		
		for(int i = 0;i < res.size();i++) {
			id = res.get(i).getId(fNamePDF);
			if(id != null) {
				res.get(i).addTextCollegato();
				return id;
			}	
		}	
		
		// Se arriva qui significa che non ha trovato la risorsa
		// Ne genera una nuova
		id = "f"+progressivo;
		progressivo++;
		res.add(new GRFontResource(id,fNamePDF,fName,fStyle));
		
		return id;
	}
	public void modifyResource(String oldFont, String newFont, int oldStyle, int newStyle) {
		// Come prima cosa scarica la vecchia risorsa
		
	}
	
	public void setFontId(String fId) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setId(fId);
	}
	public void setFontName(String fName) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setFontName(fName);
	}
	public void setFontBaseName(String bName) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setBaseName(bName);
	}
	public void setFontType(String fType) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setType(fType);
	}
	
	public GRFontResource getResource(String fId) {
		// Ritorna la risorsa puntata dall'id passato come parametro 
		for(int i = 0;i < res.size();i++)
			if(res.get(i).getBaseName().equals(fId))
				return res.get(i);
							
		return null;
	}
	public GRFontResource getResource(int i) {
		return res.get(i);
	}
	public int getTotaleResources() {
		return res.size();
	}
	
}