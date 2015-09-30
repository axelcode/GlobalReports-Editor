/*
 * ============================================================
 * class name  : com.globalreports.editor.designer.swing.toolbar.GRToolBarStrumenti
 * Begin       : 
 * Last Update : 
 *
 * Author      : Alessandro Baldini - alex.baldini72@gmail.com
 * License     : GNU-GPL v2 (http://www.gnu.org/licenses/)
 * ============================================================
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
package com.globalreports.editor.designer.swing.toolbar;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.StyleConstants;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.designer.GREditor;

@SuppressWarnings("serial")
public class GRToolBarStrumenti extends JToolBar implements ActionListener, ItemListener {
	public static final int TYPEBUTTON_GRID					= 1;
	public static final int TYPEBUTTON_ANCHORGRID			= 2;
	public static final int TYPEBUTTON_UNDO					= 3;
	public static final int TYPEBUTTON_SPOSTASOTTO			= 4;
	public static final int TYPEBUTTON_SPOSTASOPRA			= 5;
	
	public static final int TYPEBUTTON_TEXTALIGN_LEFT		= 11;
	public static final int TYPEBUTTON_TEXTALIGN_CENTER		= 12;
	public static final int TYPEBUTTON_TEXTALIGN_RIGHT		= 13;
	public static final int TYPEBUTTON_TEXTALIGN_JUSTIFY	= 14;
	
	private GREditor greditor;
	private JToggleButton bGrid;
	private JToggleButton bAnchorGrid;
	private JButton bUndo;
	private JButton bSpostaSotto;
	private JButton bSpostaSopra;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cZoom;
	
	private GRToolBarText grtoolbarText;
	private GRToolBarShape grtoolbarShape;
	
	public GRToolBarStrumenti(GREditor greditor) {
		this.greditor = greditor;
		
		ImageIcon ico_grid = new ImageIcon(GRSetting.PATHIMAGE+"ico_grid.png");
		ImageIcon ico_anchorgrid = new ImageIcon(GRSetting.PATHIMAGE+"ico_anchorgrid.png");
		ImageIcon ico_undo = new ImageIcon(GRSetting.PATHIMAGE+"ico_undo.png");
		ImageIcon ico_spostasotto = new ImageIcon(GRSetting.PATHIMAGE+"ico_spostasotto.png");
		ImageIcon ico_spostasopra = new ImageIcon(GRSetting.PATHIMAGE+"ico_spostasopra.png");
		
		bGrid = new JToggleButton(ico_grid);
		bGrid.setSelected(true);
		bGrid.addActionListener(this);
		add(bGrid);
		
		bAnchorGrid = new JToggleButton(ico_anchorgrid);
		bAnchorGrid.setSelected(true);
		bAnchorGrid.addActionListener(this);
		add(bAnchorGrid);
		
		addSeparator();
		
		bUndo = new JButton(ico_undo);
		bUndo.setToolTipText("Annulla");
		bUndo.setEnabled(false);
		bUndo.addActionListener(this);
		add(bUndo);
		
		addSeparator();
		
		bSpostaSotto = new JButton(ico_spostasotto);
		bSpostaSotto.setToolTipText("Porta in secondo piano");
		bSpostaSotto.addActionListener(this);
		add(bSpostaSotto);
		
		bSpostaSopra = new JButton(ico_spostasopra);
		bSpostaSopra.setToolTipText("Porta in primo piano");
		bSpostaSopra.addActionListener(this);
		add(bSpostaSopra);
		
		addSeparator();
		
		cZoom = new JComboBox();
		cZoom.addItem("100%");
		cZoom.addItem("200%");
		cZoom.addItem("400%");
		cZoom.addItem("800%");
		cZoom.setSelectedItem("100%");
		cZoom.setEditable(false);
		cZoom.addItemListener(this);
		add(cZoom);
		
		addSeparator();
		
		grtoolbarText = new GRToolBarText(this);
		add(grtoolbarText);
		
		addSeparator();
		
		grtoolbarShape = new GRToolBarShape(this);
		add(grtoolbarShape);
		
		setFloatable(false);
		
		clear();
		
	}
	private void clear() {
		bUndo.setEnabled(false);
		bSpostaSotto.setEnabled(false);
		bSpostaSopra.setEnabled(false);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bGrid) {
			greditor.actionToolBarStrumenti(GRToolBarStrumenti.TYPEBUTTON_GRID,bGrid.isSelected());
		} else if(e.getSource() == bAnchorGrid) {
			greditor.actionToolBarStrumenti(GRToolBarStrumenti.TYPEBUTTON_ANCHORGRID,bAnchorGrid.isSelected());
		} else if(e.getSource() == bUndo) {
			greditor.actionToolBarStrumenti(GRToolBarStrumenti.TYPEBUTTON_UNDO);
		} else if(e.getSource() == bSpostaSotto) {
			greditor.actionToolBarStrumenti(GRToolBarStrumenti.TYPEBUTTON_SPOSTASOTTO);
		} else if(e.getSource() == bSpostaSopra) {
			greditor.actionToolBarStrumenti(GRToolBarStrumenti.TYPEBUTTON_SPOSTASOPRA);
		}
	}
	public void setAction(int value) {
		greditor.setActionText(value);
	}
	public String getFontName() {
		return grtoolbarText.getFontName();
	}
	public int getFontSize() {
		return grtoolbarText.getFontSize();
	}
	public int getFontStyle() {
		return grtoolbarText.getFontStyle();
	}
	public int getFontAlignment() {
		return grtoolbarText.getFontAlignment();
	}
	public Color getColorStroke() {
		return grtoolbarShape.getColorStroke();
	}
	public Color getColorFill() {
		return grtoolbarShape.getColorFill();
	}
	public void setUndoEnabled(boolean value) {
		bUndo.setEnabled(value);
	}
	public void setUndoToolTipText(String value) {
		bUndo.setToolTipText(value);
	}
	public void setBackwardEnabled(boolean value) {
		bSpostaSotto.setEnabled(value);
	}
	public void setForwardEnabled(boolean value) {
		bSpostaSopra.setEnabled(value);
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(e.getSource() == cZoom) {
				String value = cZoom.getSelectedItem().toString();
				
				float param = Float.parseFloat(value.replaceAll("%", ""));
				param = param / 100f;
				
				greditor.setZoom(param);

			}
		}
	}
}
