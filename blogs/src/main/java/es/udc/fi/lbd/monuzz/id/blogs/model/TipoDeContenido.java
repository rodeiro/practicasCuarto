package es.udc.fi.lbd.monuzz.id.blogs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "TIPODECONTENIDO")
public class TipoDeContenido {

	private Long idTipoContenido;
	private String nombre;
	private String TipoMIME;
	private byte[] icono;
	
	protected TipoDeContenido() {
	}

	public TipoDeContenido(String nombre, String tipoMIME, byte[] icono) {
		this.nombre = nombre;
		TipoMIME = tipoMIME;
		this.icono = icono;
	}
	
	
	@Id
	@SequenceGenerator(name="gidTipo", sequenceName="id_tipo_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gidTipo")
	@Column (nullable= false)
	public Long getIdTipoContenido() {
		return idTipoContenido;
	}
	
	@Column(name= "NOMBRE")
	public String getNombre() {
		return nombre;
	}
	
	@Column(name= "MIME")
	public String getTipoMIME() {
		return TipoMIME;
	}
	
	@Column(name= "ICONO")
	public byte[] getIcono() {
		return icono;
	}
	
	
	@SuppressWarnings("unused")
	private  void setIdTipoContenido(Long idTipoContenido) {
		this.idTipoContenido = idTipoContenido;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setTipoMIME(String tipoMIME) {
		TipoMIME = tipoMIME;
	}
	public void setIcono(byte[] icono) {
		this.icono = icono;
	}

	@Override
	public String toString() {
		return "[idTipoContenido=" + idTipoContenido
				+ ", nombre=" + nombre + ", TipoMIME=" + TipoMIME + "]";
	}
	
	
}
