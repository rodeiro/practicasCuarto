package es.udc.fi.lbd.monuzz.id.blogs.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import org.hibernate.annotations.Cascade;
//import org.hibernate.annotations.CascadeType;
import javax.persistence.CascadeType;


@Entity
@Table (name = "USUARIO")
public class Usuario {
	
	private Long idUsuario;	
	private String nombreDeUsuario;
	private String password;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nombreEnPantalla;
	private List<Blog> blogs = new ArrayList<Blog>();


	public Usuario() {
	}

	public Usuario(String nombreUsuario, String password, String nombre,
			String apellido1, String apellido2, String nombreEnPantalla) {
		this.nombreDeUsuario = nombreUsuario;
		this.password = password;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nombreEnPantalla = nombreEnPantalla;
	}
	
	@Id
	@SequenceGenerator(name="gidUsuario", sequenceName="id_usuario_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gidUsuario")
	@Column (nullable = false)
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	@Column (name= "NOMBREUSUARIO", nullable = false)
	public String getNombreDeUsuario() {
		return nombreDeUsuario;
	}
	
	
	@Column (name = "PASSWORD", nullable = false)
	public String getPassword() {
		return password;
	}
	
	@Column (name = "NOMBRE", nullable=false)
	public String getNombre() {
		return nombre;
	}
	
	@Column (name = "APELLIDO1", nullable=true)
	public String getApellido1() {
		return apellido1;
	}
	
	@Column (name = "APELLIDO2", nullable = true)
	public String getApellido2() {
		return apellido2;
	}
	
	@Column (name = "NOMBREPANTALLA", nullable = true)
	public String getNombreEnPantalla() {
		return nombreEnPantalla;
	}
	
	// EAGER -> La mayor parte de las veces que se llame a un usuario será por sus blogs.
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "autor", cascade={CascadeType.ALL})
	public List<Blog> getBlogs() {
		return blogs;
	}

	@SuppressWarnings("unused")
	private void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setNombreDeUsuario(String nombreUsuario) {
		this.nombreDeUsuario = nombreUsuario;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public void setNombreEnPantalla(String nombreEnPantalla) {
		this.nombreEnPantalla = nombreEnPantalla;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}



	@Override
	public String toString() {
		return "[idUsuario=" + idUsuario + ", nombreUsuario="
				+ nombreDeUsuario + ", password=" + password + ", nombre="
				+ nombre + ", apellido1=" + apellido1 + ", apellido2="
				+ apellido2 + ", nombreEnPantalla=" + nombreEnPantalla + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 13;
		result = prime * result
				+ ((nombreDeUsuario == null) ? 0 : nombreDeUsuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		Usuario other = (Usuario) obj;
		if (nombreDeUsuario == null) {
			if (other.nombreDeUsuario != null)
				return false;
		} else if (!nombreDeUsuario.equals(other.nombreDeUsuario))
			return false;
		return true;
	}

	
}
