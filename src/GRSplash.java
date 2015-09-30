/*
 * ==========================================================================
 * class name  : GRSplash
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
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.RandomAccessFile;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.globalreports.editor.GRAbout;
import com.globalreports.editor.GRSetting;
import com.globalreports.editor.configuration.font.GRFontProperty;
import com.globalreports.editor.configuration.template.GRTemplate;
import com.globalreports.editor.designer.GREditor;
import com.globalreports.editor.designer.dialog.GRDialogTemplate;

@SuppressWarnings("serial")
public class GRSplash extends JFrame {
	
	public static void main(String[] args) {
		//new GREditor();
		
		new GRSplash();
	}
	
	private GRSplash() {
		this.initGraphics();
		
		GRFontProperty.createFontProperty();
		caricaTemplate();
		try {
			Thread.sleep(1000);
		} catch(Exception e) {}
		
		new GREditor();
		this.dispose();
	}
	private void initGraphics() {
		GRLogo panelLogo = new GRLogo();
		
		Container c = getContentPane();
		c.setLayout(null);
		
		c.add(panelLogo);
				
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,300);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(new Point((dimension.width - this.getSize().width) / 2, 
	    					  (dimension.height - this.getSize().height) / 2 )); 
		setVisible(true);
	}
	
	private void caricaTemplate() {
		File dirRootTemplate = new File("resources//template//");
		File[] elementi = dirRootTemplate.listFiles();
		
		File file = null;
        String result = "";
        for (int i = 0; i < elementi.length; i++) {
            file = elementi[i];
            if (file.isDirectory()) {
            	// Directory contenente il template
                System.out.println(file.getName());
                
                /* Legge il file e carica le impostazioni */
                RandomAccessFile raf;
                	
                try {
                	raf = new RandomAccessFile(GRSetting.PATHTEMPLATE+file.getName()+"//grtemplate.grt","r");
                	
                	GRTemplate template = new GRTemplate(GRSetting.PATHTEMPLATE+file.getName());
                	
                	result = raf.readLine();
                	while(result != null) {
                		String[] chiave = result.split("=");
                		
                		if(chiave[0].equals("$title"))
                			template.setTitle(chiave[1]);
                		else if(chiave[0].equals("$description"))
                			template.setDescription(chiave[1]);
                		else if(chiave[1].equals("$grs"))
                			template.setNameTemplate(chiave[1]);
                		
                		result = raf.readLine();
                	}
                	
                	GRDialogTemplate.grtemplate.add(template);
                	
                	raf.close();
                } catch(Exception e) {
                	System.out.println("EXT");
                }
                
            } else {
            	//System.out.println(file.getAbsolutePath());
            }
        }
	}
}

@SuppressWarnings("serial")
class GRLogo extends JPanel {
	MediaTracker tracker;
	Image img;
	Image imgLicense;
	
	public GRLogo() {
		tracker = new MediaTracker(this);
		img = Toolkit.getDefaultToolkit().getImage(GRSetting.PATHIMAGE+"logoBLUE_2.png");
		imgLicense = Toolkit.getDefaultToolkit().getImage(GRSetting.PATHLICENSE+"licenza.png");
		
		tracker.addImage(img, 0);
		tracker.addImage(imgLicense, 1);
		
		try {
			tracker.waitForAll();
		} catch(InterruptedException e) {
			System.out.println("ERR");
		}
		
		setSize(500,300);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(img,0,0,this);
		g.setFont(new Font("Verdana",Font.PLAIN,10));
		g.setColor(Color.WHITE);
		g.drawString("Copyright "+new String(Character.toChars(169))+"2015",10,284);
		g.drawString("v. "+GRAbout.MAIOR_VERSION+"."+GRAbout.MINOR_VERSION, 460, 284);
		
		
		// Logo utilizzatore prodotto
		g.drawImage(imgLicense,0,214,this);
	}
}

