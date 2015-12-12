package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapReservaEmailException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapReservaEmailException extends Exception {

    private SoapReservaEmailExceptionInfo faultInfo;  
    
    protected SoapReservaEmailException(
            SoapReservaEmailExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapReservaEmailExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
