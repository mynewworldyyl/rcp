package com.digitnexus.core.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.utils.JsonUtils;
import com.digitnexus.core.dept.Department;
import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.vo.dept.DepartmentVo;

public abstract class AbstractPersistenceListener<E,V> implements IPersistenceListener<E,V> {

	@Autowired
	protected BaseDao baseDao;
	
	@Autowired
	protected ICacheIDGenerator generator;
	
	@Autowired
	protected VoToEntityMap voToEntityMap;
	
	@Override
	public List<E> beforeSave(Object obj) {
		if(obj == null) {
			return null;
		}
		List<E> el = new ArrayList<E>();
		if( obj instanceof Collection) {
			Collection<V> l = (Collection<V>)obj;
			for(V o : l) {
				el.add(this.voToEntityForSave(o));
			}
			
		} else {
			el.add(this.voToEntityForSave((V)obj));
		}
		return el;
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> beforeUpdate(Object obj) {
		if(obj == null) {
			return null;
		}
		List<E> el = new ArrayList<E>();
		if( obj instanceof Collection) {
			Collection<V> l = (Collection<V>)obj;
			for(V o : l) {
				el.add(this.voToEntityForUpdate(o));
			}
		} else {
			el.add(this.voToEntityForUpdate((V)obj));
		}
		return el;
	
	}
	
	@SuppressWarnings("rawtypes")
	protected Class loadCls(String clsName) {
			Class clas = null;
			try {
				clas = PersistenceManager.class.getClassLoader().loadClass(clsName);
			} catch (ClassNotFoundException e) {
				throw new CommonException("ResourceNoFound",clsName);
			}
			return clas;
		}
	
	protected Object getOneCondition(String o, Field f) {
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

	@Override
	@SuppressWarnings("unchecked")
	public String[]  beforeDelete(String clsName, String ids) {
		Class<E> cls = this.loadCls(this.voToEntityMap.getEntityClsName(clsName));
		List<String> idls = JsonUtils.getInstance().getStringValueList(ids, false);
		String[] idss = new String[idls.size()];
		idss = idls.toArray(idss);
		for(String id: idss) {
			E d = this.baseDao.getEntityManager().find(cls, id);
			if(d == null) {
				throw new CommonException("EntityNotExist",id);
			}
		}
		return idss;
	}
	
	@Override
	public List<V> afterQuery(String clsName, Map<String, String> params,
			List<E> entityList) {
		if(entityList == null || entityList.isEmpty()) {
			return Collections.emptyList();
		}
		List<V> result = new ArrayList<V>();
		for(E d : entityList) {
			V vo = entityToVo(d);
			if(vo != null) {
				result.add(vo);
			}
		}
		return result;
	}

	@Override
	public Response afterDelete(Class<?> cls, String[] idss, Response resp) {
		return resp;
	}

	@Override
	public Object beforeQuery(Class<?> cls, Map<String, String> params) {
		return null;
	}

	@Override
	public Response afterSave(List<E> obj, Response resp) {
		return resp;
	}

	@Override
	public Response afterUpdate(List<E> obj, Response resp) {
		return resp;
	}

	@Override
	public Class<?> beforeGet(String clsName, String id) {
		return null;
	}

	@Override
	public Response afterGet(E obj,Response resp) {
		return resp;
	}
	
	

	@Override
	public String getVoClsName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response afterSaveEntity(E e, V vo, Response resp) {
		return resp;
	}

	@Override
	public Response afterUpdateEntity(E e, V vo, Response resp) {
		return resp;
	}

	abstract protected E voToEntityForSave(V obj);
	
	abstract protected E voToEntityForUpdate(V obj);
	
	abstract protected V entityToVo(E d);
}
