package es.udc.asi.app.model.userservice;



import java.sql.SQLException;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;;

public interface UserService {

	public User registrar(User usuario) throws SQLException;

	public void actualizar(User user) throws SQLException,InstanceNotFoundException;

	public void darBaja(Long userId) throws SQLException, InstanceNotFoundException;

	public boolean autenticar(String login, String password) throws SQLException, InstanceNotFoundException;
	
	public void cerrarSesion(Long userId);
}