/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.table.GRTable
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

import com.globalreports.editor.designer.swing.table.event.GRTableEvent;
import com.globalreports.editor.designer.swing.table.event.GRTableListener;

@SuppressWarnings("serial")
public class GRTable extends JPanel implements MouseListener {
	private GRTableListener eventObj;
	private Vector<GRTableRow> grrow;
	
	private GRTableHeader header;
	
	private JPanel panelContainer;
	private int numColumns;
	private int numRows;
	private int numElements;
	private boolean pair;
	
	private Color cRowOdd = Color.white;
	private Color cRowEqual = new Color(209,221,233);
	private Color cRowSelected = new Color(57,105,138);
	
	private GRTableRow rowSelected;
	private Font f = new Font("Tahoma",Font.PLAIN,10);
	
	public GRTable() {
		this(null, null);
		
	}
	public GRTable(String[] head) {
		this(head, null);
	}
	public GRTable(Object[][] obj) {
		this(null, obj);
	}
	public GRTable(String[] head, Object[][] obj) {
		setLayout(new BorderLayout(0,0));
		
		numColumns = 1;
		numRows = 0;
		rowSelected = null;
		pair = true;
		
		panelContainer = new JPanel(new GridLayout(0,1,0,0));
		if(head != null && head.length > 0) {
			createHead(head);
		}
		if(obj != null)
			createBody(obj);
		
		add(panelContainer, BorderLayout.NORTH);
		setBackground(Color.white);
	}
	public void addGRTableListener(GRTableListener e) {
		this.eventObj = e;
	}
	public void activateTableEvent(GRTableCell cell) {
		if(eventObj == null)
			return;
		
		for(int x = 0;x < grrow.size();x++) {
			GRTableRow row = grrow.get(x);
			
			for(int y = 0;y < numColumns;y++) {
				if(row.getCell(y) == cell) {
					eventObj.valueChanged(new GRTableEvent(cell, cell.getValue(), x, y));
				}
			}
		}
	}
	public void addRow(Object[] obj) {
		GRTableRow row = new GRTableRow(this, obj, pair);
		row.addMouseListener(this);
		
		panelContainer.add(row);
		
		if(grrow == null)
			grrow = new Vector<GRTableRow>();
		grrow.add(row);
		
		numRows++;
		numElements++;
		
		pair = !pair;
	}
	public void addSeparator(String value) {
		GRTableSeparator separator = new GRTableSeparator(value);
		
		panelContainer.add(separator);
		pair = true;
		
		numElements++;
	}
	public void addSeparator(GRTableSeparator separator) {
		panelContainer.add(separator);
		pair = true;
		
		numElements++;
	}
	public String getValueAt(int iRow, int iCol) {
		try {
			GRTableRow r = grrow.get(iRow);
			
			return r.getValue(iCol);
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("ERR");
		}
		return null;
	}
	public void setValueAt(int iRow, int iCol, String value) {
		try {
			GRTableRow r = grrow.get(iRow);
			
			r.setValue(iCol, value);
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}
	}
	public void setValueAt(int iRow, int iCol, Object value) {
		try {
			GRTableRow r = grrow.get(iRow);
			
			r.setValue(iCol, value);
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public Color getColorOdd() {
		return cRowOdd;
	}
	public Color getColorEqual() {
		return cRowEqual;
	}
	public Color getColorSelected() {
		return cRowSelected;
	}
	public Color getColorDefault(boolean value) {
		if(value)
			return cRowOdd;
		else
			return cRowEqual;
	}
	public Font getFont() {
		return f;
	}
	public void nextFocus(GRTableCell cell) {
		int nextRow = 0;
		
		if(cell == null)
			return;
		
		for(int x = 0;x < grrow.size();x++) {
			GRTableRow row = grrow.get(x);
			
			for(int y = 0;y < numColumns;y++) {
				if(row.getCell(y) == cell) {
					nextRow = x + 1;
					
					if(nextRow == grrow.size())
						nextRow = 0;
					
					row.setSelected(false);
					row = grrow.get(nextRow);
					row.setSelected(true);
					
					rowSelected = row;
					break;
				}
			}
		}
	}
	public void refresh() {
		if(rowSelected != null) {
			GRTableListener tempEvent = eventObj;
			eventObj = null;	// Sgancia un eventuale ascoltatore
			
			rowSelected.setSelected(false);	// Esegue il refresh
			eventObj = tempEvent;	// Riesegue l'aggancio dell'ascoltatore
		}
					
		rowSelected = null;
		
	}
	public int getHeight() {
		return (numElements + 1) * 28;
	}
	
	private void createHead(String[] head) {
		
		header = new GRTableHeader(head);
		panelContainer.add(header);
		
		numColumns = header.getNumColumns();
	}
	protected void createBody(Object[][] obj) {
	
		for(int i = 0;i < obj.length;i++) {
			if(obj[i][0] instanceof GRTableSeparator) {
				this.addSeparator((GRTableSeparator)obj[i][0]);
			} else {
				this.addRow(obj[i]);
			}
					
		}
	}
	private void selectRow(GRTableRow row) {
		if(row.isSelected())
			return;
		
		if(rowSelected != null)
			rowSelected.setSelected(false);
		row.setSelected(true);
		
		rowSelected = row;
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		GRTableRow row = (GRTableRow)e.getSource();
		
		selectRow(row);
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
