package net.techgy.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Aspect help class scaner from spring-context to receive classes from bundle with
 * bundle specific PatternResolver
 * @author Ivan Martyushev
 * 25.03.2008
 */
@Aspect
@Component
public class ClasspathPrefixForOsgiAspect {
    private Logger logger = LoggerFactory.getLogger(ClasspathPrefixForOsgiAspect.class);

    @Pointcut("execution(* " +
    		"org.springframework.context.support.ClassPathXmlApplicationContext.getResources(String)) " +
    		"&& args(locationPattern)")
    void execution_getResources(String locationPattern) {
    }

    @Around("execution_getResources(locationPattern)")
    public Resource[] around_getResources(ProceedingJoinPoint methodExecution,
    		String locationPattern) throws Throwable {
        logger.debug("Current locationPattern = {}", locationPattern);
        return (Resource[]) methodExecution.proceed(new Object[] {
        		fixLocationPatternIfNeeded(locationPattern)});
    }

    String fixLocationPatternIfNeeded(String locationPattern) {
        if ((locationPattern.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX))) {
            String result = /*OsgiBundleResource.BUNDLE_URL_PREFIX +*/
                    locationPattern.substring(
                    		ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX.length());
            logger.debug("FixedLocationPattern = {}", result);
            return result;
        }
        return locationPattern;
    }
}
