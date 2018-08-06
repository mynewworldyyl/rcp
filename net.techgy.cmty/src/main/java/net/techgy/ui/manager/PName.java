package net.techgy.ui.manager;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({PARAMETER}) 
@Retention(RUNTIME)
public @interface PName {

	String value() default "";
}
