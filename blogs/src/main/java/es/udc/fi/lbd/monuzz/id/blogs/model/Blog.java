package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;
import javax.persistence.CascadeType;


@Entity
@Table (name = "BLOG")
public class Blog  {
	

	private Long idBlog;
	private String titulo;
	private Timestamp fechaDeCreacion;
	private Usuario autor;
	private List<Entrada> entradas=new ArrayList<Entrada>();
	
	@SuppressWarnings("unused")
	private Blog() {}
	
	public Blog(String titulo, Timestamp fechaDeCreacion, Usuario usuario) {
		super();
		this.titulo = titulo;
		this.fechaDeCreacion = fechaDeCreacion;
		this.autor = usuario;
	}
	
	@Id
	@SequenceGenerator(name="gidBlog", sequenceName="id_blog_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gidBlog")
	@Column (nullable = false)
	public Long getIdBlog() {
		return idBlog;
	}

	@Column (name = "TITULO",nullable = false)
	public String getTitulo() {
		return titulo;
	}

	@Column (name = "FECHA", nullable = false)
	public Timestamp getFechaDeCreacion() {
		return fechaDeCreacion;
	}
	
	@ManyToOne (fetch = FetchType.EAGER)
	public Usuario getAutor() {
		return autor;
	}

	//LAZY: No siempre que se use un blog se usarán las entradas
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "blog",cascade={CascadeType.ALL})
	public List<Entrada> getEntradas() {
		return entradas;
	}
	
	@SuppressWarnings("unused")
	private void setIdBlog(Long idBlog) {
		this.idBlog = idBlog;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setFechaDeCreacion(Timestamp fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}
	
	public void setAutor(Usuario usuario) {
		this.autor = usuario;
	}
	public void setEntradas(List<Entrada> entradas) {
		this.entradas=entradas;
	}
	
	public boolean addEntrada(Entrada miEntrada){
		return this.entradas.add(miEntrada);
	}

	public boolean removeEntrada(Entrada miEntrada){
		return this.entradas.remove(miEntrada);
	}

	@Override
	public String toString() {
		return "[idBlog=" + idBlog + ", titulo=" + titulo
				+ ", fechaDeCreacion=" + fechaDeCreacion + ", autor="
				+ autor + "]"
				;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 38;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result
				+ ((fechaDeCreacion == null) ? 0 : fechaDeCreacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		Blog other = (Blog) obj;

		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;

		if (fechaDeCreacion == null) {
			if (other.fechaDeCreacion != null)
				return false;
		} else if (!fechaDeCreacion.equals(other.fechaDeCreacion))
			return false;
		return true;
	}

}
