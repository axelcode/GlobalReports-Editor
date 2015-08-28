/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRPanelPage
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
package com.globalreports.editor.designer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.globalreports.editor.GRSetting;

@SuppressWarnings("serial")
public class GRPanelPage extends JPanel {
	private final int GAP			= 40;
	private final float GAP_SHADOW	= 20;
	private final int GAP_WIDTH		= 110;
	private final int GAP_HEIGHT	= 40;
	
	private Font f1;
	private int width;
	private int height;
	private int widthPanel;
	private int heightPanel;
	private float sh1;	// x shadow
	private float sh2;	// y shadow
	GradientPaint gp1;
	GradientPaint gp2;
	
	private float zoom;
	
	public GRPanelPage() {
		this(GRSetting.WIDTHPAGE,GRSetting.HEIGHTPAGE);
	}
	
	public GRPanelPage(int width, int height) {
		this.width = width;
		this.height = height;
		
		zoom = 1.0f;
		
		f1 = new Font(null,Font.PLAIN,9);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		if((int)screenSize.getWidth() > width)
			widthPanel = (int)screenSize.getWidth();
		else
			widthPanel = width + GAP_WIDTH;
		
		if((int)screenSize.getHeight() > height)
			heightPanel = (int)screenSize.getHeight();
		else
			heightPanel = height + GAP_HEIGHT;
		
		setBackground(Color.GRAY);
		setBounds(71,0,widthPanel,heightPanel);
		setLayout(null);
		//setBorder(new MatteBorder(1,1,1,1,Color.black));
		sh1 = width + GAP_SHADOW;
		sh2 = height + GAP_SHADOW;
		
		gp1 = new GradientPaint(sh1,22f,Color.DARK_GRAY,(sh1 + 6.0f),22f,Color.GRAY);
		gp2 = new GradientPaint(22f,sh2,Color.DARK_GRAY,22f,(sh2 + 6.0f),Color.GRAY);
	}
	public void paint(Graphics g) {
		super.paint(g);
		
		int token;
		int number;
		int gapRighello = 3 * ((int)zoom);
		
		g.setFont(f1);
		/* Righello orizzontale */
		
		g.drawRect(0,0,this.getWidth(),14);
		g.setColor(Color.WHITE);
		g.fillRect(0,0,this.getWidth(),14);
		
		g.setColor(Color.BLACK);
		
		g.drawLine(20,5,20,14);
		g.drawString("0",22,10);
		token = 0;
		number = 1;
		for(int i = 20;i < this.getWidth();i = i + gapRighello) {
			if(token == 5)
				g.drawLine(i,8,i,14);
			else if(token == 10) {
				g.drawLine(i,5,i,14);
				g.drawString(""+number,i+2,10);
				
				token = 0;
				number++;
			} else {
				g.drawLine(i,11,i,14);
			}
			token++;
		}
		
		/* Righello verticale */
		g.drawRect(0,14,14,this.getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(0,14,14,this.getHeight());
		
		g.setColor(Color.BLACK);
		
		g.drawLine(5,20,14,20);
		g.drawString("0",1,18);
		token = 0;
		number = 1;
		for(int i = 20;i < this.getHeight();i = i + gapRighello) {
			if(token == 5)
				g.drawLine(8,i,14,i);
			else if(token == 10) {
				g.drawLine(5,i,14,i);
				g.drawString(""+number,1,i-2);
				
				token = 0;
				number++;
			} else {
				g.drawLine(11,i,14,i);
			}
			token++;
		}
		/*
		g.fillRect(650,22,2,891);
		g.fillRect(22,911,630,2);
		*/
		Graphics2D g2D = (Graphics2D)g;
		g2D.setPaint(gp1);
		g2D.fill(new Rectangle2D.Float(sh1,22,6,(height * (int)zoom)));
		g2D.setPaint(gp2);
		g2D.fill(new Rectangle2D.Float(22,sh2,(width * (int)zoom)+2,6));
		
	}
	public void setZoom(float value) {
		Dimension d = new Dimension();
		
		zoom = value;
	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		if((int)screenSize.getWidth() > (width * (int)zoom) + GAP_WIDTH)
			widthPanel = (int)screenSize.getWidth();
		else
			widthPanel = (width * (int)zoom) + GAP_WIDTH;
		
		if((int)screenSize.getHeight() > (height * (int)zoom) + GAP_HEIGHT)
			heightPanel = (int)screenSize.getHeight();
		else
			heightPanel = (height * (int)zoom) + GAP_HEIGHT;
		
		setBackground(Color.GRAY);
		setBounds(71,0,widthPanel,heightPanel);

		sh1 = (width * (int)zoom) + GAP_SHADOW;
		sh2 = (height * (int)zoom) + GAP_SHADOW;
		
		gp1 = new GradientPaint(sh1,22f,Color.DARK_GRAY,(sh1 + 6.0f),22f,Color.GRAY);
		gp2 = new GradientPaint(22f,sh2,Color.DARK_GRAY,22f,(sh2 + 6.0f),Color.GRAY);
		repaint();
	}
}