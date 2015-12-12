package es.udc.ws.app.soapservice;


public class SoapBadStateExceptionInfo {

    private Long ofertaId;
    private String estado;

    public SoapBadStateExceptionInfo() {
    }

    public SoapBadStateExceptionInfo(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }
    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }    
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estadO) {
        this.estado = estadO;
    }    
    
}
