package com.digitnexus.core.masterdata;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitnexus.core.db.BaseDao;
import com.digitnexus.core.osgiservice.impl.SpringContext;
import com.digitnexus.core.provider.AbstractValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;

public class ProjectKeyValueProvider extends AbstractValueProvider implements IKeyValueProvider {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> keyValues() {
		BaseDao baseDao = SpringContext.getContext().getBean(BaseDao.class);
		List<Project> l = baseDao.getEntityManager().createNamedQuery("getProjects")
				.getResultList();
		if(l == null || l.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String,String> keyValues = new HashMap<String,String>();
		for(Project c : l) {
			keyValues.put(c.getId(), c.getName());
		}
		return keyValues;
	
	}

}
