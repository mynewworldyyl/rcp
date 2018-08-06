package net.techgy.osig.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import net.techgy.osig.service.AbstractService;
import net.techgy.ui.UITable;
import net.techgy.ui.UIUtils;
import net.techgy.ui.VODefinition;
import net.techgy.ui.manager.ConfiguableManagerImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Path("/tgy")
@Component
@Qualifier("configurationService")
public class ConfigurationService extends AbstractService {

	private Logger logger = Logger.getLogger(ConfigurationService.class);
	
	
	@Override
	public String getMsg(String name) {
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		return "Your name is : " + name;
	}

	@Autowired
	private ConfiguableManagerImpl voManager;

	private <T> T doAction(String manager,String method,String attrBean) {
		Map<String,String> avo = this.fromJson(attrBean, HashMap.class);
		return (T) voManager.execute(manager,method,avo);
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/cr/{manager}/{method}")
	public String executePost(String attrBean,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		doAction(manager,method,attrBean);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/qr/{manager}/{method}")
	public String executeGet(@QueryParam("params") String params,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		List<?> attrvos = doAction(manager,method,params);
		Class cls = attrvos.get(0).getClass();
		UITable tf = UIUtils.getInstance().getTableFormat(cls);
		tf.setRows(UIUtils.getInstance().getRows(cls, attrvos));
		String json = this.toJson(tf);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/up/{manager}/{method}")
	public String executePut(String attrBean,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		doAction(manager,method,attrBean);
		return SUCCESS;
	}
	
	@DELETE
	@Path("/rm/{manager}/{method}")
	public String executeDelete(@QueryParam("params") String params,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		doAction(manager,method,params);
		return SUCCESS;
	}
	
	/*@SuppressWarnings("unchecked")
	@POST
	@Path("/exec/{manager}/{method}")
	public String executePost(String attrBean,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		List avo = this.fromJson(attrBean, ArrayList.class);
		if (null == avo) {
			return FAIL;
		}
		//this.attrManager.createAttribute(avo);
		voManager.execute(manager,method,avo.toArray());
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/qr/{manager}/{method}")
	public String executeGet(@QueryParam("qryStr") String qryStr,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		List attrvos = voManager.query(manager,method,new String[] {qryStr});
		if(null == attrvos || attrvos.isEmpty()) {
			return "";
		}
		Class cls = attrvos.get(0).getClass();
		UITable tf = UIUtils.getInstance().getTableFormat(cls);
		tf.setRows(UIUtils.getInstance().getRows(cls, attrvos));
		String json = this.toJson(tf);
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/up/{manager}/{method}")
	public String executePut(String attrBean,
			@QueryParam("voCls") String voCls,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		Class cls = this.loadClass(voCls);
		Object avo = this.fromJson(attrBean,cls);
		if (null == avo) {
			return FAIL;
		}
		voManager.update(manager,method,avo);
		return SUCCESS;
	}
	
	@DELETE
	@Path("/rm/{manager}/{method}")
	public String executeDelete(@QueryParam("voId") Long voId,
			@PathParam("manager") String manager,
			@PathParam("method") String method) {
		if (null == voId) {
			return FAIL;
		}
		this.fromJson(attrBean, ArrayList.class);
		voManager.remove(manager,method,voId);
		return SUCCESS;
	}*/
	
	

	/*@POST
	@Path("/vo/update")
	public String updateVO(String attrBean,@QueryParam("voCls") String voCls,
			@QueryParam("methodName") String methodName) {
		Class cls = this.loadClass(voCls);
		Object avo = this.fromJson(attrBean,cls);
		if (null == avo) {
			return FAIL;
		}
		voManager.update(avo.getClass(),methodName,avo);
		return SUCCESS;
	}

	@GET
	@Path("/vo/del")
	public String removeVO(@QueryParam("voId") Long voId,
			@QueryParam("voCls") String voCls,@QueryParam("methodName") String methodName) {
		if (voId <= 0) {
			return FAIL;
		}
		Class cls = this.loadClass(voCls);
		//this.attrManager.delAttribute(attrId);
		voManager.remove(cls,methodName,voId);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/vo/query")
	@Produces(MediaType.APPLICATION_JSON)
	public String findVOByLike(@QueryParam("qryStr") String qryStr,
			@QueryParam("getVoList") boolean getVoList,
			@QueryParam("voCls") String voCls,@QueryParam("methodName") String methodName) {
		Class<?> cls = this.loadClass(voCls);
		List attrvos = voManager.query(cls,methodName,qryStr);
		if(null == attrvos || attrvos.isEmpty()) {
			return "";
		}
		//List attrvos = this.attrManager.findAttributeByLike(qryStr);
		UITable tf = UIUtils.getInstance().getTableFormat(cls);
		if (getVoList) {
			tf.setVos(attrvos);
		} else {			
			tf.setRows( UIUtils.getInstance()
					.getRows(cls, attrvos));
		}
		String json = null;
		json = this.toJson(tf);
		return json;
	}*/
	
	private Class loadClass(String clsName) {
		if(null == clsName || "".equals(clsName.trim())) {
			String msg = "vo class name is NULL";
			processError(msg);
			return null;
		}
		Class cls = null;
		try {
			cls = ConfigurationService.class.forName(clsName);
		} catch (ClassNotFoundException e) {
			processError("loadClass",e);
		}
		if (null == cls) {
			String msg = "vo class not found: " + clsName;
			processError(msg);
			return null;
		}
		return cls;
	}
	
	 private void processError(String msg,Throwable ...e) {
			logger.error(msg);
			throw new RuntimeException(msg);
	 }

	@GET
	@Path("/common/vodef")
	public String getVODefinition(@QueryParam("cls") String cls) {
		Class voCls = this.loadClass(cls);
		List<VODefinition> vodef = UIUtils.getInstance().getVODefinition(voCls,null);
		String tableFormat = this.toJson(vodef);
		return tableFormat;
	}

}