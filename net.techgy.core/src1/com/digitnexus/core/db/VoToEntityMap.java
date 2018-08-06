package com.digitnexus.core.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.techgy.cmty.core.im.vo.MessageVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.digitnexus.base.excep.CommonException;
import com.digitnexus.core.idgenerator.IDUtils;
import com.digitnexus.core.uidef.annotation.TableViewEditor;
import com.digitnexus.core.uidef.annotation.TreeViewEditor;

@Component
public class VoToEntityMap {

	private Map<String,String> voToEntities = new HashMap<String,String>();
	
	private boolean isInit = false;
	
	@Value("#{configProperties['entity.package']}")
	private String entityPackage;
	
	public VoToEntityMap() {
	}
	
	@PostConstruct
	public void init() {
		
		if(this.isInit) {
			return;
		}
		this.isInit = true;
		
		if(entityPackage == null || this.entityPackage.trim().equals("")) {
			this.entityPackage="com.digitnexus";
		}
		Set<Class<?>> classes = IDUtils.getInstance().getClasses(this.entityPackage.split(","));
		for(Class<?> c : classes) {
			Class cls = null;
			if(c.isAnnotationPresent(TableViewEditor.class)) {
				TableViewEditor tv = c.getAnnotation(TableViewEditor.class);
				cls = tv.entityCls();
			}else if( c.isAnnotationPresent(TreeViewEditor.class)) {
				TreeViewEditor tv = c.getAnnotation(TreeViewEditor.class);
				cls = tv.entityCls();
			}
			if(cls != null && cls != Void.class) {
				resister(c.getName(),cls.getName());
			}
		}	
		
	}
	
	public void resister(String voClsName,String entityClsName) {
		if(this.voToEntities.containsKey(voClsName)) {
			throw new CommonException("VoClassNameRepeatRegister",voClsName,entityClsName);
		}
		voToEntities.put(voClsName, entityClsName);
	}
	
	public String getEntityClsName(String voClsName) {
		return voToEntities.get(voClsName);
	}
}
