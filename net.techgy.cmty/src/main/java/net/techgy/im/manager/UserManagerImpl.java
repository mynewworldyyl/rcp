package net.techgy.im.manager;

import java.util.List;

import net.techgy.im.dao.IGroupDao;
import net.techgy.im.dao.IUserDao;
import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.IAccount;
import net.techgy.im.models.IGroup;
import net.techgy.im.models.IUser;
import net.techgy.im.models.UserImpl;
import net.techgy.im.net.NetUtils;
import net.techgy.usercenter.AccountManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserManagerImpl implements IUserManager {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IGroupDao groupDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private AccountManager accountManager;
	
	@Transactional
	public boolean addFriend(String myAccount,String addedAccount) {
		
		IUser me = this.userDao.findUserByNickName(myAccount);	
		IUser addedUser = this.userDao.findUserByAccount(addedAccount);
		
		if(me == null || addedUser == null) {
			return false;
		}
		try {
			me.getFriendGroups().iterator().next().getFriends().add(((UserImpl)addedUser));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("fail to add friend", e);
			return false;
		}
		return true;
	}

	@Transactional
	public UserImpl createUser() {
		// TODO Auto-generated method stub
		UserImpl user = new UserImpl();
		GroupImpl g = new GroupImpl();
		g.setName(IGroup.DEFAULT_FRIEND);
		g.setDesc("default group");
		g.setOwner(user);
		user.getFriendGroups().add(g);
		return user;
	}

	@Transactional
	public void saveNewUser(UserImpl user) {
		// TODO Auto-generated method stub
		this.groupDao.save(user.getFriendGroups().iterator().next());
		this.userDao.save(user);
	}
	
	@Transactional
	public UserImpl getUserByAccountId(long accountId) {
		return this.userDao.findUserByAccountId(accountId);
	}

	
	
	@Transactional
	public List<GroupImpl> getFriends(String accountName) {
		// TODO Auto-generated method stub
		IAccount acc = this.accountManager.findAccountByName(accountName);
		if(null == acc) {
			return null;
		}
		UserImpl ui = this.userDao.findUserByAccountId(acc.getId());
		if(ui == null) {
			return null;
		}
		return NetUtils.getInstance().copyGroupList(ui);
	}

	@Transactional
	public UserImpl getUserByAccountName(String accountName) {
		// TODO Auto-generated method stub
		IAccount acc = this.accountManager.findAccountByName(accountName);
		if(null == acc) {
			return null;
		}
		UserImpl ui = this.userDao.findUserByAccountId(acc.getId());
		logger.debug("group size: " + ui.getFriendGroups().size());
		return ui;
	}
	
}
