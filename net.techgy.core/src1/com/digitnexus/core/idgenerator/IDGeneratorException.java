package com.digitnexus.core.idgenerator;

public class IDGeneratorException extends RuntimeException {

	public static final long serialVersionUID = -234329234l;
	
	public IDGeneratorException(){
		super();
	}
	
	public IDGeneratorException(String msg){
		super(msg,null);
	}
	
    public IDGeneratorException(String message, Throwable cause){
    	super(message,cause);
	}
    
    public IDGeneratorException(Throwable cause){
    	super(cause);
   	}
}
