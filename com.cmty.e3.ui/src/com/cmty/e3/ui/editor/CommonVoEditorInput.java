package com.cmty.e3.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.digitnexus.base.uidef.menu.Menu;

public class CommonVoEditorInput implements IEditorInput{

	private Menu menu;
	
	public CommonVoEditorInput(Menu m) {
		this.menu = m;
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return menu.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		
		return null;
	}

	@Override
	public String getToolTipText() {
		return this.getName();
	}

	@Override
	public int hashCode() {
		return this.menu.getObjType().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CommonVoEditorInput){
			CommonVoEditorInput newObj  = (CommonVoEditorInput)obj;
			return this.menu.getObjType().equals(newObj.menu.getObjType());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.menu.getName();
	}

	public Menu getMenu() {
		return menu;
	}

	
}
