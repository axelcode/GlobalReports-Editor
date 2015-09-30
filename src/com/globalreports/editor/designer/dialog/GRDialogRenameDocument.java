package com.globalreports.editor.designer.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;

public class GRDialogRenameDocument extends JDialog implements ActionListener {
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField txtNuovoDocumento;
	private String value;
	/**
	 * Create the dialog.
	 */
	public GRDialogRenameDocument() {
		setModal(true);
		setTitle("Rename Project");
		setResizable(false);
		Container c = getContentPane();
		
		value = null;
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		c.add(buttonPane, BorderLayout.SOUTH);
			
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		panel = new JPanel();
		
		panel_1 = new JPanel();
		c.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Nuovo nome", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(21, 30, 400, 67);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtNuovoDocumento = new JTextField();
		txtNuovoDocumento.setBounds(10, 32, 380, 24);
		panel_2.add(txtNuovoDocumento);
		txtNuovoDocumento.setColumns(10);
		
		setBounds(100, 100, 448, 182);
		setVisible(true);
	}
	
	public String getValue() {
		return value;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton) {
			this.dispose();
		} else if(e.getSource() == okButton) {
			if(!txtNuovoDocumento.getText().trim().equals("")) {
				value = txtNuovoDocumento.getText();
			}
			
			this.dispose();
		}
	}
}
