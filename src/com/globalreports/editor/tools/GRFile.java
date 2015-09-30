/*
 * ==========================================================================
 * class name  : com.globalreports.editor.tools.GRFile
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
package com.globalreports.editor.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class GRFile {
	public static String decomprimi(File directory, File fileGRS) throws IllegalArgumentException {
		/* Restituisce il nome della cartella temporanea ove è stato decompresso il file */
		String nameFolderTemp;
		
		/* Procede a decomprimere il file GRS */
		boolean success = false;
		
		nameFolderTemp = "GR"+System.currentTimeMillis();
		
		// qualche controllo
	    if (!fileGRS.exists()) // se il file zip non esiste
	    	throw new IllegalArgumentException("UnzipFile: file o directory di origine inestistente: " + fileGRS.getName());

	    if (!directory.exists()) // se la directory di destinazione non esiste
	         throw new IllegalArgumentException("UnzipFile: file o directory di destinazione inesistente: " + directory.getAbsolutePath());

	    if (!directory.isDirectory()) // se non è una directory
	         throw new IllegalArgumentException("UnzipFile: " + directory.getAbsolutePath() + " non è una directory");

	    ZipInputStream zis = null;
	     System.out.println(directory+"//"+nameFolderTemp);
	    try {
	    	 // Crea la cartella temporanea
	    	 success = (new File(directory+"//"+nameFolderTemp)).mkdir();
	    	 if(!success)
	    		 return null;
	   
	    	 success = (new File(directory+"//"+nameFolderTemp+"//images")).mkdir();
	    	 if(!success)
	    		 return null;
	    	 
	         FileInputStream fis = new FileInputStream(fileGRS);
	         zis = new ZipInputStream(new BufferedInputStream(fis));
	         ZipEntry entry;

	         //scorre la lista dei file nell'archivio zip
	         while((entry = zis.getNextEntry()) != null) {
	            String path = directory + "/" + nameFolderTemp + "//" + entry.getName(); // path del file estratto
	       
	            File file = new File(path);
	           
	            if(!(file.getName()).equals("images")) {
	            	if(file.exists()) // se un file uguale esiste già
	            		throw new IllegalArgumentException("UnzipFile: esiste un file con lo stesso nome nella directory: " + file);

	            	FileOutputStream fos = new FileOutputStream(path);
	            	BufferedOutputStream dest = new BufferedOutputStream(fos, 1024);

	            	//legge il file da estrarre
	            	int cont;
	            	byte data[] = new byte[1024];
	            	while((cont = zis.read(data, 0, 1024)) != -1)
	            		dest.write(data, 0, cont); // scrive il file nella directory di destinazione

	            	dest.flush();
	            	dest.close(); // chiude il file in scrittura
	            }
	         }

	         return nameFolderTemp;

	      } catch (FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch (IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         try {
	            if(zis != null)
	               zis.close(); // chiude l'archivio zip in lettura
	         } catch (IOException ioe) {
	            ioe.printStackTrace();
	         }
	      }

	      // qualora ci fosse qualche malfunzionamento
	      return null;
 
	}
	public static void zipDir(File directory, File zipfile) throws IOException {
		URI base = directory.toURI();
		Deque<File> queue = new LinkedList<File>();
		queue.push(directory);
		OutputStream out = new FileOutputStream(zipfile);
		Closeable res = out;
		try {
		ZipOutputStream zout = new ZipOutputStream(out);
		res = zout;
		while (!queue.isEmpty()) {
			directory = queue.pop();
			for (File kid : directory.listFiles()) {
			  String name = base.relativize(kid.toURI()).getPath();
			  if (kid.isDirectory()) {
			    queue.push(kid);
				name = name.endsWith("/") ? name : name + "/";
				zout.putNextEntry(new ZipEntry(name));
			  } else {
				zout.putNextEntry(new ZipEntry(name));
				copy(kid, zout);
				zout.closeEntry();
			  }
			}
		}
		} finally {
			res.close();
		}
	}
	public static void copyFile(File fin, File fout) throws IOException {
		InputStream iStream = new BufferedInputStream(new FileInputStream(fin));
		OutputStream oStream = new BufferedOutputStream(new FileOutputStream(fout));
		
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = iStream.read(buffer);
			
			if (readCount < 0) {
				break;
			}
				
			oStream.write(buffer, 0, readCount);
		}
		
		iStream.close();
		oStream.close();
		
		
	}
	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			
			if (readCount < 0) {
				break;
			}
				out.write(buffer, 0, readCount);
		}
	}
	private static void copy(File file, OutputStream out) throws IOException {
		InputStream in = new FileInputStream(file);
		try {
			copy(in, out);
		} finally {
			in.close();
		}
	}	
	
	public static boolean deleteDir(File path) {
		if(path.exists()) {
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDir(files[i]);
                }
                else {
                    files[i].delete();
                }
			}
		}
    
		return(path.delete());
	} 
	
}
