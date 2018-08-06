/**
 * DataDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.techgy.cmty.service;

public class DataDto  implements java.io.Serializable {
    private boolean success;

    private java.lang.String msg;

    private java.lang.String errorCode;

    private java.lang.String factoryCode;

    private java.lang.String data;

    private java.lang.String loginKey;

    private java.lang.String classType;

    private java.lang.String clientId;

    private java.lang.Object obj;

    private java.lang.String containerType;

    public DataDto() {
    }

    public DataDto(
           boolean success,
           java.lang.String msg,
           java.lang.String errorCode,
           java.lang.String factoryCode,
           java.lang.String data,
           java.lang.String loginKey,
           java.lang.String classType,
           java.lang.String clientId,
           java.lang.Object obj,
           java.lang.String containerType) {
           this.success = success;
           this.msg = msg;
           this.errorCode = errorCode;
           this.factoryCode = factoryCode;
           this.data = data;
           this.loginKey = loginKey;
           this.classType = classType;
           this.clientId = clientId;
           this.obj = obj;
           this.containerType = containerType;
    }


    /**
     * Gets the success value for this DataDto.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this DataDto.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the msg value for this DataDto.
     * 
     * @return msg
     */
    public java.lang.String getMsg() {
        return msg;
    }


    /**
     * Sets the msg value for this DataDto.
     * 
     * @param msg
     */
    public void setMsg(java.lang.String msg) {
        this.msg = msg;
    }


    /**
     * Gets the errorCode value for this DataDto.
     * 
     * @return errorCode
     */
    public java.lang.String getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this DataDto.
     * 
     * @param errorCode
     */
    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the factoryCode value for this DataDto.
     * 
     * @return factoryCode
     */
    public java.lang.String getFactoryCode() {
        return factoryCode;
    }


    /**
     * Sets the factoryCode value for this DataDto.
     * 
     * @param factoryCode
     */
    public void setFactoryCode(java.lang.String factoryCode) {
        this.factoryCode = factoryCode;
    }


    /**
     * Gets the data value for this DataDto.
     * 
     * @return data
     */
    public java.lang.String getData() {
        return data;
    }


    /**
     * Sets the data value for this DataDto.
     * 
     * @param data
     */
    public void setData(java.lang.String data) {
        this.data = data;
    }


    /**
     * Gets the loginKey value for this DataDto.
     * 
     * @return loginKey
     */
    public java.lang.String getLoginKey() {
        return loginKey;
    }


    /**
     * Sets the loginKey value for this DataDto.
     * 
     * @param loginKey
     */
    public void setLoginKey(java.lang.String loginKey) {
        this.loginKey = loginKey;
    }


    /**
     * Gets the classType value for this DataDto.
     * 
     * @return classType
     */
    public java.lang.String getClassType() {
        return classType;
    }


    /**
     * Sets the classType value for this DataDto.
     * 
     * @param classType
     */
    public void setClassType(java.lang.String classType) {
        this.classType = classType;
    }


    /**
     * Gets the clientId value for this DataDto.
     * 
     * @return clientId
     */
    public java.lang.String getClientId() {
        return clientId;
    }


    /**
     * Sets the clientId value for this DataDto.
     * 
     * @param clientId
     */
    public void setClientId(java.lang.String clientId) {
        this.clientId = clientId;
    }


    /**
     * Gets the obj value for this DataDto.
     * 
     * @return obj
     */
    public java.lang.Object getObj() {
        return obj;
    }


    /**
     * Sets the obj value for this DataDto.
     * 
     * @param obj
     */
    public void setObj(java.lang.Object obj) {
        this.obj = obj;
    }


    /**
     * Gets the containerType value for this DataDto.
     * 
     * @return containerType
     */
    public java.lang.String getContainerType() {
        return containerType;
    }


    /**
     * Sets the containerType value for this DataDto.
     * 
     * @param containerType
     */
    public void setContainerType(java.lang.String containerType) {
        this.containerType = containerType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataDto)) return false;
        DataDto other = (DataDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.success == other.isSuccess() &&
            ((this.msg==null && other.getMsg()==null) || 
             (this.msg!=null &&
              this.msg.equals(other.getMsg()))) &&
            ((this.errorCode==null && other.getErrorCode()==null) || 
             (this.errorCode!=null &&
              this.errorCode.equals(other.getErrorCode()))) &&
            ((this.factoryCode==null && other.getFactoryCode()==null) || 
             (this.factoryCode!=null &&
              this.factoryCode.equals(other.getFactoryCode()))) &&
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.loginKey==null && other.getLoginKey()==null) || 
             (this.loginKey!=null &&
              this.loginKey.equals(other.getLoginKey()))) &&
            ((this.classType==null && other.getClassType()==null) || 
             (this.classType!=null &&
              this.classType.equals(other.getClassType()))) &&
            ((this.clientId==null && other.getClientId()==null) || 
             (this.clientId!=null &&
              this.clientId.equals(other.getClientId()))) &&
            ((this.obj==null && other.getObj()==null) || 
             (this.obj!=null &&
              this.obj.equals(other.getObj()))) &&
            ((this.containerType==null && other.getContainerType()==null) || 
             (this.containerType!=null &&
              this.containerType.equals(other.getContainerType())));
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
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMsg() != null) {
            _hashCode += getMsg().hashCode();
        }
        if (getErrorCode() != null) {
            _hashCode += getErrorCode().hashCode();
        }
        if (getFactoryCode() != null) {
            _hashCode += getFactoryCode().hashCode();
        }
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getLoginKey() != null) {
            _hashCode += getLoginKey().hashCode();
        }
        if (getClassType() != null) {
            _hashCode += getClassType().hashCode();
        }
        if (getClientId() != null) {
            _hashCode += getClientId().hashCode();
        }
        if (getObj() != null) {
            _hashCode += getObj().hashCode();
        }
        if (getContainerType() != null) {
            _hashCode += getContainerType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "DataDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("success");
        elemField.setXmlName(new javax.xml.namespace.QName("", "success"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "msg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("factoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "factoryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "clientId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("obj");
        elemField.setXmlName(new javax.xml.namespace.QName("", "obj"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containerType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "containerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
