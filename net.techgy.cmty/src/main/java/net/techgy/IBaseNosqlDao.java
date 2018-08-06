package net.techgy;

import java.io.Serializable;


import org.springframework.data.mongodb.core.MongoTemplate;

public interface IBaseNosqlDao<T,ID extends Serializable> extends IBaseDao<T,ID>{

	MongoTemplate getTemplate();
}