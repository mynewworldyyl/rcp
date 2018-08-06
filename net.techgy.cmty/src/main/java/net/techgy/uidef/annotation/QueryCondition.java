package net.techgy.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.techgy.uidef.UIConstants;
import net.techgy.uidef.UIConstants.DataType;
import net.techgy.uidef.UIConstants.UIType;

@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {

	String name();
	
	DataType dataType() default DataType.String;
	
	String defaultValue() default "";
	
	UIType uiType() default UIType.Text;
	
	String maxValue() default "";
	
	String minValue() default "";
	
	int length() default -1;
	
	String[] availables() default {};
}
