package net.techgy.ui.core.toolbar;

import java.util.List;

import net.techgy.ui.core.content.IProvider;

import com.digitnexus.base.uidef.ActionDef;

public interface IToolActionDefProvider extends IProvider{

	public static final String ID = IToolActionDefProvider.class.getName();
	
	List<ActionDef> getActionDefs();
	
}
