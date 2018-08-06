package net.techgy.cmty.ui.cl;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.digitnexus.base.uidef.TreeNode;

public class AttrContentProvider implements IStructuredContentProvider, ITreeContentProvider{

	private TreeViewer viewer = null;
	
	public AttrContentProvider(TreeViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void dispose() {
		viewer = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		System.out.println("inputChanged");
		
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return this.getElements(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof TreeNode) {
			 TreeNode g = (TreeNode)element;
			return g.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof TreeNode) {
			TreeNode g = (TreeNode)element;
			return g.getChildren() != null && !g.getChildren().isEmpty();
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] vs = new Object[0];
		if(inputElement instanceof TreeNode) {
			 TreeNode g = (TreeNode)inputElement;
			 if(g.getChildren() != null) {
				 return g.getChildren().toArray();
			 }
		}else if(inputElement instanceof Object[]) {
			return (Object[])inputElement;
		}
		return vs;
	}
	
}
