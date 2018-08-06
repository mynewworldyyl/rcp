package com.digitnexus.core.dept;

import java.util.HashMap;
import java.util.Map;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.provider.DefaultValueProvider;

public class DefaultClientValueProvider implements DefaultValueProvider {

	@Override
	public Map<String,String> value() {
		Map<String,String> ps = new HashMap<String,String>();
		Client client = UserContext.getCurrentUser().getLoginClient();
		ps.put(client.getId(), client.getName());
		return ps;
	}

}
