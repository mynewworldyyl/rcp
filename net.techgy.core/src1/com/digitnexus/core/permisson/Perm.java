package com.digitnexus.core.permisson;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD})
@Retention(RUNTIME)
public @interface Perm {

	PermActionType actionType();
	
	PermAction action();
}
