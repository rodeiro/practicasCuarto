package es.udc.ws.app.dto;

import java.util.Calendar;

//import java.util.Calendar;

public class ReservaDto {

    private Long reservaId;
    private String emailUs;
    private String nconta;
    private Long ofertaId;
    private Calendar dataCrea;
    private Boolean estado;

    public ReservaDto() {
    }    
    
    public ReservaDto(Long reservaId,String emailUs,String nconta,Long ofertaId,
    		Calendar dataCrea,Boolean estado) {
    	this.dataCrea = dataCrea;
        this.emailUs = emailUs;
        this.nconta = nconta;
        this.ofertaId = ofertaId;
        this.reservaId = reservaId;
        this.estado=estado;
    }

	public Long getReservaId() {
		return reservaId;
	}

	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}

	public String getEmailUs() {
		return emailUs;
	}

	public void setEmailUs(String emailUs) {
		this.emailUs = emailUs;
	}

	public String getNconta() {
		return nconta;
	}

	public void setNconta(String nconta) {
		this.nconta = nconta;
	}

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
	}

	public Calendar getDataCrea() {
		return dataCrea;
	}

	public void setDataCrea(Calendar dataCrea) {
		this.dataCrea = dataCrea;
	}
	public void setEstado (Boolean estado) {
		this.estado=estado;
	}
	public Boolean getEstado () {
		return this.estado;
	}
}
