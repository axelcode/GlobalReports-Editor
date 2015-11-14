/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GREditor
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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.beans.PropertyVetoException;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.ZipOutputStream;

import org.jdom.*;
import org.jdom.input.*;

import com.globalreports.compiler.gr.err.GRCompFileCorruptedException;
import com.globalreports.compiler.gr.err.GRCompIOException;
import com.globalreports.compiler.gr.manage.GRCompiler;
import com.globalreports.engine.manage.GRPDF;
import com.globalreports.engine.manage.err.GRLayoutException;
import com.globalreports.engine.manage.err.GRPdfIOWriteException;
import com.globalreports.engine.manage.err.GRPdfPathNotFoundException;
import com.globalreports.engine.manage.err.GRValidateException;
import com.globalreports.editor.GRAbout;
import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.languages.GRLanguageMessage;
import com.globalreports.editor.designer.dialog.GRDialogAnteprimaXml;
import com.globalreports.editor.designer.dialog.GRDialogImpostaValoriGriglia;
import com.globalreports.editor.designer.dialog.GRDialogNewDocument;
import com.globalreports.editor.designer.dialog.GRDialogStoryBook;
import com.globalreports.editor.designer.dialog.GRDialogTemplate;
import com.globalreports.editor.designer.property.*;

import com.globalreports.editor.designer.resources.GRImageResource;
import com.globalreports.editor.designer.resources.GRResFonts;
import com.globalreports.editor.designer.resources.GRResImages;

import com.globalreports.editor.designer.swing.toolbar.GRToolBar;
import com.globalreports.editor.designer.swing.toolbar.GRToolBarDesigner;
import com.globalreports.editor.designer.swing.toolbar.GRToolBarStrumenti;
import com.globalreports.editor.graphics.GRImage;
import com.globalreports.editor.graphics.GRList;
import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRText;

import com.globalreports.editor.tools.GRFile;
import com.globalreports.editor.tools.GRLibrary;

@SuppressWarnings("serial")
public class GREditor extends JFrame implements ActionListener {
	public static final int MENUTYPE_FILE				= 1;
	public static final int MENUTYPE_MODIFICA			= 2;
	public static final int MENUTYPE_STRUMENTI			= 3;
	
	public static final int MENUVOICE_ANNULLA			= 1;
	public static final int MENUVOICE_CANCELLA			= 2;
	
	private final String PATH_TEMP 			= System.getProperty("java.io.tmpdir");
	private String path_dirTemp;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuFileNew;
	private JMenuItem menuFileNewFromTemplate;
	private JMenuItem menuFileOpen;
	private JMenuItem menuFileAdd;
	private JMenuItem menuFileAddPageFromTemplate;
	private JMenuItem menuFileSave;
	private JMenuItem menuFileSaveAs;
	private JMenuItem menuFileSaveSingle;
	private JMenuItem menuFileSaveProject;
	private JMenuItem menuFileAnteprimaPDF;
	private JMenuItem menuFileAnteprimaPDFDocument;
	private JMenuItem menuFileAnteprimaPDFXml;
	private JMenuItem menuFileExportXml;
	private JMenuItem menuFileDebug;
	private JMenu menuModifica;
	private JMenuItem menuModificaAnnulla;
	private JMenuItem menuModificaCancella;
	private JMenuItem menuModificaCopia;
	private JMenuItem menuModificaIncolla;
	private JMenu menuStrumenti;
	private JMenu menuStrumentiGriglia;
	private JMenuItem menuStrumentiGrigliaImpostaValori;
	private JMenuItem menuArchivioImmagini;
	private JMenuItem menuStrumentiStoryBook;
	private JMenuItem menuStrumentiRaggruppa;
	
	private JPanel panelToolBar;
	private GRToolBar grtoolbar;
	private GRToolBarDesigner grtoolbarDesigner;
	private GRToolBarStrumenti grtoolbarStrumenti;
	private JDesktopPane desk;
	private GRTableProperty panelProperty;
	private GRProject grproject;
	
	private GRDocument doc;
	private GRPanelFooter grfoot;
	
	private JSplitPane split;
	private JSplitPane splitProperty;
	private Font font;
	
	/* Informazioni preesistenti relative al documento */
	private String pathDocumentSaved;
	private String nameDocumentSaved;
	
	// Risorse del documento
	private GRResFonts resFont;
	private GRResImages resImg;
	
