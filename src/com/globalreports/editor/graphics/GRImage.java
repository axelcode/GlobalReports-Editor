/*
 * ==========================================================================
 * class name  : com.globalreports.editor.graphics.GRImage
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
package com.globalreports.editor.graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.resources.GRResImages;
import com.globalreports.editor.tools.GRLibrary;

public class GRImage extends GRObject {
	private Image imgOriginal;
	private Image imgScaled;
	
	private String idImg;
	private String pathFile;
	private int widthFile;
	private int heightFile;
	
	private MediaTracker tracker;
	private GRResImages resImg;
	
	public GRImage(GRPage grpage, long id, GRResImages resImg) {
		super(GRObject.TYPEOBJ_IMAGE,id,grpage);
	
		this.resImg = resImg;
		
		// Inizializza l'oggetto con valori predefiniti
		x1 = 1;
		y1 = 1;
		width = 1;
		height = 1;
		hPosition = false;
		
		tracker = new MediaTracker(grpage);
		imgOriginal = null;
				
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		heightOriginal = height;
		
		// Crea le ancore
		tsx = new Rectangle(x1-4,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(x1+width,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx = new Rectangle(x1-4,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx = new Rectangle(x1+width,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
	
		this.refreshReferenceSection();	
	}
	
	public GRImage(long id, GRPage grpage, String pathFile, int xStart, int yStart, int xEnd, int yEnd) {
		super(GRObject.TYPEOBJ_IMAGE,id,grpage);
		
		this.pathFile = pathFile;
		
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
		
		tracker = new MediaTracker(grpage);
		
		imgOriginal = Toolkit.getDefaultToolkit().getImage(pathFile);
		tracker.addImage(imgOriginal, 0);
		try {
			tracker.waitForID(0);
		} catch(InterruptedException e) {}
		
		widthFile = imgOriginal.getWidth(null);
		heightFile = imgOriginal.getHeight(null);
		
		x1Original = x1;
		y1Original = y1;
		widthOriginal = width;
		heightOriginal = height;
		
		scaledImage();
		
		// Crea le ancore
		tsx = new Rectangle(x1-4,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		tdx = new Rectangle(x1+width,y1-4,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bsx = new Rectangle(x1-4,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
		bdx = new Rectangle(x1+width,y1+height,GRObject.DIM_ANCHOR,GRObject.DIM_ANCHOR);
	
		this.refreshReferenceSection();	
	}
	
	private void scaledImage() {
		if(imgOriginal == null)
			return;
		
		imgScaled = imgOriginal.getScaledInstance(width,height,Image.SCALE_FAST);
		
		tracker.addImage(imgScaled,1);
		try {
			tracker.waitForID(1);
		} catch(InterruptedException e) {}
		
	}
	public String getPathFile() {
		return pathFile;
	}
	public int getFileWidth() {
		return widthFile;
	}
	public int getFileHeight() {
		return heightFile;
	}
	public void setIdImage(String idImg) {
		this.idImg = idImg;
		
	}
	public void setIdImageFromGRS(String idImg) {
		this.idImg = idImg;
		
		// Una volta che si conosce l'id si recupera il path dell'immagine
		// dalle risorse.
		pathFile = resImg.getResource(idImg).getPath();
		
		if(pathFile != null) {
			imgOriginal = Toolkit.getDefaultToolkit().getImage(pathFile);
			tracker.addImage(imgOriginal, 0);
			try {
				tracker.waitForID(0);
			} catch(InterruptedException e) {}
			
			widthFile = imgOriginal.getWidth(null);
			heightFile = imgOriginal.getHeight(null);
			
			scaledImage();
		}
	}
	public String getIdImage() {
		return idImg;
	}
	public void setWidth(int w) {
		super.setWidth(w);
		
		scaledImage();
	}
	public void setHeight(int h) {
		super.setHeight(h);
		
		scaledImage();
	}
	public void setZoom(float value) {
		super.setZoom(value);
		
		scaledImage();
	}
	public GRImage clone(long id) {
		return null;
	}
	public void draw(Graphics g) {
		int hGap = 0;
		int y1 = this.y1;
		
		if(hPosition) {
			hGap = hGap + grpage.hPosition;
			gapH = hGap;
		}
		
		y1 = y1 + hGap;
		
		g.drawImage(imgScaled,x1,y1,null);
		
		grpage.hPosition = y1 + height;
		
		if(selected) {
			g.drawRect(x1,y1,width,height);
			
			g.fillRect(x1-4,y1-4,4,4);
			g.fillRect(x1-4,y1+height,4,4);
			g.fillRect(x1+width,y1-4,4,4);
			g.fillRect(x1+width,y1+height,4,4);
		}
	}
	public String createCodeGRS() {
		StringBuffer buff = new StringBuffer();
		int y1 = this.y1Original;
		
		if(section == GRObject.SECTION_BODY)
			y1 = y1 - grpage.getHeaderSize();
		if(section == GRObject.SECTION_FOOTER) {
			y1 = y1 - (grpage.getHeight() - grpage.getFooterSize());
		}
		buff.append("<image>\n");
		buff.append("<refid>"+idImg+"</refid>\n");
		buff.append("<left>"+GRLibrary.fromPixelsToMillimeters(x1Original)+"</left>\n");
		buff.append("<top>"+GRLibrary.fromPixelsToMillimeters(y1)+"</top>\n");
		buff.append("<width>"+GRLibrary.fromPixelsToMillimeters(widthOriginal)+"</width>\n");
		buff.append("<height>"+GRLibrary.fromPixelsToMillimeters(heightOriginal)+"</height>\n");
		buff.append("<hposition>");
		if(hPosition)
			buff.append("relative");
		else
			buff.append("absolute");
		buff.append("</hposition>\n");
		buff.append("</image>");
		
		return buff.toString();
	}
}
