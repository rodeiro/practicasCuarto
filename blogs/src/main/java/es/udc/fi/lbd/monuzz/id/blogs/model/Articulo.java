package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import es.udc.fi.lbd.monuzz.id.blogs.model.Entrada;

@Entity
@DiscriminatorValue("AR")
public class Articulo extends Entrada {

	private String texto;

	@SuppressWarnings("unused")
	private Articulo() {}
	
	public Articulo(String titulo, Timestamp fechaPublicacion, String texto, Blog blog) {
			this.titulo = titulo;
			this.fechaDePublicacion = fechaPublicacion;
			this.texto=texto;
			this.blog=blog;
		}

	@Column (name = "texto", nullable = true) //Nulo en todos los enlaces
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "Articulo [ idEntrada=" + idEntrada
				+ ", fechaPublicacion=" + fechaDePublicacion
				+ ", titulo=" + titulo 
				+ ", texto=" + texto + " ]";
	}
	
	
}
