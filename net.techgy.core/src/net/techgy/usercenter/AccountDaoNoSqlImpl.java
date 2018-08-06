package net.techgy.usercenter;

import net.techgy.BaseNoSqlDao;
import net.techgy.im.models.IAccount;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class AccountDaoNoSqlImpl extends BaseNoSqlDao/* implements IAccountDao*/{

	
	public IAccount findAccountIdById(String accountId) {
		Query query = new Query(Criteria.where("accountId").is(accountId));
		return this.getTemplate().findOne(query, IAccount.class);
	}
	
    public IAccount findAccountByname(String accountName) {
    	Query query = new Query(Criteria.where("accountName").is(accountName));
		return this.getTemplate().findOne(query, IAccount.class);
	}
    
    public void save(IAccount account) {
	  this.getTemplate().save(account);
	}
    
    public void remove(IAccount account) {
  	  this.getTemplate().remove(account);
  	}
    
    public void removeAccountByName(String accountName) {
    	Query query = new Query(Criteria.where("accountName").is(accountName));
    	this.getTemplate().remove(query, IAccount.class);
    }
    public void removeAccountById(String accountId) {
    	Query query = new Query(Criteria.where("accountId").is(accountId));
    	this.getTemplate().remove(query, IAccount.class);
    }
}
