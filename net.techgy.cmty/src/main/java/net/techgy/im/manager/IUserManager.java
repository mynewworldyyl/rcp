package net.techgy.im.manager;

import java.util.List;

import net.techgy.im.models.GroupImpl;
import net.techgy.im.models.UserImpl;

public interface IUserManager {

	boolean addFriend(String myAccount,String addedAccount);
	
	UserImpl createUser();
	
	void saveNewUser(UserImpl user);
	
	UserImpl getUserByAccountId(long account);
	
	UserImpl getUserByAccountName(String userId);
	
	List<GroupImpl> getFriends(String userId);
	
}
