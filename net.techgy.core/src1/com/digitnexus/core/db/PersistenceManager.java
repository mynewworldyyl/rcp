package com.digitnexus.core.db;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.UserContext;
import com.digitnexus.core.dept.ClientManager;
import com.digitnexus.core.i18n.I18NUtils;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.google.gson.reflect.TypeToken;

@Component
public class PersistenceManager {

	@Autowired(required=false)
	private Set<IPersistenceListener> persistenceListener = null;
	
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private ClientManager clientManaber;
	
	@Autowired
	private ICacheIDGenerator generator;
	
	@Autowired
	private VoToEntityMap voToEntityMap;
	
	public Response save(String clsName, String json) {
    	Response resp = null;
		List<Object> es = this.getFromJson(json, clsName);
		IPersistenceListener l = this.getListener(clsName);
		if(l == null) {
			throw new CommonException("SystemError",clsName);
		}
		List<Object> objs = null;

		objs = l.beforeSave(es);
	
		if(objs != null && !objs.isEmpty()) {
			resp = new Response(true);
			for(int index = 0; index < objs.size(); index++) {
				Object e = objs.get(index);
				baseDao.save(e);
				l.afterSaveEntity(e, es.get(index), resp);
				if(!resp.isSuccess()) {
					return resp;
				}
			}
		} else {
			resp = new Response(false);
			resp.setMsg(I18NUtils.getInstance().getString("FailToSave",clsName));
		}
		if(l != null) {
			try {
				resp = l.afterSave(objs,resp);
			} catch (CommonException e) {
				throw e;
			}
		}
		return resp;
	}
	
	public Response update(String clsName, String json) {
    	Response resp = null;
		List<Object> es = this.getFromJson(json, clsName);
		IPersistenceListener l = this.getListener(clsName);
		List<Object> objs = null;
		if(l == null) {
			throw new CommonException("SystemError",clsName);
		}
		objs = l.beforeUpdate(es);
		if(objs != null && !objs.isEmpty()) {
			resp = new Response(true);
			for(int index = 0; index < objs.size(); index++) {
				Object e = objs.get(index);
				baseDao.update(e);
				l.afterUpdateEntity(e, es.get(index), resp);
				if(!resp.isSuccess()) {
					return resp;
				}
			}
		} else {
			resp = new Response(false);
			resp.setMsg(I18NUtils.getInstance().getString("FailToSave",clsName));
		}
		if(l != null) {
			try {
				resp = l.afterUpdate(objs,resp);
			} catch (CommonException e) {
				throw e;
			} catch (Exception e) {
				
			}
		}
		return resp;
	}
	
	public Response get(String clsName, String id) {
		Response resp = new Response(true);
		IPersistenceListener l = this.getListener(clsName);
		Class cls = null;
		if(l != null) {
			cls = l.beforeGet(clsName,id);
		}
		Object obj = this.baseDao.find(cls, id);
		
		if(l != null) {
			try {
				resp = l.afterGet(obj,resp);
			} catch (CommonException e) {
				throw e;
			}
			
		}
		return resp;
	}
	
	public Response delete(String clsName, String ids) {
		Response resp = new Response(true);
		IPersistenceListener l = this.getListener(clsName);
		Class cls = this.loadCls(voToEntityMap.getEntityClsName(clsName));
		String[] idss = null;
		if(l != null) {
			try {
				idss = l.beforeDelete(clsName,ids);
			} catch (CommonException e) {
				throw e;
			}
			if(idss == null) {
				Type type = new TypeToken<List<String>>(){}.getType();
				List<String> idls = JsonUtils.getInstance().fromJson(ids, type, false, false);
				idss = new String[idls.size()];
				idss = idls.toArray(idss);
			}
		}
		for(String id: idss) {
			this.baseDao.remove(cls, id);
		}
		if(l != null) {
			try {
				resp = l.afterDelete(cls, idss,resp);
			} catch (CommonException e) {
				throw e;
			} catch (Exception e) {
			}
		}
		return resp;
	}
	
	public Response query(String clsName,Map<String,String> params) {
		Response resp = new Response(true);
		Class cls = this.loadCls(clsName);
		IPersistenceListener l = this.getListener(clsName);
		Object query = null;
		List<Object> el = null;
		if(l != null) {
			//arra长充为2， 0索引项为Entity的Class实例，1索引项为查询的SQL，若为NULL，则通过默认方式构建。
			Class entityCls = this.loadCls(this.voToEntityMap.getEntityClsName(clsName));
			if(entityCls == null) {
				throw new CommonException("QueryParamError",clsName);
			}
			query = l.beforeQuery(cls,params);
			
			if( query != null) {
				if(query instanceof String) {
					el = this.baseDao.query(entityCls, params,(String)query);
				}else if(query instanceof Query) {
					el = ((Query)query).getResultList();
				}else {
					throw new CommonException("InvalideSql",clsName, query.toString());
				}
			} else {
				el = this.doQuery(entityCls, params);
			}
		}
		List<Object> vl = null;
		if(l != null) {
			vl = l.afterQuery(clsName, params, el);
		}
		if(vl == null) {
			vl = Collections.emptyList();
		}
		String json = JsonUtils.getInstance().toJson(vl,true);
		resp.setData(json);
		resp.setClassType(List.class.getName());
		vl.clear();
		return resp;
	}
	
