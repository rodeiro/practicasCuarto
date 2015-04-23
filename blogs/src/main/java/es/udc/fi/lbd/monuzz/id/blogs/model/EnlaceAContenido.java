package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EN")
public class EnlaceAContenido extends Entrada {
	
	private String url;
	private TipoDeContenido tipo;
	
	public EnlaceAContenido() {}
	
	public EnlaceAContenido(String titulo, Timestamp fechaPublicacion, String url, TipoDeContenido tipo, Blog blog) {
		this.titulo = titulo;
		this.fechaDePublicacion = fechaPublicacion;
		this.url = url;
		this.tipo = tipo;
		this.blog=blog;
	}

	@Column (nullable = true) //Nulo en todos los artículos
	public String getUrl() {
		return url;
	}

//Siempre que se cargue un enlace será interesante saber su tipo
	@ManyToOne (fetch = FetchType.EAGER)
	public TipoDeContenido getTipo() {
		return tipo;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public void setTipo(TipoDeContenido tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "EnlaceAContenido [ idEntrada=" + idEntrada + 
				", fechaPublicacion=" + fechaDePublicacion + 
				", titulo=" + titulo + ", url=" + url + 
				", tipo=" + tipo + "]";
	}

	
}
