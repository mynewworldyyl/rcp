package net.techgy.im;

import net.techgy.im.dao.IGroupDao;
import net.techgy.im.dao.IUserDao;
import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.UserImpl;
import net.techgy.usercenter.AccountImpl;
import net.techgy.usercenter.AccountManager;
import net.techgy.usercenter.IRole.RoleType;

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
public class TestGroupDao {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IGroupDao groupDao;

	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	IUserManager userManager;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSaveGroup() {
		AccountImpl account = this.getAccount();
		AccountImpl account1 = null;
		try {
			account1 = (AccountImpl) this.accountManager.findAccountByName(account.getAccountName());
			account = account1;
		} catch (Exception e) {
			this.accountManager.saveAccount(account);
		}
		UserImpl user = (UserImpl)userManager.createUser();
		user.setAccount(account);
		this.userDao.save(user);
		
		GroupImpl g = this.getGroup();
		g.setOwner(user);
		user.getFriendGroups().add(g);
		
		this.groupDao.save(g);
	}
	
	private AccountImpl getAccount() {
		AccountImpl a = this.accountManager.createNewAccount();
		a.setAccountName("testAccount2");
		a.setPassword("111");
		return a;
	}
	
	private UserImpl getUser() {
		UserImpl user = (UserImpl)userManager.createUser();
		user.setName("Beck");
		user.setType(RoleType.IM);
		return user;
	}
	private GroupImpl getGroup() {
		GroupImpl g = new GroupImpl();
		g.setDesc("my good friend");
		g.setName("good friend");
		return g;
	}
}
