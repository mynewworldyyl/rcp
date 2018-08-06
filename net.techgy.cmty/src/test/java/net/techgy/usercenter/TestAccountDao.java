package net.techgy.usercenter;

import junit.framework.Assert;
import net.techgy.im.models.IAccount;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestAccountDao {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IAccountDao acountDao;

	@Autowired
	private AccountManager accountManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void doPersist() {
		AccountImpl account = this.accountManager.createNewAccount();
		account.setAccountName("account2");
		account.setPassword("111111");
		this.acountDao.save(account);
	}
	
	private IAccount saveAccount() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account2");
		account.setPassword("111111");
		this.acountDao.save(account);
		return account;
	}

	@Test
	@Transactional
	@Rollback(true)
	public void doQeury() {
		IAccount a = saveAccount();
		IAccount account = this.acountDao.findAccountByName(a.getAccountName());
		this.logger.debug(account.getAccountName());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void doQeuryById() {
		IAccount a = saveAccount();
		IAccount account = this.acountDao.findAccountIdById(a.getId());
		this.logger.debug(account.getAccountName());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void doRemoveEntity() {
		IAccount a = saveAccount();
        this.acountDao.removeAccount(a);
        
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void doRemoveEntityByName() {
		IAccount a = saveAccount();
        this.acountDao.removeAccountByName(a.getAccountName());
        
	}
	@Test
	@Transactional
	@Rollback(true)
	public void doRemoveEntityById() {
		IAccount a = saveAccount();
        this.acountDao.removeAccountById(a.getId());
        
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void doRemoveEntityByName1() {
        IAccount a = this.acountDao.findAccountByName("test2");
        Assert.assertNotNull(a);
        
	}

}
