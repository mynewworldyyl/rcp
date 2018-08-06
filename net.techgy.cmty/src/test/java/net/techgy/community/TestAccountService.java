package net.techgy.community;

import junit.framework.Assert;
import net.techgy.usercenter.AccountImpl;
import net.techgy.usercenter.AccountManager;
import net.techgy.usercenter.AccountService;
import net.techgy.usercenter.IAccountDao;

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
public class TestAccountService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private IAccountDao accountDao;
	
	@Autowired
	private AccountManager accountManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegister() {
		String result = accountService.register("Beck", "Beck");
		Assert.assertEquals("success", result);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testLogin() {
		AccountImpl account = this.accountManager.createNewAccount();
		account.setAccountName("Beck");
		account.setPassword("Beck");		
		try {
			accountDao.findAccountByName(account.getAccountName());
		} catch (Exception e) {
			accountDao.save(account);
		}
		accountService.login(account.getAccountName(), account.getPassword());
		String result = accountService.login("Beck", "Beck");
		Assert.assertEquals("success", result);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testLogout() {
		AccountImpl account = this.accountManager.createNewAccount();
		account.setAccountName("Beck");
		account.setPassword("Beck");		
		try {
			accountDao.findAccountByName(account.getAccountName());
		} catch (Exception e) {
			accountDao.save(account);
		}
		accountService.login(account.getAccountName(), account.getPassword());
		String result = accountService.login("Beck", "Beck");
		Assert.assertEquals("success", result);
		
		result = accountService.logout(account.getAccountName());
		Assert.assertEquals("success", result);
		
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUnregisger() {
		AccountImpl account = this.accountManager.createNewAccount();
		account.setAccountName("Beck");
		account.setPassword("Beck");		
		try {
			accountDao.findAccountByName(account.getAccountName());
		} catch (Exception e) {
			accountDao.save(account);
		}
		accountService.login(account.getAccountName(), account.getPassword());
		String result = accountService.login("Beck", "Beck");
		Assert.assertEquals("success", result);
		
	    result = accountService.unregister(account.getAccountName());
		Assert.assertEquals("success", result);
		
	}

	
}
