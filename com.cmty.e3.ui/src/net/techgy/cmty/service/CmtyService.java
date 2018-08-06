/**
 * CmtyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.techgy.cmty.service;

public interface CmtyService extends java.rmi.Remote {
    public net.techgy.cmty.service.DataDto service(java.lang.String module, java.lang.String actionType, net.techgy.cmty.service.DataDto syncDataDto) throws java.rmi.RemoteException, net.techgy.cmty.service.Exception;
}
