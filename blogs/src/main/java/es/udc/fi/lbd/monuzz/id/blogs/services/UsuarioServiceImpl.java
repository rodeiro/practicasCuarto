package es.udc.fi.lbd.monuzz.id.blogs.services;

//import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.udc.fi.lbd.monuzz.id.blogs.model.Usuario;
import es.udc.fi.lbd.monuzz.id.blogs.daos.UsuarioDAO;
import es.udc.fi.lbd.monuzz.id.blogs.daos.VotoDAO;
import es.udc.fi.lbd.monuzz.id.blogs.model.Voto;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.dao.DataAccessException;


@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false)

public class UsuarioServiceImpl implements UsuarioService {

	static Logger log = Logger.getLogger("usuarios");

	@Autowired
	UsuarioDAO usuarioDAO;
	
	@Autowired
	VotoDAO votoDAO;
	
	@Override
	@Transactional(value = "miTransactionManager")
	public void registrar(Usuario miUsuario) {
		try{
			usuarioDAO.create(miUsuario);
			log.info("Usuario creado");
		}catch (DataAccessException e){
			log.error("Fallo durante el registro del usuario");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void borrar(Usuario miUsuario) {
		try{
			usuarioDAO.remove(miUsuario);
			log.info("Usuario borrado");
		}catch(DataAccessException e){
			log.error("Fallo durante el borrado del usuario");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void actualizar(Usuario miUsuario) {
		try{
			usuarioDAO.update(miUsuario);
			log.info("Usuario actualizado");
		}catch(DataAccessException e){
			log.error("Fallo durante el actualizado del usuario");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public Usuario autenticar(String login, String password) {
		try{
			Usuario usuario = usuarioDAO.findByNombreDeUsuario(login);	
			if (usuario == null) return null;
			if (usuario.getPassword().equals(password)){
				return usuario;
			}else {
				return null;
			}
		}catch(DataAccessException e){
			log.error("Error durante la autentificación del usuario");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public List<Usuario> listarUsuarios() {
		try{
			return usuarioDAO.findAll();
		}catch(DataAccessException e){
			log.error("Error al buscar usuarios");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void votar(Voto miVoto) {
		try{
			 votoDAO.create(miVoto);;
		}catch(DataAccessException e){
			log.error("Error al votar");
			throw e;
		}
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void anular(Voto miVoto) {
		try{
			 votoDAO.remove(miVoto);
		}catch(DataAccessException e){
			log.error("Error al eliminar voto");
			throw e;
		}		
	}

	
	

	

}