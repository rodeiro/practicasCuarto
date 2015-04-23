package es.udc.fi.lbd.monuzz.id.blogs.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="VOTO")
@AssociationOverrides({
@AssociationOverride(name ="clave.usuario", joinColumns = @JoinColumn(name ="idUsuario")),
@AssociationOverride(name ="clave.entrada", joinColumns = @JoinColumn(name ="idEntrada"))})
public class Voto {

	private VotoPK clave = new VotoPK();
	
	@Column (name = "POSITIVO")
	private boolean positivo;
	
	public Voto(){}

	public Voto(Usuario miUsuario, Entrada miEntrada, boolean positivo) {

		this.clave.setUsuario(miUsuario); 
		this.clave.setEntrada(miEntrada);
		this.positivo = positivo;
	}
	
	@EmbeddedId
	public VotoPK getClave() {
		return clave;
	}
	
	@Transient
	public Usuario getUsuario() {
		return clave.getUsuario();
	}
	
	@Transient
	public Entrada getEntrada() {
		return clave.getEntrada();
	}
	
	
	public boolean isPositivo() {
		return positivo;
	}

	public void setClave(VotoPK clave) {
		this.clave = clave;
	}

	public void setPositivo(boolean positivo) {
		this.positivo = positivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clave == null) ? 0 : clave.hashCode());
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
		Voto other = (Voto) obj;
		if (clave == null) {
			if (other.clave != null)
				return false;
		} else if (!clave.equals(other.clave))
			return false;
		return true;
	}
	
	
}
