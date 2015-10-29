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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GRDialogAnteprimaXml extends JDialog implements ActionListener {
	private Hashtable<String, String> xmlDefault;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField[] textDati;
	private JTextField[] textList;
	private JButton addButton;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox chkSave;
	
	//Vector<JTextField[]> textRowList; 
	Vector<Vector> textRowList;
	Vector<String> nodiXml;
	Vector<String> nodiListXml;
	private String xml;
	private String nameList;
	
	private JPanel contentTablePanel;
	private GRTable grtable;
	private int totRecordData;
	private int totRecordList;
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
		setTitle(GRLanguageMessage.messages.getString("dlgpreviewxmltitle"));
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
		
		contentTablePanel = new JPanel();
        scroll.setViewportView(contentTablePanel);
        contentTablePanel.setLayout(new BorderLayout(0, 0));
        
        textDati = new JTextField[nodi.size()];
        
        String[] titleTable = {GRLanguageMessage.messages.getString("dlgpreviewxmltabletitlecol1"),GRLanguageMessage.messages.getString("dlgpreviewxmltabletitlecol2")};
        grtable = new GRTable(titleTable);
        
        contentTablePanel.add(grtable, BorderLayout.NORTH);
      
        totRecordData = 0;
        grtable.addSeparator("DATA");
        for(int i=0;i < nodi.size();i++) {
        	Object[] objRow = {""+nodi.get(i), new JTextField()};
            grtable.addRow(objRow);
            
            if(xmlDefault != null) {
            	String value = this.getValueDefault(nodi.get(i));
            	
            	if(value != null)
            		grtable.setValueAt(i,1,value);
            }
            totRecordData++;
        }
        
        /* Nel caso di oggetti dinamici genera i record relativi alle liste */
        if(nameList != null && varList.size() > 0) { 
        	totRecordList = 0;
        	grtable.addSeparator("GRLIST::"+nameList);
        	
            addRowList();   
            
        }
        
        
        JPanel southPanel = new JPanel(new GridLayout(1,2));
        
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JPanel chkPanel = new JPanel();
		chkPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		chkSave = new JCheckBox(GRLanguageMessage.messages.getString("dlgpreviewxmlchksavesettings"));
		chkSave.setSelected(true);
		chkPanel.add(chkSave);
		
		addButton = new JButton(GRLanguageMessage.messages.getString("dlgpreviewxmlbtnaddrecord"));
		addButton.addActionListener(this);
		
		if(nameList == null)
			addButton.setVisible(false);
		else
			addButton.setVisible(true);
		buttonPane.add(addButton);
		okButton = new JButton(GRLanguageMessage.messages.getString("btnconfirm"));
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		
		cancelButton = new JButton(GRLanguageMessage.messages.getString("btncancel"));
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
			/* Nessun file xml temporaneo */
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
           
		for(int i = 0;i < nodiListXml.size();i++) {
			Object[] objList = {""+nodiListXml.get(i),new JTextField()};
			
			grtable.addRow(objList);
			
			totRecordList++;
		}
		
		contentTablePanel.setSize(contentTablePanel.getWidth(),contentTablePanel.getHeight()+1);
		contentTablePanel.setSize(contentTablePanel.getWidth(),contentTablePanel.getHeight()-1);
    	
		repaint();
	}
	
	private void setXml() {
		if(nodiXml.size() == 0 && nodiListXml.size() == 0) {
			xml = null;
			
			return;
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<globalreports>\n");
		buffer.append("<data>\n");
		for(int i = 0;i < totRecordData;i++) {
			buffer.append("<"+grtable.getValueAt(i, 0)+">"+grtable.getValueAt(i,1)+"</"+grtable.getValueAt(i, 0)+">\n");
		}
		buffer.append("</data>\n");
		
		if(nodiListXml.size() > 0) {
			buffer.append("<list>\n");
			buffer.append("<name>"+nameList+"</name>\n");
			
			if(totRecordList > 0) {
				int totRecord = totRecordList / nodiListXml.size();
			
				for(int i = 0;i < totRecord;i++) {
					buffer.append("<row>\n");
					
					for(int x = 0;x < nodiListXml.size();x++) {
						int index = (i * nodiListXml.size()) + (x + totRecordData);
						
						buffer.append("<"+grtable.getValueAt(index, 0)+">"+grtable.getValueAt(index, 1)+"</"+grtable.getValueAt(index, 0)+">\n");
						
					}
					
					buffer.append("</row>\n");
				}
				
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
