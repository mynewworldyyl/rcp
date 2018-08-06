package net.techgy.community;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Path("/community")
//@Component
public class MainService {

	private Logger logger = Logger.getLogger(this.getClass());
	@GET
	@Path("/testdata")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMetadata() {
		logger.debug("welcome to technology community hh");
		return "welcome to technology community hh";
	}
	
}
