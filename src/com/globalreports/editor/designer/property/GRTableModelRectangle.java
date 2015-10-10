/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableModelRectangle
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
import com.globalreports.editor.graphics.GRRectangle;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.combo.GRComboFontName;
import com.globalreports.editor.designer.property.combo.GRComboListFather;
import com.globalreports.editor.designer.swing.table.element.GRComboElement;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.designer.swing.table.GRTableCell;
import com.globalreports.editor.designer.swing.table.GRTableSeparator;
import com.globalreports.editor.designer.swing.table.element.GRColorElement;
import com.globalreports.editor.designer.swing.table.event.GRTableEvent;
import com.globalreports.editor.designer.swing.table.event.GRTableListener;

@SuppressWarnings("serial")
public class GRTableModelRectangle extends GRTableModel implements GRTableListener {
	
	private Object[][] element = {{"Oggetto","Rettangolo"},
								  {new GRTableSeparator("Posizione"), null},
								  {"Posizione relativa", new JCheckBox()},
								  {"Asse X", new JTextField()},
								  {"Asse Y",  new JTextField()},
								  {"Larghezza",  new JTextField()},
								  {"Altezza",  new JTextField()},
								  {new GRTableSeparator("Aspetto"), null},
								  {"Dimensione tratto", new JTextField("0.25")},
								  {"Colore tratto", new GRColorElement(Color.BLACK)},
								  {"Colore riempimento", new GRColorElement()},
								  {new GRTableSeparator("Oggetti dinamici"), null},
								  {"Lista", listFather}};
								  
	private GRRectangle objRectangle;	// Riferimento all'oggetto per poterne modificare le propriet���
	
	public GRTableModelRectangle(GRTableProperty panelProperty, String[] title) {
		super(title);
		this.panelProperty = panelProperty;
		
		createBody(element);
		addGRTableListener(this);
	}
	
	public void setGRObject(GRRectangle ref) {
		this.objRectangle = ref;
	}
	@Override
	public void valueChanged(GRTableEvent e) {
		GRTableCell cell = (GRTableCell)e.getSource();
		
		switch(e.getRow()) {
			case 2:	// X
				objRectangle.setX(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 3:	// Y
				objRectangle.setY(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 4:	// width
				objRectangle.setWidth(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 5:	// height
				objRectangle.setHeight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
			
			case 6:	// widthstroke
				objRectangle.setWidthStroke(Double.parseDouble(e.getValue()));
				break;
			
			case 7:	// colorstroke
				objRectangle.setColorStroke(((GRColorElement)cell.getObjectValue()).getColor());
				//panelProperty.getPage().refreshHeader(GRLibrary.fromMillimetersToPixels(Double.parseDouble(e.getValue())));
				break;
				
			case 8:	// colorfill
				objRectangle.setColorFill(((GRColorElement)cell.getObjectValue()).getColor());
				break;
				
			case 9:	// listfather
				objRectangle.setListFather(e.getValue());
				break;
		}
	
		panelProperty.getPage().repaint();
		
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
	public void setHeight(int value) {
		this.setValueAt(5,1,""+GRLibrary.fromPixelsToMillimeters(value));
	}
	public void setWidthStroke(double value) {
		this.setValueAt(6, 1, ""+value);
	}
	public void setColorStroke(int red, int green, int blue) {
		this.setValueAt(7,1,new Color(red, green, blue));
	}
	public void setColorFill(int red, int green, int blue) {
		if(red == -1 || green == -1 || blue == -1)
			this.setValueAt(8,1,new GRColorElement());
		else
			this.setValueAt(8, 1, new GRColorElement(new Color(red, green, blue)));
		
	}
	public void setListFather(String value) {
		if(value == null)
			this.setValueAt(9, 1, "--Nessuna--");
		else
			this.setValueAt(9,1,value);
	}
}
