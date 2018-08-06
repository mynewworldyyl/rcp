package net.techgy.ui.core.content;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CmtyContentProvider implements IStructuredContentProvider , ITreeContentProvider{
	
	
	public CmtyContentProvider() {
	}
	
	public Object[] getElements(Object inputElement) {
		 if(inputElement instanceof Collection) {
			return ((Collection)inputElement).toArray();
		}else {
			return new Object[0];
		}
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}