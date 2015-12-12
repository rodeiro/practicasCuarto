package es.udc.ws.app.soapservice;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(
    name="SoapMaxNReservaException",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapMaxNReservaException extends Exception {

    private SoapMaxNReservaExceptionInfo faultInfo;

    protected SoapMaxNReservaException(
            SoapMaxNReservaExceptionInfo faultInfo) {
        this.faultInfo = faultInfo;
    }

    public SoapMaxNReservaExceptionInfo getFaultInfo() {
        return faultInfo;
    }
}