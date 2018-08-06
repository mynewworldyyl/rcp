package com.digitnexus.core.idgenerator.impl;

import java.util.List;
import java.util.Set;

import com.digitnexus.core.db.IBaseDao;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDAssignment;

public interface IIDAssignmentDao extends IBaseDao<IDAssignment,String>{

	Integer maxPrefixValue(String tableName,String...prefixStrs);
	
	 List<IDAssignment>  loadAllUsingAssigment(String serverId);
	
	boolean isExistPrefix(IDAssignment pa);
	
	void updatePrefixAssignmentConfig(IDAssignment pa);
		
	void updateIdAssignmentValue(IDAssignment pa, long idNum);
	
	void updateIdAssignmentStatu(IDAssignment pa);
	
	List<Client> getClient(Set<String> clientType);
	
	void resetID(String entityId, Long value);
}
