package com.digitnexus.core.db;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class BaseDao extends BaseJpalDao<Object,String>{

	public List<Object> query(Class cls, Map<String,String> params,String sql) {
		Object o = null;
		if(sql == null || "".equals(sql)) {
			StringBuffer jplStr = new StringBuffer("SELECT DISTINCT a FROM Namespace a LEFT JOIN FETCH a.attrs at WHERE ");		
			/*if(conStr != null && !"".equals(conStr.trim())) {
				jplStr.append("a.namespace LIKE '%").append(conStr).append("%'")
				.append(" OR at.name LIKE '%").append(conStr).append("%'");
			} else {
				jplStr.append("a.namespace LIKE '%' OR at.name LIKE '%'");
			}*/
			sql = jplStr.toString();
		}
		Query query = this.getEntityManager().createQuery(sql);
		o = query.getResultList();
		return (List<Object>)o;
	}
}
