package net.techgy.usercenter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.techgy.common.models.MsgHeader;
import net.techgy.im.models.IAccount;
import net.techgy.osig.service.IAccountService;
import net.techgy.utils.JsonUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Path("/ac")
@Component
@Qualifier("accountService")
public class AccountService implements IAccountService{

	private Logger logger = Logger.getLogger(this.getClass());
	public static String FAIL = "fail";
	public static String SUCCESS = "success";
	
	@Autowired
	private LoginManager longinManager;
	
	@Autowired
	private AccountManager accountManager;
	
	@Autowired(required=false)
	private Set<IAccountListener> listeners = new HashSet<IAccountListener>();
	
	@GET
	@Path("/register")
	//@Produces(MediaType.APPLICATION_JSON)
	public String register(@QueryParam(MsgHeader.USERNAME) String username,
			@QueryParam("password") String password) {
		if(!checkUser(username,password)) {
			return FAIL;
		}
		IAccount account = longinManager.getAccount(username);
		if(account != null) {
			return FAIL;
		}
		 AccountImpl account1 = this.accountManager.createNewAccount();
		 account1.setPassword(password);
		 account1.setAccountName(username);
		 
		try {
			accountManager.saveAccount(account1);
			this.notifyAccountRegister(account1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return FAIL;
		}
		
		return SUCCESS;
	}

	@GET
	@Path("/login")
	//@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam(MsgHeader.USERNAME) String username,@QueryParam("password") String password) {		
		try {
			if(!checkUser(username,password)) {
				return FAIL;
			}
			if(longinManager.isLogin(username)) {
				return SUCCESS;
			}
			IAccount account = longinManager.getAccount(username);
			if(account != null && !password.equals(account.getPassword())) {
				return SUCCESS;
			}
			account = accountManager.findAccountByName(username);
			if(null == account) {
				return FAIL;
			}
			this.longinManager.addAccount(account);
			this.notifyAccountLogin(account);
			return SUCCESS;
		} catch (Exception e) {
			logger.error("Fail to login", e);
			return FAIL; 
		}
	}
	
	
	private void notifyAccountLogin(IAccount account) {
		// TODO Auto-generated method stub
		if(this.listeners.size() < 1) {
			return;
		}
		AccountEvent e = new AccountEvent(account);
		for(IAccountListener l : this.listeners) {
			l.accountLogin(e);
		}
	}

	private void notifyAccountLogout(IAccount account) {
		// TODO Auto-generated method stub
		if(this.listeners.size() < 1) {
			return;
		}
		AccountEvent e = new AccountEvent(account);
		for(IAccountListener l : this.listeners) {
			l.accountLogout(e);
		}
	}
	
	private void notifyAccountRegister(IAccount account1) {
		// TODO Auto-generated method stub
		if(this.listeners.size() < 1) {
			return;
		}
		AccountEvent e = new AccountEvent(account1);
		for(IAccountListener l : this.listeners) {
			l.accountRegister(e);
		}
	}
	
	private void notifyAccountUnregister(IAccount account1) {
		// TODO Auto-generated method stub
		if(this.listeners.size() < 1) {
			return;
		}
		AccountEvent e = new AccountEvent(account1);
		for(IAccountListener l : this.listeners) {
			l.accountUnregister(e);
		}
	}
	
	@GET
	@Path("/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@QueryParam("userId") String userId) {
		
		return null;
	}
	
	
	@GET
	@Path("/logout")
	//@Produces(MediaType.APPLICATION_JSON)
	public String logout(@QueryParam(MsgHeader.USERNAME) String username) {
		if(username == null || !longinManager.isLogin(username)) {
			return SUCCESS;
		}
		IAccount a = this.longinManager.removeAccount(username);
		notifyAccountLogout(a);
		return SUCCESS;
	}
	
	@GET
	@Path("/unregister")
	//@Produces(MediaType.APPLICATION_JSON)
	public String unregister(@QueryParam(MsgHeader.USERNAME) String username) {
		if(username == null || !longinManager.isLogin(username)) {
			return FAIL;
		}
		IAccount account = this.longinManager.removeAccount(username);
		this.accountManager.removeAccountByName(username);
		this.notifyAccountUnregister(account);
		return SUCCESS;
	}
	
	@GET
	@Path("/sl")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getSupportLanguage(@Context ServletContext context) {
		String[] lang = new String[]{"","english","zh-cn","中文简体"};
		String path = context.getRealPath("/resources");
		File file = new File(path);
		if(file.exists() && file.isDirectory()) {
			
		}
		
		return JsonUtils.getInstance().toJson(lang);
	}
	
	private boolean checkUser(String username,String password) {
		if(username == null || "".equals(username.trim())) {
			return false;
		}
		if(password == null || "".equals(password.trim())) {
			return false;
		}
		return true;
	}
}
