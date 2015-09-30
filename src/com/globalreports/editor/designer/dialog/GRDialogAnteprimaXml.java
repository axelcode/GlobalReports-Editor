/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogAnteprimaXml
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
package com.globalreports.editor.designer.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.globalreports.editor.graphics.GRList;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDialogAnteprimaXml extends JDialog implements ActionListener {
	private Hashtable<String, String> xmlDefault;
	
	private final JPanel contentPanel = new JPanel();
	private JPanel addPanel;
	
	private JTextField[] textDati;
	private JTextField[] textList;
	private JButton okButton;
	private JButton cancelButton;
	private JButton addButton;
	private JCheckBox chkSave;
	
	//Vector<JTextField[]> textRowList; 
	Vector<Vector> textRowList;
	Vector<String> nodiXml;
	Vector<String> nodiListXml;
	private String xml;
	private String nameList;
	
	private JPanel columnpanel;
	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public GRDialogAnteprimaXml(Vector<String> nodi) {
		this(nodi, null, null);
	}
	public GRDialogAnteprimaXml(String nameList, Vector<String> varList) {
		this(null, nameList, varList);
	}
	public GRDialogAnteprimaXml(Vector<String> nodi, String nameList, Vector<String> varList) {
		this.nameList = nameList;
		this.nodiXml = nodi;
		this.nodiListXml = varList;
		setMinimumSize(new Dimension(620,400));
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		// Acquisisce i dati di default
		readDefaultXml("temp");
		
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(scroll,BorderLayout.CENTER);
		
		JPanel borderlaoutpanel = new JPanel();
        scroll.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));
        
        columnpanel = new JPanel();
        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);

        textDati = new JTextField[nodi.size()];
        
        /* Genera a run time la struttura delle variabili */
        for(int i=0;i < nodi.size();i++) {
        	
            JPanel rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(300,30));
            columnpanel.add(rowPanel);
            rowPanel.setLayout(new FlowLayout());

            JLabel label1 = new JLabel("<"+nodi.get(i)+">");
            label1.setBounds(20, 5, 100, 24);
            rowPanel.add(label1);

            textDati[i] = new JTextField();
            textDati[i].setPreferredSize(new Dimension(100,24));
            textDati[i].setBounds(120,5,100,24);
            
            if(xmlDefault != null)
            	textDati[i].setText(this.getValueDefault(nodi.get(i)));
            rowPanel.add(textDati[i]);
            
            JLabel label2 = new JLabel("</"+nodi.get(i)+">");
            label2.setBounds(220, 5, 100, 24);
            rowPanel.add(label2);
            if(i % 2 == 0)
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
        }
        
        /* Nel caso di oggetti dinamici genera i panel con le liste */
        if(nameList != null && varList.size() > 0) { 
        	setPanelAddButton();
        	
        	JPanel titlePanel = new JPanel();
        	titlePanel.setPreferredSize(new Dimension(300,30));
        	titlePanel.setBackground(new Color(22,128,197));
        	columnpanel.add(titlePanel);
        	titlePanel.setLayout(new FlowLayout());
        	
        	JLabel label1 = new JLabel("GRLIST::"+nameList);
            label1.setBounds(20, 5, 100, 24);
            label1.setForeground(Color.WHITE);
            label1.setFont(new Font("Tahoma",Font.BOLD,12));
            titlePanel.add(label1);
            
            addRowList();   
            
        }
        
        JPanel southPanel = new JPanel(new GridLayout(1,2));
        
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel chkPanel = new JPanel();
		chkPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		chkSave = new JCheckBox("Salva impostazioni");
		chkSave.setSelected(true);
		chkPanel.add(chkSave);
		
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		southPanel.add(chkPanel);
		southPanel.add(buttonPane);
		
		c.add(southPanel, BorderLayout.SOUTH);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.WHITE);
		
		setBounds(100, 100, 620, 400);
		setModal(true);
		setVisible(true);		
		
	}

	private void readDefaultXml(String idSession) {
		
		try {
				
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new File("temp//"+idSession+".xml"));
					
			Element rootElement = document.getRootElement();
			//List<Element> children = rootElement.getChildren();
			List<Element> children = GRLibrary.castList(Element.class,rootElement.getChildren());	
				
			Iterator<Element> iterator = children.iterator();
			while (iterator.hasNext()){
					
				readChild((Element)iterator.next());
			} 
							
		} catch(JDOMException jde) {
			System.out.println("GRManage::readSource::JDOMException: "+jde.getMessage());
			
		} catch(IOException ioe) {
			System.out.println("GREditor::readSource::IOException: "+ioe.getMessage());
		} 
			
		
	}
	private void readChild(Element element) {
		if(element.getName().equals("data")) {
			readData(element);
		} else if(element.getName().equals("list")) {
			//System.out.println("list");
		} 
			
	}
	private void readData(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(((Element)element.getParent()).getName().equals("data")) {
				if(xmlDefault == null)
					xmlDefault = new Hashtable<String, String>();
				
				xmlDefault.put(element.getName(),element.getValue());
			} 
		}
		
	}
	private String getValueDefault(String key) {
		if(xmlDefault == null)
			return "";
		
		return xmlDefault.get(key);
		
	}
	private void saveXmlDefault() {
		if(xml == null)
			return;
		
		/* Se il file temp esiste lo cancella */
		File f = new File("temp//temp.xml");
		f.delete();
		
		RandomAccessFile rOut;
		try {
			rOut = new RandomAccessFile("temp//temp.xml","rw");
			
			rOut.writeBytes(xml+"\n");
			rOut.close();
		} catch(Exception e) {
			
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			this.setXml();
			
			if(chkSave.isSelected())
				saveXmlDefault();
			
			this.dispose();
		} else if(e.getSource() == cancelButton) {
			xml = null;
			
			this.dispose();
		} else if(e.getSource() == addButton) {
			addRowList();
		}
	}
	private void addRowList() {
		JPanel rowPanel = null;
		
		if(textRowList == null)
        	textRowList = new Vector<Vector>();
        
		Vector<JTextField> textList = new Vector<JTextField>();
        
		columnpanel.remove(addButton);
		
		/* APERTURA NODO ROW */
		JPanel recordPanelOpen = new JPanel();
		recordPanelOpen.setPreferredSize(new Dimension(300,30));
    	columnpanel.add(recordPanelOpen);
    	recordPanelOpen.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel label1 = new JLabel("<ROW>");
        label1.setBounds(20, 5, 100, 24);
        label1.setFont(new Font("Tahoma",Font.BOLD,12));
        recordPanelOpen.add(label1);
        
        /* NODI FIGLI */
    	for(int i = 0;i < nodiListXml.size();i++) {
    		rowPanel = new JPanel();
            rowPanel.setPreferredSize(new Dimension(300,30));
            //columnpanel.add(rowPanel);
            rowPanel.setLayout(new FlowLayout());

            JLabel lblNodoOpen = new JLabel("<"+nodiListXml.get(i)+">");
            lblNodoOpen.setBounds(20, 5, 100, 24);
            rowPanel.add(lblNodoOpen);

            JTextField tField = new JTextField();
            
            tField.setPreferredSize(new Dimension(100,24));
            tField.setBounds(120,5,100,24);
            rowPanel.add(tField);
            
            JLabel lblNodoClose = new JLabel("</"+nodiListXml.get(i)+">");
            lblNodoClose.setBounds(220, 5, 100, 24);
            rowPanel.add(lblNodoClose);
            if(i % 2 == 0)
                rowPanel.setBackground(SystemColor.inactiveCaptionBorder);
            
            textList.add(tField);
            columnpanel.add(rowPanel);
            
            tField.setFocusable(true);
        	
    	}
    	textRowList.add(textList);
    	
    	/* CHIUSURA NODO ROW */
    	JPanel recordPanelClose = new JPanel();
		recordPanelOpen.setPreferredSize(new Dimension(300,30));
    	columnpanel.add(recordPanelOpen);
    	recordPanelOpen.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel label2 = new JLabel("<ROW>");
        label2.setBounds(20, 5, 100, 24);
        label2.setFont(new Font("Tahoma",Font.BOLD,12));
        recordPanelClose.add(label2);
        
    	/* Aggiunta del pulsante nuovo nodo row */
    	columnpanel.add(addPanel);   
    	
    	/* Questo va fatto per refreshare l'area */
    	this.setSize(this.getWidth(),this.getHeight()+1);
    	this.setSize(this.getWidth(),this.getHeight()-1);
    	
	}
	private void setPanelAddButton() {
		addPanel = new JPanel();
    	addPanel.setPreferredSize(new Dimension(300,36));
    	addPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    	
    	addButton = new JButton("Aggiungi");
    	addButton.addActionListener(this);
    	//addButton.setBounds(20, 5, 100, 24);
        addPanel.add(addButton);
	}
	private void setXml() {
		if(nodiXml.size() == 0 && nodiListXml.size() == 0) {
			xml = null;
			
			return;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<globalreports>\n");
		buffer.append("<data>\n");
		for(int i = 0;i < nodiXml.size();i++) {
			buffer.append("<"+nodiXml.get(i)+">"+textDati[i].getText()+"</"+nodiXml.get(i)+">\n");
		}
		buffer.append("</data>\n");
		
		if(nodiListXml.size() > 0) {
			buffer.append("<list>\n");
			buffer.append("<name>"+nameList+"</name>\n");
			
			for(int x = 0;x < textRowList.size();x++) {
				Vector<JTextField> tRow = textRowList.get(x);
				
				buffer.append("<row>\n");
				
				for(int y = 0;y < tRow.size();y++) {
					buffer.append("<"+nodiListXml.get(y)+">"+tRow.get(y).getText()+"</"+nodiListXml.get(y)+">\n");
				}
				
				buffer.append("</row>\n");
			}
			
			buffer.append("</list>\n");
		}
		
		buffer.append("</globalreports>\n");
		
		xml = buffer.toString();
	}
	public String getXml() {
		return xml;
	}
}
