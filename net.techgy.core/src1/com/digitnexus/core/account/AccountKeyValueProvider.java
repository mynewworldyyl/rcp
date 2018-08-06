package com.digitnexus.core.account;

import java.util.Map;

import com.digitnexus.core.osgiservice.impl.ClientServiceImpl;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class AccountKeyValueProvider  extends AbstractValueProvider implements IKeyValueProvider {

	@Override
	public Map<String, String> keyValues() {
		AccountManager am = SpringContext.getContext().getBean(AccountManager.class);
		Map<String,String> keyValues = am.keyValues();
		return keyValues;
	}

}