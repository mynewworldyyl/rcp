package net.techgy.im;

import junit.framework.Assert;
import net.techgy.im.dao.IChannelDao;
import net.techgy.im.dao.IGroupDao;
import net.techgy.im.dao.IUserDao;
import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.ChannelImpl;
import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.IChannel.ChannelType;
import net.techgy.im.models.IUser;
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
public class TestUserDao {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IGroupDao groupDao;
	
	@Autowired
	private IChannelDao channelDao;

	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	IUserManager userManager;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testPersist() {
		AccountImpl account = this.getAccount();
		UserImpl user = (UserImpl)this.userManager.createUser();
		this.accountManager.saveAccount(account);
		
		user.setAccount(account);	
		userDao.saveUser(user);	
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSaveUserWithGroup() {
		AccountImpl account = null;
		try {
			 account = (AccountImpl)this.accountManager.findAccountById(1);
		} catch (Exception e) {
			
		}

		UserImpl user1 = this.getUser();
		user1.setAccount(account);
		user1.setName("testUser1");
		user1.setType(RoleType.IM);
		this.userDao.save(user1);
		
		GroupImpl g = this.getGroup();
		g.getFriends().add(user1);
		
		UserImpl user = this.getUser();
		user.setName("testOtherUser");
		user.setAccount(account);
		user.getFriendGroups().add(g);
		g.setOwner(user);		
		this.userDao.save(user);
		
		UserImpl user2 =(UserImpl)this.userDao.findUserById(user.getId());
		
		Assert.assertNotNull(user2);
		Assert.assertNotNull(user2.getFriendGroups());
		Assert.assertNotNull(user2.getFriendGroups().size() == 1);
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void deleteUserWithGroup() {
		UserImpl user1 =(UserImpl)this.userDao.findUserById(51);	
		Assert.assertNotNull(user1);
		Assert.assertNotNull(user1.getFriendGroups());		
		this.userDao.removeUser(user1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUserWithChannel() {
		AccountImpl account = this.getAccount();
		AccountImpl account1 =(AccountImpl) this.accountManager.findAccountByName(account.getAccountName());
		if(account1 == null){
			this.accountManager.saveAccount(account);
		}
		UserImpl user = (UserImpl)this.userManager.createUser();
		user.setAccount(account1);
		this.userDao.save(user);
		
		ChannelImpl channel = this.getChannel();
		channel.setCreator(user);
		user.getChannels().add(channel);
		channel.getUsers().add(user);
		
		this.userDao.save(user);
		this.channelDao.save(channel);
	}

	
	@Test
	@Transactional
	@Rollback(false)
	public void testSaveUserWithChannelAndGroup() {
		AccountImpl account = this.getAccount();
		AccountImpl account1 =(AccountImpl) this.accountManager.findAccountByName(account.getAccountName());
		if(account1 == null){
			this.accountManager.saveAccount(account);
		}
		
		UserImpl user = (UserImpl)this.userManager.createUser();
		user.setAccount(account);
		this.userDao.save(user);
		
		ChannelImpl channel = this.getChannel();
		channel.setCreator(user);
		user.getChannels().add(channel);
		channel.getUsers().add(user);
		
		GroupImpl g = this.getGroup();
		g.setOwner(user);
		user.getFriendGroups().add(g);
		
		this.groupDao.save(g);
		//this.userDao.save(user);
		this.channelDao.save(channel);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueryByName() {
		IUser user = this.userDao.findUserByNickName("test2");
		Assert.assertNotNull(user);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueryById() {
		IUser user = this.userDao.findUserById(102);
		Assert.assertNotNull(user);
		
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
	
	private ChannelImpl getChannel() {
		ChannelImpl g = new ChannelImpl();
		g.setChannelName("public chattiing");
		g.setChannelType(ChannelType.PUBLIC);
		g.setDescription("you can say any word here");
		return g;
	}
	
}
