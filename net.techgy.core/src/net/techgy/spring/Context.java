package net.techgy.spring;

import java.util.ArrayList;
import java.util.List;

import net.techgy.im.handler.IQueueManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Context implements ApplicationContextAware{

	private static ApplicationContext context ;
	
	@Autowired
	private List<ContextListener> listeners = new ArrayList<ContextListener>();
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
            context = applicationContext;
            IQueueManager qm = context.getBean(IQueueManager.class);
            if(qm == null) {
            	throw new NullPointerException("IQueueManager not found");
            }
            qm.init();
            this.init();
	}
	
	private void init() {
		if(this.listeners.isEmpty()) {
			return;
		}
		for(ContextListener l : this.listeners) {
			l.afterContext(context);
		}
	}

	public static <T> T getBean(Class<T> t) {
		return context.getBean(t);
	}
	
}
