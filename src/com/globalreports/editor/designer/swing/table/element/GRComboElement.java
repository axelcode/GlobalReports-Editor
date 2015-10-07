package com.globalreports.editor.designer.swing.table.element;

import javax.swing.JComboBox;

public class GRComboElement extends JComboBox {
	public GRComboElement() {
		
	}
	public GRComboElement(String value) {
		addItem(value);
	}
	public GRComboElement(String[] value) {
		super(value);
	}
	
	public void addElement(String[] value) {
		for(int i = 0;i < value.length;i++)
			addItem(value[i]);
	}
}
