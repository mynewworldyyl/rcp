package com.digitnexus.core.uidef.demo;

import java.util.HashMap;
import java.util.Map;

import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class TestKeyValueProvider  extends AbstractValueProvider  implements IKeyValueProvider{

	@Override
	public Map<String, String> keyValues() {
		Map<String,String> kvs = new HashMap<String,String>();
		kvs.put("1", "value1");
		kvs.put("2", "value2");
		kvs.put("3", "value3");
		return kvs;
	}

	
}
