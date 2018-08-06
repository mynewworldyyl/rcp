/**
 * ServiceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.techgy.cmty.service;

public class ServiceResponse  implements java.io.Serializable {
    private net.techgy.cmty.service.DataDto dataDto;

    public ServiceResponse() {
    }

    public ServiceResponse(
           net.techgy.cmty.service.DataDto dataDto) {
           this.dataDto = dataDto;
    }


    /**
     * Gets the dataDto value for this ServiceResponse.
     * 
     * @return dataDto
     */
    public net.techgy.cmty.service.DataDto getDataDto() {
        return dataDto;
    }


    /**
     * Sets the dataDto value for this ServiceResponse.
     * 
     * @param dataDto
     */
    public void setDataDto(net.techgy.cmty.service.DataDto dataDto) {
        this.dataDto = dataDto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceResponse)) return false;
        ServiceResponse other = (ServiceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataDto==null && other.getDataDto()==null) || 
             (this.dataDto!=null &&
              this.dataDto.equals(other.getDataDto())));
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
        if (getDataDto() != null) {
            _hashCode += getDataDto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "serviceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataDto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://service.cmty.techgy.net", "dataDto"));
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
