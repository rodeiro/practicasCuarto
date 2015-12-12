package es.udc.ws.app.model.oferta;

import java.util.Calendar;
import java.sql.Date;


public class Oferta {

    private Long ofertaId;
    private String descripcion;
    private Calendar dataini;
    private Calendar datafin;
    private Calendar datadis;
    private float costeR;
    private float costeD;
    private Integer maxi;
    private  String nomeO;
    private String estadO;
    private int maxres;
     


    public Oferta(String des, Calendar dini, Calendar dfin,
    		Calendar ddis,float cosr,float cosd,Integer maxi, String nome,int maxres){
         
        this.descripcion = des;
        this.nomeO=nome;
        this.dataini = dini;
        if (dini != null) {
            this.dataini.set(Calendar.MILLISECOND, 0);
        }
        this.datafin = dfin;
        if (dfin != null) {
            this.datafin.set(Calendar.MILLISECOND, 0);
        }
        this.datadis = ddis;
        if (ddis == null) {
            this.datadis.add(dataini.DAY_OF_MONTH, 15);
        }
        this.datadis.set(Calendar.MILLISECOND, 0);
        
        this.maxi = maxi;
        
        this.estadO="Creado";
        this.costeR = cosr;
        this.costeD = cosd;
        this.maxres=maxres;
        
    }
    public Oferta(Long ofertaId, String descripcion,Calendar dataini,Calendar datafin,
    		Calendar dataDis, float costeR, float costeD, Integer maxi, String nomeO,
    		String estadO, int maxres)
    {
    	this(descripcion,dataini,datafin,dataDis,costeR,costeD,maxi,nomeO,maxres);
    	this.estadO=estadO;
    	this.ofertaId = ofertaId;
    	
    }


    public Long getOfertaId() {
        return ofertaId;
    }
    public String getEstado() {
        return estadO;
    }
    public void setEstado(String Estado) {
        this.estadO=Estado;
    }
    public void setOfertaId(Long ofertaId) {
        this.ofertaId=ofertaId;
    }    
    public String getNome() {
        return nomeO;
    }
    public void setNome(String nome) {
        this.nomeO=nome;
    }
    public void setDataIni(Calendar dini) {
        this.dataini = dini;
        if (dataini != null) {
            this.dataini.set(Calendar.MILLISECOND, 0);
        }
    }
    public Calendar getDataini() {
        return dataini;
    }
    public void setDataFin(Calendar dfin) {
        this.datafin = dfin;
        if (datafin != null) {
            this.datafin.set(Calendar.MILLISECOND, 0);
        }
    }

    public Calendar getDatafin() {
        return datafin;
    }
    public void setDataDis(Calendar ddis) {
        this.datadis = ddis;
        if (datadis != null) {
            this.datadis.set(Calendar.MILLISECOND, 0);
        }
    }
    public float getCosteR() {
        return costeR;
    }
    public Calendar getDatadis() {
        return datadis;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMax() {
        return maxi;
    }

    public void setMax(Integer maxi) {
        this.maxi = maxi;
    }

    public void setCosteR(float costeR) {
        this.costeR=costeR;
    }
    public void setCosteD(float costeD) {
        if (costeD <= costeR)
           	this.costeD=costeD;       
        	
    }

    public float getCosteD() {
        return costeD;
    }
    public int getMaxRes(){
    	return this.maxres;
    }
    public void setResS(){
    	this.maxres=this.maxres+1;
    }
    public void setResR(){
    	if (this.maxres>0){
    		this.maxres=this.maxres-1;
    	}
    }
    public void setMaxRes(int Maxres){
    	this.maxres=Maxres;
    	
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(costeD);
		result = prime * result + Float.floatToIntBits(costeR);
		result = prime * result + ((datadis == null) ? 0 : datadis.hashCode());
		result = prime * result + ((datafin == null) ? 0 : datafin.hashCode());
		result = prime * result + ((dataini == null) ? 0 : dataini.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((estadO == null) ? 0 : estadO.hashCode());
		result = prime * result + maxi;
		result = prime * result + ((nomeO == null) ? 0 : nomeO.hashCode());
		result = prime * result
				+ ((ofertaId == null) ? 0 : ofertaId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (Float.floatToIntBits(costeD) != Float.floatToIntBits(other.costeD))
			return false;
		if (Float.floatToIntBits(costeR) != Float.floatToIntBits(other.costeR))
			return false;
		if (datadis == null) {
			if (other.datadis != null)
				return false;
		} else if (!datadis.equals(other.datadis))
			return false;
		if (datafin == null) {
			if (other.datafin != null)
				return false;
		} else if (!datafin.equals(other.datafin))
			return false;
		if (dataini == null) {
			if (other.dataini != null)
				return false;
		} else if (!dataini.equals(other.dataini))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (estadO == null) {
			if (other.estadO != null)
				return false;
		} else if (!estadO.equals(other.estadO))
			return false;
		if (maxi != other.maxi)
			return false;
		if (nomeO == null) {
			if (other.nomeO != null)
				return false;
		} else if (!nomeO.equals(other.nomeO))
			return false;
		if (ofertaId == null) {
			if (other.ofertaId != null)
				return false;
		} else if (!ofertaId.equals(other.ofertaId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Oferta [ofertaId=" + ofertaId + ", descripcion=" + descripcion
				+ ", dataini=" + dataini + ", datafin=" + datafin
				+ ", datadis=" + datadis + ", costeR=" + costeR + ", costeD="
				+ costeD + ", maxi=" + maxi + ", nomeO=" + nomeO + ", estadO="
				+ estadO + "]";
	}

	

}