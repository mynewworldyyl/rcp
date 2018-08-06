package net.techgy.usercenter;

import net.techgy.im.models.IAccount;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestAccountManager {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountManager accountManager;

	@Test
	public void testSaveAccount() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account3");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
	}

	@Test
	public void doQeury() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account3");
		account.setPassword("111111");
		accountManager.saveAccount(account);
		IAccount account2 = this.accountManager.findAccountByName(account.getAccountName());
		Assert.notNull(account2);
	}
	
	@Test
	public void doQeuryById() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account233");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
		IAccount account1 = this.accountManager.findAccountById(account.getId());
		Assert.notNull(account1);
	}
	
	@Test
	public void doRemoveEntity() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account233");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
        this.accountManager.remove(account);
        
	}
	
	@Test
	public void doRemoveEntityByName() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account2");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
        this.accountManager.removeAccountByName(account.getAccountName());
        
	}
	@Test
	public void doRemoveEntityById() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account2");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
        this.accountManager.removeByAccountId(account.getId());
	}

}
