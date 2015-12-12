package es.udc.ws.app.model.reserva;

import java.util.Calendar;



public class Reserva {

    private Long reservaId;
    private boolean estadoRes;
    private String emailUs;
    private String nconta;
    private Long ofertaId;
    private Calendar dataCrea;
    
    
    
    public Reserva(Long reservaId,String email,boolean estadoRes, String conta,Long oid, 
    		Calendar dataCrea){
    	this.ofertaId = oid;
    	this.emailUs = email;
    	this.nconta = conta;    	
    	this.estadoRes = estadoRes;
    	this.dataCrea = dataCrea;
    	this.reservaId = reservaId;
    }	
    
    public Reserva(String email, String conta,Long oid){
    	this.ofertaId = oid;
    	this.emailUs = email;
    	this.nconta = conta;    	
    	this.estadoRes = false;
    	this.dataCrea=Calendar.getInstance();
    	this.dataCrea.set(Calendar.MILLISECOND, 0);
    	
    	
    }
    public Reserva(Long reservaId,String email, String conta,Long oid){
    	this(email,conta,oid); 	
    	this.dataCrea=Calendar.getInstance();
    	this.dataCrea.set(Calendar.MILLISECOND, 0);
    	this.reservaId = reservaId;
    }
    public Long getReservaId() {
        return reservaId;
    }
    
    public Calendar getDataCrea(){
    	return dataCrea;
    }
    
    public void setestadoRes(boolean estado) {
        this.estadoRes=estado;
    }
    public boolean getestadoRes() {
        return estadoRes;
    }
    public void setemailUs(String email) {
        this.emailUs=email;
    }
    public String getemailUs() {
        return emailUs;
    }
    public void setnconta(String nconta) {
        this.nconta=nconta;
    }
    public String getnconta() {
        return nconta;
    }
    public Long getOfid() {
        return ofertaId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Reserva other = (Reserva) obj;       

        if (reservaId == null) {
            if (other.reservaId != null) {
                return false;
            }
        } else if (!reservaId.equals(other.reservaId)) {
            return false;
        }
        if (emailUs == null) {
            if (other.emailUs != null) {
                return false;
            }
        } else if (!emailUs.equals(other.emailUs)) {
            return false;
        }
        if (nconta == null) {
            if (other.nconta != null) {
                return false;
            }
        } else if (!nconta.equals(other.nconta)) {
            return false;
        }
        if (ofertaId == null) {
            if (other.ofertaId != null) {
                return false;
            }
        } else if (!ofertaId.equals(other.ofertaId)) {
            return false;
        }
        
        if (estadoRes && other.estadoRes) {
        	if (estadoRes ^ other.estadoRes)
        		return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reservaId == null) ? 0 : reservaId.hashCode());
        result = prime * result + ((ofertaId == null) ? 0 : ofertaId.hashCode());
        result = prime * result + ((emailUs == null) ? 0 : emailUs.hashCode());
        return result;
    }
}    