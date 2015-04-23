package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "disc" , discriminatorType = DiscriminatorType.STRING)
@Table (name = "ENTRADA")
public abstract class Entrada {

	protected Long idEntrada;
	protected String titulo;
	protected Timestamp fechaDePublicacion;
	protected String disc;
	protected Blog blog;
	protected List<Voto> votos = new ArrayList<Voto>();
	
	@Id
	@SequenceGenerator(name="gidEntrada", sequenceName="id_entrada_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gidEntrada")
	@Column (name = "ID_ENTRADA", nullable= false)
	public Long getIdEntrada() {
		return idEntrada;
	}
	
	@Column (name = "TITULO", nullable = false)
	public String getTitulo() {
		return titulo;
	}
	
	@Column (name = "FECHA", nullable= false)
	public Timestamp getFechaDePublicacion() {
		return fechaDePublicacion;
	}
	

	
	@ManyToOne(fetch = FetchType.EAGER)
	public Blog getBlog() {
		return blog;
	}
	
	//EAGER: Las entradas y los votos se usarán juntos habitualmente
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="clave.entrada",cascade={CascadeType.ALL})
	public List<Voto> getVotos() {
		return this.votos;
	}
	
	@SuppressWarnings("unused")
	private void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setFechaDePublicacion(Timestamp fechaPublicacion) {
		this.fechaDePublicacion = fechaPublicacion;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public void setVotos(List<Voto> votos){
		this.votos=votos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + ((blog == null) ? 0 : blog.hashCode());
		result = prime
				* result
				+ ((fechaDePublicacion == null) ? 0 : fechaDePublicacion.hashCode());
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
		Entrada other = (Entrada) obj;
		if (blog == null) {
			if (other.blog != null)
				return false;
		} else if (!blog.equals(other.blog))
			return false;
		if (fechaDePublicacion == null) {
			if (other.fechaDePublicacion != null)
				return false;
		} else if (!fechaDePublicacion.equals(other.fechaDePublicacion))
			return false;
		return true;
	}

	
	
}
