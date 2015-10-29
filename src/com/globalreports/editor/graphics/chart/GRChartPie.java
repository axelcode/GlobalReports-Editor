/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.chart.GRChartPie
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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.graphics.GRChart;
import com.globalreports.editor.graphics.GRObject;

public class GRChartPie extends GRChartModel {
	private int raggio;
	
	private double valueMin;
	private double wPos;
	private double hPos;
	
	private boolean staticData;
	private double totaleValori;
	private int totaleFette;
	
	public GRChartPie(GRChart grchart) {
		super(grchart);
		
		setCanvas();
	}

	private void setCanvas() {
		// Inizializza l'ambiente
						
		// Calcola il raggio a seconda delle dimensioni dell'area.
		// La legenda laterale occuperà il 40% dell'area
		// La legenda top o bottom il 20%
		// Se la legenda non è visibile viene utilizzata l'intera area per il grafico
						
		if(grchart.hasLegend()) {
			GRChartLegend legend = grchart.getLegend();
			
			if(legend.getPosition() == GRChartLegend.POSLEGEND_LEFT || legend.getPosition() == GRChartLegend.POSLEGEND_RIGHT) {
				wPos = grchart.getWidth() * .5;
				hPos = grchart.getHeight();
			} else {
				wPos = grchart.getWidth();
				hPos = grchart.getHeight() * .8;
			}
		} else {
			wPos = grchart.getWidth();
			hPos = grchart.getHeight();
		}
		
		if(wPos > hPos) {
			if(((wPos * .35) + (wPos / 10.0)) < hPos) {
				valueMin = wPos;
			} else {
				valueMin = (wPos * hPos) / ((wPos * .35) + (wPos / 10.0));
			}
		} else {
			valueMin = wPos;
		}
		
		/**
		 * if(wPos > hPos) {
			if(wPos * .35 < hPos) {
				valueMin = wPos;
			} else {
				valueMin = (wPos * hPos) / (wPos * .35);
			}
		} else {
			valueMin = wPos;
		}
		 */
		
		// Adesso verifica se i dati arrivano da xml oppure sono statici
		GRChartData grdata = grchart.getStaticData();
		if(grdata != null) {
			GRChartVoice grvoice;
			
			grdata.first();
			while((grvoice = grdata.next()) != null) {
				totaleValori = totaleValori + grvoice.getValue();
				
				if(grvoice.getTypeColor() == GRChartVoice.TYPECOLOR_AUTOMATIC) {
					if(grvoice.getColor() == null)
						grvoice.setColor(this.getColor());
				}
				
			}
		}
		
	}
	public void refresh() {
		this.setCanvas();
	}
	public void draw(Graphics g) {
		int constSpessore = 3;	// 1 mm di spessore ogni 10 mm (1 / 10)
		
		Graphics2D g2d = (Graphics2D)g;
		Color oldColor = g2d.getColor();
		
		double xPosition = grchart.getX() + ((wPos - valueMin) / 2);
		double yPosition = grchart.getY() + ((hPos - (valueMin * .35)) / 2);
		double wChart = valueMin;
		double hChart = valueMin * .35;
		
		constSpessore = (int)(wChart / 10.0);
		
		yPosition = yPosition + (constSpessore / 2);
		
		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		
		GRChartData grdata = grchart.getStaticData();
		
		if(grdata != null) {
			double angStart = 0;
			double angExt = 0;
			
			GRChartVoice grvoice;
			grdata.first();
			
			while((grvoice = grdata.next()) != null) {
				angExt =  (grvoice.getValue() / totaleValori) * 360;
				
				arc.setAngleStart(angStart);
				arc.setAngleExtent(angExt);
				
				angStart = angStart + angExt;
				
				// Parte inferiore
				arc.setFrame(xPosition, yPosition, wChart, hChart);
				g2d.setColor(grvoice.getColor());
			    g2d.fill(arc);
			    g2d.setColor(Color.black);
			    g2d.draw(arc);
			    
			 // Parte superiore
				for(int i = 0;i < constSpessore;i++) {
				    arc.setFrame(xPosition, yPosition-i, wChart, hChart);
				    g2d.setColor(grvoice.getColor());
				    g2d.fill(arc);
				    
				    if((i+1) == constSpessore)
				    g2d.setColor(Color.black);
				    g2d.draw(arc);
			    }
			}
			
	    
		} else {
			arc.setAngleStart(0.0);
			arc.setAngleExtent(360.0);
			
			// Parte inferiore
			arc.setFrame(xPosition, yPosition, wChart, hChart);
			g2d.setColor(new Color(57, 105, 138));
			g2d.fill(arc);
			g2d.setColor(Color.black);
			g2d.draw(arc);
			
			// Parte superiore
			for(int i = 0;i < constSpessore;i++) {
			    arc.setFrame(xPosition, yPosition-i, wChart, hChart);
			    g2d.setColor(new Color(57,105,138));
			    g2d.fill(arc);
			    
			    if((i+1) == constSpessore)
			    g2d.setColor(Color.black);
			    g2d.draw(arc);
		    }
		}
		
	    
	    /*
	    for(int i = 0;i < constSpessore;i++) {
		    arc.setFrame(xPosition, yPosition-i, wChart, hChart);
		    g2d.setColor(new Color(57,105,138));
		    g2d.fill(arc);
		    
		    if((i+1) == constSpessore)
		    g2d.setColor(Color.black);
		    g2d.draw(arc);
	    }
	    */
	    
	    g2d.setColor(oldColor);
	}
	
}
