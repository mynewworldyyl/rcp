package com.digitnexus.core.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.digitnexus.base.uidef.UIConstants.DataType;
import com.digitnexus.base.uidef.UIConstants.UIType;

@Target({ElementType.FIELD,ElementType.TYPE,ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemField {

	String name() default "";
	
	int order();
	
    UIType uiType() default UIType.Text;
    
	boolean isIdable() default false;
	
	String label() default "";
	
	DataType dataType() default DataType.String;
	
	String defaultValue() default "";
	
	Class<?> defaultValueProvider() default Void.class;
	
	String maxValue() default "";
	
	String minValue() default "";
	
	int length() default -1;
	
	String[] availables() default {};
	
	Class<?> valueProvider() default Void.class;
	
	int lengthByChar() default -1;
	
	boolean hide() default false;
	
	boolean editable() default true;
	
	boolean hideInRow() default false;
	
	NamedParam[] namedParams() default {};
	
	Class<?> validatedNodeType() default Void.class;
	
	String[] permClientTypes() default {};
}
