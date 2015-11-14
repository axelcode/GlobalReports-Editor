/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.swing.GRPanelCondition
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
package com.globalreports.editor.designer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.globalreports.editor.graphics.textcondition.GRDocumentTextCondition;

public class GRPanelCondition extends JPanel {
	private GRDocumentTextCondition docText;
	
	Font fValue = new Font(Font.MONOSPACED,Font.PLAIN, 10);
	Font fCondition = new Font(Font.MONOSPACED,Font.BOLD, 9);
	
	String[] testo;
	
	public GRPanelCondition(GRDocumentTextCondition docText) {
		this.docText = docText;
		
		testo = getContentText(docText.getValue());
		
		setPreferredSize(new Dimension(0,60));
		setBackground(Color.WHITE);
		
		System.out.println(this.getWidth());
	}
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		Font oldF = g2d.getFont();
		
		FontMetrics metrics = g2d.getFontMetrics(fValue);
		
		g2d.setFont(fValue);
		
		g2d.drawString(testo[0],10,10);
		g2d.drawString(testo[1],10,20);
		g2d.drawString(testo[2],10,30);
		
		g2d.setColor(Color.white);
		g2d.drawLine(0,this.getHeight()-2,this.getWidth(),this.getHeight()-2);
		
		g2d.setColor(new Color(151,153,157));
		g2d.drawLine(0,this.getHeight()-1,this.getWidth(),this.getHeight()-1);
		
		g2d.setFont(oldF);
	}
	
	private String[] getContentText(String value) {
		
		int lenText = value.length();
		String[] text = {"","",""};
		
		try {
			text[0] = value.substring(0,97);
			text[1] = value.substring(97,194);
			text[2] = value.substring(194,291);
			
			if(value.length() > 291)
				text[2] = value.substring(194,288)+"...";
			
		} catch(StringIndexOutOfBoundsException e) {
			
			if(text[0].equals(""))
				text[0] = value;
			else if(text[1].equals(""))
				text[1] = value.substring(97);
			else if(text[2].equals(""))
				text[2] = value.substring(194);
			
						
		}
		return text;
		
	}
}
