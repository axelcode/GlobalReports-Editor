/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableCellEditor
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

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.util.EventObject;

import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.designer.property.combo.*;

public class GRTableCellEditor implements TableCellEditor {
	private final static int CELLTYPE_BOOLEAN			= 0;
	private final static int CELLTYPE_EDITSTRING		= 1;
	private final static int CELLTYPE_COLOR				= 2;
	private final static int CELLTYPE_COMBO_FONTNAME	= 3;
	private final static int CELLTYPE_COMBO_FONTALIGN	= 4;
	private final static int CELLTYPE_COMBO_FONTSTYLE	= 5;
	private final static int CELLTYPE_COMBO_FONTSIZE	= 6;
	
	private DefaultCellEditor checkEditor;
	private DefaultCellEditor comboEditorFontAlignment;
	private DefaultCellEditor comboEditorFontName;
	private DefaultCellEditor comboEditorFontStyle;
	private DefaultCellEditor comboEditorFontSize;
	private DefaultCellEditor textEditor;
	private GRColorCellEditor colorEditor;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comboFontAlignment;
	@SuppressWarnings("rawtypes")
	private JComboBox comboFontName;
	@SuppressWarnings("rawtypes")
	private JComboBox comboFontStyle;
	@SuppressWarnings("rawtypes")
	private JComboBox comboFontSize;
	
	private int objSel;
	private int rowSel;
	private int colSel;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GRTableCellEditor() {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		checkEditor = new DefaultCellEditor(checkBox);
    
		comboFontAlignment = new JComboBox();
		comboFontAlignment.addItem("Left");
		comboFontAlignment.addItem("Center");
		comboFontAlignment.addItem("Right");
		comboFontAlignment.addItem("Justify");
	    comboEditorFontAlignment = new DefaultCellEditor(comboFontAlignment);
	    
	    comboFontName = new JComboBox();
	    for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
	    	comboFontName.addItem(GRFontProperty.grfontInstalled.get(i).getBaseName());
		}
	    comboEditorFontName = new DefaultCellEditor(comboFontName);
	    
	    comboFontStyle = new JComboBox();
	    comboFontStyle.addItem("Normale");
	    comboFontStyle.addItem("Corsivo");
	    comboFontStyle.addItem("Grassetto");
	    comboFontStyle.addItem("Grassetto Corsivo");
	    comboEditorFontStyle = new DefaultCellEditor(comboFontStyle);
	    
	    String[] fontsizes = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","72"};
	    comboFontSize = new JComboBox(fontsizes);
	    comboFontSize.setEditable(true);
	    comboEditorFontSize = new DefaultCellEditor(comboFontSize);
	    
		JTextField textField = new JTextField();
		textField.setFont(new Font("Lucida Grande",Font.PLAIN,10));
		textEditor = new DefaultCellEditor(textField);
		
		colorEditor = new GRColorCellEditor();
		
		objSel = 0;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//System.out.println("getTableCellEditorComponent: "+value);
		((GRTableModel)table.getModel()).activeListener();

		if (value instanceof Boolean) { // Boolean
			objSel = CELLTYPE_BOOLEAN;
			return checkEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
		} else if(value instanceof GRComboFontName) {
			objSel = CELLTYPE_COMBO_FONTNAME;
			rowSel = row;
			colSel = column;
			String str = (value == null) ? "" : ((GRComboFontName)value).getValueSelected();
			return comboEditorFontName.getTableCellEditorComponent(table, str, isSelected, row, column);
		} else if(value instanceof GRComboFontAlignment) {
			objSel = CELLTYPE_COMBO_FONTALIGN;
			rowSel = row;
			colSel = column;
			String str = (value == null) ? "" : ((GRComboFontAlignment)value).getValueSelected();
			return comboEditorFontAlignment.getTableCellEditorComponent(table, str, isSelected, row, column);
		} else if(value instanceof GRComboFontStyle) {
			objSel = CELLTYPE_COMBO_FONTSTYLE;
			rowSel = row;
			colSel = column;
			String str = (value == null) ? "" : ((GRComboFontStyle)value).getValueSelected();
			return comboEditorFontStyle.getTableCellEditorComponent(table, str, isSelected, row, column);
		} else if(value instanceof GRComboFontSize) {
			objSel = CELLTYPE_COMBO_FONTSIZE;
			rowSel = row;
			colSel = column;
			String str = (value == null) ? "" : ((GRComboFontSize)value).getValueSelected();
			return comboEditorFontSize.getTableCellEditorComponent(table, str, isSelected, row, column);
		} else if (value instanceof String) { // String
			objSel = CELLTYPE_EDITSTRING;
			rowSel = row;
			colSel = column;
			return textEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
		} else if(value instanceof GRColorCellEditor) {
			objSel = CELLTYPE_COLOR;
			rowSel = row;
			colSel = column;
			
			colorEditor = (GRColorCellEditor)value;
			colorEditor.editColor();
			
			table.setValueAt(new GRColorCellEditor(colorEditor.getColor()),row,column);
			return null;
			//return colorEditor.getCell();
			
		}
    
