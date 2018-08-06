package net.techgy.ui.core.layout;

import org.eclipse.swt.SWT;

public class PlaceHoder {

	public int horizontalType = SWT.LEFT;
	public int verticalType = SWT.TOP;
	
	public int left=0;
	
	public int right=0;
	
	public int width=0;
	
	public int height=0;
	
	public int offsetLeft=0;
	
	public int offsetRight=0;
	
	public PlaceHoder relatedPlaceHoder;
	
	public PlaceHoder(int left,int right,int width,int height) {
		this.left = left;
		this.right = right;
		this.width = width;
		this.height = height;
	}
	
	public PlaceHoder(int left,int right,int width,int height,
			PlaceHoder relatedPlaceHoder) {
		this(left, right, width, height);
		this.relatedPlaceHoder = relatedPlaceHoder;
	}
	
	public PlaceHoder(int left,int right,int width,int height,
			PlaceHoder relatedPlaceHoder,int offsetLeft,int offsetRight) {
		this(left, right, width, height);
		this.relatedPlaceHoder = relatedPlaceHoder;
	}
}
