package net.techgy.ipojo.impl;

import net.techgy.ipojo.IPojoService;

public class PojoServiceImpl implements IPojoService{

	@Override
	public String sayHello(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg + ", you have got pojo service!");
		return msg;
	}

	
}
