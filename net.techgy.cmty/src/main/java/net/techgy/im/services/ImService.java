package net.techgy.im.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.techgy.im.manager.IUserManager;
import net.techgy.im.models.IUser;
import net.techgy.usercenter.AccountImpl;
import net.techgy.usercenter.AccountManager;
import net.techgy.usercenter.LoginManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ImService {

	private Logger logger = Logger.getLogger(this.getClass());
	private static String FAIL = "fail";
	private static String SUCCESS = "success";
	
	@Autowired
	private LoginManager longinManager;
	
	@Autowired
	private AccountManager accountManager;
	
	@Autowired
	private IUserManager userManager;
	
	@GET
	@Path("/register")
	//@Produces(MediaType.APPLICATION_JSON)
	public String createUser(@QueryParam("username") String username) {
		
		return SUCCESS;
	}
	
	
	
	@GET
	@Path("/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@QueryParam("userId") String userId) {
		
		return null;
	}
	
	@GET
	@Path("/unregister")
	//@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@QueryParam("username") String username) {
		
		return SUCCESS;
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
