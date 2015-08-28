/*
 * ==========================================================================
 * class name  : com.globalreports.editor.designer.GRUndo
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

import com.globalreports.editor.graphics.GRImage;
import com.globalreports.editor.graphics.GRObject;
import com.globalreports.editor.graphics.GRRectangle;
import com.globalreports.editor.graphics.GRText;

public class GRUndo {
	public static final int ACTION_CLEAROBJ		= 1;
	public static final int ACTION_INSERTOBJ	= 2;
	public static final int ACTION_BACKWARDOBJ	= 3;
	public static final int ACTION_FORWARDOBJ	= 4;
	
	public static final int UNDO_INSERTOBJ		= 1;
	public static final int UNDO_CLEAROBJ		= 2;
	public static final int UNDO_BACKWARDOBJ	= 3;
	public static final int UNDO_FORWARDOBJ		= 4;
	
	private GRObject grobj;	
	private int idObj;
	private int action;
	private int actionUndo;
	
	public GRUndo(GRObject grobj, int id, int act) {
		this.grobj = grobj;
		idObj = id;
		action = act;
		
		this.setActionUndo();
	}
	
	private void setActionUndo() {
		switch(action) {
			case GRUndo.ACTION_CLEAROBJ:
				actionUndo = GRUndo.UNDO_CLEAROBJ;
				break;
			
			case GRUndo.ACTION_INSERTOBJ:
				actionUndo = GRUndo.UNDO_INSERTOBJ;
				break;
				
			case GRUndo.ACTION_BACKWARDOBJ:
				actionUndo = GRUndo.UNDO_BACKWARDOBJ;
				break;
				
			case GRUndo.ACTION_FORWARDOBJ:
				actionUndo = GRUndo.UNDO_FORWARDOBJ;
				break;
					
		}
	}
	public GRObject getObject() {
		return grobj;
	}
	public int getLastId() {
		return idObj;
	}
	public int getActionUndo() {
		return actionUndo;
	}
	public String getActionUndoToString() {
		String ritorno = "";
		
		switch(action) {
			case GRUndo.ACTION_CLEAROBJ:
				ritorno = "Annulla cancellazione ";
				break;
				
			case GRUndo.ACTION_BACKWARDOBJ:
				ritorno = "Ripristina livello originale ";
				
			case GRUndo.ACTION_FORWARDOBJ:
				ritorno = "Ripristina livello originale ";
		}
		
		if(grobj instanceof GRText)
			ritorno = ritorno + "testo";
		else if(grobj instanceof GRImage) 
			ritorno = ritorno + "immagine";
		else if(grobj instanceof GRRectangle)
			ritorno = ritorno + "rettangolo";
		
		return ritorno;
	}
}
