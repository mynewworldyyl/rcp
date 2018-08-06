package net.techgy.im.handler;

import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.UserImpl;
import net.techgy.im.net.ISession;
import net.techgy.im.net.ISessionManager;
import net.techgy.osig.service.impl.RapSession;
import net.techgy.usercenter.AccountEvent;
import net.techgy.usercenter.AccountImpl;
import net.techgy.usercenter.IAccountListener;
import net.techgy.usercenter.IRole.RoleType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImAccountListener implements IAccountListener {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserManager userManger;
	
	@Autowired
	private IQueueManager queueManager;
	
	 @Autowired
	 private ISessionManager manager;
	
	@Override
	public void accountLogin(AccountEvent event) {
		  String accountName = event.getAccount().getAccountName();		  
		  RapSession session = (RapSession)this.manager.createSession(null, null, accountName, null);
          session.onOpen();    
	}

	@Override
	public void accountLogout(AccountEvent event) {
		 
	}

	@Override
	public void accountRegister(AccountEvent event) {
		// TODO Auto-generated method stub
		try {
			UserImpl user = (UserImpl)userManger.createUser();
			AccountImpl a = (AccountImpl)event.getAccount();
			user.setAccount(a);
			user.setName(a.getAccountName());
			user.setType(RoleType.IM);
			userManger.saveNewUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		   logger.error(e.getMessage());
		}
	}

	@Override
	public void accountUnregister(AccountEvent event) {
          
	}

}
