package com.digitnexus.core.idgenerator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface IDStrategy {
	 //cannot updatable, will get the server id by default
	  public String serverId() default "0";
	  /*
	   * cannot updatable, it will create a new entity if you update this value for specify Entity.
	   * default value: 
	   *     a. get this entiryId value annotation for this class;
	   *     b. if a is not specify, get the Table annotation name;
	   *     c. if b not found, get entity class name.
	   */
	  public String entiryId() default "";  
	  //updatable
	  public int prefixValueLen() default 4;
	  //updatable
	  public String  idValueType() default "";
	  //public long idInitValue() default 1l;
	  //updatable
	  public int idStepLen() default 1;
	 
	  public int cacheSize() default 1;
	  
	  public String tableName() default "";
	  
	  public String radix() default "radix10";
	  
	  public boolean useClient() default false;
	  
	  public boolean resetByTimer() default false;
	  
	  //M001 factory, C001 project, S001 vendor, A001 big area
	  public String clientType() default "M001";// ClientTypeCode.Manufacture.value;
}
