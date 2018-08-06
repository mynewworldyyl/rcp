package com.digitnexus.core.idgenerator.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.digitnexus.core.db.BaseJpalDao;
import com.digitnexus.core.dept.Client;
import com.digitnexus.core.idgenerator.IDAssignment;

@Repository("idAssignmentDao")
public class IDAssignmentDao extends BaseJpalDao<IDAssignment,String> implements IIDAssignmentDao {

	@Override
	public Integer maxPrefixValue(String tableName,String...clientId) {
		String jplStr = " SELECT max(a.prefixValue) FROM IDAssignment a where a.entityId=:entityId ";
		if(clientId != null && clientId.length >= 1) {
			jplStr = jplStr + " and a.clientId=:clientId";
		}		
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("entityId", tableName);
		if(clientId != null && clientId.length >= 1 ) {
			query.setParameter("clientId", clientId[0]);
		}
		Integer value = (Integer)query.getSingleResult();
		return value;
	}

	@Override
	public List<IDAssignment> loadAllUsingAssigment(String serverId) {
		String jplStr = "select s from IDAssignment s where s.serverId= :serverId and s.statu='using' ";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("serverId", serverId);
		List<IDAssignment> ll = query.getResultList();
		return ll;
	}

	@Override
	public boolean isExistPrefix(IDAssignment pa) {
		String jplStr = " SELECT a FROM IDAssignment a where a.serverId=:serverId and a.entityId=:entityId and a.statu='using' and a.clientId=:clientId";
		Query query = this.getEntityManager().createQuery(jplStr);
		String sid = pa.getServerId();
		String eid = pa.getEntityId();
		query.setParameter("serverId", sid);
		query.setParameter("entityId", eid);
		query.setParameter("clientId", pa.getClientId());
		Object o;
		try {
			o = query.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return o != null;
	}

	@Override
	public void updatePrefixAssignmentConfig(IDAssignment pa) {
		String jplStr = " update IDAssignment a set a.prefixValueLen=:prefixValueLen,a.idValueType=:idValueType,"
				+ " a.idStepLen=:idStepLen, a.tableName=:tableName  where a.serverId=:serverId and a.entityId=:entityId  and a.statu='using' and a.clientId=:clientId";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("prefixValueLen", pa.getPrefixValueLen());
		query.setParameter("idValueType", pa.getIdValueType());
		query.setParameter("idStepLen", pa.getIdStepLen());
		query.setParameter("tableName", pa.getTableName());
		
		query.setParameter("serverId", pa.getServerId());
		query.setParameter("entityId", pa.getEntityId());
		query.setParameter("clientId", pa.getClientId());
		
		query.executeUpdate();
	}

	@Override
	public void updateIdAssignmentStatu(IDAssignment pa) {
		// TODO Auto-generated method stub
		String jplStr = " update IDAssignment a set a.statu=:statu"
				+ " where a.serverId= :serverId and a.entityId=:entityId and a.clientId=:clientId";
		Query query = this.getEntityManager().createQuery(jplStr);
		
		query.setParameter("statu", pa.getStatu());		
		query.setParameter("serverId", pa.getServerId());
		query.setParameter("entityId", pa.getEntityId());
		query.setParameter("clientId", pa.getClientId());
		
		query.executeUpdate();
	}	
	
	@Override
	public void updateIdAssignmentValue(IDAssignment pa, long idNum) {
		// TODO Auto-generated method stub
		String jplStr = " update IDAssignment a set a.idValue = a.idValue + :idNum where a.serverId= :serverId and a.entityId=:entityId and a.statu='using'  and a.clientId=:clientId";
		Query query = this.getEntityManager().createQuery(jplStr);
		
		query.setParameter("idNum", idNum);
		query.setParameter("serverId", pa.getServerId());
		query.setParameter("entityId", pa.getEntityId());
		query.setParameter("clientId", pa.getClientId());
		
		query.executeUpdate();
	}

	@Override
	public List<Client> getClient(Set<String> clientType) {
		String jplStr = "select c from com.dn.soa.entity.Client c where c.typecode in :typeCodes ";
		Query query = this.getEntityManager().createQuery(jplStr);
		query.setParameter("typeCodes", clientType);
		List<Client> ll = query.getResultList();	
		return ll;
	}

	@Override
	public void resetID(String entityId, Long value) {
		String jplStr = " update IDAssignment a set a.idValue = :idValue where a.entityId=:entityId and a.statu='using' ";
		Query query = this.getEntityManager().createQuery(jplStr);
		
		query.setParameter("idValue", value);
		query.setParameter("entityId", entityId);
		
		query.executeUpdate();
	}	
	
	
	
}
