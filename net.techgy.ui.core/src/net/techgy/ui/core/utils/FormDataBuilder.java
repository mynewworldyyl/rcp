package net.techgy.ui.core.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;


public class FormDataBuilder {

	private int leftNumerator,  leftDenominator,  leftOffset;
	
	private int topNumerator,  topDenominator,  topOffset;
	
	private int bottomNumerator,  bottomDenominator,  bottomOffset;
	
	private int rightNumerator,  rightDenominator,  rightOffset;
	
	private Control leftControl, topControl, rightControl, bottomControl;
	
	private int leftAlignment, rightAlignment, topAlignment, bottomAlignment;
	
	private boolean left = false, right  = false,  top  = false,  bottom = false;
	
	private int width=SWT.DEFAULT, height=SWT.DEFAULT;
	
	public FormDataBuilder() {
		this(SWT.DEFAULT,SWT.DEFAULT);
	}
	
	public FormDataBuilder(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public static final FormDataBuilder builder(int width, int height) {
		return new FormDataBuilder(width,height);
	}
	
	public static final FormDataBuilder builder() {
		return new FormDataBuilder(SWT.DEFAULT,SWT.DEFAULT);
	}
	
	public FormDataBuilder left(int leftNumerator,  int leftDenominator,  int leftOffset) {
		left = true;
		this.leftDenominator = leftNumerator;
		this.leftDenominator = leftDenominator;
		this.leftOffset = leftOffset;
		return this;
	}
	
	public FormDataBuilder left(Control control, int offset, int alignment) {
		left = true;
		this.leftControl = control;
		this.leftAlignment = alignment;
		this.leftOffset = offset;
		return this;
	}
	
	public FormDataBuilder top(int topNumerator,  int topDenominator,  int topOffset) {
		top = true;
		this.topNumerator = topNumerator;
		this.topDenominator = topDenominator;
		this.topOffset = topOffset;
		return this;
	}
	
	public FormDataBuilder top(Control control, int offset, int alignment) {
		top = true;
		this.topControl = control;
		this.topAlignment = alignment;
		this.topOffset = offset;
		return this;
	}
	
	
	public FormDataBuilder  bottom(int bottomNumerator,  int bottomDenominator,  int bottomOffset) {
		bottom = true;
		this.bottomNumerator = bottomNumerator;
		this.bottomDenominator = bottomDenominator;
		this.bottomOffset = bottomOffset;
		return this;
	}
	
	public FormDataBuilder bottom(Control control, int offset, int alignment) {
		bottom = true;
		this.bottomControl = control;
		this.bottomAlignment = alignment;
		this.bottomOffset = offset;
		return this;
	}
	
	public FormDataBuilder right(int rightNumerator,  int rightDenominator,  int rightOffset) {
		right = true;
		this.rightNumerator = rightNumerator;
		this.rightDenominator = rightDenominator;
		this.rightOffset = rightOffset;
		return this;
	}
	
	public FormDataBuilder right(Control control, int offset, int alignment) {
		right = true;
		this.rightControl = control;
		this.rightAlignment = alignment;
		this.rightOffset = offset;
		return this;
	}
	
	public void apply(Control control) {
		FormData fd = new FormData(this.width, this.height);
		
		if(this.left) {
			if(null == this.leftControl) {
				fd.left = new FormAttachment(this.leftNumerator, this.leftDenominator, this.leftOffset);
			}else {
				fd.left = new FormAttachment(this.leftControl, this.leftOffset, this.leftAlignment);
			}
		}
		
		if(this.top) {
			if(null == this.topControl) {
				fd.top = new FormAttachment(this.topNumerator, this.topDenominator, this.topOffset);
			}else {
				fd.top = new FormAttachment(this.topControl, this.topOffset, this.topAlignment);
			}
		}
		
		if(this.right) {
			if(null == this.rightControl) {
				fd.right = new FormAttachment(this.rightNumerator, this.rightDenominator, this.rightOffset);
			}else {
				fd.right = new FormAttachment(this.rightControl, this.rightOffset, this.rightAlignment);
			}
		}
		
		if(this.bottom) {
			if(null == this.bottomControl) {
				fd.bottom = new FormAttachment(this.bottomNumerator, this.bottomDenominator, this.bottomOffset);
			}else {
				fd.bottom = new FormAttachment(this.bottomControl, this.bottomOffset, this.bottomAlignment);
			}
		}
	    
	    control.setLayoutData(fd);
		
	}
}
