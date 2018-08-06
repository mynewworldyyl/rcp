package com.digitnexus.core.idgenerator;

import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.digitnexus.base.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Path("/id")
@Component
@Scope("singleton")
public class IDService {
  
	    @Autowired
	    @Qualifier("cacheBaseIDManager")
	    private IIDGenerator generator;
	    
	    @Value("#{configProperties['isMainServer']}")
		private boolean isMainServer;
	    
	    @Value("#{configProperties['debugModel']}")
		private boolean debugModel;
	    
	    private static final String ERROR_NOT_ID_SERVER = "Your request not a ID SERVER";
	    
	    private static final String ERROR_ENTITY_NOT_FOUND = "Entity not found: ";
	    
	    @POST
	    @Path("createPrefix")
	    public String createPrefix(@QueryParam("idAssignmentStr") String idAssignmentStr) {	    	
	    	IDAssignment ida = JsonUtils.getInstance().fromJson(idAssignmentStr, IDAssignment.class,false,false);
	    	IDAssignment newIda = this.generator.createNewPrefixIDAssignment(ida);	    	
			return JsonUtils.getInstance().toJson(newIda,false);
	    }
	    
	    @GET
	    @Path("numberId")
	    public String numberIds(@QueryParam("entityId") String entityId,@QueryParam("clientId") String clientId,@QueryParam("idNum") int idNum) {	    	
	    	if(!isMainServer&&!debugModel) {
	    		return ERROR_NOT_ID_SERVER;
	    	}
			try {
				Class entityCls = IDService.class.getClassLoader().loadClass(entityId);
			    Set<Number> ids = this.generator.getNumIds(entityCls,clientId, idNum);
			   /* StringBuffer sb = new StringBuffer();
				for(Number id : ids) {
					sb.append(id.toString()).append(",");
				}
				sb.deleteCharAt(sb.length()-1);*/
			    Type type = new TypeToken<Set<Long>>(){}.getType();
			    String str = new Gson().toJson(ids,type);
			    return str;
			} catch (ClassNotFoundException e) {
				return ERROR_ENTITY_NOT_FOUND + entityId;
			}
	    }
	    
	    private String getArrayStr(Set<String> ids) {
			StringBuffer sb = new StringBuffer();
			for(String id : ids) {
				sb.append(id).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
			
		}

		@GET
	    @Path("stringId")
	    public String stringIds(@QueryParam("entityId") String entityId,@QueryParam("clientId") String clientId,@QueryParam("idNum") int idNum,
	    		@QueryParam("idLen") int idLen,@QueryParam("idPrefix") String idPrefix) {	    	
	        if(!isMainServer&& !debugModel) {
	    		return ERROR_NOT_ID_SERVER;
	    	}
			try {
				Class<?> entityCls = IDService.class.getClassLoader().loadClass(entityId);
				String[] prefixes = null;
				if(idPrefix != null && !"".equals(idPrefix.trim())) {
					prefixes = idPrefix.split(",");
				}
			    Set<String> ids = this.generator.getStringIds(entityCls,clientId, idNum, idLen, prefixes);
			    Type type = new TypeToken<Set<String>>(){}.getType();
			    String str = new Gson().toJson(ids,type);
			    return str;
			} catch (ClassNotFoundException e) {
				return ERROR_ENTITY_NOT_FOUND + entityId;
			}
	    }
	    
}
