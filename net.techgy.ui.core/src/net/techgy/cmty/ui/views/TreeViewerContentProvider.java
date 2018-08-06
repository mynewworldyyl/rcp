package net.techgy.cmty.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.digitnexus.base.uidef.menu.Menu;

public class TreeViewerContentProvider  implements IStructuredContentProvider, ITreeContentProvider
  {

    public void inputChanged( Viewer v, Object oldInput, Object newInput ) {
    	
    }

    public void dispose() {
    	
    }

    public Object[] getElements( Object parent ) {
      if(parent instanceof List) {
    	  return ((List)parent).toArray();
      }else if(parent instanceof Menu ) {
    	 Menu m = (Menu)parent;
         if(m.getSubMenus() != null && !m.getSubMenus().isEmpty()) {
        	 return m.getSubMenus().toArray();
         }else {
        	 return null;
         }
      } else {
     	 return null;
      }
    }

    public Object getParent( Object child ) {
    	if( child instanceof Menu ) {
    		return ((Menu)child).getParent();
    	} else {
        	return null;
         }
    }

    public Object[] getChildren( Object parent ) {   	
       return this.getElements(parent);
    }

    public boolean hasChildren( Object parent ) {
      return this.getElements(parent) != null;
    }
  }