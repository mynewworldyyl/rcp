package com.digitnexus.core.dept;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class ClientDaoImpl extends BaseJpalDao<Client,String> implements IClientDao{

	public Client getClientByName(String name) {
		Client ct = null;
		String sql = "select a from Client a where a.name='" + name + "'";
		Query q = this.getEntityManager().createQuery(sql);
		ct = (Client)q.getSingleResult();
		return ct;
	}
	
	public Client getClient(String rootClientId) {
		String sql = "select a from Client a where a.id='" + rootClientId + "'";
		Query q = this.getEntityManager().createQuery(sql);
		Client ct = (Client)q.getSingleResult();
		return ct;
	}
	
	public Set<Client> getClients(Set<String> ids) {
		if(ids == null || ids.size() < 1) {
			return null;
		}
		StringBuffer sql = new StringBuffer("select a from Client a where a.id='");
		String firstId = ids.iterator().next();
		sql.append(ids.iterator().next()).append("'");
		ids.remove(firstId);
		for(String id : ids) {
			sql.append(" OR a.id='").append(id).append("'");
		}
		ids.add(firstId);
		Query q = this.getEntityManager().createQuery(sql.toString());
		List<Client> l = q.getResultList();
		Set<Client> set = new HashSet<Client>();
		set.addAll(l);
		return set;
	}
	
}
