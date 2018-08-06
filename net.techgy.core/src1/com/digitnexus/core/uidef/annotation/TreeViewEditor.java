package com.digitnexus.core.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.digitnexus.base.uidef.UIConstants;
import com.digitnexus.base.uidef.UIConstants.ActionType;

@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeViewEditor {

	ItemField[] queryConditions() default {};
	
	String name() default "";
	
	Action[] actions() default {
		@Action(name="Add", url="crudService/save",actionType=ActionType.Add),
		@Action(name="Delete", url="crudService/delete" ,actionType=ActionType.Delete),
		@Action(name="Query", url="crudService/query",actionType=ActionType.Query),
		@Action(name="Save", url="crudService/save",actionType=ActionType.Save),

	};
	
	NamedParam[] namedParams() default {
		@NamedParam(name=UIConstants.UPDATE_PATH,value="crudService/update"),
		@NamedParam(name=UIConstants.UPDATE__METHOD,value="GET"),
	};
	
	//boolean rowEditable() default true;
	
	Class<?> entityCls() default Void.class;
	
	boolean notNeedPerm() default false;
	
}
