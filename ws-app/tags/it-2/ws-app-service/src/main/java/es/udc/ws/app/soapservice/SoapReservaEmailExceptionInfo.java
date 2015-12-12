package es.udc.ws.app.soapservice;

public class SoapReservaEmailExceptionInfo {

    private String email;
    

    public SoapReservaEmailExceptionInfo() {
    }    
    
    public SoapReservaEmailExceptionInfo(String Email) {
        this.email=Email;
        
    }

    public String getMail() {
        return this.email;
    }

    public void setMail(String Email){
    	this.email=Email;
    }
        
}