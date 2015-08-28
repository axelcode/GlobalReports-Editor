/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRColorCellEditor
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

import com.globalreports.editor.tools.GRLibrary;

import java.util.EventObject;

@SuppressWarnings("serial")
public class GRColorCellEditor extends AbstractCellEditor {
	public static final int TRANSPARENT		= -1;
	
	private JLabel lblColor;
	private Color color;
	private JPanel panel;
	private String strColor;
	
	public GRColorCellEditor() {
		this(255,255,255);
	}
	public GRColorCellEditor(int red, int green, int blue) {
		if(red == -1 || green == -1 || blue == -1) {
			this.color = null;
			
		} else {
			this.color = new Color(red,green,blue);
			
		}
		
		this.init();
	}
	public GRColorCellEditor(Color c) {
		this.color = c;
		
		this.init();
	}
	public GRColorCellEditor(int c) {
		if(c == GRColorCellEditor.TRANSPARENT) {
			color = new Color(255,255,255);
			
		} else {
			color = new Color(c,c,c);
			
		}
		
		this.init();
	}
	private void init() {
		panel = new JPanel();
		panel.setBackground(color);
		
		strColor = getColorString();
		lblColor = new JLabel();
		
		lblColor.setFont(new Font("Lucida Grande",Font.PLAIN,10));

		lblColor.setForeground(GRLibrary.ColorText(color));
		lblColor.setText(strColor);
		panel.add(lblColor);
		
	}
	public String getColorString() {
		if(color == null)
			return "trasparente";
		
		return color.getRed()+" "+color.getGreen()+" "+color.getBlue();
	}
	public void removeCellEditorListener(CellEditorListener l) {
		System.out.println("GRColorCellEditor::removeCellEditorListener");
		//cellEditors[objSel].removeCellEditorListener(l);
	}
	public void addCellEditorListener(CellEditorListener l) {
		System.out.println("GRColorCellEditor::addCellEditorListener");
	}
	public void cancelCellEditing() {
		System.out.println("GRColorCellEditor::cancelCellEditing");
	}
	public boolean stopCellEditing() {
		System.out.println("GRColorCellEditor::stopCellEditing");
		return true;
	}
	public boolean shouldSelectCell(EventObject anEvent) {
		System.out.println("GRColorCellEditor::shouldSelectCell");
		return true;
	}
	public boolean isCellEditable(EventObject anEvent) {
		System.out.println("GRColorCellEditor::isCellEditable");
		return true;
	}
	public Object getCellEditorValue() {
		return null;
	}
	public void editColor() {
		Color c = JColorChooser.showDialog(panel, "Color Chooser", color);
		
		if(c == null) 
			return;
			
		color = c;
		panel.setBackground(color);
		
		lblColor.setForeground(GRLibrary.ColorText(color));
		lblColor.setText(getColorString());
	}
	public Color getColor() {
		return color;
	}
	public Component getCell() {
		
		return panel;
	}
	
}