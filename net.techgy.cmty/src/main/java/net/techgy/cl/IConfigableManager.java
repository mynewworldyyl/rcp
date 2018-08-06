package net.techgy.cl;

import java.util.List;
import java.util.Map;

public interface IConfigableManager {

	  <T> boolean create(String managerName,String methodName,Map<String,String> args);
	    
	  <T> boolean update(String managerName,String methodName,Map<String,String> args);
	    
	  boolean remove(String managerName,String methodName,Map<String,String> args);
	    
	  <T> List<T> query(String managerName,String methodName,Map<String,String> args);
	  
	  <T> T queryUniqueResult(String managerName,String methodName,Map<String,String> args);
	    
	  <T> T execute(String managerName,String methodName,Map<String,String> args);
}
