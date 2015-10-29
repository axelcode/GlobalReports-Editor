/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.dialog.GRDialogCreateChart
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTabbedPane;

import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.GRPage;
import com.globalreports.editor.designer.swing.GRTitlePanel;
import com.globalreports.editor.designer.swing.table.GRTable;
import com.globalreports.editor.graphics.GRChart;
import com.globalreports.editor.graphics.chart.GRChartVoice;
import com.globalreports.editor.tools.GRLibrary;

import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class GRDialogCreateChart extends JDialog implements ActionListener, ItemListener {
	private GRPage grpage;
	private final JPanel contentPanel = new JPanel();
	private JTabbedPane tabbedPane;
	
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel;
	
	private JRadioButton rdDatiXml;
	private JRadioButton rdDatiStatici;
	private JButton btnAddLabel;
	
	private JScrollPane scrollTable;
	private GRTable grtableLabel;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	private JLabel lblChart;
	private GRToggleButtonChart[] btnPie;
	private GRToggleButtonChart btnSelect;
	
	private Vector<GRChartVoice> grvoice;
	
	public GRDialogCreateChart(GRPage grpage, int x1, int y1, int x2, int y2) {
		this.grpage = grpage;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	
		grvoice = null;
		btnSelect = null;
		
		setTitle(GRLanguageMessage.messages.getString("chartdialogtitle"));
		setBounds(100, 100, 620, 518);
		Container c = getContentPane();
		
		c.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		c.add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.setBounds(6, 6, 497, 295);
		c.add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Scelta del grafico", createPanePie());
		tabbedPane.addTab("Dati", createPaneDati());
		
		JPanel panelBottom = new JPanel(new GridLayout(0,2));
		
		JPanel labelPane = new JPanel();
		labelPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		lblChart = new JLabel("");
		labelPane.add(lblChart);
		
		panelBottom.add(labelPane);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okButton = new JButton(GRLanguageMessage.messages.getString("btnconfirm"));
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		
		cancelButton = new JButton(GRLanguageMessage.messages.getString("btncancel"));
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		panelBottom.add(buttonPane);
		
		c.add(panelBottom, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	private JPanel createPanePie() {
		JPanel panelPie = new JPanel();
		panelPie.setLayout(new GridLayout(2,0));
		panelPie.setBackground(Color.WHITE);
		
		// PIE
		JPanel panelContent = new JPanel(new BorderLayout());
		panelContent.add(new GRTitlePanel(GRLanguageMessage.messages.getString("chartpie")), BorderLayout.NORTH);
		
		JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelContent.add(panelButton, BorderLayout.CENTER);
		panelButton.setBackground(Color.WHITE);
		
		btnPie = new GRToggleButtonChart[4];
		btnPie[0] = new GRToggleButtonChart(GRChart.TYPECHART_PIE,GRChart.CHARTVIEW_2D);
		btnPie[1] = new GRToggleButtonChart(GRChart.TYPECHART_PIE_EXPLOSE,GRChart.CHARTVIEW_2D);
		btnPie[2] = new GRToggleButtonChart(GRChart.TYPECHART_PIE,GRChart.CHARTVIEW_3D);
		btnPie[3] = new GRToggleButtonChart(GRChart.TYPECHART_PIE_EXPLOSE,GRChart.CHARTVIEW_3D);
		
		ButtonGroup bg = new ButtonGroup();
		for(int i = 0;i < btnPie.length;i++) {
			btnPie[i].addActionListener(this);
			bg.add(btnPie[i]);
			panelButton.add(btnPie[i]);
		}
		
		panelPie.add(panelContent);
		
		return panelPie;
	}
	private JPanel createPaneDati() {
		JPanel panelDati = new JPanel(new BorderLayout());
		ButtonGroup bg = new ButtonGroup();
		
		JPanel panelScelta = new JPanel(new GridLayout(2,0,10,10));
		panelScelta.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Origine dati", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		rdDatiXml = new JRadioButton("Dati provenienti da xml");
		rdDatiXml.setSelected(true);
		rdDatiXml.addItemListener(this);
		bg.add(rdDatiXml);
		rdDatiStatici = new JRadioButton("Dati statici");
		rdDatiStatici.addItemListener(this);
		bg.add(rdDatiStatici);
		
		panelScelta.add(rdDatiXml);
		panelScelta.add(rdDatiStatici);
		
		JPanel panelTable = new JPanel(new BorderLayout());
		String[] titleTable = {"Etichetta","Valore"};
		Object[][] valueTable = {{new JTextField(), new JTextField()}};
		
		grtableLabel = new GRTable(titleTable, valueTable);
		panelTable.add(grtableLabel, BorderLayout.NORTH);
		
		scrollTable = new JScrollPane(panelTable);
		
		JPanel panelAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnAddLabel = new JButton("Aggiungi etichetta");
		btnAddLabel.addActionListener(this);
		panelAdd.add(btnAddLabel);
		
		panelDati.add(panelScelta, BorderLayout.NORTH);
		panelDati.add(scrollTable, BorderLayout.CENTER);
		panelDati.add(panelAdd, BorderLayout.SOUTH);
				
		scrollTable.setVisible(false);
		btnAddLabel.setVisible(false);
		
		return panelDati;
	}
	private void sceltaGrafico(GRToggleButtonChart btn) {
		lblChart.setText(btn.getText());
		
		btnSelect = btn;
			
	}
	private void insertNewChart() {
		// Verifica se ci sono dati statici da associare al grafico
		for(int i = 0;i < grtableLabel.getNumRows();i++) {
			if(!grtableLabel.getValueAt(i, 0).equals("")) {
				String label = grtableLabel.getValueAt(i, 0);
				
				double value;
				if(grtableLabel.getValueAt(i, 1).equals(""))
					value = 0.0;
				else
					value = Double.parseDouble(grtableLabel.getValueAt(i,1));
				
				if(grvoice == null)
					grvoice = new Vector<GRChartVoice>();
				
				grvoice.add(new GRChartVoice(label, value, null));
				
			}
		}
		grpage.insertChart(x1,  y1,  x2,  y2, btnSelect.getTypeChart(), btnSelect.getView(), grvoice);
		
		this.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton) {
			if(btnSelect == null) {
				JOptionPane.showMessageDialog(this, GRLanguageMessage.messages.getString("dlgmessagewarningchart"),GRLanguageMessage.messages.getString("dlgmessagewarningtitle"),JOptionPane.WARNING_MESSAGE);
			
				return;
			}
			
			insertNewChart();
		} else if(e.getSource() == cancelButton) {
			grpage.annullaInsertChart();
			
			this.dispose();
		} else if(e.getSource() == btnAddLabel) {
			if(grtableLabel.getValueAt(grtableLabel.getNumRows()-1, 0).equals(""))
				return;
			
			Object[] objRow = {new JTextField(), new JTextField()};
			grtableLabel.addRow(objRow);
			
			this.setSize(new Dimension(this.getWidth(), this.getHeight()+1));
			this.setSize(new Dimension(this.getWidth(), this.getHeight()-1));

		} else {
			sceltaGrafico((GRToggleButtonChart)e.getSource());
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(e.getSource() == rdDatiStatici) {
				scrollTable.setVisible(true);
				btnAddLabel.setVisible(true);
				
				repaint();
			} else if(e.getSource() == rdDatiXml) {
				scrollTable.setVisible(false);
				btnAddLabel.setVisible(false);
			}
				
		} 
	}
}

class GRToggleButtonChart extends JToggleButton {
	private String[] PIE = {"ico_pie2d_69.png","ico_pie2d_esplosa_69.png","ico_pie3d_69.png","ico_pie3d_esplosa_69.png"};
	
	private int typeChart;
	private int view;
	public GRToggleButtonChart(int typeChart, int view) {
		super();
		
		this.typeChart = typeChart;
		this.view = view;
		
		switch(typeChart) {
			case GRChart.TYPECHART_PIE:
				if(view == GRChart.CHARTVIEW_2D) {
					setText(GRLanguageMessage.messages.getString("chartpie2d"));
					setIcon(new ImageIcon(GRSetting.PATHIMAGE+PIE[0]));
				} else {
					setText(GRLanguageMessage.messages.getString("chartpie3d"));
					setIcon(new ImageIcon(GRSetting.PATHIMAGE+PIE[2]));
				}
				break;
			
			case GRChart.TYPECHART_PIE_EXPLOSE:
				if(view == GRChart.CHARTVIEW_2D) {
					setText(GRLanguageMessage.messages.getString("chartpie2dexplose"));
					setIcon(new ImageIcon(GRSetting.PATHIMAGE+PIE[1]));
				} else {
					setText(GRLanguageMessage.messages.getString("chartpie3dexplose"));
					setIcon(new ImageIcon(GRSetting.PATHIMAGE+PIE[3]));
				}
				break;
		}
		
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.BOTTOM);
	}
	
	public int getTypeChart() {
		return typeChart;
	}
	public int getView() {
		return view;
	}
}

