package net.techgy.cmty.service;

public class CmtyServiceProxy implements net.techgy.cmty.service.CmtyService {
  private String _endpoint = null;
  private net.techgy.cmty.service.CmtyService cmtyService = null;
  
  public CmtyServiceProxy() {
    _initCmtyServiceProxy();
  }
  
  public CmtyServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCmtyServiceProxy();
  }
  
  private void _initCmtyServiceProxy() {
    try {
      cmtyService = (new net.techgy.cmty.service.Service_ServiceLocator()).getBaseCmtyServiceImplPort();
      if (cmtyService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cmtyService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)cmtyService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cmtyService != null)
      ((javax.xml.rpc.Stub)cmtyService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public net.techgy.cmty.service.CmtyService getCmtyService() {
    if (cmtyService == null)
      _initCmtyServiceProxy();
    return cmtyService;
  }
  
  public net.techgy.cmty.service.DataDto service(java.lang.String module, java.lang.String actionType, net.techgy.cmty.service.DataDto syncDataDto) throws java.rmi.RemoteException, net.techgy.cmty.service.Exception{
    if (cmtyService == null)
      _initCmtyServiceProxy();
    return cmtyService.service(module, actionType, syncDataDto);
  }
  
  
}