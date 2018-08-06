package com.digitnexus.base.protocol;

import java.io.Serializable;

public class Response implements Serializable{

	private boolean isSuccess = true;
	private String msg = "";
	private String appkey = "";
	
	private String classType = "";
	
	private String data = null;
	
	private Object obj = null; 

	public Response() {
		this(true);
	}
	
	public Response(boolean f) {
		this.isSuccess = f;
	}
	
	public Response(boolean f,String msg) {
		this(f);
		this.msg=msg;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
