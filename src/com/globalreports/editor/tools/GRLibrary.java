/*
 * ==========================================================================
 * class name  : com.globalreports.editor.tools.GRLibrary
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
package com.globalreports.editor.tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.globalreports.editor.graphics.GRText;

public abstract class GRLibrary {
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	    	r.add(clazz.cast(o));
	    
	    return r;
	}
	
	public static int fromMillimetersToPixels(String value) {
		return fromMillimetersToPixels(Double.parseDouble(value));
	}
	public static int fromMillimetersToPixels(double value) {
		return (int)value * 3;
	}
	public static double fromPixelsToMillimeters(int value) {
		return arrotonda(value / 3.0);
	}
	public static float getLeadingFromMillimeters(float value) {
		return value * 2.83f;
		//return value * 1.985f;
	}
	private static double arrotonda(double x){
		x = Math.floor(x*100);
		x = x/100;
		return x;
	}
	
	public static Color ColorText(Color background) {
		// Ritorna il colore pi� appropriato del testo a seconda del colore di sfondo.
		// Utilizza la formula sulla luminanza - costante: 500
		// Calcola se conviene di pi� utilizzare il nero oppure il bianco
		
		if(background == null)
			return Color.BLACK;
		
		int backgroundColor = background.getRed() + background.getGreen() + background.getBlue();
		
		if((765 - backgroundColor) < backgroundColor)
			return Color.BLACK;
		/*
		if(background.getRed() + background.getGreen() + background.getBlue() < 500)
			return Color.white;
		*/
		return Color.WHITE;
	}
	public static String getFontPDF(String fName, int fStyle) {
		switch(fStyle) {
			case GRText.STYLETEXT_NORMAL:
				return fName;
			
			case GRText.STYLETEXT_BOLD:
				return fName+"-Bold";
				
			case GRText.STYLETEXT_ITALIC:
				return fName+"-Italic";
				
			case GRText.STYLETEXT_BOLDITALIC:
				return fName+"-BoldItalic";
		}
		
		return null;
	}
	public static int getFontStyleFromFontPDF(String f) {
		String token[] = f.split("-");
		
		if(token.length == 1)
			return GRText.STYLETEXT_NORMAL;
		else {
			if(token[1].equals("Bold"))
				return GRText.STYLETEXT_BOLD;
			else if(token[1].equals("Italic"))
				return GRText.STYLETEXT_ITALIC;
			else if(token[1].equals("BoldItalic"))
				return GRText.STYLETEXT_BOLDITALIC;
		}
		return GRText.STYLETEXT_NORMAL;
	}
	public static String lineOctToASCII(String value) {
		StringBuffer buffer = new StringBuffer();
		
		for(int i = 0;i < value.length();i++) {
			
			if(value.codePointAt(i) == 92) {
				buffer.append(GRLibrary.fromOctToASCII(value.substring((i+1),(i+4))));
				i = i + 3;
			} else {
				buffer.append(value.charAt(i));
			}
			
		}
		return buffer.toString();
	}
	public static String fromOctToASCII(String value) {
		if(value.equals("012"))
			return new String(Character.toChars(10));
		else if(value.equals("045"))
			return new String(Character.toChars(37));
		else if(value.equals("050"))
			return new String(Character.toChars(40));
		else if(value.equals("051"))
			return new String(Character.toChars(41));
		else if(value.equals("057"))
			return new String(Character.toChars(47));
		else if(value.equals("074"))
			return new String(Character.toChars(60));
		else if(value.equals("076"))
			return new String(Character.toChars(62));
		else if(value.equals("173"))
			return new String(Character.toChars(123));
		else if(value.equals("174"))
			return new String(Character.toChars(124));
		else if(value.equals("175"))
			return new String(Character.toChars(125));
		else if(value.equals("200"))
			return new String(Character.toChars(128));
		else if(value.equals("260"))
			return new String(Character.toChars(176));
		else if(value.equals("272"))
			return new String(Character.toChars(186));
		else if(value.equals("340"))
			return new String(Character.toChars(224));
		else if(value.equals("350"))
			return new String(Character.toChars(232));
		else if(value.equals("351"))
			return new String(Character.toChars(233));
		else if(value.equals("354"))
			return new String(Character.toChars(236));
		else if(value.equals("371"))
			return new String(Character.toChars(249));
		else if(value.equals("372"))
			return new String(Character.toChars(250));
		return null;
	}
	public static String lineASCIIToOct(String value) {
		StringBuffer buffer = new StringBuffer();
		
		for(int i = 0;i < value.length();i++) {
			buffer.append(GRLibrary.fromASCIIToOct(value.codePointAt(i)));
			
		}
		
		return buffer.toString();
	}
	public static String fromASCIIToOct(int value) {
		
		switch(value) {
			case 10:
				return "\\012";
			
			case 13:
				return "";
				
			case 37:
				return "\\045";
				
			case 40:
				return "\\050";
				
			case 41:
				return "\\051";
				
			case 47:
				return "\\057";
				
			case 60:
				return "\\074";
				
			case 62:
				return "\\076";
				
			case 124:
				return "\\174";
				
			case 128:
				return "\\200";
						
			case 176:
				return "\\260";
				
			case 186:
				return "\\272";
				
			case 224:
				return "\\340";
				
			case 225:
				return "\\341";
				
			case 232:
				return "\\350";
				
			case 233:
				return "\\351";
				
			case 236:
				return "\\354";
				
			case 237:
				return "\\355";
				
			case 243:
				return "\\363";
				
			case 249:
				return "\\371";
				
			case 250:
				return "\\372";
				
			case 8217:
				return "\\222";
				
			case 8364:
				return "\\200";
		}
		
		return String.valueOf((char)value);
		
	}
}
