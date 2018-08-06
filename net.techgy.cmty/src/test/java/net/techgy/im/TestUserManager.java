package net.techgy.im;

import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.UserImpl;
import net.techgy.usercenter.AccountImpl;
import net.techgy.usercenter.AccountManager;

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
public class TestUserManager {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private AccountManager accountManager;
	
	@Test
	@Rollback(false)
	public void testPersist() {
	   AccountImpl account = accountManager.createNewAccount();
	   account.setAccountName("testPersist");
	   account.setPassword("testPersist");
	   this.accountManager.saveAccount(account);
	 
	   UserImpl user =(UserImpl) userManager.createUser();
	   user.setAccount(account);
	   this.userManager.saveNewUser(user);
	}
}
