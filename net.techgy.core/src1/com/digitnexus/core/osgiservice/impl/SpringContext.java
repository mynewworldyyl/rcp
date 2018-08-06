package com.digitnexus.core.osgiservice.impl;

import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext  implements ApplicationContextAware{

	 private static ApplicationContext springContext = null;
	 
	 private Map<String, Object>  beans = null;
	 
	 public static ApplicationContext getContext() {
			return springContext;
	 }
	 
	 
	 public Map<String, Object> getBeans() {
		return beans;
	}

	public void setApplicationContext(ApplicationContext springContext) throws BeansException {
		 SpringContext.springContext = springContext;
			beans = springContext.getBeansWithAnnotation(Path.class);
		}
	 
		public <T> T getBean(Class<?> serviceClass) {
			return (T)springContext.getBean(serviceClass);
		}

		public <T> T getBean(String beanName) {
			return (T)springContext.getBean(beanName);
		}
}
