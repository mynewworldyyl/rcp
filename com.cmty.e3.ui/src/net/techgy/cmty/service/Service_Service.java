/**
 * Service_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.techgy.cmty.service;

public interface Service_Service extends javax.xml.rpc.Service {
    public java.lang.String getBaseCmtyServiceImplPortAddress();

    public net.techgy.cmty.service.CmtyService getBaseCmtyServiceImplPort() throws javax.xml.rpc.ServiceException;

    public net.techgy.cmty.service.CmtyService getBaseCmtyServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
