package com.digitnexus.core.idgenerator;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.UserContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("cacheBaseIDManager")
public class CacheBaseIDManager implements ICacheIDGenerator{

	private static final String HTTP_PARAM_ENTITY_ID="entityId";
	private static final String HTTP_PARAM_ID_NUM="idNum";
	private static final String HTTP_PARAM_CLIENT_ID="clientId";
	private static final String HTTP_PARAM_ID_LEN="idLen";
	private static final String HTTP_PARAM_PREFIX="idPrefix";
	
	private static final String HTTP_PARAM_NUMBER_ID="numberId";
	private static final String HTTP_PARAM_STRING_ID="stringId";
	
	private Map<String,Queue<Object>> idcacheMap = new HashMap<String,Queue<Object>>();
	
	private Map<String,IDStrategy> cacheSizeMap = new HashMap<String,IDStrategy>();
	
	@Value("#{configProperties['maxCacheSize']}")
	private int  maxCacheSize;
	
	@Value("#{configProperties['isMainServer']}")
	private boolean isMainServer;
	
	@Value("#{configProperties['idServiceUrl']}")
	private String idServiceUrl;
	
	@Value("#{configProperties['connectionTimeout']}")
	private int connectionTimeout;
	
	@Autowired
	private AbstractPreIDGenerator generator;
	
	@Value("#{configProperties['entity.package']}")
	private String entityPackage;

	@Value("#{configProperties['http.timeout']}")
	private int timeout = 0;
	
	private boolean isInit = false;
	
	private  HttpClient httpClient = null;
	
	@SuppressWarnings("deprecation")
	private void  initHttpClient() {
		 httpClient = new DefaultHttpClient();
         HttpParams httpParams = httpClient.getParams();
         HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
         HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
         
         HttpConnectionParams.setSoTimeout(httpParams, timeout);
	}

	private void doInitConfig() {
		if (this.isInit) {
			return;
		}
		this.isInit = true;

		initHttpClient();

		if (entityPackage == null || this.entityPackage.trim().equals("")) {
			this.entityPackage = "com.digitnexus";
		}
		Set<Class<?>> classes = IDUtils.getInstance().getClasses(
				entityPackage.split(","));
		for (Class<?> c : classes) {
			if (!c.isAnnotationPresent(IDStrategy.class)) {
				continue;
			}
			IDStrategy s = c.getAnnotation(IDStrategy.class);
			String entityId = IDUtils.getInstance().getEntityID(c);
			cacheSizeMap.put(entityId, s);
		}

	}
	
	@Override
	public String getStringId(Class<?> entityCls) {
		return this.getStringIds(entityCls,null, 1, 0).iterator().next();
	}

	@Override
	public String getStringId(Class<?> entityCls,String clientId, String...prefixStrs) {
		return this.getStringIds(entityCls,clientId, 1, 0, prefixStrs).iterator().next();
	}
	
	@Override
	public Set<String> getStringIds(Class<?> entityCls,String clientId, int idNum,String...prefixStrs) {
		Set<String> ids = this.getStringIds(entityCls,clientId, idNum, 0, prefixStrs);
		return ids;
	}
	

	@Override
	public String getStringId(Class<?> entityCls,String clientId, int idLen, String... prefixStrs) {
		return this.getStringIds(entityCls,clientId, 1, idLen, prefixStrs).iterator().next();
	}

	private String getKey(String entityId,String clientId) {
		String key = null;
		if(clientId == null || "".equals(clientId.trim())) {
			key = entityId+"_0";
		}else {
			key = entityId+"_" + clientId;
		}
		return key;
	}

	@Override
	public Set<String> getStringIds(Class<?> entityCls,String clientId, int idNum, int idLen, String... prefixStrs) {
		this.doInitConfig();
		String entityId = IDUtils.getInstance().getEntityID(entityCls);
		String key = this.getKey(entityId, clientId);
		IDStrategy idstrategy = this.cacheSizeMap.get(entityId);
		if(idstrategy == null) {
			throw new CommonException("IDStrategyNotExist",entityId);
		}
		int cacheSize = idstrategy.cacheSize();
		/*
		 if(cacheSize == null || cacheSize <=1) {
			Set<String> cacheIds = null;
			if(this.isIdService) {
				cacheIds = this.generator.getStringIds(entityCls,1,idLen,prefixStrs);
			}else {
				cacheIds = this.doReqStringRemoteIds(entityCls,1,idLen,prefixStrs);
			}
			return cacheIds;
		}
		*/
		Queue<Object> q = this.idcacheMap.get(key);
		if(q == null) {
			cacheSize = cacheSize > this.maxCacheSize ? this.maxCacheSize:cacheSize;
			q = new LinkedBlockingQueue<Object>();
			this.idcacheMap.put(key, q);
		}		
		if(q.size() < idNum) {
			cacheSize = cacheSize > this.maxCacheSize ? this.maxCacheSize:cacheSize;
			int needIdNum =  cacheSize < 1 ? 1: cacheSize;
			needIdNum = needIdNum < idNum ? idNum : needIdNum;
			Set<String> cacheIds = null;
			if(this.isMainServer) {
				cacheIds = this.generator.getStringIds(entityCls,clientId,needIdNum,idLen,prefixStrs);
			} else {
				cacheIds = this.doReqStringRemoteIds(entityCls,needIdNum,idLen,prefixStrs);
			}
			q.addAll(cacheIds);
		}
		Set<String> ids = new HashSet<String>();
	    for(;idNum >0;idNum--) {
	    	ids.add((String)q.poll());
	    }
		return ids;
	}


