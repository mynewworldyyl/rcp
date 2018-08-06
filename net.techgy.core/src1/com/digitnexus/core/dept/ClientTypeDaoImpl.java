package com.digitnexus.core.dept;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class ClientTypeDaoImpl extends BaseJpalDao<ClientType,String>{

	public ClientType getClientTypeByTypeCode(String typecode) {
		ClientType ct = null;
		String sql = "select a from ClientType a where a.typeCode='" + typecode + "'";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (ClientType)q.getSingleResult();
		return ct;
	}
}
