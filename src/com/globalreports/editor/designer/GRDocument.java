/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRDocument
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

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.util.Iterator;
import java.util.List;

import org.jdom.*;

import com.globalreports.editor.designer.resources.GRResFonts;
import com.globalreports.editor.designer.resources.GRResImages;
import com.globalreports.editor.graphics.*;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDocument extends JInternalFrame implements ChangeListener, InternalFrameListener, ComponentListener {
	private final int GAP_WIDTH		= 110;
	private final int GAP_HEIGHT	= 40;
	
	private GREditor greditor;
	private GRPanelSection panelSection;
	private GRPanelPage panelPage;
	private GRPage grpage;
	private JScrollBar scrollVertical;
	
	private JPanel panelMaster;
	
	private String nameDocument;
	
	/* Dimensioni della pagina */
	private int widthPage;
	private int heightPage;
	
	public GRDocument() {
		this(null,"Nuovo documento");
	}
	public GRDocument(GREditor gredit,String title) {
		this(gredit,title,210,297);
	}
	public GRDocument(GREditor gredit,String title, int w, int h) {
		this(gredit,title,w,h,true,true,true);
	}
	public GRDocument(GREditor gredit, String title, int w, int h, boolean resizable, boolean closeable, boolean maximizable) {
		super(title,resizable,closeable,maximizable);
		
		greditor = gredit;
		nameDocument = title;
		widthPage = GRLibrary.fromMillimetersToPixels((double)w);	
		heightPage = GRLibrary.fromMillimetersToPixels((double)h);
		
		this.init();
		
	}
	
	private void init() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		panelPage = new GRPanelPage(widthPage,heightPage);
		
		grpage = new GRPage(this,greditor.getTableProperty(),greditor.getToolBarDesigner(),greditor.getToolBarStrumenti(),widthPage,heightPage);
		panelPage.add(grpage);
		
		panelSection = new GRPanelSection(grpage,panelPage.getHeight());
		
		panelMaster = new JPanel();
		panelMaster.setBackground(Color.GRAY);
		panelMaster.setPreferredSize(new Dimension(widthPage + GAP_WIDTH,heightPage + GAP_HEIGHT));
		//panel.setSize(690,1200);
		panelMaster.setLayout(null);
		panelMaster.add(panelSection);
		panelMaster.add(panelPage);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(panelMaster);
		c.add(scroll,BorderLayout.CENTER);
		
		scrollVertical = new JScrollBar();
		scrollVertical.setOrientation(JScrollBar.VERTICAL);
		//c.add(scrollVertical,BorderLayout.EAST);
		
		BoundedRangeModel model = scrollVertical.getModel();
		model.addChangeListener(this);
		
		setSize(300,200);
		setResizable(true);
		setBackground(new Color(220,220,220));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addInternalFrameListener(this);
		addComponentListener(this);
		setVisible(true);
		
	}
	public void setHeaderPage(int value) {
		panelSection.setHeader(value);
	}
	public int getWidthPage() {
		return (int)GRLibrary.fromPixelsToMillimeters(widthPage);
	}
	public int getHeightPage() {
		return (int)GRLibrary.fromPixelsToMillimeters(heightPage);
	}
	public double getHeaderSize() {
		return grpage.getHeaderSizeToMM();
	}
	public double getFooterSize() {
		return grpage.getFooterSizeToMM();
	}
	public void stateChanged(ChangeEvent e) {
		BoundedRangeModel model = (BoundedRangeModel)e.getSource();
		if(model.getValueIsAdjusting()) {
			double y = (971 - (double)this.getHeight()) / 90;
			y = model.getValue() * y * -1;
			panelPage.setLocation(71,(int)y);
			panelSection.setLocation(0,(int)y);
		}
	}
	public void setNameDocument(String value) {
		nameDocument = value;
		setTitle(value);
	}
	public String getNameDocument() {
		return nameDocument;
	}
	public Point getGapGrid() {
		return grpage.getGapGrid();
	}
	
	public void setGapGrid(int x, int y) {
		grpage.setGapGrid(x, y);
	}
	public  void setAction(int type) {
		grpage.setAction(type);
	}
	public void settings(int type, boolean value) {
		grpage.settings(type, value);
	}
	public void setActionText(int value) {
		grpage.setActionText(value);
	}
	public String getFontIdResources(String fName, int fStyle) {
		return greditor.getFontIdResources(fName, fStyle);
	}
	public String getImgIdResources(GRImage grimg) {
		return greditor.getImgIdResources(grimg);
	}
	public int getTotaleObject() {
		return grpage.getTotaleObject();
	}
	public GRObject getObject(int i) {
		return grpage.getObject(i);
	}
	public void writeDocument(String pathFile) {
		greditor.writeLayout(pathFile);
	}
	
	public GRResFonts getFontResources() {
		return greditor.getFontResources();
	}
	public GRResImages getImgResources() {
		return greditor.getImgResources();
	}
	
	public void openPage(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
		
			if(element.getName().equals("typography")) {
				if(((Element)element.getParent()).getName().equals("page")) {
					//grdoc.setPageProperty(GRPage.TYPEPROP_TYPOGRAPHY,element.getValue());
					//typography = element.getValue();
					System.out.println("typography");
				}
			} else if(element.getName().equals("pagewidth")) {
				if(((Element)element.getParent()).getName().equals("page")) {
					//grdoc.setPageProperty(GRPage.TYPEPROP_PAGEWIDTH,element.getValue());
					System.out.println("pagewidth");
				}
			} else if(element.getName().equals("pageheight")) {
				if(((Element)element.getParent()).getName().equals("page")) {
					//grdoc.setPageProperty(GRPage.TYPEPROP_PAGEHEIGHT,element.getValue());
					System.out.println("pageheight");
				}	
			} else if(element.getName().equals("pageheader")) {
				if(((Element)element.getParent()).getName().equals("page")) {
					//grdoc.setPageProperty(GRPage.TYPEPROP_PAGEHEADER,element.getValue());
					grpage.refreshHeader(GRLibrary.fromMillimetersToPixels(Double.parseDouble(element.getValue())));
				}	
			} else if(element.getName().equals("pagefooter")) {
				if(((Element)element.getParent()).getName().equals("page")) {
					//grdoc.setPageProperty(GRPage.TYPEPROP_PAGEFOOTER,element.getValue());
					System.out.println("pagefooter");
				}	
			} else if(element.getName().equals("grheader")) {
				grpage.loadHead(element);
			} else if(element.getName().equals("grbody")) {
				grpage.loadBody(element);
			}
		}	
				
		grpage.repaint();
	}
	
	// Comandi derivanti dal men�
	public void manageMenu(int idVoce, boolean value) {
		this.manageMenu(idVoce, value, null);
	}
	public void manageMenu(int idVoce, boolean value, String testo) {
		greditor.manageMenu(idVoce, value, testo);
	}
	public void undo() {
		grpage.undo();
	}
	public void clearObject() {
		grpage.clearObject();
	}
	public void backwardObject() {
		grpage.backwardObject();
	}
	public void forwardObject() {
		grpage.forwardObject();
	}
	public void setZoom(float value) {
		panelMaster.setPreferredSize(new Dimension((widthPage * (int)value) + GAP_WIDTH, (heightPage * (int)value) + GAP_HEIGHT));
		panelPage.setZoom(value);
		panelSection.setHeight(panelPage.getHeight());
		grpage.setZoom(value);
	}
	public void internalFrameActivated(InternalFrameEvent e) {
		greditor.setDocumentActive(this);
	}
	public void internalFrameClosed(InternalFrameEvent e) {}
	public void internalFrameClosing(InternalFrameEvent e) {}
	public void internalFrameDeactivated(InternalFrameEvent e) {}
	public void internalFrameDeiconified(InternalFrameEvent e) {}
	public void internalFrameIconified(InternalFrameEvent e) {}
	public void internalFrameOpened(InternalFrameEvent e) {}

	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentResized(ComponentEvent e) {
		//panelPage.repaint();
	}
	public void componentShown(ComponentEvent e) {}
	
	public void functionDebug() {
		
	}
}