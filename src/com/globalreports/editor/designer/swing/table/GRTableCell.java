/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.table.GRTableCell
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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.globalreports.editor.designer.swing.table.element.GRComboElement;
import com.globalreports.editor.designer.swing.table.element.GRColorElement;

@SuppressWarnings("serial")
public class GRTableCell extends JPanel implements MouseListener, FocusListener, ItemListener, KeyListener {
	public static final int		TYPECELL_STRING		= 1;
	public static final int		TYPECELL_CHECKBOX	= 2;
	public static final int 	TYPECELL_TEXTFIELD	= 3;
	public static final int 	TYPECELL_COMBOBOX	= 4;
	public static final int		TYPECELL_COLOR		= 5;
	
	private int typeCell;
	private CardLayout layout;
	
	private String cellValue;
	private Object objValue;
	
	/* Oggetti di supporto */
	// JCheckBox
	JCheckBox chkDefault;
	JCheckBox chkSelected;
	
	// JTextField
	JLabel labelValue;
	JTextField txtValue;
	
	// JComboBox
	JLabel labelValueCombo;
	JComboBox cmbValue;
	
	// Color
	GRColorElement colorDefault;
	GRColorElement colorSelected;

	private GRTable grtable;
	
	public GRTableCell(GRTable grtable, Object obj, boolean pair) {
		this.grtable = grtable;
		
		layout = new CardLayout();
		setBackground(Color.white);
		setLayout(layout);
		
		JPanel pDefault = new JPanel();
		pDefault.setBackground(grtable.getColorDefault(pair));
		
		JPanel pSelected = new JPanel();
		pSelected.setBackground(grtable.getColorSelected());
				
		if(obj instanceof String) {
			typeCell = TYPECELL_STRING;
			
			pDefault.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel l1 = new JLabel((String)obj);
			pDefault.add(l1);
			
			pSelected.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel l = new JLabel((String)obj);
			l.setForeground(Color.white);
			pSelected.add(l);
			
			cellValue = l1.getText();
		} else if(obj instanceof JCheckBox) {
			typeCell = TYPECELL_CHECKBOX;
			
			chkDefault = new JCheckBox();
			chkDefault.setOpaque(false);
			pDefault.setLayout(new FlowLayout(FlowLayout.CENTER));
			pDefault.add(chkDefault);
						
			chkSelected = new JCheckBox();
			chkSelected.setOpaque(false);
			pSelected.setLayout(new FlowLayout(FlowLayout.CENTER));
			pSelected.setBackground(Color.white);
			pSelected.add(chkSelected);
			
			cellValue = "false";
			
		} else if(obj instanceof JTextField) {
			typeCell = TYPECELL_TEXTFIELD;
			
			txtValue = (JTextField)obj;
			labelValue = new JLabel(txtValue.getText());
			
			pDefault.setLayout(new FlowLayout(FlowLayout.LEFT));
			pDefault.add(labelValue);
			
			pSelected.setLayout(new GridLayout(1,1));
			pSelected.add(txtValue);
			txtValue.addFocusListener(this);
			txtValue.addKeyListener(this);
			
			cellValue = labelValue.getText();
			
		} else if(obj instanceof JComboBox) {
			typeCell = TYPECELL_COMBOBOX;
			
			cmbValue = (JComboBox)obj;
			cmbValue.addItemListener(this);
			labelValueCombo = new JLabel(""+cmbValue.getSelectedItem());
			
			pDefault.setLayout(new FlowLayout(FlowLayout.LEFT));
			pDefault.add(labelValueCombo);
			
			pSelected.setLayout(new GridLayout(1,1));
			pSelected.add(cmbValue);
			
			cellValue = labelValueCombo.getText();
		} else if(obj instanceof GRComboElement) {
			typeCell = TYPECELL_COMBOBOX;
			
			cmbValue = (GRComboElement)obj;
			cmbValue.addItemListener(this);
			
			labelValueCombo = new JLabel(""+cmbValue.getSelectedItem());
			
			pDefault.setLayout(new FlowLayout(FlowLayout.LEFT));
			pDefault.add(labelValueCombo);
			
			pSelected.setLayout(new GridLayout(1,1));
			pSelected.add(cmbValue);
			
			cellValue = labelValueCombo.getText();
		} else if(obj instanceof GRColorElement) {
			typeCell = TYPECELL_COLOR;
			
			GRColorElement color = (GRColorElement)obj;
			
			colorDefault = new GRColorElement(color.getColor(), color.transparency());
			colorSelected = new GRColorElement(color.getColor(), color.transparency());
			
			pDefault.setLayout(new GridLayout(1,1));
			pDefault.add(colorDefault);
			
			pSelected.setLayout(new GridLayout(1,1));
			pSelected.add(colorSelected);
			
			cellValue = colorDefault.toString();
			objValue = colorDefault;
			
			colorDefault.setCell(this);
			colorSelected.setCell(this);
		}
		
		//addFocusListener(this);
		
		add(pDefault);
		add(pSelected);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
		g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
	}
	
