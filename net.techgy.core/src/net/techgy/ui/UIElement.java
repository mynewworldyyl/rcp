package net.techgy.ui;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * support the field currently
 * @author yyl
 *
 */
@Target({METHOD, FIELD}) 
@Retention(RUNTIME)
public @interface UIElement {
	 
	 Class valueCls() default Void.class;
}
