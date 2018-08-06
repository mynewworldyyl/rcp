package net.techgy.usercenter;

import net.techgy.im.models.IAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


public class AccountManager {

	@Autowired
	private IAccountDao accountDao;
	
	@Transactional
	@Rollback(false)
	public void saveAccount(IAccount user) {
		 this.accountDao.save(user);
	}
	
	@Transactional
	@Rollback(false)
	public IAccount findAccountById(long accountId) {
		return accountDao.findAccountIdById(accountId);
	}
	
	@Transactional
	@Rollback(false)
    public IAccount findAccountByName(String accountName) {
    	return this.accountDao.findAccountByName(accountName);
	}
	
	@Transactional
	@Rollback(false)
    public void remove(IAccount user) {
  	   this.accountDao.removeAccount(user);
  	}
	
	@Transactional
	@Rollback(false)
    public void removeAccountByName(String accountname) {
   	   this.accountDao.removeAccountByName(accountname);
   	}
	
	@Transactional
	@Rollback(false)
    public void removeByAccountId(long accountId) {
    	   this.accountDao.removeAccountById(accountId);
    }
	
	public AccountImpl createNewAccount() {
		AccountImpl account = new AccountImpl();
		
		return account;
 }
	
}
