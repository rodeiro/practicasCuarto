
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1.7
 * 
 */
@XmlRootElement(name = "SoapInputValidationException", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoapInputValidationException", namespace = "http://soap.ws.udc.es/", propOrder = {
    "faultInfo",
    "message",
    "suppressed"
})
public class SoapInputValidationExceptionBean {

    private String faultInfo;
    private String message;
    private Throwable[] suppressed;

    /**
     * 
     * @return
     *     returns String
     */
    public String getFaultInfo() {
        return this.faultInfo;
    }

    /**
     * 
     * @param faultInfo
     *     the value for the faultInfo property
     */
    public void setFaultInfo(String faultInfo) {
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 
     * @param message
     *     the value for the message property
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     returns Throwable[]
     */
    public Throwable[] getSuppressed() {
        return this.suppressed;
    }

    /**
     * 
     * @param suppressed
     *     the value for the suppressed property
     */
    public void setSuppressed(Throwable[] suppressed) {
        this.suppressed = suppressed;
    }

}
