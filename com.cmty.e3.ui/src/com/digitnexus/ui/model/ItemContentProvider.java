package com.digitnexus.ui.model;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.digitnexus.base.uidef.FieldDef;

public class ItemContentProvider implements IStructuredContentProvider {
	
	private Object input = null;
	
	public ItemContentProvider() {
	}
	
	/**
	 * 返回元素数组
	 */
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof Object[]) {
			return (Object[])inputElement;
		}else if(inputElement instanceof Collection) {
			return ((Collection)inputElement).toArray();
		}else {
			return new Object[0];
		}
	}

	public void dispose() {
		this.input = null;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if(this.input == oldInput) {
        	return;
        }
        this.input = newInput;
	}

}