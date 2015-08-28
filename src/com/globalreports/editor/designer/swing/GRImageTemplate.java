package com.globalreports.editor.designer.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;

import com.globalreports.editor.GRSetting;

@SuppressWarnings("serial")
public class GRImageTemplate extends JPanel {
	private MediaTracker tracker;
	private Image imgTemplate[];
	private String[] nameImg = {"modEmpty_s.png","modBaseCNP_s.png"};
	private int indexTemplate;
	
	public GRImageTemplate() {
		tracker = new MediaTracker(this);
		imgTemplate = new Image[2];
		indexTemplate = 0;
		
		for(int i = 0;i < nameImg.length;i++) {
			imgTemplate[i] = Toolkit.getDefaultToolkit().getImage(GRSetting.PATHTEMPLATE+nameImg[i]);
		
			tracker.addImage(imgTemplate[i],i);
			try {
				tracker.waitForID(i);
			} catch(InterruptedException e) {}
		}
		
		setBackground(Color.WHITE);
		setSize(196,272);
		setVisible(true);
		
	}
	
	public void setTemplate(int index) {
		if(index <= 0 || index >= nameImg.length)
			indexTemplate = -1;
		
		indexTemplate = index;
		repaint();
	}
	public void paint(Graphics g) {
		super.paint(g);
		
		if(indexTemplate != -1)
			g.drawImage(imgTemplate[indexTemplate],0,0,this);
	}
	
	public int getIndexTemplate() {
		return indexTemplate;
	}
}
