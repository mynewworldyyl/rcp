package com.digitnexus.core.dept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class ClientKeyValueProvider extends AbstractValueProvider implements IKeyValueProvider {

	@Override
	public Map<String, String> keyValues() {
		ClientManager clientManager = SpringContext.getContext().getBean(ClientManager.class);
		List<Client> clients = clientManager.getSubClients(UserContext.getCurrentClientId());
		Map<String,String> keyValues = new HashMap<String,String>();
		for(Client c : clients) {
			keyValues.put(c.getId(), c.getName());
		}
		return keyValues;
	}

}
