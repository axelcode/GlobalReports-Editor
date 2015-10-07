/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.table.GRTableRow
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
package com.globalreports.editor.designer.swing.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

public class GRTableRow extends JPanel {
	private GRTable grtable;
	private Vector<GRTableCell> grcell;
	
	private boolean typeAlternate;
	private boolean selected;
	
	public GRTableRow(GRTable grtable, Object[] r, boolean type) {
		this.grtable = grtable;
		this.typeAlternate = type;
		
		grcell = new Vector<GRTableCell>();
		selected = false;
		
		setBackground(Color.white);
		setMaximumSize(new Dimension(0,8));
		setLayout(new GridLayout(1,0));
		
		//addMouseListener(this);
		
		if(r == null) {
			// Genera una riga vuota
			
		} else {
			createRecord(r);
		}
	}
	
	private void createRecord(Object[] r) {
		
		// Cicla per tutte le colonne
		for(int i = 0;i < grtable.getNumColumns();i++) {
			
			JPanel p = new JPanel();
			p.setBackground(Color.white);
			p.setLayout(new GridLayout(1,0));
				
			GRTableCell cell = new GRTableCell(grtable,r[i],typeAlternate);
			grcell.add(cell);
			
			add(cell);
		}
		
	}
	public String getValue(int indexColumn) throws ArrayIndexOutOfBoundsException {
		return grcell.get(indexColumn).getValue();
		
	}
	public void setValue(int indexColumn, String value) {
		grcell.get(indexColumn).setValue(value);
	}
	public void setValue(int indexColumn, Object value) {
		grcell.get(indexColumn).setValue(value);
	}
	public void setSelected(boolean value) {
		selected = value;
		
		for(int i = 0;i < grcell.size();i++)
			grcell.get(i).setSelected(selected);
		
	}
	public boolean isSelected() {
		return selected;
	}
	public GRTableCell getCell(int index) {
		return grcell.get(index);
	}
}
