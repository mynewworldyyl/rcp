package com.digitnexus.core.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.digitnexus.base.uidef.UIConstants.ActionType;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedParam {

	String name() default "";
	
	String value();
}
