/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.chart.GRChartModel
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
 */package com.globalreports.editor.graphics.chart;

import java.awt.Color;
import java.awt.Graphics;

import com.globalreports.editor.graphics.GRChart;

public abstract class GRChartModel {
	protected GRChart grchart;
	
	protected Color[] defaultColor = {new Color(140,204,120),
									  new Color(244,128,87),
									  new Color(64,195,249),
									  new Color(236,237,115),
									  new Color(214,157,235)};
	private int currentColor;
	
	public GRChartModel(GRChart grchart) {
		this.grchart = grchart;
		
		currentColor = 0;
	}
	
	public static int min(int a, int b) {
		if(a >= b)
			return b;
		
		return a;
	}
	public static double min(double a, double b) {
		if(a >= b)
			return b;
		
		return a;
	}
	protected void refreshColor() {
		currentColor = 0;
	}
	protected Color getColor() {
		Color c;
		
		if(currentColor >= defaultColor.length) {
			// Sceglie un colore a caso
			c = new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255));
		} else {
			// Altrimenti prende uno di quelli di default già censiti
			c = defaultColor[currentColor];
		}
		
		currentColor++;
		return c;
	}
	public abstract void draw(Graphics g);
	public abstract void refresh();
}
