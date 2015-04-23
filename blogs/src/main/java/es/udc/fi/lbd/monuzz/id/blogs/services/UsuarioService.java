package es.udc.fi.lbd.monuzz.id.blogs.services;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.blogs.model.*;

public interface UsuarioService {
	void registrar (Usuario miUsuario);
	void borrar(Usuario miUsuario);
	void actualizar (Usuario miUsuario);
	Usuario autenticar(String login, String password);
	List<Usuario> listarUsuarios();
	void votar(Voto miVoto);
	void anular(Voto miVoto);
}
