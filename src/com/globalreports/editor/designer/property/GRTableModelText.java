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

import javax.swing.event.*;

import com.globalreports.editor.tools.GRLibrary;
import com.globalreports.editor.graphics.GRText;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.combo.*;

@SuppressWarnings("serial")
public class GRTableModelText extends GRTableModel implements TableModelListener {
	private Object[][] element = {{"Type","Text"},
			  {"",""},
			  {"Relative Height Position",new Boolean(false)},
			  {"Left",""},
			  {"Top",""},
			  {"Width",""},
			  {"Line Spacing",""},
			  {"",""},
			  {"Font",new GRComboFontName("Verdana")},
			  {"Style",new GRComboFontStyle("Normal")},
			  {"Size",new GRComboFontSize("10")},
			  {"Alignment",new GRComboFontAlignment("Left")},
			  {"Color",new GRColorCellEditor(0,0,0)}};
			  
	private GRText objText;	// Riferimento all'oggetto per poterne modificare le propriet�
	
	public GRTableModelText(GRPage page) {
		this.pagina = page;
		eventChangeActive = false;
		setDataVector(element,header);
		addTableModelListener(this);
	}
	
	public void tableChanged(TableModelEvent e) {
	
		if(eventChangeActive) {
			switch(e.getFirstRow()) {
				case 2:		// H Position
					objText.setHPosition((Boolean)getValueAt(2,1));
					break;
					
				case 3:
					objText.setX(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(3,1).toString())));
					break; 
					
				case 4:
					objText.setY(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(4,1).toString())));
					break;	
					
				case 5:
					objText.setWidth(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(5,1).toString())));
					break;	
				
				case 6:
					objText.setLineSpacing(Float.parseFloat(getValueAt(6,1).toString()));
					break;
					
				case 8:		// Font family
					objText.setFontFamily(((GRComboFontName)getValueAt(8,1)).getValueSelected());
					break;
					
				case 9:		// Font Style
					objText.setFontStyle(((GRComboFontStyle)getValueAt(9,1)).getValueSelected());
					break;
					
				case 10:		// Font Size
					objText.setFontSize(Integer.parseInt(((GRComboFontSize)getValueAt(10,1)).getValueSelected()));
					break;
					
				case 11:	// Font Alignment
					objText.setFontAlignment(((GRComboFontAlignment)getValueAt(11,1)).getValueSelected());
					break;
				
				case 12:	// Font Color
					break;
		}
		
		objText.refresh();
		
		eventChangeActive = false;
		pagina.repaint();
		}
	}
	public boolean isCellEditable(int row, int column) {
		if (column == 1) {
			if(row == 0 || row == 1 || row == 7)
				return false;
			
			return true;
		}
	
		return false;
	}
	
	public void setGRObject(GRText ref) {
		this.objText = ref;
	}
	public void setHPosition(boolean value) {
		this.setValueAt(new Boolean(value),2,1);
	}
	public void setLeft(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),3,1);
	}
	public void setTop(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),4,1);
	}
	public void setWidth(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),5,1);
	}
	public void setLineSpacing(float value) {
		this.setValueAt(""+value, 6, 1);
	}
	public void setFont(String fontName) {
		this.setValueAt(new GRComboFontName(fontName),8,1);
	}
	public void setFontStyle(String fStyle) {
		this.setValueAt(new GRComboFontStyle(fStyle),9,1);
	}
	public void setFontSize(int fSize) {
		this.setValueAt(new GRComboFontSize(""+fSize),10,1);
	}
	public void setFontAlignment(String fAlign) {
		this.setValueAt(new GRComboFontAlignment(fAlign),11,1);
	}
	
}
