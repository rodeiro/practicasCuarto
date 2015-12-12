package es.udc.ws.app.dto;

import java.util.Calendar;


public class OfertaDto {

    private Long ofertaId;
    private String descripcion;
    private float costeD;
    private float costeR;
    private String nomeO;
    private Calendar datafin;
    private Calendar datadis;
    private Calendar dataini;
    private Integer maxi;
   
    public OfertaDto() {
    }    
    
    public OfertaDto(Long ofertaId,String descripcion, Calendar dataini, Calendar datafin,
    		Calendar datadis,float costeR,float costeD,Integer maxi, String nomeO) {
    	   this.ofertaId = ofertaId;
           this.descripcion = descripcion;
           this.dataini = dataini;
           this.datafin = datafin;
           this.datadis = datadis;
           this.costeR = costeR;
           this.costeD = costeD;
           this.maxi = maxi;
           this.nomeO = nomeO;

    }
  
    


	public Long getOfertaId() {
		return ofertaId;
	}

	public void setOfertaId(Long ofertaId) {
		this.ofertaId = ofertaId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getCosteD() {
		return costeD;
	}

	public void setCosteD(float costeD) {
		this.costeD = costeD;
	}

	public float getCosteR() {
		return costeR;
	}

	public void setCosteR(float costeR) {
		this.costeR = costeR;
	}

	public String getNomeO() {
		return nomeO;
	}

	public void setNomeO(String nomeO) {
		this.nomeO = nomeO;
	}

	public Calendar getDatafin() {
		return datafin;
	}

	public void setDatafin(Calendar datafin) {
		this.datafin = datafin;
	}

	public Calendar getDatadis() {
		return datadis;
	}

	public void setDatadis(Calendar datadis) {
		this.datadis = datadis;
	}

	public Calendar getDataini() {
		return dataini;
	}

	public void setDataini(Calendar dataini) {
		this.dataini = dataini;
	}

	public Integer getMaxi() {
		return maxi;
	}

	public void setMaxi(Integer maxi) {
		this.maxi = maxi;
	}

	@Override
    public String toString() {
        return "OfertaDto [ descripcion=" + descripcion
                + ", costeD=" + costeD
                + ", nomeO=" + nomeO + ", dataFin=" + datafin + ", dataDis=" + datadis + " ]";
    }
}
