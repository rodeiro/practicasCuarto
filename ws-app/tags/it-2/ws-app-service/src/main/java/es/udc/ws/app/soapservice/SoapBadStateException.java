package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapBadStateException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapBadStateException extends Exception {

    private SoapBadStateExceptionInfo faultInfo;

    protected SoapBadStateException(
            SoapBadStateExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapBadStateExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}
