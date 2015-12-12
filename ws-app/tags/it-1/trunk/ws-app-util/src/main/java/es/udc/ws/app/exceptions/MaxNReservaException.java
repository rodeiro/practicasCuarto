package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class MaxNReservaException extends Exception {

    private Long ofertaID;
    private int maxI;

    public MaxNReservaException(Long ofertaID, int maxI) {
        super("Reserva with id=\"" + ofertaID + 
              "\" has reached maximun number of" +maxI +"reserves"  );
        this.ofertaID = ofertaID;
        this.maxI = maxI;
    }

    public Long getOfertaId() {
        return ofertaID;
    }

    public int getMaxI () {
        return maxI;
    }

 
}