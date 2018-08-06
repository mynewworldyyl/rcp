package com.digitnexus.core.dept;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class EmployeeDaoImpl extends BaseJpalDao<Employee,String>{

	public Employee getEmpByName(String clientId,String name) {
		Employee ct = null;
		String sql = "select a from Employee a where a.client.id='" + clientId + "' and a.name='" + name + "'";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (Employee)q.getSingleResult();
		return ct;
	}
	
	
	public List<Employee> queryEmp(Map<String,Object> params) {
		List<Employee>  ct = null;
		StringBuffer sql = new StringBuffer("SELECT a FROM Employee a WHERE a.client.id != '0' ");
		
		List<String> deptIds = (List<String>)params.get("deptIds");
		if(deptIds != null && !deptIds.isEmpty()) {
			sql.append(" AND a.belongDept.id IN :deptIds");
		}
		
		String name = (String)params.get("name");
		if(name != null && !"".equals(name.trim())) {
			sql.append(" AND a.name LIKE %'").append(name).append("%' ");
		}
		
		//admin 账号可以查看admin账号
		boolean isAdmin = (Boolean)params.get("isAdmin");
		if(!isAdmin) {
			sql.append(" AND a.typecode='").append(Employee.TYPE_COMMON).append("' ");
		}
		
		//a.client.id='" + clientId + "' and a.name='" + name + "'";
		Query q = this.getEntityManager().createQuery(sql.toString());
		
		if(deptIds != null && !deptIds.isEmpty()) {
			q.setParameter("deptIds", deptIds);
		}
		
		ct = q.getResultList();
		return ct;
	}
}
