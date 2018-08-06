package net.techgy.ui.core.actions;

import java.util.Map;

import com.digitnexus.base.uidef.ActionDef;

public interface IAction {

	public static final int OK=0;
	
	public static final int FAIL=1;
	
	public String getId();

	public int execute(ActionDef ad,Map<String,Object> params);
}
