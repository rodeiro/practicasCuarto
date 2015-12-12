package es.udc.ws.app.exceptions;


@SuppressWarnings("serial")
public class ReservaEmailException extends Exception {

    private String email;

    public ReservaEmailException(String mail) {
        super("Reserva with email=\"" + mail + 
              "\" has already been reserved (expirationDate = \"");
        this.email = mail;
   
    }

    public String getMail() {
        return this.email;
    }


    public void setMail(String mail) {
        this.email = mail;
    }
}