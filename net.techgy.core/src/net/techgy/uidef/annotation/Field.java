package net.techgy.uidef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.techgy.uidef.UIConstants;
import net.techgy.uidef.UIConstants.UIType;

@Target({ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

	String name() default "";
	
	int order() default -1;

	UIType uiType() default UIType.Text;
}
