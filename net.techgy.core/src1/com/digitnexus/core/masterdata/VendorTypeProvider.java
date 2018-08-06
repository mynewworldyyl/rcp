package com.digitnexus.core.masterdata;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitnexus.core.db.BaseDao;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class VendorTypeProvider extends AbstractValueProvider implements IKeyValueProvider {

	@Override
	public Map<String, String> keyValues() {
		
		BaseDao baseDao = SpringContext.getContext().getBean(BaseDao.class);
		StringBuffer sb = new StringBuffer("SELECT DISTINCT a FROM CommonValue a WHERE a.typecode='");
		sb.append(CommonValue.VENDOR_TYPE).append("'");
		List<CommonValue> l = baseDao.getEntityManager().createQuery(sb.toString()).getResultList();
		if(l == null || l.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String,String> keyValues = new HashMap<String,String>();
		for(CommonValue c : l) {
			keyValues.put(c.getId(), c.getName());
		}
		return keyValues;
	}

}