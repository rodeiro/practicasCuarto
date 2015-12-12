package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class OfertaExpirationException extends Exception {

    private Long ofertaId;
    private Calendar dataFin;

    public OfertaExpirationException(Long ofertaId, Calendar dataFin) {
        super("Oferta with id=\"" + ofertaId + 
              "\" has expired (expirationDate = \"" + 
              dataFin + "\")");
        this.ofertaId = ofertaId;
        this.dataFin = dataFin;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public Calendar getDataFin() {
        return dataFin;
    }

    public void setDataFin(Calendar expirationDate) {
        this.dataFin = expirationDate;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }
}