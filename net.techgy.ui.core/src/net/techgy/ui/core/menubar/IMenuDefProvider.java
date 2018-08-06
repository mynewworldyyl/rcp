package net.techgy.ui.core.menubar;

import java.util.List;

import net.techgy.ui.core.content.IProvider;
import net.techgy.ui.core.toolbar.IToolActionDefProvider;

import com.digitnexus.base.uidef.menu.MenuActionDef;

public interface IMenuDefProvider extends IProvider{

	public static final String ID = IMenuDefProvider.class.getName();
	
	List<MenuActionDef> getMenuDefs();
	
}