	public GREditor() {
		super("Global Reports Editor");
		
		// Look & Feel
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(ClassNotFoundException cnfe) {
			System.out.println("GREditor::construct::ClassNotFoundException: "+cnfe.getMessage());
		} catch(InstantiationException ie) {
			System.out.println("GREditor::construct::InstantiationException: "+ie.getMessage());
		} catch(IllegalAccessException iae) {
			System.out.println("GREditor::construct::IllegalAccessException: "+iae.getMessage());
		} catch(UnsupportedLookAndFeelException ulafe) {
			System.out.println("GREditor::construct::UnsupportedLookAndFeelException: "+ulafe.getMessage());
		}
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		font = new Font("Lucida Grande",Font.PLAIN,10);
		
		menuBar = new JMenuBar();
		
		// MENU FILE
		menuFile = new JMenu(GRLanguageMessage.messages.getString("menufile"));
		menuBar.add(menuFile);
		
		menuFileNew = new JMenuItem(GRLanguageMessage.messages.getString("menufilenew"));
		menuFileNew.addActionListener(this);
		menuFileNewFromTemplate = new JMenuItem(GRLanguageMessage.messages.getString("menufilenewfromtemplate"));
		menuFileNewFromTemplate.addActionListener(this);
		menuFileOpen = new JMenuItem(GRLanguageMessage.messages.getString("menufileopen"));
		menuFileOpen.addActionListener(this);
		menuFileAdd = new JMenuItem(GRLanguageMessage.messages.getString("menufileadd"));
		menuFileAdd.addActionListener(this);
		menuFileAddPageFromTemplate = new JMenuItem(GRLanguageMessage.messages.getString("menufileaddpagefromtemplate"));
		menuFileAddPageFromTemplate.addActionListener(this);
		menuFileSaveAs = new JMenuItem(GRLanguageMessage.messages.getString("menufilesaveas"));
		menuFileSaveAs.addActionListener(this);
		menuFileSaveSingle = new JMenuItem(GRLanguageMessage.messages.getString("menufilesavesingle"));
		menuFileSaveSingle.addActionListener(this);
		menuFileSaveProject = new JMenuItem(GRLanguageMessage.messages.getString("menufilesaveproject"));
		menuFileSaveProject.addActionListener(this);
		menuFileAnteprimaPDF = new JMenuItem(GRLanguageMessage.messages.getString("menufileanteprimapdf"));
		menuFileAnteprimaPDF.addActionListener(this);
		menuFileAnteprimaPDFDocument = new JMenuItem(GRLanguageMessage.messages.getString("menufileanteprimapdfdocument"));
		menuFileAnteprimaPDFDocument.addActionListener(this);
		menuFileAnteprimaPDFXml = new JMenuItem(GRLanguageMessage.messages.getString("menufileanteprimapdfxml"));
		menuFileAnteprimaPDFXml.addActionListener(this);
		menuFileExportXml = new JMenuItem(GRLanguageMessage.messages.getString("menufileexportxml"));
		menuFileExportXml.addActionListener(this);
		menuFileExportXml.setEnabled(false);
		
		menuFile.add(menuFileNew);
		menuFile.add(menuFileNewFromTemplate);
		menuFile.add(menuFileOpen);
		menuFile.add(menuFileAdd);
		menuFile.add(menuFileAddPageFromTemplate);
		menuFile.addSeparator();
		menuFile.add(menuFileSaveSingle);
		menuFile.add(menuFileSaveProject);
		menuFile.add(menuFileSaveAs);
		menuFile.addSeparator();
		menuFile.add(menuFileAnteprimaPDF);
		menuFile.add(menuFileAnteprimaPDFDocument);
		menuFile.add(menuFileAnteprimaPDFXml);
		menuFile.addSeparator();
		menuFile.add(menuFileExportXml);
		//menuFile.add(menuFileDebug);
		
		// MENU MODIFICA
		menuModifica = new JMenu(GRLanguageMessage.messages.getString("menuedit"));
		menuBar.add(menuModifica);
		
		menuModificaAnnulla = new JMenuItem(GRLanguageMessage.messages.getString("menueditclear"));
		menuModificaAnnulla.setEnabled(false);
		menuModificaAnnulla.addActionListener(this);
		menuModificaCancella = new JMenuItem(GRLanguageMessage.messages.getString("menueditdelete"));
		menuModificaCancella.setEnabled(false);
		menuModificaCancella.addActionListener(this);
		menuModificaCopia = new JMenuItem(GRLanguageMessage.messages.getString("menueditcopy"));
		menuModificaCopia.setEnabled(false);
		menuModificaCopia.addActionListener(this);
		menuModificaIncolla = new JMenuItem(GRLanguageMessage.messages.getString("menueditpaste"));
		menuModificaIncolla.setEnabled(false);
		menuModificaIncolla.addActionListener(this);
		
		menuModifica.add(menuModificaAnnulla);
		menuModifica.add(menuModificaCancella);
		menuModifica.addSeparator();
		menuModifica.add(menuModificaCopia);
		menuModifica.add(menuModificaIncolla);
		
		// MENU STRUMENTI
		menuStrumenti = new JMenu(GRLanguageMessage.messages.getString("menutools"));
		menuBar.add(menuStrumenti);
		
		menuStrumentiGriglia = new JMenu(GRLanguageMessage.messages.getString("menutoolsgrid"));
		menuStrumenti.add(menuStrumentiGriglia);
		menuStrumentiGrigliaImpostaValori = new JMenuItem(GRLanguageMessage.messages.getString("menutoolsgridmanagevalue"));
		menuStrumentiGrigliaImpostaValori.addActionListener(this);
		menuStrumentiGriglia.add(menuStrumentiGrigliaImpostaValori);
		
		menuStrumenti.addSeparator();
		menuArchivioImmagini = new JMenuItem("Archivio immagini...");
		menuArchivioImmagini.addActionListener(this);
		menuArchivioImmagini.setEnabled(false);
		//menuStrumenti.add(menuArchivioImmagini);
		menuStrumentiStoryBook = new JMenuItem("Story Book...");
		menuStrumentiStoryBook.addActionListener(this);
		menuStrumenti.add(menuStrumentiStoryBook);
		
		menuStrumenti.addSeparator();
		menuStrumentiRaggruppa = new JMenuItem("Raggruppa");
		menuStrumentiRaggruppa.addActionListener(this);
		menuStrumenti.add(menuStrumentiRaggruppa);
		
		setJMenuBar(menuBar);
		
		panelToolBar = new JPanel();
		panelToolBar.setLayout(new GridLayout(2,1));
		
		grtoolbar = new GRToolBar(this);
		panelToolBar.add(grtoolbar);
				
		grtoolbarStrumenti = new GRToolBarStrumenti(this);
		grtoolbarStrumenti.setVisible(false);
		panelToolBar.add(grtoolbarStrumenti);
		
		c.add(panelToolBar,BorderLayout.NORTH);
		
		c.setFont(font);
		panelProperty = new GRTableProperty();
		
		desk = new JDesktopPane();
		
		grproject = new GRProject(this);
		splitProperty = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelProperty.getProperty(),null);
		//splitProperty = new JSplitPane(JSplitPane.VERTICAL_SPLIT,grproperty,null);
		splitProperty.setDividerLocation(300);
		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,splitProperty,desk);
		split.setDividerLocation(300);
		c.add(split,BorderLayout.CENTER);
		
		panelProperty.setSplit(splitProperty);
		
		/* Footer
		 * Contiene le info relative all'oogetto selezionato
		 */
		/*
		JPanel grfoot = new JPanel();
		grfoot.setPreferredSize(new Dimension(0,40));
		c.add(grfoot,BorderLayout.SOUTH);
		*/
		grfoot = new GRPanelFooter();
		c.add(grfoot, BorderLayout.SOUTH);
		
		setSize(800,600);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		this.init();
		
	}
	
	private void init() {
		resFont = null;
		resImg = null;
		
		this.newEnvironment();
	}
		
	public void setMenuVoiceEnabled(int typeMenu, int voiceMenu, boolean value) {
		switch(typeMenu) {
		case MENUTYPE_FILE:
			break;
			
		case MENUTYPE_MODIFICA:
			if(voiceMenu == 1) {
				
			} else if(voiceMenu == 2) {
				
			} else if(voiceMenu == 3) {
				// Copia
				menuModificaCopia.setEnabled(value);
			} else if(voiceMenu == 4) {
				// Incolla
				menuModificaIncolla.setEnabled(value);
			}
			break;
			
		case MENUTYPE_STRUMENTI:
			break;
		}
	}
	
	public void newEnvironment() {
		// Operazioni di pulizia dell'ambiente. Lo prepara per un nuovo lavoro
		grproject.clear();	// Svuota i progetti
		
		if(grtoolbarDesigner != null) {
			grtoolbar.remove(grtoolbarDesigner);
			grtoolbarDesigner = null;
		}
		grtoolbarStrumenti.setVisible(false);
		
		grtoolbar.activeButton(true, true, false, false, false);
		doc = null;
	}
	public void actionToolBar(int type) {
		grfoot.setObject(type);
		
		if(doc == null)
			return;
		doc.setAction(type);
	}
	public void actionToolBarStrumenti(int type, boolean value) {
		doc.settings(type, value);
	}
	public void setActionText(int value) {
		doc.setActionText(value);
	}
	public void actionToolBarStrumenti(int type) {
		switch(type) {
			case GRToolBarStrumenti.TYPEBUTTON_UNDO:
				this.undo();
				break;
				
			case GRToolBarStrumenti.TYPEBUTTON_SPOSTASOTTO:
				doc.backwardObject();
				break;
				
			case GRToolBarStrumenti.TYPEBUTTON_SPOSTASOPRA:
				doc.forwardObject();
				break;
		}
	}
	public GRTableProperty getTableProperty() {
		return panelProperty;
	}
	
	public GRToolBarDesigner getToolBarDesigner() {
		return grtoolbarDesigner;
	}
	public GRToolBarStrumenti getToolBarStrumenti() {
		return grtoolbarStrumenti;
	}
	
	private void clearSession() {
		pathDocumentSaved = null;
		nameDocumentSaved = null;
		this.setTitle("GlobalReports Editor");
		
		desk.removeAll();
		
		newEnvironment();
	}
	public void newDocumentDialog() {
		new GRDialogNewDocument(this);
	}
	public void newDocument(int width, int height) {
		
		if(doc != null) {
			int i = JOptionPane.showConfirmDialog(this, "Desideri salvare il documento attualmente aperto?","Nuovo documento",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			
			if(i == JOptionPane.CANCEL_OPTION)
				return;
			if(i == JOptionPane.OK_OPTION)
				saveProject(true,true);
			
			grproject.clear();
		}
		
		this.clearSession();
		
		if(grtoolbarDesigner == null) {
			grtoolbarDesigner = new GRToolBarDesigner(this);
			grtoolbar.add(grtoolbarDesigner);
		}
		grtoolbar.activeButton(true, true, true, true, true);
		grtoolbarStrumenti.setVisible(true);
		
		String nameDocument = "Pagina" + (grproject.getTotalePagine()+1);
		
		/* Crea il nuovo documento */
		doc = new GRDocument(this,nameDocument,width,height);
		//GRDocumentNew doc = new GRDocumentNew(this,nameDocument,210,297);
		
		/* Aggiunta del nuovo documento al progetto */
		grproject.addPage(doc);
		splitProperty.setBottomComponent(grproject);
		splitProperty.setDividerLocation(360);
		
		grproject.expand();
		
		desk.add(doc);
		try {
			doc.setMaximum(true);
		} catch(PropertyVetoException pve) {
			System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
		}
		
	}
	public void openDocument() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Global Reports Source","grs"));
		fc.setCurrentDirectory(new File("."));
		int r = fc.showOpenDialog(this);
		if(r == JFileChooser.APPROVE_OPTION) {
			openDoc(fc.getSelectedFile());
		}
		
	
	}
	public void openDoc(File f) {
		this.clearSession();
		
		grtoolbarDesigner = new GRToolBarDesigner(this);
		grtoolbar.add(grtoolbarDesigner);
		
		grproject.clear();
		
		pathDocumentSaved = f.getParent();
		nameDocumentSaved = f.getName();
		if(nameDocumentSaved.endsWith(".grs"))
			nameDocumentSaved = nameDocumentSaved.substring(0,nameDocumentSaved.length()-4);
		
		/* Decomprime il file GRS */
		String dirTemp = GRFile.decomprimi(new File(PATH_TEMP),f);
		if(dirTemp == null) {
			JOptionPane.showMessageDialog(this,"Si sono verificati problemi nell'apertura del file selezionato. Si prega di riprovare!","GREditor::openDoc::decomprimi is null",JOptionPane.ERROR_MESSAGE);
			
			System.out.println("Errore nella decompressione del file GRS");
			
			return;
		}
		
		path_dirTemp = PATH_TEMP+dirTemp+"//";
			
		try {
			/* Legge il file .main */
			this.readSource(new File(path_dirTemp+"grmain.xml"));
			
			/* Cicla per tutti le pagine presenti nel documento */
			for(int i = 0;i < grproject.getTotalePagine();i++) {
				doc = grproject.getDocument(i);
				this.readSource(new File(PATH_TEMP+dirTemp+"//"+grproject.getDocument(i).getNameDocument()+".xml"));
			
				desk.add(doc);
				doc.setMaximum(true);
			}
			
			splitProperty.setBottomComponent(grproject);
			splitProperty.setDividerLocation(360);
			
			grproject.expand();
			
			grtoolbarStrumenti.setVisible(true);
			grtoolbar.activeButton(true, true, true, true, true);
			
			this.setTitle(nameDocumentSaved + " - GlobalReports Editor");
			grproject.setNameProject(nameDocumentSaved);
			
		} catch(PropertyVetoException pve) {
			System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
		} catch(JDOMException jde) {
			JOptionPane.showMessageDialog(this,jde.getMessage(),"GREditor::openDocument::JDOMException",JOptionPane.ERROR_MESSAGE);
			
			grtoolbar.remove(grtoolbarDesigner);
							
		}
	}
	public void addPageDocument() {
		String nameDocument = "Pagina" + (grproject.getTotalePagine()+1);
		
		/* Creo il nuovo documento */
		doc = new GRDocument(this,nameDocument);
		
		/* Aggiungo un documento al progetto */
		grproject.addPage(doc);
		
		desk.add(doc);
		try {
			doc.setMaximum(true);
		} catch(PropertyVetoException pve) {
			System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
		}
		
		
	}
	public void addPageFromTemplate(File f) {
		/* Decomprime il file GRS */
		String dirTemp = GRFile.decomprimi(new File(PATH_TEMP),f);
		
		if(dirTemp == null) {
			JOptionPane.showMessageDialog(this,"Si sono verificati problemi nell'apertura del file selezionato. Si prega di riprovare!","GREditor::addPageFromTemplate::decomprimi is null",JOptionPane.ERROR_MESSAGE);
			
			System.out.println("Errore nella decompressione del file GRS");
			
			return;
		}
		
		path_dirTemp = PATH_TEMP+dirTemp+"//";
		
		int totPagineGiaPresenti = grproject.getTotalePagine();
		
		try {
			/* Legge il file .main */
			this.readSource(new File(path_dirTemp+"grmain.xml"));
			
			/* Cicla per tutti le pagine presenti nel documento */
			for(int i = (0 + totPagineGiaPresenti);i < grproject.getTotalePagine();i++) {
				doc = grproject.getDocument(i);
				
				this.readSource(new File(PATH_TEMP+dirTemp+"//"+grproject.getDocument(i).getOldNameDocument()+".xml"));
			
				desk.add(doc);
				doc.setMaximum(true);
			}
			/*
			splitProperty.setBottomComponent(grproject);
			splitProperty.setDividerLocation(360);
			
			grproject.expand();
			
			grtoolbarStrumenti.setVisible(true);
			grtoolbar.activeButton(true, true, true, true, true);
			*/
			
			
		} catch(PropertyVetoException pve) {
			System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
		} catch(JDOMException jde) {
			JOptionPane.showMessageDialog(this,jde.getMessage(),"GREditor::openDocument::JDOMException",JOptionPane.ERROR_MESSAGE);
			
			grtoolbar.remove(grtoolbarDesigner);
							
		}
	}
	public void addPageExists() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Global Reports Source","xml"));
		fc.setCurrentDirectory(new File("."));
		int r = fc.showOpenDialog(this);
		if(r == JFileChooser.APPROVE_OPTION) {
			
			try {
				this.readSource(fc.getSelectedFile());
				
				grproject.addPage(doc);
				
				//desk.add(doc);
				
				doc.setMaximum(true);
				
			} catch(PropertyVetoException pve) {
				System.out.println("GRDocument::init::PropertyVetoException: "+pve.getMessage());
			} catch(JDOMException jde) {
				JOptionPane.showMessageDialog(this,jde.getMessage(),"GREditor::addPageExists::JDOMException",JOptionPane.ERROR_MESSAGE);
								
			}
		}
	}
	
	
	public void saveProject(boolean allPage, boolean saveAs) {
		if(pathDocumentSaved == null || nameDocumentSaved == null || saveAs) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Global Reports Source","grs"));
			fc.setCurrentDirectory(new File("."));
			
			int r = fc.showSaveDialog(this);
			if(r == JFileChooser.APPROVE_OPTION) {
				nameDocumentSaved = fc.getSelectedFile().getName();
				
				if(nameDocumentSaved.endsWith(".grs"))
					nameDocumentSaved = nameDocumentSaved.substring(0,nameDocumentSaved.length()-4);
							
				pathDocumentSaved = fc.getSelectedFile().getParent();
							
				this.writeGRS(nameDocumentSaved,pathDocumentSaved, allPage);	
				
				this.setTitle(nameDocumentSaved+" - GlobalReports Editor");
			}
		} else {
			this.writeGRS(nameDocumentSaved,pathDocumentSaved, allPage);
			
			this.setTitle(nameDocumentSaved+" - GlobalReports Editor");
		}
		
		grproject.setNameProject(nameDocumentSaved);
	}
	public void setZoom(float value) {
		doc.setZoom(value);
	}
	private void functionDebug() {
		doc.functionDebug();
	}
	private void printWithXml() {
		if(doc.getTotaleObject() == 0) {
			this.printPDF(true,"");
		} else {
			Vector<String> variables = new Vector<String>();
			Vector<String> varList = new Vector<String>();
			
			// Cicla per tutti gli oggetti contenuti nel body della pagina
			for(int i = 0;i < doc.getTotaleObject();i++) {
				if(doc.getObject(i) instanceof GRText && doc.getObject(i).getListFather() == null) {
					
					GRText refText = (GRText)doc.getObject(i);
					Vector<String> var = refText.getVariables();
					
					for(int v = 0;v < var.size();v++) {
						if(!variables.contains(var.get(v)))
							variables.add(var.get(v));
					}
				}
						
			}
			
			// Cerca tutti gli oggetti GRList
			String nameList = null;
			for(int i = 0;i < doc.getTotaleObject();i++) {
				if(doc.getObject(i) instanceof GRList) {
					nameList = ((GRList)doc.getObject(i)).getNameXml();
					
					for(int x = 0;x < doc.getTotaleObject();x++) {
						if(doc.getObject(x) instanceof GRText && doc.getObject(x).hasListFather()) {
							GRText refText = (GRText)doc.getObject(x);
							
							if(refText.getListFather().getNameXml().equals(nameList)) {
								Vector<String> var = refText.getVariables();
								
								for(int v = 0;v < var.size();v++) {
									if(!varList.contains(var.get(v)))
										varList.add(var.get(v));
								}
							}
						}
					}
				}
			}
			
			if(variables.size() == 0 && varList.size() == 0) {
				this.printPDF(true, "");
			} else {
				String datiXml = new GRDialogAnteprimaXml(variables,nameList,varList).getXml();

				if(datiXml != null)
					this.printPDF(true,datiXml);
			}
		}
		
	}
	public void exportXmlDati() {
		
		String pathSave = "";
		
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("File XML Dati","xml"));
		fc.setCurrentDirectory(new File("."));
		
		int r = fc.showSaveDialog(this);
		if(r == JFileChooser.APPROVE_OPTION) {
			if(fc.getSelectedFile().toString().endsWith(".xml"))
				pathSave = fc.getSelectedFile().toString().substring(0,fc.getSelectedFile().toString().length()-4);
			else
				pathSave = fc.getSelectedFile().toString();
			
			this.writeXmlDati(pathSave);	
		}
		
	}
	private void writeXmlDati(String nameFile) {
		RandomAccessFile rOut;
		Vector<String> variables = new Vector<String>();
		
		try {
			File f = new File(nameFile+".xml");
			f.delete();
			
			rOut = new RandomAccessFile(nameFile+".xml","rw");
			
			rOut.writeBytes("<globalreports>\n");
			rOut.writeBytes("<data>\n");
			
			// Cicla per tutti gli oggetti contenuti nel body della pagina
			for(int i = 0;i < doc.getTotaleObject();i++) {
				if(doc.getObject(i) instanceof GRText) {
					GRText refText = (GRText)doc.getObject(i);
					Vector<String> var = refText.getVariables();
					
					for(int v = 0;v < var.size();v++) {
						if(!variables.contains(var.get(v)))
							variables.add(var.get(v));
					}
				}
						
			}
			
			for(int i = 0;i < variables.size();i++)
				rOut.writeBytes("<"+variables.get(i)+"/>\n");
				
			rOut.writeBytes("</data>\n");
			
			rOut.writeBytes("</globalreports>\n");
			
			rOut.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("GRDocument::writeDocument::FileNotFoundException: "+fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("GRDocument::writeDocument::IOException: "+ioe.getMessage());
		}
		
	}
	public void printPDF(boolean allDoc, String xml) {
		// Salva il file GRS fin qui creato nella cartella temporanea
		this.writeGRS("temp", GRSetting.PATHTEMP, allDoc);
		
		// Compila il file temporaneo appena creato
		GRCompiler grcomp = new GRCompiler(GRSetting.PATHTEMP+"temp.grs");
		try {
			grcomp.compilaNew(GRSetting.PATHTEMP+"temp.grb");
		} catch(GRCompFileCorruptedException fce) {
			JOptionPane.showMessageDialog(this,fce.getMessage(),"GREditor::printPDF::GRCompFileCorruptedException",JOptionPane.ERROR_MESSAGE);
			return;
		} catch(GRCompIOException ioe) {
			JOptionPane.showMessageDialog(this, ioe.getMessage(),"GREditor::printPDF::GRCompIOException",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Genera il file PDF
		GRPDF glob = new GRPDF();
		
		try {
			glob.setLayout(GRSetting.PATHTEMP+"temp.grb");
			
			glob.writePDF(GRSetting.PATHTEMP+"temp.pdf",xml);
		} catch(GRLayoutException le) {
			JOptionPane.showMessageDialog(this,le.getMessage(),"GREditor::printPDF::GRLayoutException",JOptionPane.ERROR_MESSAGE);
			
			return;
		} catch (GRPdfPathNotFoundException pnfe) {
			JOptionPane.showMessageDialog(this,pnfe.getMessage(),"GREditor::printPDF::GRPdfPathNotFoundException",JOptionPane.ERROR_MESSAGE);
			
			return;
		} catch (GRPdfIOWriteException iowe) {
			JOptionPane.showMessageDialog(this,iowe.getMessage(),"GREditor::printPDF::GRPdfIOWriteException",JOptionPane.ERROR_MESSAGE);
			
			return;
		} catch (GRValidateException ve) {
			JOptionPane.showMessageDialog(this,ve.getMessage(),"GREditor::printPDF::GRValidateException",JOptionPane.ERROR_MESSAGE);
			
			return;
		} 
				
		// Lancio il file per visualizzare l'anteprima
		try {
			java.awt.Desktop.getDesktop().open(new File(GRSetting.PATHTEMP+"temp.pdf"));
		} catch(Exception e) {}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menuFileNew) {
			this.newDocumentDialog();	
		} else if(e.getSource() == menuFileNewFromTemplate) {
			new GRDialogTemplate(this);
		} else if(e.getSource() == menuFileOpen) {
			this.openDocument();
		} else if(e.getSource() == menuFileAdd) {
			this.addPageDocument();
		} else if(e.getSource() == menuFileAddPageFromTemplate) {
			new GRDialogTemplate(this, GRDialogTemplate.TYPECALL_ADDPAGE);
		} else if(e.getSource() == menuFileSaveAs) {
			this.saveProject(true, true);
		} else if(e.getSource() == menuFileSaveSingle) {
			this.saveProject(false, true);
		} else if(e.getSource() == menuFileSaveProject) {
			this.saveProject(true,false);
		} else if(e.getSource() == menuFileAnteprimaPDF) {
			this.printPDF(false, "");
		} else if(e.getSource() == menuFileAnteprimaPDFDocument) {
			this.printPDF(true, "");
		} else if(e.getSource() == menuFileAnteprimaPDFXml) {
			this.printWithXml();
		} else if(e.getSource() == menuFileExportXml) {
			this.exportXmlDati();
		} else if(e.getSource() == menuFileDebug) {
			this.functionDebug();
		} else if(e.getSource() == menuModificaAnnulla) {
			this.undo();
		} else if(e.getSource() == menuModificaCancella) {
			doc.clearObject();
		} else if(e.getSource() == menuModificaCopia) {
			doc.copyObject();
		} else if(e.getSource() == menuModificaIncolla) {
			doc.pasteObject();
		} else if(e.getSource() == menuStrumentiGrigliaImpostaValori) {
			new GRDialogImpostaValoriGriglia(this, doc.getGapGrid());
		} else if(e.getSource() == menuArchivioImmagini) {
			new GRImageArchive();
		} else if(e.getSource() == menuStrumentiStoryBook) {
			new GRDialogStoryBook(doc);
		} else if(e.getSource() == menuStrumentiRaggruppa) {
			doc.raggruppaOggetti();
		}
	}
	public void setGapGrid(int x, int y) {
		doc.setGapGrid(x, y);
	}
	public void undo() {
		doc.undo();
	}
	public void setDocumentActive(GRDocument grdoc) {
		this.doc = grdoc;
		
	}
	
	public void manageMenu(int idVoce, boolean value) {
		this.manageMenu(idVoce, value, null);	
	}
	public void manageMenu(int idVoce, boolean value, String testo) {
		if(idVoce == GREditor.MENUVOICE_ANNULLA) {
			menuModificaAnnulla.setEnabled(value);
			grtoolbarStrumenti.setUndoEnabled(value);
			if(testo != null) {
				menuModificaAnnulla.setText(testo);
				grtoolbarStrumenti.setUndoToolTipText(testo);
			}
		} else if(idVoce == GREditor.MENUVOICE_CANCELLA) {
			menuModificaCancella.setEnabled(value);
			
		}
	}
	
	/* Elementi del documento */
	public String getFontIdResources(String fName, int fStyle) {
		if(resFont == null)
			resFont = new GRResFonts();
			
		return resFont.addResource(fName,fStyle);
	}
	public String getImgIdResources(GRImage grimg) {
		// Se il path non è stato censito viene inserito
		// Restituisce l'id dell'immagine
		if(resImg == null)
			resImg = new GRResImages();
			
		return resImg.addResource(grimg);
		
	}
	public GRResFonts getFontResources() {
		return resFont;
	}
	public GRResImages getImgResources() {
		return resImg;
	}
	public void removeImgResources(String id) {
		resImg.removeResource(id);
	}
	private void writeHeaderXml(RandomAccessFile raf) throws IOException {
		raf.writeBytes("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		raf.writeBytes("<globalreports edit=\""+GRAbout.EDIT_NAME+"\" version=\""+GRAbout.MAIOR_VERSION+"."+GRAbout.MINOR_VERSION+"\">\n");
	}
	public void writeGRS(String nameFile, String pathSave, boolean allPage) {
		RandomAccessFile rOut;
		String nameFileTemp = "GREDIT"+System.currentTimeMillis();
		//System.out.println(PATH_TEMP);
		try {
			File f = new File(pathSave+"//"+nameFile+".grs");	// Se il file GRS esiste lo cancella
			f.delete();
			
			/* Crea la struttura relativa al GRS, nella cartella temporanea, che andr� successivamente zippata */
			(new File(PATH_TEMP+"//"+nameFileTemp)).mkdir();
			(new File(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile)).mkdir();
			(new File(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile+"//images")).mkdir();
			
			/* Scrivo il main */
			rOut = new RandomAccessFile(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile+"//grmain.xml","rw");
			this.writeHeaderXml(rOut);	// Intestazione
			
			/* GRINFO */
			rOut.writeBytes("<grinfo>\n");
			rOut.writeBytes("<namedocument>"+nameFile+"</namedocument>\n");
			rOut.writeBytes("</grinfo>\n");
			
			/* GRRESOURCES */
			rOut.writeBytes("<grresources>\n");
			// Inserisce eventuali immagini
			if(resImg != null) {
				rOut.writeBytes("<images>\n");
				
				for(int i = 0;i < resImg.getTotaleResources();i++) {
					GRImageResource refResImage = resImg.getResource(i);
					
					/* Importa l'immagine all'interno della cartella images */
					GRFile.copyFile(new File(refResImage.getPath()), new File(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile+"//images//"+refResImage.getNameFile()));
					rOut.writeBytes("<image>\n");
					rOut.writeBytes("<id>"+refResImage.getId()+"</id>\n");
					rOut.writeBytes("<type>"+refResImage.getType()+"</type>\n");
					rOut.writeBytes("<path>"+refResImage.getNameFile()+"</path>\n");
					rOut.writeBytes("<originalwidth>"+refResImage.getWidth()+"</originalwidth>\n");
					rOut.writeBytes("<originalheight>"+refResImage.getHeight()+"</originalheight>\n");
					rOut.writeBytes("</image>\n");
				}
				
				rOut.writeBytes("</images>\n");
			}
			rOut.writeBytes("</grresources>\n");
		
			/* GRPAGES */
			rOut.writeBytes("<grpages>\n");
			// Inserisce le pagine
			if(!allPage) {
				rOut.writeBytes("<grpage>"+doc.getNameDocument()+"</grpage>\n");
			} else {
				for(int i = 0;i < grproject.getTotalePagine();i++) {
					rOut.writeBytes("<grpage>"+grproject.getDocument(i).getNameDocument()+"</grpage>\n");
				}
			}
			rOut.writeBytes("</grpages>\n");
					
			rOut.writeBytes("</globalreports>\n");
			rOut.close();
			
			/* Scrivo le pagine agganciate al documento */
			// Inserisce le pagine
			if(!allPage) {
				//rOut = new RandomAccessFile("temp//test//"+doc.getNameDocument()+".xml","rw");
				rOut = new RandomAccessFile(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile+"//"+doc.getNameDocument()+".xml","rw");
				this.writePage(rOut,doc);
				rOut.close();
				//return;
			} else {
				for(int i = 0;i < grproject.getTotalePagine();i++) {
					rOut = new RandomAccessFile(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile+"//"+grproject.getDocument(i).getNameDocument()+".xml","rw");
					this.writePage(rOut, grproject.getDocument(i));
					rOut.close();
				}
			}
			
			/* Comprime la cartella e crea il file GRS */
			GRFile.zipDir(new File(PATH_TEMP+"//"+nameFileTemp+"//"+nameFile), new File(pathSave+"//"+nameFile+".grs"));
			
			/* Cancella la struttura dalla cartella temporanea */
			GRFile.deleteDir(new File(PATH_TEMP+"//"+nameFileTemp));
			
		} catch(FileNotFoundException fnfe) {
			System.out.println("GRDocument::writeDocument::FileNotFoundException: "+fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("GRDocument::writeDocument::IOException: "+ioe.getMessage());
		}
	}
	public void writeLayout(String nameFile, boolean allDoc) {
		RandomAccessFile rOut;
		
		try {
			File f = new File(nameFile+".grs");	// Se il file GRS esiste lo cancella
			f.delete();
			
			/* Crea la struttura relativa al GRS che andr� successivamente zippata */
			(new File(nameFile)).mkdir();
			(new File(nameFile+"//images")).mkdir();
			
			rOut = new RandomAccessFile(nameFile+".xml","rw");
			this.writeHeaderXml(rOut);
			rOut.writeBytes("<grresources>\n");
			
			// Prima di procedere alla scrittura carica le risorse utilizzate
						
			// Inserisce eventuali immagini
			if(resImg != null) {
				rOut.writeBytes("<images>\n");
				
				for(int i = 0;i < resImg.getTotaleResources();i++) {
					GRImageResource refResImage = resImg.getResource(i);
					rOut.writeBytes("<image>\n");
					rOut.writeBytes("<id>"+refResImage.getId()+"</id>\n");
					rOut.writeBytes("<type>"+refResImage.getType()+"</type>\n");
					rOut.writeBytes("<path>"+refResImage.getPath()+"</path>\n");
					rOut.writeBytes("<originalwidth>"+refResImage.getWidth()+"</originalwidth>\n");
					rOut.writeBytes("<originalheight>"+refResImage.getHeight()+"</originalheight>\n");
					rOut.writeBytes("</image>\n");
				}
				
				rOut.writeBytes("</images>\n");
			}
			
			rOut.writeBytes("</grresources>\n");
			
			// Inserisce le pagine
			if(!allDoc)
				this.writePage(rOut,doc);
			else {
				for(int i = 0;i < grproject.getTotalePagine();i++) {
					this.writePage(rOut, grproject.getDocument(i));
				}
			}
			
			rOut.writeBytes("</globalreports>\n");
			
			rOut.close();
		} catch(FileNotFoundException fnfe) {
			System.out.println("GRDocument::writeDocument::FileNotFoundException: "+fnfe.getMessage());
		} catch(IOException ioe) {
			System.out.println("GRDocument::writeDocument::IOException: "+ioe.getMessage());
		}
	}
	public void writeLayout(String nameFile) {
		this.writeLayout(nameFile, false);	// Scrive solamente la pagina attualmente selezionata
	}
	private void writePage(RandomAccessFile rOut, GRDocument doc) throws IOException {
		int totObject;
		
		this.writeHeaderXml(rOut);	// Intestazione
		
		rOut.writeBytes("<page>\n");
		rOut.writeBytes("<typography>MM</typography>\n");
		rOut.writeBytes("<pagewidth>"+doc.getWidthPage()+"</pagewidth>\n");
		rOut.writeBytes("<pageheight>"+doc.getHeightPage()+"</pageheight>\n");
		
		// SEZIONI HEAD E FOOT
		if(doc.getHeaderSize() > 0) {
			rOut.writeBytes("<pageheader>"+doc.getHeaderSize()+"</pageheader>\n");
		}
		if(doc.getFooterSize() > 0) {
			rOut.writeBytes("<pagefooter>"+doc.getFooterSize()+"</pagefooter>\n");
		}
		
		/* Cicla per tutti gli oggetti.
		 * La scrittura prevede l'inserimento degli oggetti nell'ordine in cui
		 * sono stati disegnati. Fanno eccezione gli oggetti legati ad una
		 * eventuale lista. Questi NON vengono inseriti, sarà la lista che
		 * li inserirà nel suo codice
		 */
		// HEAD
		totObject = 0;
		for(int i = 0;i < doc.getTotaleObject();i++) {
			if(doc.getObject(i).getSection() == GRObject.SECTION_HEADER) {
				if(!doc.getObject(i).hasListFather()) {
					if(totObject == 0) {
						rOut.writeBytes("<grheader>\n");
					}
					
					rOut.writeBytes(doc.getObject(i).createCodeGRS()+"\n");
					
					totObject++;
				}
			}
		}
		if(totObject > 0)	// Chiude la section
			rOut.writeBytes("</grheader>\n");
		
		// BODY
		totObject = 0;
		for(int i = 0;i < doc.getTotaleObject();i++) {
			if(doc.getObject(i).getSection() == GRObject.SECTION_BODY) {
				if(!doc.getObject(i).hasListFather()) {
					if(totObject == 0) {
						rOut.writeBytes("<grbody>\n");
					}
					
					if(!doc.getObject(i).hasListFather()) {
						rOut.writeBytes(doc.getObject(i).createCodeGRS()+"\n");
					}
					totObject++;
				}
				
			}
		}
		if(totObject > 0)	// Chiude la section
			rOut.writeBytes("</grbody>\n");
		
		// FOOT
		totObject = 0;
		for(int i = 0;i < doc.getTotaleObject();i++) {
			if(doc.getObject(i).getSection() == GRObject.SECTION_FOOTER) {
				if(!doc.getObject(i).hasListFather()) {
					if(totObject == 0) {
						rOut.writeBytes("<grfooter>\n");
					}
					
					rOut.writeBytes(doc.getObject(i).createCodeGRS()+"\n");
					
					totObject++;
				}
			}
		}
		if(totObject > 0)	// Chiude la section
			rOut.writeBytes("</grfooter>\n");
				
		rOut.writeBytes("</page>\n");
		rOut.writeBytes("</globalreports>\n");
		
	}
	/* Apertura di un doc preesistente. XML */
	private boolean readSource(File grsource) throws JDOMException {
		try {
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(grsource);
				
			Element rootElement = document.getRootElement();
			//List<Element> children = rootElement.getChildren();
			List<Element> children = GRLibrary.castList(Element.class,rootElement.getChildren());	
			
			Iterator<Element> iterator = children.iterator();
			while (iterator.hasNext()){
				
				readChild((Element)iterator.next());
			} 
						
			return true;
		} catch(JDOMException jde) {
			System.out.println("GRManage::readSource::JDOMException: "+jde.getMessage());
			throw jde;
		} catch(IOException ioe) {
			JOptionPane.showMessageDialog(this,"File selezionato inesistente!","GREditor::readSource::IOException",JOptionPane.ERROR_MESSAGE);
			
			System.out.println("GREditor::readSource::IOException: "+ioe.getMessage());
		} 
		
		return false;
		
	}
	private void readMainInfo(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("namedocument")) {
				if(((Element)element.getParent()).getName().equals("grinfo")) {
					/* Imposta il nome dell'intero documento */
				}
			} 
		}
		
	}
	private void readMainFiles(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("grpage")) {
				if(((Element)element.getParent()).getName().equals("grpages")) {
					doc = new GRDocument(this,element.getValue());
					
					grproject.addPage(doc);
				}
			} 
		}
		
		System.out.println("FATTO");
	}
	private void readChild(Element element) {
		if(element.getName().equals("grinfo")) {
			readMainInfo(element);
		} else if(element.getName().equals("grresources")) {
			readResources(element);
		} else if(element.getName().equals("grpages")) {
			readMainFiles(element);
		} else if(element.getName().equals("page")) {
			doc.openPage(element);
		}
			
	}
	/*
	private void readChild(Element element) {
		List<Element> children = GRLibrary.castList(Element.class,element.getChildren());
		Iterator<Element> iterator = children.iterator();
		
		if(element.getName().equals("grresources")) {
			readResources(element);
		} else if(element.getName().equals("page")) {
			doc.openPage(element);
		}
			
		while (iterator.hasNext()){
			readChild(iterator.next());
		} 
	}
	*/
	private void readResources(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("images")) {
				readImages(element);
			} 
		}
		
	}
	
	private void readImage(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("id")) {
				if(((Element)element.getParent()).getName().equals("image")) {
					resImg.setImageId(element.getValue());
				}
			} else if(element.getName().equals("path")) {
				if(((Element)element.getParent()).getName().equals("image")) {
					resImg.setImagePath(path_dirTemp,element.getValue());
				}
			} else if(element.getName().equals("originalwidth")) {
				if(((Element)element.getParent()).getName().equals("image")) {
					resImg.setImageOriginalWidth(element.getValue());
				}
			} else if(element.getName().equals("originalheight")) {
				if(((Element)element.getParent()).getName().equals("image")) {
					resImg.setImageOriginalHeight(element.getValue());
				}
			}
		}
	}
	private void readImages(Element el) {
		List children = el.getChildren();
		Iterator iterator = children.iterator();
		
		while(iterator.hasNext()) {
			Element element = (Element)iterator.next();
			
			if(element.getName().equals("image")) {
				if(((Element)element.getParent()).getName().equals("images")) {
					
					if(resImg == null)
						resImg = new GRResImages();
						
					resImg.addResource();
					
					readImage(element);
				}
			}
		}
		
	}

	
}
