package com.digitnexus.base.service;


public interface IClientService {

/*	<T> T getBean(Class<?> serviceClass);
	
	<T> T getBean(String beanName)*/;
	
	String call(String url,String jsonParams);
	
	/**
	 * Hide from client.
	 * 
	 * @param url
	 * @param jsonParams
	 * @return
	 * @throws Exception
	 */
	//String processCall(String url, String jsonParams) throws Exception;
}
