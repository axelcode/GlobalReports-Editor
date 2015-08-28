/*
 * ============================================================
 * class name  : com.globalreports.editor.designer.swing.toolbar.GRToolBarText
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.graphics.GRText;

@SuppressWarnings("serial")
public class GRToolBarText extends JToolBar implements ActionListener {
	@SuppressWarnings("rawtypes")
	private JComboBox cFontName;
	@SuppressWarnings("rawtypes")
	private JComboBox cFontSize;
	
	private JToggleButton bFontBold;
	private JToggleButton bFontItalic;
	private JToggleButton bAlignLeft;
	private JToggleButton bAlignCenter;
	private JToggleButton bAlignRight;
	private JToggleButton bAlignJustify;
	
	private JButton bColor;
	private JButton bSearch;
	
	private GRToolBarStrumenti grFather;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GRToolBarText(GRToolBarStrumenti grtool) {
		this.grFather = grtool;
		
		cFontName = new JComboBox();
		
		for(int i = 0;i < GRFontProperty.grfontInstalled.size();i++) {
			cFontName.addItem(GRFontProperty.grfontInstalled.get(i).getBaseName());
		}
		
		cFontName.setSelectedItem(GRSetting.FONTNAME);
		add(cFontName);
		
		cFontSize = new JComboBox();
		cFontSize.addItem(8);
		cFontSize.addItem(9);
		cFontSize.addItem(10);
		cFontSize.addItem(11);
		cFontSize.addItem(12);
		cFontSize.addItem(14);
		cFontSize.addItem(16);
		cFontSize.addItem(18);
		cFontSize.addItem(20);
		cFontSize.addItem(22);
		cFontSize.addItem(24);
		cFontSize.addItem(26);
		cFontSize.addItem(28);
		cFontSize.addItem(36);
		cFontSize.addItem(48);
		cFontSize.addItem(72);
		cFontSize.setSelectedItem(GRSetting.FONTSIZE);
		cFontSize.setEditable(true);
		add(cFontSize);
		
		ImageIcon ico_fontbold = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontbold.png");
		ImageIcon ico_fontitalic = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontitalic.png");
		ImageIcon ico_alignleft = new ImageIcon(GRSetting.PATHIMAGE+"ico_alignleft.png");
		ImageIcon ico_aligncenter = new ImageIcon(GRSetting.PATHIMAGE+"ico_aligncenter.png");
		ImageIcon ico_alignright = new ImageIcon(GRSetting.PATHIMAGE+"ico_alignright.png");
		ImageIcon ico_alignjustify = new ImageIcon(GRSetting.PATHIMAGE+"ico_alignjustify.png");
		ImageIcon ico_fontcolor = new ImageIcon(GRSetting.PATHIMAGE+"ico_fontcolor.png");
		ImageIcon ico_search = new ImageIcon(GRSetting.PATHIMAGE+"ico_search.png");
		
		bFontBold = new JToggleButton(ico_fontbold);
		add(bFontBold);
		bFontItalic = new JToggleButton(ico_fontitalic);
		add(bFontItalic);
		
		addSeparator();
		
		bAlignLeft = new JToggleButton(ico_alignleft);
		bAlignLeft.setSelected(true);
		bAlignLeft.addActionListener(this);
		add(bAlignLeft);
		bAlignCenter = new JToggleButton(ico_aligncenter);
		bAlignCenter.addActionListener(this);
		add(bAlignCenter);
		bAlignRight = new JToggleButton(ico_alignright);
		bAlignRight.addActionListener(this);
		add(bAlignRight);
		bAlignJustify = new JToggleButton(ico_alignjustify);
		bAlignJustify.addActionListener(this);
		add(bAlignJustify);
		
		addSeparator();
		
		bColor = new JButton(ico_fontcolor);
		add(bColor);
		bSearch = new JButton(ico_search);
		add(bSearch);
		
		setFloatable(false);
	}
	
	private void clearSelect() {
		bAlignLeft.setSelected(false);
		bAlignCenter.setSelected(false);
		bAlignRight.setSelected(false);
		bAlignJustify.setSelected(false);
	}
	public void actionPerformed(ActionEvent e) {
		clearSelect();
		
		JToggleButton b = (JToggleButton)e.getSource();
		b.setSelected(true);
		
		if(e.getSource() == bAlignLeft)
			grFather.setAction(GRToolBarStrumenti.TYPEBUTTON_TEXTALIGN_LEFT);
		else if(e.getSource() == bAlignCenter)
			grFather.setAction(GRToolBarStrumenti.TYPEBUTTON_TEXTALIGN_CENTER);
		else if(e.getSource() == bAlignRight)
			grFather.setAction(GRToolBarStrumenti.TYPEBUTTON_TEXTALIGN_RIGHT);
		else if(e.getSource() == bAlignJustify)
			grFather.setAction(GRToolBarStrumenti.TYPEBUTTON_TEXTALIGN_JUSTIFY);
		
	}
	public String getFontName() {
		return ""+cFontName.getSelectedItem();
	}
	public int getFontSize() {
		return Integer.parseInt(""+cFontSize.getSelectedItem());
	}
	public int getFontStyle() {
		int ritorno = GRText.STYLETEXT_NORMAL;
		
		if(bFontBold.isSelected()) {
			if(bFontItalic.isSelected())
				ritorno = GRText.STYLETEXT_BOLDITALIC;
			else
				ritorno = GRText.STYLETEXT_BOLD;
		} else {
			if(bFontItalic.isSelected())
				ritorno = GRText.STYLETEXT_ITALIC;
		}
		
		return ritorno;
	}
	public int getFontAlignment() {
		if(bAlignLeft.isSelected())
			return GRText.ALIGNTEXT_LEFT;
		else if(bAlignCenter.isSelected())
			return GRText.ALIGNTEXT_CENTER;
		else if(bAlignRight.isSelected())
			return GRText.ALIGNTEXT_RIGHT;
		else if(bAlignJustify.isSelected())
			return GRText.ALIGNTEXT_JUSTIFY;
		
		return -1;
	}
}
