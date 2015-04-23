package es.udc.fi.lbd.monuzz.id.blogs.daos;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.blogs.model.*;

public interface UsuarioDAO {
	public Long create(Usuario miUsuario);
	public void remove (Usuario miUsuario);
	public void update (Usuario miUsuario);
	public List<Usuario> findAll();
	public Usuario findByNombreDeUsuario (String nombreDeUsuario);	
}
