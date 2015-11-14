/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRChart
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
 */package com.globalreports.editor.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.property.GRTableModelChart;
import com.globalreports.editor.designer.property.GRTableProperty;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.graphics.chart.GRChartData;
import com.globalreports.editor.graphics.chart.GRChartLegend;
import com.globalreports.editor.graphics.chart.GRChartModel;
import com.globalreports.editor.graphics.chart.GRChartPie;
import com.globalreports.editor.graphics.chart.GRChartVoice;
import com.globalreports.editor.tools.GRLibrary;

public class GRChart extends GRObject {
	public static final int TYPECHART_NOTDEFINED	= 0;
	public static final int TYPECHART_PIE			= 1;
	public static final int TYPECHART_PIE_EXPLOSE	= 2;
	
	public static final int CHARTVIEW_2D			= 1;
	public static final int CHARTVIEW_3D			= 2;
	
	private int typeChart;
	private int view;
	private GRChartLegend legend;
	private boolean legendView;
	private String nameXml;
	
	private GRChartModel grchartModel;
	private GRTableModelChart modelTable;
	private GRChartData grchartdata;
	
	public GRChart(GRPage grpage, long id, int xStart, int yStart, int xEnd, int yEnd) {
		super(GRObject.TYPEOBJ_CHART,id,grpage);
		
		nameXml = "chart"+id;
		grchartdata = null;
		
		view = CHARTVIEW_3D;
		legend = new GRChartLegend();
		legendView = false;
		
		if(xStart < xEnd) {
			x1 = xStart;
			width = xEnd - xStart;
		} else {
			x1 = xEnd;
			width = xStart - xEnd;
		}
		if(yStart < yEnd) {
			y1 = yStart;
			height = yEnd - yStart;
		} else {
			y1 = yEnd;
			height = yStart - yEnd;
		}
	
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		heightOriginal = height;
		
		// Crea le ancore
		tsx = new Rectangle(x1-4,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(x1+width,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx = new Rectangle(x1-4,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx = new Rectangle(x1+width,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		
		this.setTypeChart(TYPECHART_NOTDEFINED);
		
		typeModel = GRTableProperty.TYPEMODEL_CHART;
		this.refreshReferenceSection();	
	}

	public void setTypeChart(int typeChart) {
		if(typeChart == TYPECHART_NOTDEFINED)
			this.typeChart = typeChart;
		else {
			if(typeChart != this.typeChart) {
				switch(typeChart) {
					case TYPECHART_PIE:
						grchartModel = new GRChartPie(this);
						break;
				}
				
				this.typeChart = typeChart;
			}
		}
		
	}
	public void setView(int view) {
		this.view = view;
	}
	public int getView() {
		return view;
	}
	public void setChart(int typeChart, int view) {
		this.typeChart = typeChart;
		this.view = view;
	}
	public void setLegend(boolean value) {
		this.legendView = value;
	}
	public boolean hasLegend() {
		return legendView;
	}
	public GRChartLegend getLegend() {
		return legend;
	}
	public void setWidth(int value) {
		super.setWidth(value);
		
		if(grchartModel != null)
			grchartModel.refresh();
	}
	public void setHeight(int value) {
		super.setHeight(value);
		
		if(grchartModel != null)
			grchartModel.refresh();
	}
	public void addVoice(String label, double value, Color c) {
		if(grchartdata == null)
			grchartdata = new GRChartData();
		
		grchartdata.addVoice(label,  value, c);
		
	}
	public void addDataStatic(Vector<GRChartVoice> grdata) {
		if(grchartdata == null)
			grchartdata = new GRChartData();
		
		grchartdata.addData(grdata);
		grchartModel.refresh();
	}
	public GRChartData getStaticData() {
		return grchartdata;
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// Disegna il contorno esterno
		if(selected) {
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x1,y1,width,height);
		}
		
		if(grchartModel != null)
			grchartModel.draw(g);
	}
	public void setProperty(GRTable model) {
		this.modelTable = (GRTableModelChart)model;
		modelTable.setGRObject(this);
		
		this.refreshProperty();
	}
	public void refreshProperty() {
		if(modelTable == null)
			return ;
		
		modelTable.setLeft(this.getOriginalX());
		modelTable.setTop(this.getOriginalY());
		modelTable.setWidth(this.getOriginalWidth());
		modelTable.setHeight(this.getOriginalHeight());
		
	}
	public String getNameObject() {
		return GRLanguageMessage.messages.getString("tlbgrchart");
	}
	@Override
	public GRObject clone(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1Original;
		
		/*
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		if(section == GRObject.SECTION_FOOTER) {
			y1 = y1 - (grpage.getHeight() - grpage.getFooterSize());
		}
		*/
		
		buff.append("<chart>\n");
		buff.append("<type>");
		switch(typeChart) {
			case TYPECHART_PIE:
				buff.append("pie");
				break;
		}
		buff.append("</type>\n");
		buff.append("<view>");
		switch(view) {
			case CHARTVIEW_2D:
				buff.append("2d");
				break;
				
			case CHARTVIEW_3D:
				buff.append("3d");
				break;
		}
		buff.append("</view>\n");
		buff.append("<left>"+GRLibrary.fromPixelsToMillimeters(x1Original)+"</left>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<width>"+GRLibrary.fromPixelsToMillimeters(widthOriginal)+"</width>\n");
		buff.append("<height>"+GRLibrary.fromPixelsToMillimeters(heightOriginal)+"</height>\n");
		
		if(legendView) {
			buff.append("<legend>\n");
			buff.append("<title>"+legend.getTitle()+"</title>\n");
			buff.append("<position>"+legend.getPositionToString()+"</position>\n");
			buff.append("</legend>\n");
		}
		
		if(grchartdata != null) {
			buff.append("<data>\n");
			
			grchartdata.first();
			GRChartVoice refVoice;
			while((refVoice = grchartdata.next()) != null) {
				
				buff.append("<voice>\n");
				buff.append("<label>"+refVoice.getLabel()+"</label>\n");
				buff.append("<value>"+refVoice.getValue()+"</value>\n");
				buff.append("<colorfill>"+refVoice.getColor()+"</colorfill>\n");
				buff.append("</voice>\n");
			}
			
			buff.append("</data>\n");
		}
		buff.append("</chart>");
		
		return buff.toString();
	}

	@Override
	public int getTypeModel() {
		// TODO Auto-generated method stub
		return typeModel;
	}
}
