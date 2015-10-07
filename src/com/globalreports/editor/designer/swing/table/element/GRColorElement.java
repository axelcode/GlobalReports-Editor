package com.globalreports.editor.designer.swing.table.element;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.globalreports.editor.designer.dialog.GRDialogColorChooser;
import com.globalreports.editor.designer.resources.GRColor;
import com.globalreports.editor.designer.swing.table.GRTableCell;
import com.globalreports.editor.tools.GRLibrary;

public class GRColorElement extends JPanel implements MouseListener {
	private final Color TRANSPARENTCOLOR	= new Color(240, 240 ,240);
	
	private Color color;
	private JLabel labelColor;
	private boolean hasTransparent;
	
	private GRTableCell cell;
	
	public GRColorElement() {
		this(null, true);
	}
	public GRColorElement(Color color) {
		this(color, false);
	}
	public GRColorElement(Color color, boolean hasTransparent) {
		this.color = color;
		this.hasTransparent = hasTransparent;
		
		labelColor = new JLabel();
		render();
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		if(color == null) {
			setBackground(TRANSPARENTCOLOR);
			labelColor.setForeground(Color.BLACK);
		} else {
			setBackground(color);
			labelColor.setForeground(GRLibrary.ColorText(color));
		}
		add(labelColor);
		
		addMouseListener(this);
	}
	public void setCell(GRTableCell cell) {
		this.cell = cell;
	}
	public boolean transparency() {
		return hasTransparent;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
		
		render();
	}
	public JPanel getColorElement() {
		return null;
	}
	public String toString() {
		return labelColor.getText();
	}
	private void render() {
		if(color == null) {
			labelColor.setForeground(Color.BLACK);
			labelColor.setText("trasparente");
			setBackground(TRANSPARENTCOLOR);
		} else {
			labelColor.setText(color.getRed()+" "+color.getGreen()+" "+color.getBlue());
			setBackground(color);
			labelColor.setForeground(GRLibrary.ColorText(color));
		}
		
		if(cell != null)
			cell.refreshCell(GRTableCell.TYPECELL_COLOR);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		GRColor c = GRDialogColorChooser.showDialog(color, hasTransparent);
		
		if(c != null) {
			color = c.getColor();
			render();
		}
			
		//color = GRDialogColorChooser.showDialog(color, hasTransparent).getColor();
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
