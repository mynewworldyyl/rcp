package com.digitnexus.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.digitnexus.base.protocol.Response;
import com.digitnexus.base.uidef.FieldDef;
import com.digitnexus.base.uidef.TreeNode;

public class Utils {

	private static final Utils instance = new Utils();
	private Utils(){};
	public static Utils getInstance(){return instance;}
	
	public byte[] loadStreamAsBtyes(InputStream is) {
		byte[] bytes = new byte[1024];
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = -1;
		try {
			while((len=is.read(bytes)) > 0) {
				baos.write(bytes, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public String[] getMapKeys(Map<String,String> params,String[] values) {
		if(values == null || values.length < 1) {
			return null;
		}
		if(params == null || params.isEmpty()) {
			return null;
		}
		
		Map<String,String> kvs = this.exchangeKeyValue(params);
		String[] keys = new String[values.length];
		int index = 0;
		for(String v : values) {
			keys[index++] = kvs.get(v);
		}
		return keys;
	}
	
	public Map<String,String> getMapKeyValues(FieldDef fd) {
		Map<String,String> kvs = null;
		if (fd.getAvailables() != null && fd.getAvailables().length > 0) {
			kvs = Utils.getInstance().arrayAsMap(fd.getAvailables());
		} else if (fd.getKeyValues() != null && !fd.getKeyValues().isEmpty()) {
			kvs = fd.getKeyValues();
		} else {
			kvs = Collections.emptyMap();
		}
		return kvs;
	}
	
	public String[] getMapValues(Map<String,String> params,String[] keys) {
		if(keys == null || keys.length < 1) {
			return null;
		}
		if(params == null || params.isEmpty()) {
			return null;
		}
		
		String[] values = new String[keys.length];
		int index = 0;
		for(String k : keys) {
			values[index++] = params.get(k);
		}
		return values;
	}
	
	 public Map<String,String> exchangeKeyValue(Map<String,String> params) {
	    	Map<String,String> ps = new HashMap<String,String>();
	    	for(Map.Entry<String, String> e : params.entrySet()) {
	    		ps.put(e.getValue(), e.getKey());
	    	}
	    	return ps;
		}
	    

	    public Map<String,String> arrayAsMap(String[] items) {
	    	Map<String,String> ps = new HashMap<String,String>();
	    	for(String item : items) {
	    		ps.put(item, item);
	    	}
	    	return ps;
		}
	
	public String[] getMapKeys(Map<String,String> params) {
		String[] keys = new String[params.size()];
		return params.keySet().toArray(keys);
	}
	
	public String[] getMapValues(Map<String,String> params) {
		String[] keys = new String[params.size()];
		return params.values().toArray(keys);
	}
	
	public String[] getCollectionValues(Object collection) {
		Collection c = null;
		String type = collection.getClass().getName();
		if(collection instanceof Collection) {
			c = (Collection)collection;
		}else if(collection instanceof Map) {
			c = ((Map)collection).values();
		}else if(collection instanceof Object[]) {
			c = Arrays.asList((Object[])collection);
		} else if(collection instanceof String) {
			String[] is = this.stringToArray((String)collection);
			if(is != null) {
				c = Arrays.asList(is);
			}
		}
		if(c == null || c.isEmpty()) {
			return null;
		}
		String[] vs = new String[c.size()];
		Iterator ite = c.iterator();
		for(int index = 0; ite.hasNext(); index++) {
			vs[index] = ite.next().toString().trim();
		}
		return vs;
	}
	
    public FieldDef getFieldDef(List<FieldDef> fieldDefs,String fieldName) {
    	for(FieldDef fd : fieldDefs) {
    		if(fieldName.equals(fd.getFieldName())) {
    			return fd;
    		}
    	}
    	return null;
	}
    
    public boolean isCollection(FieldDef fd) {
    	if(fd == null) {
    		return false;
    	}
		Class fCls = null;
		try {
			fCls = Utils.class.forName(fd.getClsName());;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(fCls == null) {
			return false;
		}
		return Collection.class.isAssignableFrom(fCls) || (new Object[0]).getClass().isAssignableFrom(fCls) 
				|| Map.class.isAssignableFrom(fCls);
	}
    
    public String[] stringToArray(String values) {
    	if(values == null || values.trim().equals("")) {
    		return null;
    	}
    	String[] vs = null;
		if(values.startsWith("[") && values.endsWith("]")) {
			values = values.substring(1);
			values = values.substring(0,values.length()-1);
			vs = values.split(",");
		}else {
			vs = new String[1];
			vs[0] = values;
		}
		return vs;
    }
    
    
    public String[] getTreeLabels(FieldDef fd,String[] keys) {
		if(keys == null || keys.length < 1) {
			return null;
		}
	    List<TreeNode> params = fd.getTreeRoots();
		if(params == null || params.isEmpty()) {
			return null;
		}
		
		List<String> l = new ArrayList<String>();
		getTreeLabels(params,l,keys,fd.getValidatedNodeType());
		if(l.isEmpty()) {
			return null;
		}
		
		String[] values = new String[l.size()];
		values = l.toArray(values);
		
		return values;
	}
    
    public void getTreeLabels(List<TreeNode> params, List<String> l, String[] keys,String nodeType) {
    	if(params == null || params.isEmpty()) {
			return;
		}
    	if(keys == null || keys.length < 1) {
			return ;
		}
    	for(TreeNode t : params) {
    		if(t.getNodeType().equals(nodeType)) {
    			for(String k : keys) {
    				if(k.equals(t.getId())) {
    					l.add(t.getLabel());
    					break;
    				}
    			}
    		}
    		getTreeLabels(t.getChildren(),l,keys,nodeType);
    	}
	}
    
    public String getResponse(Object obj,boolean state,String msg) {
		Response resp = new Response(state);
		if(obj != null) {
			resp.setData(JsonUtils.getInstance().toJson(obj, true));
			resp.setClassType(obj.getClass().getName());
		}
		if(msg != null) {
			resp.setMsg(msg);
		}
		return JsonUtils.getInstance().toJson(resp, false);
	}
}
