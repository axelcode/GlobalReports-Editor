/*
 * ==========================================================================
 * class name  : com.globalreports.editor.configuration.font.GRFontProperty
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
package com.globalreports.editor.configuration.font;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.GRDocument;
import com.globalreports.editor.tools.GRLibrary;

public class GRFontProperty {
	public static Vector<GRFont> grfont;
	public static Vector<GRFontFamily> grfontInstalled;
	private GRFontFamily refFontFamily;
	private GRFont refFont;
	
	public GRFontProperty() {
		GRFontProperty.grfont = new Vector<GRFont>();
		GRFontProperty.grfontInstalled = new Vector<GRFontFamily>();
		
		this.init();
	}
	
	private void init() {
		
		File dirFont = new File(GRSetting.PATHFONTCONFIG);
		String[] files = dirFont.list();
		
		for(int i = 0;i < files.length;i++) {
			if(files[i].endsWith(".xml")) {
				readSource(new File(GRSetting.PATHFONTCONFIG+files[i]));
			}
				
		}
		//readSource(new File(GRSetting.PATHFONTCONFIG+"font-config.xml"));
	}
	
	public static void createFontProperty() {
		new GRFontProperty();
	}
	public static GRFont getFont(String name) {
		for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
			GRFontFamily grfontFamily = grfontInstalled.get(i);
			
			if(grfontFamily.isEquals(name)) {
				return grfontFamily.getFont(name);
			}
			
		}
		return null;
	}
	
	private void readSource(File grsource) {
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(grsource);
				
			Element rootElement = document.getRootElement();
			//List<Element> children = rootElement.getChildren();
			List<Element> children = GRLibrary.castList(Element.class,rootElement.getChildren());	
			
			Iterator<Element> iterator = children.iterator();
			while (iterator.hasNext()){
				readChild((Element)iterator.next());
			} 
			
		} catch(JDOMException jde) {
			System.out.println("GRFontProperty::readSource::JDOMException: "+jde.getMessage());
		} catch(IOException ioe) {
			System.out.println("GRFontProperty::readSource::IOException: "+ioe.getMessage());
		} 
		
	}
	private void readChild(Element element) {
		if(element.getName().equals("fontbasename")) {
			boolean trovato = false;
			
			for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
				if(GRFontProperty.grfontInstalled.get(i).getBaseName().equals(element.getValue())) {
					trovato = true;
					
					break;
				}					
			}
			if(!trovato) {
				
				refFontFamily = new GRFontFamily(element.getValue());
				GRFontProperty.grfontInstalled.add(refFontFamily);
			}
		} else if(element.getName().equals("grfont")) {
			readFont(element);
		}
	}
	private void readFont(Element el) {
		List<Element> children = el.getChildren();
		Iterator<Element> iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("fontname")) {
				if(((Element)element.getParent()).getName().equals("grfont")) {
					refFontFamily.addFont(element.getValue());
				}
			} else if(element.getName().equals("widths")) {
				if(((Element)element.getParent()).getName().equals("grfont")) {
					readWidths(element);
				}
			}
		}
		
	}
	private void readWidths(Element el) {
		List<Element> children = el.getChildren();
		Iterator<Element> iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("char")) {
				if(((Element)element.getParent()).getName().equals("widths")) {
					int index = Integer.parseInt(element.getChildText("index"));
					int w = Integer.parseInt(element.getChildText("w"));
					
					refFontFamily.addFontWidth(index, w);
				}
			}
		}
		
	}
}
