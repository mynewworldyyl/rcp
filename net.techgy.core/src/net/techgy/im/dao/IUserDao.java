package net.techgy.im.dao;

import net.techgy.IBaseDao;
import net.techgy.im.models.IAccount;
import net.techgy.im.models.UserImpl;

public interface IUserDao extends IBaseDao<UserImpl,Long>{

	UserImpl findUserById(long userId);
	UserImpl findUserByNickName(String name);
	UserImpl findUserByAccount(String name);
	UserImpl findUserByAccountId(long accountId);
	
	void saveUser(UserImpl user);
	void removeUser(UserImpl user);
	void updateUser(UserImpl user);
	
}
