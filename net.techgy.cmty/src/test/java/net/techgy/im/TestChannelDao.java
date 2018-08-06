package net.techgy.im;

import net.techgy.im.dao.IChannelDao;
import net.techgy.im.dao.IUserDao;
import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.ChannelImpl;
import net.techgy.im.models.IChannel.ChannelType;
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
public class TestChannelDao {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IChannelDao channelDao;

	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	IUserManager userManager;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSaveChannel() {
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
		
		ChannelImpl channel = this.getChannel();
		this.channelDao.saveChannel(channel);
		
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
	
	private ChannelImpl getChannel() {
		ChannelImpl g = new ChannelImpl();
		g.setChannelName("public chattiing");
		g.setChannelType(ChannelType.PUBLIC);
		g.setDescription("you can say any word here");
		return g;
	}
	
}
