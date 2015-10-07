/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.GRHandle
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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GRTableHeader extends JPanel {
	//private Color cSeparator1 = new Color(217, 219, 223);
	//private Color cSeparator2 = new Color(179, 183, 192);
	private Color cSeparator1 = new Color(139, 146, 159);
	private Color cSeparator2 = new Color(87, 97, 116);
	
	private GradientPaint gp;
	
	private int numColumns;
	private String[] head;
	
	public GRTableHeader() {
		this(null);
	}
	public GRTableHeader(String[] head) {
		numColumns = head.length;
		this.head = head;
		
		JPanel panelHead = new JPanel();
		panelHead.setLayout(new GridLayout(0,head.length));
		
		for(int i = 0;i < head.length;i++) {
			/*
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			
			JLabel l = new JLabel(head[i]);
			p.add(l);
			*/
			
			GRTableCellHeader p = new GRTableCellHeader(head[i]);
			
			panelHead.add(p);
		}
		
		setLayout(new GridLayout(0,1));
		add(panelHead);
	}
	
	public int getNumColumns() {
		return numColumns;
	}
}

class GRTableCellHeader extends JPanel {
	private Color cSeparator1 = new Color(139, 146, 159);
	private Color cSeparator2 = new Color(87, 97, 116);
	//private Color cSeparator1 = new Color(217, 219, 223);
	//private Color cSeparator2 = new Color(179, 183, 192);
		
	private GradientPaint gp;
	private String title;
	private Font f;
	
	public GRTableCellHeader(String value) {
		this.title = value;
		
		f = new Font("Tahoma",Font.BOLD,12);
		setPreferredSize(new Dimension(100,24));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
			
		Graphics2D g2d = (Graphics2D)g;
		gp = new GradientPaint(0, 0, cSeparator1, 0, (float)this.getHeight(), cSeparator2);
		
		g2d.setPaint(gp);
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(this.getWidth()-1, 0, this.getWidth()-1, this.getHeight());
		//g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
		
		g2d.setFont(f);
		g2d.setColor(cSeparator1);
		g2d.drawString(title,11,19);
		
		g2d.setColor(Color.white);
		g2d.drawString(title,10,18);
		
	}
}

