package com.digitnexus.base.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)
public @interface Info {
    public String name() default "";
	public String[] fields() default {};
	public String[] labels() default {};
}
