/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.resources.GRResImages
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

import com.globalreports.editor.graphics.GRImage;

public class GRResImages {
	private Vector<GRImageResource> res;
	private int progressivo;
	
	public GRResImages() {
		progressivo = 1;
		
		res = new Vector<GRImageResource>();
	}
	
	public void addResource() {
		res.add(new GRImageResource());
		
		progressivo++;
	}
	
	public String addResource(GRImage grimg) {
		// Verifica che il path non sia già censito
		String id = "";
		String pathFile = grimg.getPathFile();
		
		for(int i = 0;i < res.size();i++) {
			id = res.get(i).getId(pathFile);
			if(id != null)
				return id;
				
		}	
		
		// Se arriva qui significa che non ha trovato la risorsa
		// Ne genera una nuova
		id = "Im"+progressivo;
		progressivo++;
		res.add(new GRImageResource(id,pathFile,grimg.getFileWidth(),grimg.getFileHeight()));
		
		return id;
	}
		
	public void removeResource(String id) {
		for(int i = 0;i < res.size();i++) {
			if(res.get(i).getId().equals(id)) {
				res.remove(i);
			}
		}
	}
	public void setImageId(String idImg) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setId(idImg);
	}
	public void setImagePath(String path, String value) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setPath(path, value);
	}
	public void setImageOriginalWidth(String value) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setWidth(Integer.parseInt(value));
	}
	public void setImageOriginalHeight(String value) {
		if(res.size() == 0) 
			this.addResource();
			
		res.lastElement().setHeight(Integer.parseInt(value));
	}
	public GRImageResource getResource(String imgId) {
		
		// Ritorna la risorsa puntata dall'id passato come parametro 
		for(int i = 0;i < res.size();i++)
			if(res.get(i).getId().equals(imgId))
				return res.get(i);
				
		return null;
	}
	public GRImageResource getResource(int i) {
		return res.get(i);
	}
	
	public int getTotaleResources() {
		return res.size();
	}
}