		return null;
	}

	public Object getCellEditorValue() {
		String str;
		System.out.println("getCellEditorValue");
		switch (objSel) {
			//case COMBO:
			//	String str = (String) comboBox.getSelectedItem();
			//	return new GRComboString(str);
				
		    case CELLTYPE_BOOLEAN:
		    	return checkEditor.getCellEditorValue();
		    
		    case CELLTYPE_COMBO_FONTNAME:
		    	str = (String)comboFontName.getSelectedItem();
		    	System.out.println(str);
		        return new GRComboFontName(str);
		    
			case CELLTYPE_COMBO_FONTALIGN:
		    	str = (String)comboFontAlignment.getSelectedItem();
		    	System.out.println(str);
		        return new GRComboFontAlignment(str);
			
			case CELLTYPE_COMBO_FONTSTYLE:
		    	str = (String)comboFontStyle.getSelectedItem();
		    	System.out.println(str);
		        return new GRComboFontStyle(str);
		    
			case CELLTYPE_COMBO_FONTSIZE:
		    	str = (String)comboFontSize.getSelectedItem();
		    	System.out.println(str);
		        return new GRComboFontSize(str);
		        
		    case CELLTYPE_EDITSTRING:
		    	System.out.println("VALUE ["+rowSel+" - "+colSel+"]: "+textEditor.getCellEditorValue());
				return textEditor.getCellEditorValue();
	    
			case CELLTYPE_COLOR:
				//System.out.println(colorString.toString());
				//return colorString.toString();
				return colorEditor.getCell();
				
			default:
				return null;
	    }
	}
	
	public Component getComponent() {
		System.out.println("getComponent");
		//return cellEditors[objSel].getComponent();
		return null;
	}

	public boolean stopCellEditing() {
		System.out.println("StopCellEditing");
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				return checkEditor.stopCellEditing();
			
			case CELLTYPE_COMBO_FONTNAME:
		    	return comboEditorFontName.stopCellEditing();
		    
			case CELLTYPE_COMBO_FONTALIGN:
		    	return comboEditorFontAlignment.stopCellEditing();
		    	
			case CELLTYPE_COMBO_FONTSTYLE:
		    	return comboEditorFontStyle.stopCellEditing();
			
			case CELLTYPE_COMBO_FONTSIZE:
		    	return comboEditorFontSize.stopCellEditing();
		    	
			case CELLTYPE_EDITSTRING:
				return textEditor.stopCellEditing();
				
		}
		
		return true;
	}

	public void cancelCellEditing() {
		System.out.println("cancelCellEditing");
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				checkEditor.cancelCellEditing();
				break;
			
			case CELLTYPE_COMBO_FONTNAME:
		    	comboEditorFontName.cancelCellEditing();
				break;
				
			case CELLTYPE_COMBO_FONTALIGN:
		    	comboEditorFontAlignment.cancelCellEditing();
				break;
				
			case CELLTYPE_COMBO_FONTSTYLE:
		    	comboEditorFontStyle.cancelCellEditing();
				break;
				
			case CELLTYPE_COMBO_FONTSIZE:
		    	comboEditorFontSize.cancelCellEditing();
				break;
				
			case CELLTYPE_EDITSTRING:
				textEditor.cancelCellEditing();
				break;
		}
	}

	public boolean isCellEditable(EventObject anEvent) {
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				return checkEditor.isCellEditable(anEvent);
				
			case CELLTYPE_EDITSTRING:
				return textEditor.isCellEditable(anEvent);
			
			case CELLTYPE_COMBO_FONTNAME:
		    	return comboEditorFontName.isCellEditable(anEvent);
		    
			case CELLTYPE_COMBO_FONTALIGN:
		    	return comboEditorFontAlignment.isCellEditable(anEvent);
		    	
			case CELLTYPE_COMBO_FONTSTYLE:
		    	return comboEditorFontStyle.isCellEditable(anEvent);
		    	
			case CELLTYPE_COMBO_FONTSIZE:
		    	return comboEditorFontSize.isCellEditable(anEvent);
		}
	
		return true;
	}

	public boolean shouldSelectCell(EventObject anEvent) {
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				return checkEditor.shouldSelectCell(anEvent);
				
			case CELLTYPE_EDITSTRING:
				return textEditor.shouldSelectCell(anEvent);
				
			case CELLTYPE_COMBO_FONTNAME:
		    	return comboEditorFontName.shouldSelectCell(anEvent);
		    
			case CELLTYPE_COMBO_FONTALIGN:
		    	return comboEditorFontAlignment.shouldSelectCell(anEvent);
		    	
			case CELLTYPE_COMBO_FONTSTYLE:
		    	return comboEditorFontStyle.shouldSelectCell(anEvent);
		    	
			case CELLTYPE_COMBO_FONTSIZE:
		    	return comboEditorFontSize.shouldSelectCell(anEvent);
		    	
		}
		return true;
	}

	public void addCellEditorListener(CellEditorListener l) {
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				checkEditor.addCellEditorListener(l);
				break;
				
			case CELLTYPE_EDITSTRING:
				textEditor.addCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTNAME:
		    	comboEditorFontName.addCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTALIGN:
		    	comboEditorFontAlignment.addCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTSTYLE:
		    	comboEditorFontStyle.addCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTSIZE:
		    	comboEditorFontSize.addCellEditorListener(l);
				break;
		}
	}

	public void removeCellEditorListener(CellEditorListener l) {
		switch(objSel) {
			case CELLTYPE_BOOLEAN:
				checkEditor.removeCellEditorListener(l);
				
			case CELLTYPE_EDITSTRING:
				textEditor.removeCellEditorListener(l);
				
			case CELLTYPE_COMBO_FONTNAME:
		    	comboEditorFontName.removeCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTALIGN:
		    	comboEditorFontAlignment.removeCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTSTYLE:
		    	comboEditorFontStyle.removeCellEditorListener(l);
				break;
				
			case CELLTYPE_COMBO_FONTSIZE:
		    	comboEditorFontSize.removeCellEditorListener(l);
				break;
		}
	}

	public void setClickCountToStart(int n) {
		System.out.println("setClickCountToStart");
		
		//cellEditors[objSel].setClickCountToStart(n);
	}

	public int getClickCountToStart() {
		return 1;
		
	}
}
	
	