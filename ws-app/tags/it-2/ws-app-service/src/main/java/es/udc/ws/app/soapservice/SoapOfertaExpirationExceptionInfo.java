package es.udc.ws.app.soapservice;

import java.util.Calendar;

public class SoapOfertaExpirationExceptionInfo {

    private Long ofertaId;
    private Calendar dataFin;

    public SoapOfertaExpirationExceptionInfo() {
    }

    public SoapOfertaExpirationExceptionInfo(Long ofertaId, 
                                           Calendar dataFin) {
        this.ofertaId = ofertaId;
        this.dataFin = dataFin;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public Calendar getDataFin() {
        return dataFin;
    }

    public void setDataFin(Calendar dataFin) {
        this.dataFin = dataFin;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }    
    
}
