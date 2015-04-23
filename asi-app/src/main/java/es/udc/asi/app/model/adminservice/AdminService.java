package es.udc.asi.app.model.adminservice;



import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;
import es.udc.asi.app.model.blog.*;
import es.udc.asi.app.model.entrada.*;

public interface AdminService {

	public Blog CrearBlog (Blog blog) throws SQLException;
	
	public void ModificarBlog (Blog blog) throws SQLException, InstanceNotFoundException;
	
	public void BorrarBlog (Long blogId) throws SQLException, InstanceNotFoundException;
	
	public Entrada CrearEntrada (Entrada entrada) throws SQLException;
	
	public void ModificarEntrada (Entrada entrada) throws SQLException, InstanceNotFoundException;
	
	public void BorrarEntrada (Long entradaId) throws SQLException, InstanceNotFoundException;
	
	public List <Blog> ListarMisBlogs (long userId) throws SQLException, InstanceNotFoundException;
	
	public List <Entrada> ListarMisEntradas(long userId) throws SQLException, InstanceNotFoundException;
	
	public void VotarEntrada (long userId, long entradaId, boolean voto) throws SQLException, InstanceNotFoundException;
	
	 public Long crearTipo(String nombre, String mime, String archivo)
   		throws FileNotFoundException;
}