package com.digitnexus.core.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.digitnexus.core.UserContext;
import com.digitnexus.core.account.Account;

@Aspect
@Component
public class DaoAdvisor {
	private static final String CREATED_ON = "setCreatedOn";
	private static final String UPDATED_ON = "setUpdatedOn";
	private static final String UPDATED_BY = "setUpdatedBy";
	private static final String CREATED_BY = "setCreatedBy";
	
	private static final String CREATED_ON_GET = "getCreatedOn";
	private static final String UPDATED_ON_GET = "getUpdatedOn";
	private static final String UPDATED_BY_GET = "getUpdatedBy";
	private static final String CREATED_BY_GET = "getCreatedBy";
	
	/** 
     * Pointcut 
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数 
     * 该方法就是一个标识，不进行调用 
     */  
    @Pointcut("execution(* com.digitnexus.core.db.BaseJpalDao.save*(..))  ")  
    private void aspectjSaveMethod(){};
    
    @Pointcut("execution(* com.digitnexus.core.db.BaseJpalDao.merge*(..)) "
    		+ " || execution(* com.digitnexus.core.db.BaseJpalDao.update*(..)) "
    		)  
    private void aspectjUpdateMethod(){};
      
    @Before("aspectjUpdateMethod()")    
    public void beforeUpdateAdvice(JoinPoint joinPoint) {
    	//joinPoint.
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length >0) {
        	Object entity = args[0];
        	Class cls = entity.getClass();
        	try {
        		Timestamp t = new Timestamp(System.currentTimeMillis());
        		Method guon = cls.getDeclaredMethod(UPDATED_ON_GET, null);
        		if(guon != null) {
        			Object v = guon.invoke(entity, null);
        			if(v == null) {
        				Method uon = cls.getDeclaredMethod(UPDATED_ON, Date.class);
        				uon.invoke(entity, t);
        			}
        		}
        		Account a = null;
				try {
					a = UserContext.getCurrentUser().getAccount();
				} catch (NullPointerException e) {
				}			
				if(a != null) {
					Method guby = cls.getDeclaredMethod(UPDATED_BY_GET, null);
	        		if(guby != null) {
	        			Object v = guby.invoke(entity, null);
	        			if(v == null) {
	        				Method cby = cls.getDeclaredMethod(UPDATED_BY, Account.class );
	    					cby.invoke(entity, a);
	        			}
	        		}
				}
				
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}
        }
    }  
    
    /**  
     * Before 
     * 在核心业务执行前执行，不能阻止核心业务的调用。 
     * set :
     * private Date createdOn;
     * private Date updatedOn;
     * private Account updatedBy;
     * private Account createdBy;
     * 
     * @param joinPoint  
     */    
    @Before("aspectjSaveMethod()")    
    public void beforeSaveAdvice(JoinPoint joinPoint) {
    	//joinPoint.
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length >0) {
        	Object entity = args[0];
        	Class cls = entity.getClass();
        	try {
        		
        		Timestamp t = new Timestamp(System.currentTimeMillis());
        		Method gcon = cls.getDeclaredMethod(CREATED_ON_GET, null);
        		if(gcon != null) {
        			Object v = gcon.invoke(entity, null);
        			if(v == null) {
        				Method con = cls.getDeclaredMethod(CREATED_ON, Date.class );
        				con.invoke(entity, t);
        			}
        		}
        		Account a = null;
				try {
					a = UserContext.getCurrentUser().getAccount();
				} catch (NullPointerException e) {
				}		
				if(a != null) {
	        		Method gcby = cls.getDeclaredMethod(CREATED_BY_GET, null);
	        		if(gcby != null) {
	        			Object v = gcby.invoke(entity, null);
	        			if(v == null) {
	        				Method uby = cls.getDeclaredMethod(CREATED_BY, Account.class);
	    					uby.invoke(entity, a);
	        			}
	        		}
				}
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}
        }
        beforeUpdateAdvice(joinPoint);
    }  
      
    /**  
     * After  
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice 
     * @param joinPoint 
     */  
   // @After(value = "aspectjSaveMethod()")    
    public void afterAdvice(JoinPoint joinPoint) {    
        System.out.println("-----End of afterAdvice()------");  
    }    
  
    /**  
     * Around  
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理, 
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 
     * 执行完AfterAdvice，再转到ThrowingAdvice 
     * @param pjp 
     * @return 
     * @throws Throwable 
     */   
   // @Around(value = "aspectjSaveMethod()")    
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {    
        System.out.println(" 此处可以做类似于Before Advice的事情");  
        //调用核心逻辑  
        Object retVal = pjp.proceed();  
        System.out.println("-----End of aroundAdvice()------");  
        return retVal;  
    }    
      
    /**  
     * AfterReturning  
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice 
     * @param joinPoint 
     */   
    //@AfterReturning(value = "aspectjSaveMethod()", returning = "retVal")    
    public void afterReturningAdvice(JoinPoint joinPoint, String retVal) {    
        System.out.println("-----End of afterReturningAdvice()------");  
    }  
      
    /** 
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息 
     *  
     * 注意：执行顺序在Around Advice之后 
     * @param joinPoint 
     * @param ex 
     */  
    //@AfterThrowing(value = "aspectjSaveMethod()", throwing = "ex")    
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {    
        System.out.println("-----End of afterThrowingAdvice()------");    
    }    
}
