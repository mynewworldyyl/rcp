package com.digitnexus.core.account;

import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.db.BaseJpalDao;

@Component
public class AccountDaoImpl extends BaseJpalDao<Account,String> implements IAccountDao{

	public Account getActiveAccountByUsernameAndPassword(String un,String pw) {
		//"SELECT DISTINCT a FROM Namespace a LEFT JOIN FETCH a.attrs at WHERE "
		String sql = "SELECT a FROM Account a LEFT JOIN FETCH a.groups g  LEFT JOIN FETCH a.permissons p "
				+ " WHERE a.statu='Active' AND a.accountName= '"+ un + "' AND a.password='" +pw +"' ";
		//sql = this.appendClientId(sql, cid);
		Query query = this.getEntityManager().createQuery(sql);
		Account a = (Account)query.getSingleResult();
		return a;
	}
	
	public Account getActiveAccountByAccountName(String un,String clientId) {
		//"SELECT DISTINCT a FROM Namespace a LEFT JOIN FETCH a.attrs at WHERE "
		String sql = "SELECT a FROM Account a "
				+ " LEFT JOIN FETCH a.groups g  "
				+ " LEFT JOIN FETCH a.permissons p "
				+ " JOIN FETCH a.relatedClients rc "
				+ " WHERE a.statu='Active' "
				+ " AND a.accountName= '"+ un + "' "
				+ "  AND rc.id='" + clientId + "' "
				;
		//sql = this.appendClientId(sql, clientId);
		Query query = this.getEntityManager().createQuery(sql);
		Account a = (Account)query.getSingleResult();
		return a;
	}
	
	public Account getAccountByAccountName(String un) {
		//"SELECT DISTINCT a FROM Namespace a LEFT JOIN FETCH a.attrs at WHERE "
		String sql = "SELECT a FROM Account a LEFT JOIN FETCH a.groups g  LEFT JOIN FETCH a.permissons p "
				+ " WHERE a.accountName= '"+ un + "' ";
		//sql = this.appendClientId(sql, cid);
		Query query = this.getEntityManager().createQuery(sql);
		Account a = (Account)query.getSingleResult();
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> getAccountByAccountIds(Set<String> ids) {
		String sql = "Select a from Account a WHERE a.id IN :ids";
		Query query = this.getEntityManager().createQuery(sql);
		query.setParameter("ids", ids);
		List<Account> l = query.getResultList();
		return l;
	}
	
	private String appendClientId(String sql , String cid) {
		sql = sql + " AND g.client.id='" + cid + "' "
				  +" AND p.client.id='" + cid + "' "
				  +" AND rc.id='" + cid + "' ";
		return sql;
	}
	
	
}
