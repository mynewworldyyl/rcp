package net.cmty.ui.core.editor;

import com.digitnexus.base.uidef.menu.Menu;

public class BaseDefEditorInput implements IEditorInput {

	private Menu menu = null;
	
	public BaseDefEditorInput(Menu m) {
		this.menu = m;
	}
	
	@Override
	public Object getModel() {
		return menu;
	}

	@Override
	public boolean equals(IEditorInput input) {
		if(!(input.getModel() instanceof Menu)) {
			return false;
		}
		if(menu == null && input.getModel() == null) {
			return true;
		}
		return this.getId().equals(((Menu)input.getModel()).getObjType());
	}

	@Override
	public String getId() {
		return menu.getObjType();
	}

	@Override
	public String getTitle() {
		return menu.getName();
	}

}
