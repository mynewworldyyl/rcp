package com.digitnexus.core.dept;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class DeptDaoImpl extends BaseJpalDao<Department,String>{

	public Department getDeptByName(String clientId,String name) {
		Department ct = null;
		String sql = "select a from Department a where a.client.id='" + clientId + "' and a.deptName='" + name + "'";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (Department)q.getSingleResult();
		return ct;
	}
	
	public Department getRoot() {
		Department ct = null;
		String sql = "SELECT a FROM Department a WHERE a.client.id != '0' AND a.parent=null";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (Department)q.getSingleResult();
		return ct;
	}
	
}
