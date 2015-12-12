package es.udc.ws.app.soapservice;


public class SoapMaxNReservaExceptionInfo {

    private Long ofertaId;
    private Integer maxI;

    public SoapMaxNReservaExceptionInfo() {
    }

    public SoapMaxNReservaExceptionInfo(Long ofertaId, Integer maxI) {
        this.ofertaId = ofertaId;
        this.maxI = maxI;
    }

	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
	}

	public Integer getMaxI() {
		return maxI;
	}

	public void setMaxI(Integer maxI) {
		this.maxI = maxI;
	}

    
}
