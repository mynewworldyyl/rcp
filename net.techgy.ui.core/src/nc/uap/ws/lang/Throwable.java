/**
 * Throwable.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package nc.uap.ws.lang;

public class Throwable  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.lang.String message1;

    private java.lang.String[] stackTrace1;

    public Throwable() {
    }

    public Throwable(
           java.lang.String message1,
           java.lang.String[] stackTrace1) {
        this.message1 = message1;
        this.stackTrace1 = stackTrace1;
    }


    /**
     * Gets the message1 value for this Throwable.
     * 
     * @return message1
     */
    public java.lang.String getMessage1() {
        return message1;
    }


    /**
     * Sets the message1 value for this Throwable.
     * 
     * @param message1
     */
    public void setMessage1(java.lang.String message1) {
        this.message1 = message1;
    }


    /**
     * Gets the stackTrace1 value for this Throwable.
     * 
     * @return stackTrace1
     */
    public java.lang.String[] getStackTrace1() {
        return stackTrace1;
    }


    /**
     * Sets the stackTrace1 value for this Throwable.
     * 
     * @param stackTrace1
     */
    public void setStackTrace1(java.lang.String[] stackTrace1) {
        this.stackTrace1 = stackTrace1;
    }

    public java.lang.String getStackTrace1(int i) {
        return this.stackTrace1[i];
    }

    public void setStackTrace1(int i, java.lang.String _value) {
        this.stackTrace1[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Throwable)) return false;
        Throwable other = (Throwable) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.message1==null && other.getMessage1()==null) || 
             (this.message1!=null &&
              this.message1.equals(other.getMessage1()))) &&
            ((this.stackTrace1==null && other.getStackTrace1()==null) || 
             (this.stackTrace1!=null &&
              java.util.Arrays.equals(this.stackTrace1, other.getStackTrace1())));
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
        if (getMessage1() != null) {
            _hashCode += getMessage1().hashCode();
        }
        if (getStackTrace1() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStackTrace1());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStackTrace1(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Throwable.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.uap.nc/lang", "Throwable"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.uap.nc/lang", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stackTrace1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.uap.nc/lang", "stackTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.uap.nc/lang", "StackTraceElement"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
