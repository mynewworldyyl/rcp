package com.digitnexus.core.db;

import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.uidef.BaseDef;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.provider.DefaultValueProvider;
import com.digitnexus.core.provider.IKeyValueProvider;
import com.digitnexus.core.provider.ITreeNodeProvider;
import com.digitnexus.core.uidef.service.UIDefManager;

@Component("crudService")
@Path("/crudService")
@Scope("singleton")
public class CommonCRUDService {
	
	@Autowired
	private UIDefManager uiDefManager;
	
	@Autowired
	private PersistenceManager persisManager;
	
	@GET
	@Path("/def")
	@Transactional
	public String getDef(@QueryParam("clsName") String cls) {
		Response resp = new Response(true);
		BaseDef def = uiDefManager.getDef(cls);
		String json = JsonUtils.getInstance().toJson(def,true);
		resp.setData(json);
		resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	@GET
	@Path("/id")
	@Transactional
	public String getId(@QueryParam("clsName") String clsName) {
		
		Response resp = new Response(true);
		resp.setData(persisManager.getOneId(clsName));
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	/**
	 * 根据类名及查询条件查找数据
	 * @param clsName VO的类名
	 * @param params 条询条件，也是编辑器头部输入的条件
	 * @return 查询的VO实例列表
	 */
	@POST
	@Path("/query")
	@Transactional
	public String query(@FormParam("clsName") String clsName, @FormParam("body") Map<String,String> params) {
		Response resp = persisManager.query(clsName, params);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
	
	/**
	 * 由ID及VO的类名查找实例
	 * @param clsName VO的完整类名
	 * @param id VO对应的实例ID
	 * @return 查询结果
	 */
	@GET
	@Path("/get")
	@Transactional
    public String get(@FormParam("clsName") String clsName, @FormParam("id") String id) {
		Response resp = persisManager.get(clsName, id);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
	/**
	 *  保存实例
	 * @param clsName 类名
	 * @param vos vo的值，json格式
	 * @return
	 */
    @POST
	@Path("/save")
    @Transactional
    public String save(@FormParam("clsName") String clsName,@FormParam("vos") String vos) {
    	Response resp = persisManager.save(clsName, vos);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    /**
     * 增加等价于保存
     * @param clsName
     * @param body
     * @return
     */
    @POST
   	@Path("/add")
    @Transactional
    public String add(@FormParam("clsName") String clsName,@FormParam("body") String body) {
      return this.save(clsName, body);
   	}
    
    /**
     * 删除一行，也就是删除一个VO
     * @param clsName
     * @param ids
     * @return
     */
    @POST
 	@Path("/delete")
    @Transactional
    public String delete(@FormParam("clsName") String clsName, @FormParam("ids") String ids) {
    	Response resp = persisManager.delete(clsName, ids);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
 
    /**
     * 更新VO
     * @param clsName
     * @param vos
     * @return
     */
    @POST
 	@Path("/update")
    @Transactional
    public String update(@FormParam("clsName") String clsName,@FormParam("vos") String vos) {
    	Response resp = persisManager.update(clsName, vos);
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    /**
     * 导出
     * @param clsName
     * @param req
     * @return
     */
    @POST
 	@Path("/export")
    @Transactional
    public String export(String clsName,String req) {
    	System.out.println("update: " + clsName + " for " + req);
    	Response resp = new Response(true);
		//String json = JsonUtils.getInstance().toJson(ql,true);
		//resp.setData(json);
		//resp.setClassType(def.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    /**
     *  查询值列表，如下拉选择框的选项列表，列表框的列表，单选按钮组列表，多选按钮组列表
     * @param clsName 值提供者的类名
     * @return
     */
    @GET
	@Path("/getValuesList")
	@Transactional
	public String crudService(@QueryParam("clsName") String clsName) {
		Class cls = this.loadCls(clsName);
		Object value = null;
		if(cls != null && cls != Void.class) {
			Object provider = null;
			try {
				provider = cls.newInstance();
			} catch (Exception e) {
				throw new CommonException("ValueProviderInitError",clsName);
			} 
			//cls是否继承自IKeyValueProvider
			if(IKeyValueProvider.class.isAssignableFrom(cls)) {
    			IKeyValueProvider p = (IKeyValueProvider)provider;
    			value  = p.keyValues();
    		}else if(ITreeNodeProvider.class.isAssignableFrom(cls)) {
    			ITreeNodeProvider p = (ITreeNodeProvider)provider;
    			value = p.getRoot();
    		}else if(cls == DefaultValueProvider.class) {
				DefaultValueProvider p = (DefaultValueProvider)provider;
				value = p.value();
			}
		}
		Response resp = null;
		if(value != null) {
			resp = new Response(true);
			resp.setData(JsonUtils.getInstance().toJson(value,true));
		}else {
			resp = new Response(false);
			resp.setMsg(I18NUtils.getInstance().getString("NotKnowException"));
		}
		resp.setClassType(value.getClass().getName());
		String respStr = JsonUtils.getInstance().toJson(resp,false);
		return respStr;
	}
    
    private Class loadCls(String clsName) {
		Class<?> clas = null;
		try {
			clas = PersistenceManager.class.getClassLoader().loadClass(clsName);
		} catch (ClassNotFoundException e) {
			throw new CommonException("ResourceNoFound",clsName);
		}
		return clas;
	}
}
