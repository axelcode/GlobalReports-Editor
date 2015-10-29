/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableModelText
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
package com.globalreports.editor.designer.property;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.*;

import com.globalreports.editor.tools.GRLibrary;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.combo.*;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.designer.swing.table.GRTableCell;
import com.globalreports.editor.designer.swing.table.GRTableSeparator;
import com.globalreports.editor.designer.swing.table.element.GRColorElement;
import com.globalreports.editor.designer.swing.table.element.GRComboElement;
import com.globalreports.editor.designer.swing.table.event.GRTableEvent;
import com.globalreports.editor.designer.swing.table.event.GRTableListener;

@SuppressWarnings("serial")
public class GRTableModelText extends GRTableModel implements GRTableListener {
	
	private GRComboElement fName = new GRComboElement();
	private GRComboElement fStyle = new GRComboElement();
	private GRComboElement fSize = new GRComboElement();
	private GRComboElement fAlign = new GRComboElement();
	
	private Object[][] element = {{"Oggetto","Testo"},
								  {new GRTableSeparator("Posizione"), null},
								  {"Posizione relativa", new JCheckBox()},
								  {"Asse X", new JTextField()},
								  {"Asse Y", new JTextField()},
								  {"Larghezza", new JTextField()},
								  {"Interlinea", new JTextField()},
								  {new GRTableSeparator("Aspetto"), null},
								  {"Font", fName},
								  {"Stile", fStyle},
								  {"Dimensione", fSize},
								  {"Allineamento", fAlign},
								  {"Colore", new GRColorElement(Color.BLACK)},
								  {new GRTableSeparator("Oggetti dinamici"), null},
								  {"Lista", listFather}};
			  
	private GRText objText;	// Riferimento all'oggetto per poterne modificare le proprietà
	
	public GRTableModelText(GRTableProperty panelProperty, String[] title) {
		super(title);
		this.panelProperty = panelProperty;
		
		// Font Family
		for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
	    	fName.addItem(GRFontProperty.grfontInstalled.get(i).getBaseName());
		}
		
		// Font Style
		fStyle.addItem("Normale");
		fStyle.addItem("Corsivo");
		fStyle.addItem("Grassetto");
		fStyle.addItem("Grassetto Corsivo");
		
		// Font Size
		String[] fontsizes = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","72"};
	    fSize.addElement(fontsizes);
	    fSize.setEditable(true);
		
	    // Font Alignment
	    fAlign.addItem("Sinistra");
	    fAlign.addItem("Centrato");
	    fAlign.addItem("Destra");
	    fAlign.addItem("Giustificato");
	    	    
		createBody(element);
		addGRTableListener(this);
	}
	
	public void setGRObject(GRText ref) {
		this.objText = ref;
		
	}
	public void setHPosition(boolean value) {
		this.setValueAt(1, 1, ""+value);
		//this.setValueAt(new Boolean(value),2,1);
	}
	public void setLeft(int value) {
		this.setValueAt(2,1,""+GRLibrary.fromPixelsToMillimeters(value));
	}
	public void setTop(int value) {
		this.setValueAt(3,1,""+GRLibrary.fromPixelsToMillimeters(value));
	}
	public void setWidth(int value) {
		this.setValueAt(4,1,""+GRLibrary.fromPixelsToMillimeters(value));
	}
	public void setLineSpacing(float value) {
		this.setValueAt(5,1,""+value);
	}
	public void setFont(String fontName) {
		this.setValueAt(6,1,fontName);
	}
	public void setFontStyle(String fStyle) {
		this.setValueAt(7,1,fStyle);
	}
	public void setFontSize(int fSize) {
		this.setValueAt(8,1,""+fSize);
	}
	public void setFontAlignment(String fAlign) {
		this.setValueAt(9,1,fAlign);
	}
	public void setListFather(String value) {
		if(value == null)
			this.setValueAt(11, 1, "--Nessuna--");
		else
			this.setValueAt(11,1,value);
	}
	
	@Override
	public void valueChanged(GRTableEvent e) {
		GRTableCell cell = (GRTableCell)e.getSource();
		
		switch(e.getRow()) {
			case 1:	// hposition
				if(e.getValue().equals("true"))
					objText.setHPosition(true);
				else
					objText.setHPosition(false);
				break;
				
			case 2:	// X
				objText.setX(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 3:	// Y
				objText.setY(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 4:	// width
				objText.setWidth(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 5:	// linespacing
				objText.setLineSpacing(Float.parseFloat(e.getValue()));
				break;
			
			case 6:	// fontfamily
				objText.setFontFamily(e.getValue());
				break;
			
			case 7:	// fontstyle
				objText.setFontStyle(e.getValue());
				break;
				
			case 8:	// fontsize
				objText.setFontSize(Integer.parseInt(e.getValue()));
				break;
				
			case 9:	// fontalignment
				objText.setFontAlignment(this.getFontSize(e.getValue()));
				break;
				
			case 10:	// fontcolor
				break;
				
			case 11:	// listfather
				objText.setListFather(e.getValue());
				break;
			
		}

		objText.refresh();
		panelProperty.getPage().repaint();
		
	}
	
	private int getFontSize(String value) {
		if(value.equals("Sinistra"))
			return GRText.ALIGNTEXT_LEFT;
		else if(value.equals("Centrato"))
			return GRText.ALIGNTEXT_CENTER;
		else if(value.equals("Destra"))
			return GRText.ALIGNTEXT_RIGHT;
		else if(value.equals("Giustificato"))
			return GRText.ALIGNTEXT_JUSTIFY;
		
		return -1;
	}
}
