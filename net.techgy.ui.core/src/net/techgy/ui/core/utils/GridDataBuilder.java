package net.techgy.ui.core.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;


public class GridDataBuilder {

	private int horizontalAlignment= GridData.BEGINNING,  verticalAlignment=GridData.CENTER,  
			horizontalSpan=1,  verticalSpan=1;
	public int horizontalIndent = 0;
	public int verticalIndent = 0;
	private boolean grabExcessHorizontalSpace = false,  grabExcessVerticalSpace = false;
	
	public int widthHint = SWT.DEFAULT;
	public int heightHint = SWT.DEFAULT;
	
	public int minimumWidth=0, minimumHeight=0;
	
	public boolean exclude=false;
	
	public GridDataBuilder() {
		this(SWT.DEFAULT,SWT.DEFAULT);
	}
	
	public GridDataBuilder(int width, int height) {
		this.widthHint = width;
		this.heightHint = height;
	}
	
	public static final GridDataBuilder builder(int width, int height) {
		return new GridDataBuilder(width,height);
	}
	
	public static final GridDataBuilder builder() {
		return new GridDataBuilder(SWT.DEFAULT,SWT.DEFAULT);
	}
	
	public GridDataBuilder alignment(int horizontalAlignment, int verticalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		return this;
	}
	
	public GridDataBuilder span(int horizontalSpan,  int verticalSpan) {
		this.horizontalSpan = horizontalSpan;
		this.verticalSpan = verticalSpan;
		return this;
	}
	
	public GridDataBuilder grab(boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace) {
		 this.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		 this.grabExcessVerticalSpace = grabExcessVerticalSpace;
		return this;
	}
	
	public GridDataBuilder min(int minimumWidth,int minimumHeight) {
		this.minimumWidth = minimumWidth;
		this.minimumHeight = minimumHeight;
		return this;
	}
	
	public GridDataBuilder exclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public void apply(Control control) {
		
		GridData fd = new GridData(this.widthHint, this.heightHint);
	    fd.grabExcessHorizontalSpace = this.grabExcessHorizontalSpace;
	    fd.grabExcessVerticalSpace = this.grabExcessVerticalSpace;
	    fd.horizontalSpan = this.horizontalSpan;
	    fd.verticalSpan = this.verticalSpan;
	    fd.horizontalAlignment = this.horizontalAlignment;
	    fd.verticalAlignment = this.verticalAlignment;
	    fd.minimumWidth = this.minimumWidth;
	    fd.minimumHeight = this.minimumHeight;
	    fd.exclude = this.exclude;
	    fd.heightHint = this.heightHint;
	    fd.widthHint = this.widthHint;
		
	    control.setLayoutData(fd);
	}
}
