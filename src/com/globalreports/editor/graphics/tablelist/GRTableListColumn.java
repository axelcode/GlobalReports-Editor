package com.globalreports.editor.graphics.tablelist;

public class GRTableListColumn {
	private int index;
	private int left;
	private int width;
	private GRTableListSection grtablelistHeader;
	private GRTableListSection grtablelistBody;
	private GRTableListSection grtablelistFooter;
	
	public GRTableListColumn(int index,int left,int width) {
		this(index,left,width,null,null,null);
	}
	public GRTableListColumn(int index,int left,int width, GRTableListSection grtablelistH, GRTableListSection grtablelistB, GRTableListSection grtablelistF) {
		this.index = index;
		this.left = left;
		this.width = width;
		
		grtablelistHeader = grtablelistH;
		grtablelistBody = grtablelistB;
		grtablelistFooter = grtablelistF;
		
		if(grtablelistHeader != null)
			grtablelistHeader.addCell(width);
		if(grtablelistBody != null)
			grtablelistBody.addCell(width);
		if(grtablelistFooter != null)
			grtablelistFooter.addCell(width);
		
	}
	
	public void setLeft(int value) {
		this.left = value;
	}
	public int getLeft() {
		return left;
	}
	public void setWidth(int value) {
		int gap = value - this.width;
		this.width = value;
		
		if(grtablelistHeader != null) {
			/* 
			 * Cicla fino a che non trova un riferimento ad una cella.
			 * Se ritorna null è perchè la vecchia cella è stata mergiata a quella precedente
			 */
			GRTableListCell refCell = null;
			int i = index;
			while((refCell = grtablelistHeader.getCellColumn(i)) == null) {
				i--;
			}
			
			if(refCell != null) {
				int wCell = refCell.getWidth();
				
				refCell.setWidth(wCell + gap);
			}
		}	
		
		if(grtablelistBody != null) {
			/* 
			 * Cicla fino a che non trova un riferimento ad una cella.
			 * Se ritorna null è perchè la vecchia cella è stata mergiata a quella precedente
			 */
			GRTableListCell refCell = null;
			int i = index;
			while((refCell = grtablelistBody.getCellColumn(i)) == null) {
				i--;
			}
			
			if(refCell != null) {
				int wCell = refCell.getWidth();
				
				refCell.setWidth(wCell + gap);
			}
		}	
		
		if(grtablelistFooter != null) {
			/* 
			 * Cicla fino a che non trova un riferimento ad una cella.
			 * Se ritorna null è perchè la vecchia cella è stata mergiata a quella precedente
			 */
			GRTableListCell refCell = null;
			int i = index;
			while((refCell = grtablelistFooter.getCellColumn(i)) == null) {
				i--;
			}
			
			if(refCell != null) {
				int wCell = refCell.getWidth();
				
				refCell.setWidth(wCell + gap);
			}
		}	
	}
	public int getWidth() {
		return width;
	}
}
