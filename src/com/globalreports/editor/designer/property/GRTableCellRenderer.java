/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableCellRenderer
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
import javax.swing.table.*;

import com.globalreports.editor.designer.property.combo.*;
import com.globalreports.editor.designer.property.element.*;

@SuppressWarnings("serial")
public class GRTableCellRenderer extends DefaultTableCellRenderer {
	private JCheckBox checkBox = new JCheckBox();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
	    if (value instanceof Boolean) { // Boolean
			checkBox.setSelected(((Boolean) value).booleanValue());
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			return checkBox;
	    } else if(value instanceof GRComboProperty) {
	    	String str = (value == null) ? "" : ((GRComboProperty)value).getValueSelected();
	        return super.getTableCellRendererComponent(table, str, isSelected,
	            hasFocus, row, column);
	    } else if(value instanceof GRColorString) {
	    	//Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			Color c = ((GRColorString)value).getColor();
			cell.setBackground(c);
			return cell;
		} else if(value instanceof GRColorCellEditor) {
			return ((GRColorCellEditor)value).getCell();
		}
	    String str = (value == null) ? "" : value.toString();
		cell.setBackground(Color.WHITE);
	    return super.getTableCellRendererComponent(table, str, isSelected, hasFocus, row, column);
	}
}