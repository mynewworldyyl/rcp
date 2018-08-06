package net.techgy.osig.service;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface IConfigurationService {

	public String getMsg(String name);
	
	public String executePost(String attrBean,@PathParam("manager") String manager,@PathParam("method") String method);
	
	public String executeGet(@QueryParam("params") String params,@PathParam("manager") String manager,@PathParam("method") String method);

	public String executePut(String attrBean,@PathParam("manager") String manager,@PathParam("method") String method);
	
	public String executeDelete(@QueryParam("params") String params,@PathParam("manager") String manager,@PathParam("method") String method);	
	
}