	private Set<String>  doReqStringRemoteIds(Class<?> entityCls, int idNum, int idLen,String[] prefixStrs) {
		
         StringBuffer sb = new StringBuffer(this.idServiceUrl);
         sb.append(HTTP_PARAM_STRING_ID)
         .append("?")
         .append(HTTP_PARAM_ENTITY_ID).append("=").append(entityCls.getName())
         .append("&").append(HTTP_PARAM_ID_NUM).append("=").append(idNum)
         .append("&").append(HTTP_PARAM_ID_LEN).append("=").append(idLen)
         .append("&").append(HTTP_PARAM_CLIENT_ID).append("=").append(UserContext.getCurrentClientId());
         
         String pre = "";
         if(prefixStrs != null && prefixStrs.length >0) {
        	 StringBuffer sb1 = new StringBuffer();
        	 for(String s : prefixStrs) {
        		 sb1.append(s).append(",");
        	 }
        	 sb1.deleteCharAt(sb1.length()-1);
        	 pre = sb1.toString();
         }
         sb.append("&").append(HTTP_PARAM_PREFIX).append("=").append(pre);
         
         HttpUriRequest request = new HttpGet(sb.toString());
         Set<String>  ids= null;
         String content = null;
         try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
		    content = EntityUtils.toString(entity, HttpProtocolParams.getContentCharset(httpClient.getParams()));
			Type type = new TypeToken<Set<String>>(){}.getType();
			content=content.substring(1, content.length()-1);
			content = content.replaceAll("\\\\", "");
			ids = new Gson().fromJson(content, type);
		} catch (Exception e) {
		    throw new RuntimeException(content,e);
		} 
		return ids;
	}
	
	private <T extends Number> Set<T>  doReqNumRemoteIds(Class<?> entityCls, int idNum) {
		
		HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();
        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        HttpConnectionParams.setConnectionTimeout(httpParams, connectionTimeout);
        
        HttpConnectionParams.setSoTimeout(httpParams, timeout);

        StringBuffer sb = new StringBuffer(this.idServiceUrl);
        sb.append(HTTP_PARAM_NUMBER_ID)
        .append("?")
        .append(HTTP_PARAM_ENTITY_ID).append("=").append(entityCls.getName())
        .append("&").append(HTTP_PARAM_ID_NUM).append("=").append(idNum)
        .append("&").append(HTTP_PARAM_CLIENT_ID).append("=").append(UserContext.getCurrentClientId());
        
        HttpUriRequest request = new HttpGet(sb.toString());
        Set<T>  ids= null;
        try {
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity, HttpProtocolParams.getContentCharset(httpParams));
			content=content.substring(1, content.length()-1);
			Type type = new TypeToken<Set<T>>(){}.getType();
			ids = new Gson().fromJson(content, type);			
		}  catch (Exception e) {
		    throw new RuntimeException(e);
		} 
       httpClient.getConnectionManager().shutdown();
		return ids;
	}


	@Override
	public <T extends Number> T getNumId(Class<?> entityCls,String clientId) {
		return (T)this.getNumIds(entityCls,clientId,1).iterator().next();
	}

	@Override
	public IDAssignment createNewPrefixIDAssignment(IDAssignment pa) {
		return generator.createNewPrefixIDAssignment(pa);
	}


	@Override
	public <T extends Number> Set<T> getNumIds(Class<?> entityCls,String clientId,int idNum) {
		this.doInitConfig();
		String entityId = IDUtils.getInstance().getEntityID(entityCls);
		IDStrategy idstrategy = this.cacheSizeMap.get(entityId);
		Integer cacheSize1 = idstrategy.cacheSize();
		String key = this.getKey(entityId, clientId);
		Queue<Object> q = this.idcacheMap.get(key);
		int cacheSize = cacheSize1 == null ? 0 : cacheSize1;
		if(q == null) {
			cacheSize = cacheSize > this.maxCacheSize ? cacheSize:  this.maxCacheSize;
			q = new LinkedBlockingQueue<Object>();
			this.idcacheMap.put(key, q);
		}		
		if(q.size() < idNum) {
			cacheSize = cacheSize > this.maxCacheSize ? this.maxCacheSize:cacheSize;
			int needIdNum =  cacheSize < 1 ? 1: cacheSize;
			needIdNum = needIdNum < idNum ? idNum : needIdNum;
			Set<T> cacheIds = null;
			if(this.isMainServer) {
				cacheIds =  this.generator.getNumIds(entityCls,clientId, needIdNum);
			}else {
				/*if(needIdNum < 10) {
					needIdNum = 10;
				}*/
				cacheIds = this.doReqNumRemoteIds(entityCls,needIdNum);
			}
			q.addAll(cacheIds);
		}
		Set<T> ids = new HashSet<T>();
	    for(;idNum >0;idNum--) {
	    	ids.add((T)q.poll());
	    }
		return ids;
	}
	
	public void resetID() {
		
	}
}
