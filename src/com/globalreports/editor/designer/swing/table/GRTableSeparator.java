/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.table.GRTableSeparator
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

public class GRTableSeparator extends JPanel {
	//private Color cSeparator1 = new Color(143, 158, 77);
	//private Color cSeparator2 = new Color(108, 128, 14);
	//private Color cSeparator1 = new Color(187, 92, 83);
	//private Color cSeparator2 = new Color(160, 25, 12);
	private Color cSeparator1 = new Color(217, 237, 246);
	private Color cSeparator2 = new Color(174, 195, 214);
	
	private GradientPaint gp = null;
	private Font f;
	private String title;
	
	public GRTableSeparator() {
		this("");
	}
	public GRTableSeparator(String value) {
		f = new Font("Tahoma",Font.BOLD,12);
		
		title = value;
		/*
		Font f = new Font("Tahoma",Font.BOLD,12);
		
		JLabel lblShadow = new JLabel(value);
		lblShadow.setFont(f);
		lblShadow.setForeground(Color.black);
		lblShadow.setBounds(9,4,100,20);
		
		JLabel lblTitle = new JLabel(value);
		lblTitle.setFont(f);
		lblTitle.setForeground(Color.white);
		lblTitle.setBounds(8,3,100,20);
		
		add(lblTitle);
		add(lblShadow);

		setBackground(cSeparator);
		setMaximumSize(new Dimension(0,8));
		
		setLayout(null);
		//setLayout(new FlowLayout(FlowLayout.LEFT));
		 * 
		 */
	}
	
	public void paint(Graphics g) {
		super.paint(g);
				
		Graphics2D g2d = (Graphics2D)g;
		
		if(gp == null)
			gp = new GradientPaint(0, 0, cSeparator1, 0, (float)this.getHeight(), cSeparator2);
		
		g2d.setPaint(gp);
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		
		g2d.setColor(Color.white);
		g2d.drawLine(0,this.getHeight()-2,this.getWidth(),this.getHeight()-2);
		
		g2d.setColor(new Color(151,153,157));
		g2d.drawLine(0,this.getHeight()-1,this.getWidth(),this.getHeight()-1);
		
		g2d.setFont(f);
		
		g2d.setColor(cSeparator2);
		g2d.drawString(title,11,19);
		
		g2d.setColor(new Color(57,105,138));
		g2d.drawString(title,10,18);
		
	}
}
