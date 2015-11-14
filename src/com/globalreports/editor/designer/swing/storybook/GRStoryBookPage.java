/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.storybook.GRStoryBookPage
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
package com.globalreports.editor.designer.swing.storybook;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.graphics.GRCircle;
import com.globalreports.editor.graphics.GRImage;
import com.globalreports.editor.graphics.GRLine;
import com.globalreports.editor.graphics.GRList;
import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRRectangle;

public class GRStoryBookPage extends JPanel {
	private GRObject grobj;
	private boolean selected;
	private boolean last;
	
	/* Colori */
	private Color cSeparator1 = new Color(217, 237, 246);
	private Color cSeparator2 = new Color(174, 195, 214);
	private GradientPaint gp = null;
	
	Color cBlue = new Color(57, 105, 138);
	Font fTitle = new Font("Tahoma",Font.BOLD,11);
	Font fValue = new Font(Font.MONOSPACED,Font.PLAIN, 9);
	
	public GRStoryBookPage(GRObject grobj) {
		this.grobj = grobj;
		
		setPreferredSize(new Dimension(220,160));
		setBackground(new Color(209,221,233));
		
		selected = false;
		last = false;
	}
	public void setLastPage() {
		last = true;
	}
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(Color.white);
		g2d.fillRoundRect(10,10,200,120, 20,20);
		
		g2d.setColor(cBlue);
		g2d.drawRoundRect(10,10,200,120, 20,20);
		g2d.drawLine(10, 32, 210, 32);
		g2d.fillRect(120,30,90,2);
		
		g2d.setFont(fTitle);
		g2d.drawString(grobj.getNameObject(), 20, 26);
		
		drawContent(g2d);
		
		// Disegna la freccia
		if(!last)
			g2d.drawImage(GRStoryBook.icoNext, 98, 135, null);
		
		if(grobj.getHPosition())
			g2d.drawImage(GRStoryBook.icoLink,180,0,null);

