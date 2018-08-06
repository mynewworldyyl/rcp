package com.digitnexus.core.masterdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digitnexus.core.idgenerator.ICacheIDGenerator;
import com.digitnexus.core.idgenerator.IDStrategy;

@Component("defaultCodeFactory")
@IDStrategy
public class DefaultMaterialCodeFactory implements IMaterialCodeFactory {

	@Autowired
	protected ICacheIDGenerator generator;
	
	@Override
	public String getCode() {
		return generator.getStringId(DefaultMaterialCodeFactory.class);
	}

}
