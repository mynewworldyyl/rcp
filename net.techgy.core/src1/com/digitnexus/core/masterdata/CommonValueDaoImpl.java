package com.digitnexus.core.masterdata;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class CommonValueDaoImpl extends BaseJpalDao<CommonValue,String>{

	List<CommonValue> getCommonValues(Map<String,String> params) {
		return null;
	}
	
	public CommonValue getVendorType(String id) {
		StringBuffer sb = new StringBuffer("SELECT DISTINCT a FROM CommonValue a WHERE 1=1 ");
		sb.append(" AND a.typecode='").append(CommonValue.VENDOR_TYPE).append("'")
		  .append(" AND a.id='").append(id).append("'");
		return (CommonValue)this.getEntityManager().createQuery(sb.toString()).getSingleResult();
	}
	
	
}
