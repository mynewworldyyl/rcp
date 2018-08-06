package com.digitnexus.core.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.digitnexus.base.uidef.UIConstants.ActionType;
import com.digitnexus.core.dept.ClientType;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Action{

	ActionType actionType();
	
	String name() default "";
	
	String url();
	
	String method() default "POST";
	
	NamedParam[] namedParams() default {};
	
	String[] permClientTypes() default {ClientType.Admin,ClientType.Region,ClientType.Factory};
}
