package es.udc.ws.app.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ReservaExpirationException extends Exception {

    private Long reservaId;
    private Calendar dataDis;

    public ReservaExpirationException(Long reservaId, Calendar dataDis) {
        super("Reserva with id=\"" + reservaId + 
              "\" has expired (expirationDate = \"" + 
              dataDis + "\")");
        this.reservaId = reservaId;
        this.dataDis = dataDis;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public Calendar getdataDis() {
        return dataDis;
    }

    public void setDataDis(Calendar expirationDate) {
        this.dataDis = expirationDate;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }
}