/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRProject
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.globalreports.editor.designer.dialog.GRDialogRenameDocument;
import com.globalreports.editor.designer.swing.GRTreeNode;
import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRTableList;

@SuppressWarnings("serial")
public class GRProject extends JPanel implements TreeSelectionListener, ActionListener, MouseListener {
	private GREditor greditor;
	private JTree grtree;
	private DefaultMutableTreeNode rootProject;
	private DefaultTreeModel grtreeModel;
	private int totalePagine;
	private int autoCounter;
	
	// POPUP MENU
	private JPopupMenu menuProject;
	private JMenuItem menuProjectRinomina;
	private JMenuItem menuProjectEliminaPagina;
	
	public GRProject(GREditor greditor) {
		this.greditor = greditor;
		this.init();
	}
	private void init() {
		setLayout(new BorderLayout());
		
		autoCounter = 1;
		
		rootProject = new DefaultMutableTreeNode("GlobalReports Project");
		
		grtree = new JTree(rootProject);
		grtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		grtreeModel = (DefaultTreeModel)grtree.getModel();
		grtree.setEditable(true);
		
		grtree.addTreeSelectionListener(this);
		grtree.addMouseListener(this);
		
		add(grtree,BorderLayout.CENTER);
		
		totalePagine = 0;
		
		menuProject = new JPopupMenu();
		
		menuProjectRinomina = new JMenuItem("Rinomina...");
		menuProjectRinomina.addActionListener(this);
		menuProject.add(menuProjectRinomina);
		
		menuProjectEliminaPagina = new JMenuItem("Elimina pagina");
		menuProjectEliminaPagina.addActionListener(this);
		menuProject.add(menuProjectEliminaPagina);
	}
	public void expand() {
		grtree.expandRow(0);
	}
	public void addPage(GRDocument doc) {
		GRTreeNode page = new GRTreeNode(doc);
		doc.setNameDocument("Pagina"+autoCounter);
		page.setName(doc.getNameDocument());
		autoCounter++;
		
		rootProject.add(page);
		grtreeModel.reload();
				
		totalePagine++;
		grtree.setSelectionRow(totalePagine);
		
	}
	public int getTotalePagine() {
		return totalePagine;
	}
	public void setNameDocument(String value) {
		TreePath selPath = grtree.getSelectionPath();
		
		if(selPath.getLastPathComponent() instanceof GRTreeNode) {
			GRTreeNode nodo = (GRTreeNode)selPath.getLastPathComponent();
			
			nodo.setName(value);
		} else {
			return;
		}
		
		grtreeModel.reload();
		
		grtree.setSelectionPath(selPath);
	}
	private void clearDocument() {
		TreePath selPath = grtree.getSelectionPath();
		
		if(selPath.getLastPathComponent() instanceof GRTreeNode) {
			GRTreeNode nodo = (GRTreeNode)selPath.getLastPathComponent();
			
			rootProject.remove(nodo);
			grtreeModel.reload();
			
			grtree.setSelectionRow(1);
			totalePagine--;
		} else {
			return;
		}
	}
	public void valueChanged(TreeSelectionEvent e) {
		if(!(e.getPath().getLastPathComponent() instanceof GRTreeNode)) {
			return;
		}
		GRTreeNode dm = ((GRTreeNode)(e.getPath().getLastPathComponent()));
		
		try {
			dm.getDocument().setSelected(true);
		} catch(PropertyVetoException pve) {
			System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
		}
		
	}
	public void clear() {
		this.remove(grtree);
		
		this.init();
	}
	public GRDocument getDocument(int i) {
		return ((GRTreeNode)rootProject.getChildAt(i)).getDocument();
	}
	public void setNameProject(String value) {
		rootProject.setUserObject(value);
		
		grtreeModel.reload();
		grtree.setSelectionRow(totalePagine);
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent me) {
		if(me.isPopupTrigger()) {
			menuProject.show(me.getComponent(),me.getX(),me.getY());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == menuProjectRinomina) {
			String value = new GRDialogRenameDocument().getValue();
			
			if(value != null)
				this.setNameDocument(value);
		} else if(e.getSource() == menuProjectEliminaPagina) {
			this.clearDocument();
		}
	}
}
