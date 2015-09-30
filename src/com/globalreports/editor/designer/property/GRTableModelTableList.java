/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableModelTableList
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

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.graphics.GRTableList;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRTableModelTableList extends GRTableModel implements TableModelListener {
	private Object[][] element = {{"Type","Table List"},
			  {"",""},
			  {"Relative Height Position",new Boolean(false)},
			  {"Name XML dati",""},
			  {"Left",""},
			  {"Top",""},
			  {"Width",""},
			  {"",""},
			  {"<html><b>HEADER</b></html>",""},
			  {"WidthStroke","0.25"},
			  {"ColorStroke",new GRColorCellEditor(0,0,0)},
			  {"ColorFill",new GRColorCellEditor(GRColorCellEditor.TRANSPARENT)},
			  {"MinHeight",""},
			  {"",""},
			  {"<html><b>BODY</b></html>",""},
			  {"WidthStroke","0.25"},
			  {"ColorStroke",new GRColorCellEditor(0,0,0)},
			  {"ColorFill",new GRColorCellEditor(GRColorCellEditor.TRANSPARENT)},
			  {"MinHeight",""},
			  {"",""},
			  {"<html><b>FOOTER</b></html>",""},
			  {"WidthStroke","0.25"},
			  {"ColorStroke",new GRColorCellEditor(0,0,0)},
			  {"ColorFill",new GRColorCellEditor(GRColorCellEditor.TRANSPARENT)},
			  {"MinHeight",""}};
			  
	private GRTableList objTableList;	// Riferimento all'oggetto per poterne modificare le propriet���

	public GRTableModelTableList(GRPage page) {
		this.pagina = page;
		eventChangeActive = false;
		setDataVector(element,header);
		addTableModelListener(this);
	}
	
	public void tableChanged(TableModelEvent e) {
		
		if(eventChangeActive) {
			
			switch(e.getFirstRow()) {
				case 3:
					objTableList.setNameXml(getValueAt(3,1).toString());
					break;
					
				case 4:
					objTableList.setX(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(4,1).toString())));
					break;
				
				case 5:
					objTableList.setY(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(5,1).toString())));
					break;	

				case 6:
					objTableList.setWidth(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(6,1).toString())));
					break;	

				case 9:
					objTableList.setHeaderWidthStroke(Double.parseDouble(getValueAt(9,1).toString()));
					break;
				
				case 10:
					objTableList.setHeaderColorStroke(((GRColorCellEditor)getValueAt(10,1)).getColor());
					break;
				
				case 11:
					objTableList.setHeaderColorFill(((GRColorCellEditor)getValueAt(11,1)).getColor());
					break;
					
				case 12:
					objTableList.setHeaderMinHeight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(12,1).toString())));
					break;	

				case 15:
					objTableList.setBodyWidthStroke(Double.parseDouble(getValueAt(15,1).toString()));
					break;
				
				case 16:
					objTableList.setBodyColorStroke(((GRColorCellEditor)getValueAt(16,1)).getColor());
					break;
				
				case 17:
					objTableList.setBodyColorFill(((GRColorCellEditor)getValueAt(17,1)).getColor());
					break;
					
				case 18:
					objTableList.setBodyMinHeight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(18,1).toString())));
					break;	
					
				case 21:
					objTableList.setFooterWidthStroke(Double.parseDouble(getValueAt(21,1).toString()));
					break;
				
				case 22:
					objTableList.setFooterColorStroke(((GRColorCellEditor)getValueAt(22,1)).getColor());
					break;
				
				case 23:
					objTableList.setFooterColorFill(((GRColorCellEditor)getValueAt(23,1)).getColor());
					break;
					
				case 24:
					objTableList.setFooterMinHeight(GRLibrary.fromMillimetersToPixels(Double.parseDouble(getValueAt(24,1).toString())));
					break;	
			}
			
			eventChangeActive = false;
			pagina.repaint();
		}
	}
	public boolean isCellEditable(int row, int column) {
        if (column == 1) {
			if(row == 0 || row == 1 || row == 7 || row == 13 || row == 18 || row == 19)
				return false;
				
        	return true;
        }
        
        return false;
    }
	
	public void setGRObject(GRTableList ref) {
		this.objTableList = ref;
	}
	public void setLeft(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),4,1);
	}
	public void setTop(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),5,1);
	}
	public void setWidth(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value),6,1);
	}
	public void setNameXml(String value) {
		this.setValueAt(value, 3, 1);
	}
	
	// HEAD
	public void setHeaderWidthStroke(double value) {
		this.setValueAt(""+value,9,1);
	}
	public void setHeaderColorStroke(Color c) {
		this.setValueAt(new GRColorCellEditor(c), 10, 1);
	}
	public void setHeaderColorStroke(int red, int green, int blue) {
		this.setValueAt(new GRColorCellEditor(red,green,blue),10,1);
	}
	public void setHeaderColorFill(Color c) {
		if(c == null)
			this.setValueAt(new GRColorCellEditor(-1,-1,-1,true), 11, 1);
		else
			this.setValueAt(new GRColorCellEditor(c.getRed(),c.getGreen(),c.getBlue(),true), 11, 1);
	}
	public void setHeaderColorFill(int red, int green, int blue) {
		if(red == -1 || green == -1 || blue == -1)
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),11,1);
		else
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),11,1);
	}
	public void setHeaderMinHeight(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value), 12, 1);
	}
	
	// BODY
	public void setBodyWidthStroke(double value) {
		this.setValueAt(""+value,15,1);
	}
	public void setBodyColorStroke(Color c) {
		this.setValueAt(new GRColorCellEditor(c), 16, 1);
	}
	public void setBodyColorStroke(int red, int green, int blue) {
		this.setValueAt(new GRColorCellEditor(red,green,blue),16,1);
	}
	public void setBodyColorFill(Color c) {
		if(c == null)
			this.setValueAt(new GRColorCellEditor(-1,-1,-1,true), 17, 1);
		else
			this.setValueAt(new GRColorCellEditor(c.getRed(),c.getGreen(),c.getBlue(),true), 17, 1);
	}
	public void setBodyColorFill(int red, int green, int blue) {
		if(red == -1 || green == -1 || blue == -1)
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),17,1);
		else
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),17,1);
	}
	public void setBodyMinHeight(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value), 18, 1);
	}
	
	// FOOTER
	public void setFooterWidthStroke(double value) {
		this.setValueAt(""+value,21,1);
	}
	public void setFooterColorStroke(Color c) {
		this.setValueAt(new GRColorCellEditor(c), 22, 1);
	}
	public void setFooterColorStroke(int red, int green, int blue) {
		this.setValueAt(new GRColorCellEditor(red,green,blue),22,1);
	}
	public void setFooterColorFill(Color c) {
		if(c == null)
			this.setValueAt(new GRColorCellEditor(-1,-1,-1,true), 23, 1);
		else
			this.setValueAt(new GRColorCellEditor(c.getRed(),c.getGreen(),c.getBlue(),true), 23, 1);
	}
	public void setFooterColorFill(int red, int green, int blue) {
		if(red == -1 || green == -1 || blue == -1)
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),23,1);
		else
			this.setValueAt(new GRColorCellEditor(red,green,blue,true),23,1);
	}
	public void setFooterMinHeight(int value) {
		this.setValueAt(""+GRLibrary.fromPixelsToMillimeters(value), 24, 1);
	}
}