		if(selected) {
			g2d.setColor(new Color(151,153,157));
			g2d.drawLine(0,1,this.getWidth(),1);
			
			g2d.setColor(new Color(23,71,105));
			g2d.drawLine(0,2,this.getWidth(),2);
			
			g2d.setColor(Color.white);
			g2d.drawLine(0,this.getHeight()-2,this.getWidth(),this.getHeight()-2);
			
			g2d.setColor(new Color(151,153,157));
			g2d.drawLine(0,this.getHeight()-1,this.getWidth(),this.getHeight()-1);
			
		}
		
	}
	public void setSelected(boolean value) {
		selected = value;
		
		if(selected)
			setBackground(new Color(57, 105, 138));
		else
			setBackground(new Color(209,221,233));
		
	}
	public GRObject getObject() {
		return grobj;
	}
	private void drawContent(Graphics2D g2d) {
		Font fOld = g2d.getFont();
		Color cOld = g2d.getColor();
		
		switch(grobj.getType()) {
			case GRObject.TYPEOBJ_LINE:
				GRLine refLine = (GRLine)grobj;
				// Se il colore del bordo è bianco, lo rende grigio chiaro
				if(refLine.getColorStroke().getRed() >= 230 &&
				   refLine.getColorStroke().getGreen() >= 230 &&
				   refLine.getColorStroke().getBlue() >= 230)
					g2d.setColor(Color.LIGHT_GRAY);
				else
					g2d.setColor(refLine.getColorStroke());
				g2d.drawLine(20, 50, 120, 100);
				break;
				
			case GRObject.TYPEOBJ_RECTANGLE:
				GRRectangle refRect = (GRRectangle)grobj;
				
				if(refRect.getColorFill() != null) {
					g2d.setColor(refRect.getColorFill());
					g2d.fillRect(20, 50, 100, 50);
				}
				
				// Se il colore del bordo è bianco, lo rende grigio chiaro
				if(refRect.getColorStroke().getRed() >= 230 &&
				   refRect.getColorStroke().getGreen() >= 230 &&
				   refRect.getColorStroke().getBlue() >= 230)
					g2d.setColor(Color.LIGHT_GRAY);
				else
					g2d.setColor(refRect.getColorStroke());
				g2d.drawRect(20, 50, 100, 50);
				
				break;
			
			case GRObject.TYPEOBJ_CIRCLE:
				GRCircle refCircle = (GRCircle)grobj;
				
				if(refCircle.getColorFill() != null) {
					g2d.setColor(refCircle.getColorFill());
					g2d.fillOval(20, 50, 50, 50);
				}
				
				// Se il colore del bordo è bianco, lo rende grigio chiaro
				if(refCircle.getColorStroke().getRed() >= 230 &&
				   refCircle.getColorStroke().getGreen() >= 230 &&
				   refCircle.getColorStroke().getBlue() >= 230)
					g2d.setColor(Color.LIGHT_GRAY);
				else
					g2d.setColor(refCircle.getColorStroke());
				
				g2d.drawOval(20, 50, 50, 50);
				
				break;
				
			case GRObject.TYPEOBJ_TEXT:
				String[] t = getContentText(grobj.toString());
				
				g2d.setFont(fValue);
				g2d.setColor(Color.black);
				
				g2d.drawString(t[0], 20, 50);
				g2d.drawString(t[1], 20, 62);
				g2d.drawString(t[2], 20, 74);
				g2d.drawString(t[3], 20, 86);
				g2d.drawString(t[4], 20, 98);
				g2d.drawString(t[5], 20, 110);
				
				break;
				
			case GRObject.TYPEOBJ_IMAGE:
				GRImage refImage = (GRImage)grobj;
				g2d.drawImage(getImageScaled(refImage.getImage()),20,50,null);
				
				break;
				
			case GRObject.TYPEOBJ_LIST:
				GRList refList = (GRList)grobj;
				
				Color oldC = g2d.getColor();
				Composite compositeOld = g2d.getComposite();
				AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f);
				
				g2d.setColor(Color.BLUE);
				
				g2d.drawString(refList.getNameXml(), 20, 86);
				g2d.drawRect(10,90,200,20);
				g2d.setComposite(composite);
				g2d.setPaint(Color.BLUE);
				g2d.fill(new Rectangle(10,90,200,20));
				
				g2d.setComposite(compositeOld);
				g2d.setColor(oldC);
				
				break;
		}
		
		g2d.setColor(cOld);
		g2d.setFont(fOld);
	}
	
	private Image getImageScaled(Image img) {
		Image imgScaled = null;
		int w,wOriginal;
		int h,hOriginal;
		
		wOriginal = img.getWidth(null);
		hOriginal = img.getHeight(null);
		
		h =  60;
		w = (h * wOriginal) / hOriginal;
		
		if(w > 180) {
			w = 180;
			h = (w * hOriginal) / wOriginal;
		}
		
		MediaTracker tracker = new MediaTracker(this);
		
		imgScaled = img.getScaledInstance(w,h,Image.SCALE_FAST);
		tracker.addImage(imgScaled,0);
		try {
			tracker.waitForID(0);
		} catch(InterruptedException e) {}
		return imgScaled;
	}
	private String[] getContentText(String value) {
		int lenText = value.length();
		String[] text = {"","","","","",""};
		
		try {
			text[0] = value.substring(0,36);
			text[1] = value.substring(36,72);
			text[2] = value.substring(72,108);
			
			text[3] = value.substring(108,144);
			text[4] = value.substring(144,180);
			text[5] = value.substring(180,216);
			
			if(value.length() > 216)
				text[5] = value.substring(180,213)+"...";
			
		} catch(StringIndexOutOfBoundsException e) {
			
			if(text[0].equals(""))
				text[0] = value;
			else if(text[1].equals(""))
				text[1] = value.substring(36);
			else if(text[2].equals(""))
				text[2] = value.substring(72);
			else if(text[3].equals(""))
				text[3] = value.substring(108);
			else if(text[4].equals(""))
				text[4] = value.substring(144);
			else if(text[5].equals(""))
				text[5] = value.substring(180);
						
		}
		return text;
		
	}
	
}
