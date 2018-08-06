package com.digitnexus.core.idgenerator.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitnexus.core.idgenerator.CacheBaseIDManager;

public class IDProcessor {

	@Autowired
	private TableBaseIDGenerator tableBaseIDGenerator;
	
	@Autowired
	private CacheBaseIDManager cacheBaseIDGenerator;
	
	public static boolean f = false;
	public void work() {
         System.out.println("重置ID值开始"+ new Date(System.currentTimeMillis()));
		tableBaseIDGenerator.resetID();
		cacheBaseIDGenerator.resetID();
        f = true;
        System.out.println("重置ID值结束"+ new Date(System.currentTimeMillis()));
	}
}
