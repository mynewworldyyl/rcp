package com.digitnexus.core.dept;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class ProjectKeyValueProvider extends AbstractValueProvider implements IKeyValueProvider {

	@Override
	public Map<String, String> keyValues() {
		ClientManager clientManager = SpringContext.getContext().getBean(ClientManager.class);
		Set<Client> clients = clientManager.getProjects();
		Map<String,String> keyValues = new HashMap<String,String>();
		for(Client c : clients) {
			keyValues.put(c.getId(), c.getName());
		}
		return keyValues;
	}

}
