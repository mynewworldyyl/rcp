package net.techgy.osig.service;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.techgy.common.models.MsgHeader;

public interface IAccountService {

	@GET
	@Path("/register")
	String register(@QueryParam(MsgHeader.USERNAME) String username,@QueryParam("password") String password);

	@GET
	@Path("/login")
	//@Produces(MediaType.APPLICATION_JSON)
	public String login(@QueryParam(MsgHeader.USERNAME) String username,@QueryParam("password") String password);
	
	@GET
	@Path("/friends")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@QueryParam("userId") String userId);
	
	@GET
	@Path("/logout")
	//@Produces(MediaType.APPLICATION_JSON)
	public String logout(@QueryParam(MsgHeader.USERNAME) String username);
	
	@GET
	@Path("/unregister")
	//@Produces(MediaType.APPLICATION_JSON)
	public String unregister(@QueryParam(MsgHeader.USERNAME) String username);
	
	@GET
	@Path("/sl")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getSupportLanguage(@Context ServletContext context);
	
	
}