	public String getOneId(String voClsName) {
		String entityClsName = this.voToEntityMap.getEntityClsName(voClsName);
		if(entityClsName == null) {
			entityClsName = voClsName;
		}
		return this.generator.getStringId(this.loadCls(entityClsName));
	}
	
	private List<Object> doQuery(Class entityCls, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT a FROM ").append(entityCls.getName()).append(" a WHERE a.client.id!='0' ");
		
		List<String> subClients = this.clientManaber.getSubClientIds(UserContext.getCurrentClientId());
		if(subClients != null && !subClients.isEmpty()) {
			sb.append(" AND a.client.id IN :clients");
		}
		
		//Map<String,Object> queryParams = new HashMap<String,Object>();
		if(params != null && !params.isEmpty()) {
			for(Map.Entry<String, String> e : params.entrySet()) {
				String fn = e.getKey();
				String fv = e.getValue();
				if(fv == null || fv.trim().equals("")) {
					continue;
				}
				try {
					Field f = entityCls.getDeclaredField(fn);
					Object value = getOneCondition(fv,f);
					if(value != null) {
						sb.append(" OR a.").append(f.getName()).append(" LIKE '%" ).append(value).append("%' ");
					}
				}catch (CommonException e2) {
					throw e2;
				} catch (NoSuchFieldException e1) {
				} catch (SecurityException e1) {
				} 
			}
		}
		
		Query q = this.baseDao.getEntityManager().createQuery(sb.toString());
		
		if(subClients != null && !subClients.isEmpty()) {
			q.setParameter("clients", subClients);
		}
		
		/*for(Map.Entry<String, Object> e : queryParams.entrySet()) {
			q.setParameter(e.getKey(), e.getValue());
		}*/
		
		return q.getResultList();
	}

	private Object getOneCondition(String o, Field f) {
		Class type = f.getType();
		Object value = null;
		if(type == Double.TYPE|| type == Double.class) {
			value = Double.valueOf(o.toString());
		}else if(type == Float.TYPE|| type == Float.class) {
			value = Float.valueOf(o.toString());
		}else if(type == Long.TYPE || type == Long.class) {
			value = Long.valueOf(o.toString());
		}else if(type == Integer.TYPE|| type == Integer.class) {
			value = Integer.valueOf(o.toString());
		}else if(type == Short.TYPE|| type == Short.class) {
			value = Short.valueOf(o.toString());
		}else if(type == Byte.TYPE|| type == Byte.class) {
			value = Byte.valueOf(o.toString());
		}else if(type == Boolean.TYPE|| type == Boolean.class) {
			value = Boolean.valueOf(o.toString());
		}else if(type == Character.TYPE|| type == Character.class) {
			value = o.toString().charAt(0);
		}else if(type == Void.TYPE|| type == Void.class) {
			value = Byte.valueOf(o.toString());
		}else if(type == String.class) {
			value = o.toString();
		}
		if(null != value) {
			return value;
		}
		return null;
	}

	
	private List<Object> getFromJson(String json,String clsName) {
		Class cls = this.loadCls(clsName);
		List<Object> objList = new ArrayList<Object>();
		try {
			Type type = new TypeToken<List<Object>>(){}.getType();
			List<Object> l = JsonUtils.getInstance().fromJson(json, type, false, false);
			if(l != null && !l.isEmpty()) {
				for(Object o : l) {
					Object e = JsonUtils.getInstance().fromJson(JsonUtils.getInstance().toJson(o, false), cls);
					objList.add(e);
				}
			}
		}catch (CommonException e2) {
			throw e2;
		} catch (Exception e) {
			Object entity = JsonUtils.getInstance().fromJson(json, this.loadCls(clsName), false, false);
			objList.add(entity);
		}
		return objList;
	}
	
	private Class loadCls(String clsName) {
		Class<?> clas = null;
		try {
			clas = PersistenceManager.class.getClassLoader().loadClass(clsName);
		} catch (CommonException e2) {
			throw e2;
		}catch (ClassNotFoundException e) {
			throw new CommonException("ResourceNoFound",clsName);
		}
		return clas;
	}
	
	private IPersistenceListener getListener(String clsName) {
		for(IPersistenceListener l : this.persistenceListener) {
			if(clsName.equals(l.getVoClsName())) {
				return l;
			}
		}
		return null;
	}
	
	
}
