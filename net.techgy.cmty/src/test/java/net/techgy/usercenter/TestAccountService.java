package net.techgy.usercenter;

import junit.framework.Assert;
import net.techgy.im.models.IAccount;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/app-*.xml" })
public class TestAccountService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	private LoginManager longinManager;
	
	private IAccount saveAccount() {
		AccountImpl account =  this.accountManager.createNewAccount();
		account.setAccountName("account2");
		account.setPassword("111111");
		this.accountManager.saveAccount(account);
		return account;
	}
	
	@Test
	@Transactional
	public void testRegister() {
		String result = accountService.register("Beck", "111");
		Assert.assertEquals(true, "success".equals(result));
	}
	
	@Test
	@Transactional
	public void testUnregister() {
		IAccount account = this.saveAccount();
		longinManager.addAccount(account);
		String result = accountService.unregister(account.getAccountName());
		Assert.assertEquals(true, "success".equals(result));
	}
	
	@Test
	@Transactional
	public void testLogin() {
		IAccount account = this.saveAccount();
		String result = accountService.login(account.getAccountName(),
				account.getPassword());
		Assert.assertEquals(true, "success".equals(result));
	}
	
	@Test
	@Transactional
	public void testLogout() {
		IAccount account = this.saveAccount();
		longinManager.addAccount(account);
		String result = accountService.logout(account.getAccountName());
		Assert.assertEquals(true, "success".equals(result));
	}
	
	
}
