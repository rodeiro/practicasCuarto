package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapOfertaExpirationException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapOfertaExpirationException extends Exception {

    private SoapOfertaExpirationExceptionInfo faultInfo;

    protected SoapOfertaExpirationException(
            SoapOfertaExpirationExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapOfertaExpirationExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}