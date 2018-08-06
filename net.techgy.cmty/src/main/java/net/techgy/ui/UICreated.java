package net.techgy.ui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface UICreated {

	 String uiType() default UIConstants.UI_TYPE_TEXT;
	 //specify constants such as UIConstants.DATA_TYPES
	 
	 String[] values() default {};
	 
	 String defValue() default "";
	 
	 boolean notCreated() default false;
	 
}
