package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class VotoPK implements Serializable{
	private static final long serialVersionUID = 7307247513262871460L;
	private Entrada entrada;
	private Usuario usuario;
	
	@ManyToOne
	public Entrada getEntrada() {
		return this.entrada;
	}

	@ManyToOne
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setEntrada(Entrada miEntrada){
		this.entrada = miEntrada;
	}

	public void setUsuario(Usuario miUsuario){
		this.usuario = miUsuario;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((entrada == null) ? 0 : entrada.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotoPK other = (VotoPK) obj;
		if (entrada == null) {
			if (other.entrada != null)
				return false;
		} else if (!entrada.equals(other.entrada))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	
}
