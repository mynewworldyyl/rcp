package net.techgy.usercenter;

import net.techgy.IBaseDao;
import net.techgy.im.models.IAccount;


public interface IAccountDao extends IBaseDao<IAccount,Long>{

	 IAccount findAccountIdById(long accountId);
	
     IAccount findAccountByName(String accountName);
     void saveAccount(IAccount account);
     void removeAccount(IAccount account);
    
     void removeAccountByName(String accountName);
     void removeAccountById(long accountId);

}
