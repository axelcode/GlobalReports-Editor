package com.globalreports.editor.designer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoundedRangeModel;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.GRPanelPage;
import com.globalreports.editor.designer.GRPanelSection;

public class GRDocumentNew extends JInternalFrame {
	private GREditor greditor;
	private GRPanelSection panelSection;
	private GRPanelPage panelPage;
	private GRPage grpage;
	private JScrollBar scrollVertical;
	
	private String nameDocument;
	
	/* Dimensioni della pagina */
	private int widthPage;
	private int heightPage;
	
	public GRDocumentNew() {
		this(null,"Nuovo documento");
	}
	public GRDocumentNew(GREditor gredit,String title) {
		this(gredit,title,210,297);
	}
	public GRDocumentNew(GREditor gredit,String title, int w, int h) {
		this(gredit,title,w,h,true,true,true);
	}
	public GRDocumentNew(GREditor gredit, String title, int w, int h, boolean resizable, boolean closeable, boolean maximizable) {
		super(title,resizable,closeable,maximizable);
		
		greditor = gredit;
		nameDocument = title;
		widthPage = w;
		heightPage = h;
		
		this.init();
		
	}
	
	private void init() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout(0,0));
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scroll.setBounds(10,10,600,200);
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		//panel.setLayout(null);
		
		JPanel grpage = new JPanel();
		grpage.setPreferredSize(new Dimension(630,891));
		//grpage.setSize(630,891);
		grpage.setBackground(Color.white);
		grpage.setBorder(new MatteBorder(1,1,1,1,Color.black));
		grpage.setLocation(20,20);
		grpage.setLayout(null);
		panel.add(grpage);
		
		scroll.setViewportView(panel);
		c.add(scroll,BorderLayout.CENTER);
		
		setSize(300,200);
		setResizable(true);
		setBackground(new Color(220,220,220));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setVisible(true);
	}
}
