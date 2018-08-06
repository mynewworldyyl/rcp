package net.techgy.ui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface UIQuery {

	 //use for complex type to get the display value which should be add by the field names
	 String[] uiValueFields() default "";
	 
	 String valueSeperator() default ":";
	 
	 String elementSeperator() default ",";
	 /*
	  * the column name for the table
	  */
	 String displayName() default "";
	 
	 int seq() default -1;	
	 
	 boolean unvisible() default false;
	 
	 String resKey() default "";
	 
	 String conditionType() default "";
	 
	 
}