	public JComponent getCell() {
		return this;
	}
	public Object getObjectValue() {
		return objValue;
	}
	public void setSelected(boolean value) {
		if(value) {
			if(typeCell == TYPECELL_TEXTFIELD) {
				txtValue.setText(labelValue.getText());
			} else if(typeCell == TYPECELL_CHECKBOX) {
				chkSelected.setSelected(chkDefault.isSelected());
			} else if(typeCell == TYPECELL_COMBOBOX) {
				cmbValue.setSelectedItem(labelValueCombo.getText());
			} else if(typeCell == TYPECELL_COLOR) {
				colorSelected.setColor(colorDefault.getColor());
			}
				
			layout.last(this);
			if(typeCell == TYPECELL_TEXTFIELD)
				txtValue.requestFocusInWindow();
		} else {
			if(typeCell == TYPECELL_TEXTFIELD) {
				cellValue = txtValue.getText();
				
				if(!txtValue.getText().equals(labelValue.getText())) {
					grtable.activateTableEvent(this);
				}
				labelValue.setText(txtValue.getText());
			} else if(typeCell == TYPECELL_CHECKBOX) {
				chkDefault.setSelected(chkSelected.isSelected());
			} else if(typeCell == TYPECELL_COMBOBOX) {
				cellValue = ""+cmbValue.getSelectedItem();
				
				if(!(""+cmbValue.getSelectedItem()).equals(labelValueCombo.getText())) {
					grtable.activateTableEvent(this);
				}
				labelValueCombo.setText(""+cmbValue.getSelectedItem());
			} else if(typeCell == TYPECELL_COLOR) {
				cellValue = colorSelected.toString();
				
				if(!colorSelected.toString().equals(colorDefault.toString())) {
					grtable.activateTableEvent(this);
				}
				colorDefault.setColor(colorSelected.getColor());
			}
			
			layout.first(this);
		}
		
	}
	public String getValue() {
		return cellValue;
	}
	public void setValue(String value) {
		cellValue = value;
		if(typeCell == TYPECELL_TEXTFIELD) {
			labelValue.setText(value);
			txtValue.setText(value);
		} else if(typeCell == TYPECELL_COMBOBOX) {
			labelValueCombo.setText(value);
			//cmbValue.setSelectedItem(value);
		}
	}
	public void setValue(Object value) {
		if(value instanceof Color) {
			colorDefault.setColor((Color)value);
			colorSelected.setColor((Color)value);
		} else if(value instanceof GRColorElement) {
			colorDefault.setColor(((GRColorElement)value).getColor());
			colorSelected.setColor(((GRColorElement)value).getColor());
		}
	}
	public void refreshCell(int type) {
		switch(type) {
		case TYPECELL_COLOR:
			cellValue = colorSelected.toString();
			
			if(!colorSelected.toString().equals(colorDefault.toString())) {
				grtable.activateTableEvent(this);
			}
			//colorDefault.setColor(colorSelected.getColor());
			
			break;
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		txtValue.setSelectionStart(0);
		txtValue.setSelectionEnd(txtValue.getText().length());
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		cellValue = ""+cmbValue.getSelectedItem();
		grtable.activateTableEvent(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10) {
			
			grtable.nextFocus(this);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
