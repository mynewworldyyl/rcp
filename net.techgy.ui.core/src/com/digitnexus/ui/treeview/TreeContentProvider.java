package com.digitnexus.ui.treeview;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TreeContentProvider implements ITreeContentProvider{

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof Object[]) {
			return (Object[])inputElement;
		}else if(inputElement instanceof Collection) {
			return ((Collection)inputElement).toArray();
		}else {
			return new Object[0];
		}	
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(!(parentElement instanceof TreeNode) || ((TreeNode)parentElement).getChildren().isEmpty()) {
			return new Object[0];
		}
		return ((TreeNode)parentElement).getChildren().toArray();
	}

	@Override
	public Object getParent(Object element) {
		if(!(element instanceof TreeNode)) {
			return null;
		}
		return ((TreeNode)element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		Object[] a = getChildren(element);
		return a != null && a.length > 0;
	}

}
