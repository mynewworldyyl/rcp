package com.digitnexus.core.masterdata;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.digitnexus.core.db.BaseJpalDao;

@Component
public class MaterialTypeDaoImpl extends BaseJpalDao<MaterialType,String>{

	List<MaterialType> getCommonValues(Map<String,String> params) {
		return null;
	}
	
}
