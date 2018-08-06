package net.techgy.spring;

import org.springframework.context.ApplicationContext;

public interface ContextListener {

	void afterContext(ApplicationContext context);
}
