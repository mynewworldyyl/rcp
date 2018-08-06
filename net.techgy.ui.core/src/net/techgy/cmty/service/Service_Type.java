/**
 * Service_Type.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.techgy.cmty.service;

public class Service_Type  implements java.io.Serializable {
    private java.lang.String module;

    private java.lang.String actionType;

    private net.techgy.cmty.service.DataDto syncDataDto;

    public Service_Type() {
    }

    public Service_Type(
           java.lang.String module,
           java.lang.String actionType,
           net.techgy.cmty.service.DataDto syncDataDto) {
           this.module = module;
           this.actionType = actionType;
           this.syncDataDto = syncDataDto;
    }


    /**
     * Gets the module value for this Service_Type.
     * 
     * @return module
     */
    public java.lang.String getModule() {
        return module;
    }


    /**
     * Sets the module value for this Service_Type.
     * 
     * @param module
     */
    public void setModule(java.lang.String module) {
        this.module = module;
    }


    /**
     * Gets the actionType value for this Service_Type.
     * 
     * @return actionType
     */
    public java.lang.String getActionType() {
        return actionType;
    }


    /**
     * Sets the actionType value for this Service_Type.
     * 
     * @param actionType
     */
    public void setActionType(java.lang.String actionType) {
        this.actionType = actionType;
    }


    /**
     * Gets the syncDataDto value for this Service_Type.
     * 
     * @return syncDataDto
     */
    public net.techgy.cmty.service.DataDto getSyncDataDto() {
        return syncDataDto;
    }


    /**
     * Sets the syncDataDto value for this Service_Type.
     * 
     * @param syncDataDto
     */
    public void setSyncDataDto(net.techgy.cmty.service.DataDto syncDataDto) {
        this.syncDataDto = syncDataDto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Service_Type)) return false;
        Service_Type other = (Service_Type) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.module==null && other.getModule()==null) || 
             (this.module!=null &&
              this.module.equals(other.getModule()))) &&
            ((this.actionType==null && other.getActionType()==null) || 
             (this.actionType!=null &&
              this.actionType.equals(other.getActionType()))) &&
            ((this.syncDataDto==null && other.getSyncDataDto()==null) || 
             (this.syncDataDto!=null &&
              this.syncDataDto.equals(other.getSyncDataDto())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getModule() != null) {
            _hashCode += getModule().hashCode();
        }
        if (getActionType() != null) {
            _hashCode += getActionType().hashCode();
        }
        if (getSyncDataDto() != null) {
            _hashCode += getSyncDataDto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Service_Type.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "service"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module");
        elemField.setXmlName(new javax.xml.namespace.QName("", "module"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syncDataDto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "syncDataDto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "DataDto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
