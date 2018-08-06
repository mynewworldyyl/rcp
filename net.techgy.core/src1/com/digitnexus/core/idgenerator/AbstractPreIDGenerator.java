package com.digitnexus.core.idgenerator;



public abstract class AbstractPreIDGenerator implements IIDGenerator {

/*	@Override
	@Transactional
	public Integer createNumPrefix(String serverId, String tableName)  throws IDGeneratorException{
		return this.createPrefix(serverId,tableName);
	}

	@Override
	@Transactional
	public String createStrPrefix(String serverId, String tableName) throws IDGeneratorException{
		Integer value = this.createPrefix(serverId,tableName);
		if(value != null) {
			return value.toString();
		}
		throw new IDGeneratorException();
	}*/
	
	
}
