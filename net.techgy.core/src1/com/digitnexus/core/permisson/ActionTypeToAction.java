package com.digitnexus.core.permisson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActionTypeToAction {

	public static final Map<PermActionType,Set<PermAction>> actionTypeToActions = 
			new  HashMap<PermActionType,Set<PermAction>>();
	
	static {
		Set<PermAction> air = new HashSet<PermAction>();
		air.add(PermAction.Create);
		air.add(PermAction.Delete);
		air.add(PermAction.Export);
		air.add(PermAction.Query);
		actionTypeToActions.put(PermActionType.AssetInRequest, air);
		
		
	}
}
