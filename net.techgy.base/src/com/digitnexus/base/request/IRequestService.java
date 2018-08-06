package com.digitnexus.base.request;


public interface IRequestService {

	public String getReqDef(String cls);
	
	public String queryReqList(String cls,String jsonBody);
	
    public String getReq(String cls,String reqNum);
    
    public String saveReq(String clsName,String reqJson);
    
    public String delReq(String cls,String reqNum);
 
    public String updateReq(String cls,String req);
    
    public String extAction(String actionComponentName,String method,String jsonBody);
}
