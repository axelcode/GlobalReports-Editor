/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.property.GRTableProperty
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

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.swing.table.GRTable;

public class GRTableProperty  {
	public static final int TYPEMODEL_PAGE				= 0;
	public static final int TYPEMODEL_LINE				= 1;
	public static final int TYPEMODEL_RECTANGLE			= 2;
	public static final int TYPEMODEL_IMAGE				= 3;
	public static final int TYPEMODEL_TEXT				= 4;
	public static final int TYPEMODEL_LIST				= 5;
	public static final int TYPEMODEL_TABLELIST			= 6;
	
	private JPanel panelProperty;
	private GRTableModel grtable;
	private GRPage pagina;
	private JSplitPane split;
		
	/* Oggetti tabella. Uno per ogni oggetto */
	private GRTableModel grtablePage;
	private GRTableModel grtableRectangle;
	private GRTableModel grtableLine;
	private GRTableModel grtableImage;
	private GRTableModel grtableText;
	private GRTableModel grtableList;
	private GRTableModel grtableTableList;
	
	public GRTableProperty() {
		String[] title = {"Proprietà","Valore"};
		//grtable = new GRTableModel(title);
		grtable = new GRTableModel(title);
		
		/* Istanzia le tabelle riferite agli oggetti
		 * 
		 */
		grtablePage = new GRTableModelPage(this,title);
		grtableRectangle = new GRTableModelRectangle(this,title);
		grtableLine = new GRTableModelLine(this,title);
		grtableImage = new GRTableModelImage(this,title);
		grtableText = new GRTableModelText(this,title);
		grtableList = new GRTableModelList(this,title);
		grtableTableList = new GRTableModelTableList(this,title);
		
		panelProperty = new JPanel(new GridLayout(0,1));
		panelProperty.setBackground(Color.white);
		panelProperty.add(grtable);
		
		
	}
	public void setSplit(JSplitPane split) {
		this.split = split;
	}
	public void setPage(GRPage pagina) {
		this.pagina = pagina;
	}
	public GRPage getPage() {
		return pagina;
	}
	public JPanel getProperty() {
		return panelProperty;
	}
	public GRTable getTable() {
		return grtable;
	}
	public void setModel(int type) {
		switch(type) {
			case TYPEMODEL_PAGE:
				grtable = grtablePage;
				panelProperty.removeAll();
				
				break;
			
			case TYPEMODEL_RECTANGLE:
				grtable = grtableRectangle;
				panelProperty.removeAll();
				
				break;
				
			case TYPEMODEL_LINE:
				grtable = grtableLine;
				panelProperty.removeAll();
				
				break;
				
			case TYPEMODEL_IMAGE:
				grtable = grtableImage;
				panelProperty.removeAll();
				
				break;
				
			case TYPEMODEL_TEXT:
				grtable = grtableText;
				panelProperty.removeAll();
				
				break;
				
			case TYPEMODEL_LIST:
				grtable = grtableList;
				panelProperty.removeAll();
				
				break;
				
			case TYPEMODEL_TABLELIST:
				grtable = grtableTableList;
				panelProperty.removeAll();
				
				break;
		}
		
		grtable.refresh();
		panelProperty.add(grtable);
		panelProperty.repaint();
		
		/* Quello che segue serve per forzare il refresh dello split */
		split.setDividerSize(split.getDividerSize()+1);
		split.setDividerSize(split.getDividerSize()-1);
		
		if(split.getDividerLocation() < grtable.getHeight())
			split.setDividerLocation(grtable.getHeight()+1);
	}
	
	public void addListFather(String value) {
		grtableRectangle.addListFather(value);
		grtableText.addListFather(value);
		
	}
	public void removeListFather(String value) {
		grtableRectangle.removeListFather(value);
		grtableText.removeListFather(value);
	}
	
	
}
