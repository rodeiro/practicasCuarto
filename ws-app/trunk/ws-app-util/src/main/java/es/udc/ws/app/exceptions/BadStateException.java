package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class BadStateException extends Exception {

    private Long ofertaID;
    private String estadoO;

    public BadStateException(Long ofertaID, String estadoO) {
        super((estadoO == "true" || estadoO == "false") ? "Reserva with Id: " + ofertaID +
        		"has state " + estadoO
        		: "Oferta with Id: " +ofertaID +  "has state:" + estadoO);
        this.ofertaID = ofertaID;
        this.estadoO = estadoO;
    }

    public Long getOfertaId() {
        return ofertaID;
    }

    public String getEstado() {
        return estadoO;
    }

 
}