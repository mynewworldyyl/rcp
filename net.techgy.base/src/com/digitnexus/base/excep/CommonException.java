package com.digitnexus.base.excep;


public class CommonException extends RuntimeException {
	
	private String i18nKey=null;
	
	private String[] args = null;
	
	public CommonException(String i18nKey,String...args){
    	super(i18nKey);
		this.i18nKey = i18nKey;
		this.args =args;
	}

    public CommonException(String i18nKey,Throwable e,String...args){
    	super(i18nKey,e);
		this.i18nKey = i18nKey;
		this.args = args;
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer(super.getMessage());
		if(this.args != null) {
			for(String a : this.args) {
				sb.append(a);
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return this.getMessage();
	}
    
	
}
