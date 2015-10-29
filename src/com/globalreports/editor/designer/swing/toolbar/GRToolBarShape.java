/*
 * ============================================================
 * class name  : com.globalreports.editor.designer.swing.toolbar.GRToolBarShape
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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.dialog.GRDialogColorChooser;
import com.globalreports.editor.designer.resources.GRColor;


@SuppressWarnings("serial")
public class GRToolBarShape extends JToolBar implements ActionListener {
	private GRToolBarStrumenti grFather;
	
	private JButton bColorStroke;
	private JButton bColorFill;
	private GRIcoColorShape panelColor;
	
	public GRToolBarShape(GRToolBarStrumenti grtool) {
		this.grFather = grtool;
		
		ImageIcon ico_colorstroke = new ImageIcon(GRSetting.PATHIMAGE+"ico_colorstroke.png");
		ImageIcon ico_colorfill = new ImageIcon(GRSetting.PATHIMAGE+"ico_colorfill.png");
		
		panelColor = new GRIcoColorShape(GRSetting.COLORSTROKE,GRSetting.COLORFILL);
		add(panelColor);
		
		bColorStroke = new JButton(ico_colorstroke);
		bColorStroke.addActionListener(this);
		bColorStroke.setToolTipText(GRLanguageMessage.messages.getString("tlbcolorstroke"));
		add(bColorStroke);
		bColorFill = new JButton(ico_colorfill);
		bColorFill.addActionListener(this);
		bColorFill.setToolTipText(GRLanguageMessage.messages.getString("tlbcolorfill"));
		add(bColorFill);
		
		//setLayout(new GridLayout(1,3));
		setFloatable(false);
	}
	
	public Color getColorStroke() {
		return panelColor.getColorStroke();
	}
	public Color getColorFill() {
		return panelColor.getColorFill();
	}
	public void actionPerformed(ActionEvent e) {
		GRColor c;
		if(e.getSource() == bColorStroke) {
			c = GRDialogColorChooser.showDialog(panelColor.getColorStroke(),false);
			//c = JColorChooser.showDialog(this, "Color Chooser", panelColor.getColorStroke());
			
			if(c == null) 
				return;
				
			panelColor.setColorStroke(c.getColor());
		} else if(e.getSource() == bColorFill) {
			c = GRDialogColorChooser.showDialog(panelColor.getColorFill(),true);
			
			if(c == null) 
				return;
			
			if(c.isTransparent())
				panelColor.setColorFill(GRSetting.COLORFILL);
			else
				panelColor.setColorFill(c.getColor());

		}
	}
}

@SuppressWarnings("serial")
class GRIcoColorShape extends Canvas {
	private Color colorStroke;
	private Color colorFill;
	
	public GRIcoColorShape(Color cStroke) {
		this(cStroke,null);
	}
	public GRIcoColorShape(Color cStroke, Color cFill) {
		this.colorStroke = cStroke;
		this.colorFill = cFill;
		
		setSize(25,25);
		setVisible(true);
	}
	public void paint(Graphics g) {
		//super.paint(g);
		
		if(colorFill != null) {
			g.setColor(colorFill);
			g.fillRect(0, 0, 24, 24);
		}
		
		g.setColor(colorStroke);
		g.drawRect(0, 0, 24, 24);
	}
	
	public void setColorStroke(Color c) {
		colorStroke = c;
		
		repaint();
	}
	public Color getColorStroke() {
		return colorStroke;
	}
	public void setColorFill(Color c) {
		colorFill = c;
		
		repaint();
	}
	public Color getColorFill() {
		return colorFill;
	}
}