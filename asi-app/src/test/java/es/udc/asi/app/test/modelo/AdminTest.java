package es.udc.asi.app.test.modelo;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import es.udc.asi.app.model.adminservice.AdminService;
import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.blog.SqlBlogDao;
//import main.java.es.udc.asi.app.model.blogservice.BlogService;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.entrada.SqlEntradaDao;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;
//import main.java.es.udc.asi.app.model.userservice.UserService;

	//private static SqlUserDao userDao = null;
	
public class AdminTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");
	SqlBlogDao blogDAO = (SqlBlogDao) context.getBean("BlogDAO");
	SqlEntradaDao entradaDAO = (SqlEntradaDao) context.getBean("EntradaDAO");
	//UserService userSer = (UserService) context.getBean("UserService");
	AdminService adminSer = (AdminService) context.getBean("AdminService");

	
	
	

	@Test
	public void testAddBlog() throws InstanceNotFoundException, SQLException {
		
	//Crear usuario
		
			User addedUser=userDAO.create(new User("John", "Maven", "Springed", "johnms", "pass", "mavenS"));
			
			//Blog
			Blog addedBlog = null;
			Calendar fecha = Calendar.getInstance();
		
			addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Cocina", fecha));
			Blog foundBlog = blogDAO.find(addedBlog.getBlogId());
			
			assertEquals(addedBlog, foundBlog);

			// Clear Database
				adminSer.BorrarBlog(addedBlog.getBlogId());
				userDAO.remove(addedUser.getUserId());

		}
	
	


	@Test
	public void testUpdateBlog() throws InstanceNotFoundException, SQLException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("Tom", "Hardy", "Springed", "th", "pass", "TH"));
		
		//Blog
		Blog addedBlog = null;
		Calendar fecha = Calendar.getInstance();
		addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Cine", fecha));

		addedBlog.setTitulo("Moda");
		adminSer.ModificarBlog(addedBlog);
		
		Blog foundBlog = blogDAO.find(addedBlog.getBlogId());
		
		assertEquals("Moda", foundBlog.getTitulo());

		// Clear Database
		adminSer.BorrarBlog(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());

	}
	
	

	@Test
	public void testAddEntradaAndFindEntrada() throws InstanceNotFoundException, 
	FileNotFoundException, SQLException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Bruce", "Willis", "K", "Bru", "pass", "BW."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Cocina", fechaB));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = adminSer.CrearEntrada(new Entrada(addedBlog.getBlogId(),"Verano",fechaE,1,
				"Texto de prueba", "url", tipoE));
		
		Entrada encontrada = entradaDAO.findEntrada(entrada.getEntradaId());
		
		assertEquals(encontrada, entrada);

		// Clear Database
		adminSer.BorrarEntrada(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		adminSer.BorrarBlog(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		

	}
	
	
	

	@Test
	public void testUpdateEntrada() throws InstanceNotFoundException, 
	FileNotFoundException, SQLException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Marco", "Polo", "K", "MP", "pass", "MP."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Viajes", fechaB));
	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = adminSer.CrearEntrada(new Entrada(addedBlog.getBlogId(),"China",fechaE,1,
				"Texto de prueba", "url", tipoE));
		
		entrada.setTitulo("Asia");
		adminSer.MoficarEntrada(entrada);
		Entrada encontrada = entradaDAO.findEntrada(entrada.getEntradaId());
		assertEquals(encontrada.getTitulo(), "Asia");

		// Clear Database
		adminSer.BorrarEntrada(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		adminSer.BorrarBlog(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		

	}
	
	
	

	@Test
	public void testEntradasUsuario() throws InstanceNotFoundException, 
	FileNotFoundException, SQLException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Mel", "Gibson", "K", "M.", "pass", "MG."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Peliculas", fechaB));
		Blog addedBlog2 = null;
		Calendar fechaB2 = Calendar.getInstance();
		addedBlog2 = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Recetas", fechaB2));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = adminSer.CrearEntrada(new Entrada(addedBlog.getBlogId(),"Hamlet",fechaE,0,
				"Texto", "url", tipoE));
		
		Calendar fechaE2 = Calendar.getInstance();
		Entrada entrada2 = adminSer.CrearEntrada(new Entrada(addedBlog2.getBlogId(),"Macbeth",fechaE2,0,
				"Texto", "url", tipoE));
		
		List <Entrada> entradas =	adminSer.ListarMisEntradas(addedUser.getUserId());
		
		assertEquals(entradas.size(), 2);

		// Clear Database
		adminSer.BorrarEntrada(entrada.getEntradaId());
		adminSer.BorrarEntrada(entrada2.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		adminSer.BorrarBlog(addedBlog.getBlogId());
		adminSer.BorrarBlog(addedBlog2.getBlogId());
		userDAO.remove(addedUser.getUserId());
		
	}
	
	
	
	

	@Test
	public void testVotar() throws InstanceNotFoundException, 
	FileNotFoundException, SQLException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Brad", "Pitt", "K", "B.", "pass", "BP."));
		User addedUser2=userDAO.create(new User("Angelina", "Jolie", "P", "A.", "pass", "AJ."));

		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = adminSer.CrearBlog(new Blog(addedUser.getUserId(), "Hijos", fechaB));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = adminSer.CrearEntrada(new Entrada(addedBlog.getBlogId(),"Ni�os",fechaE,1,
				"Texto", "url", null));
		
		//Votaci�n
		adminSer.VotarEntrada(addedUser2.getUserId(), entrada.getEntradaId(), true);
		

		// Clear Database
		entradaDAO.removeVote(addedUser2.getUserId(), entrada.getEntradaId());
		adminSer.BorrarEntrada(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		adminSer.BorrarBlog(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		userDAO.remove(addedUser2.getUserId());
		
	}
	/*
	
	
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNoUser() throws InstanceNotFoundException, SQLException {	
			userSer.autenticar("tirso", "Pass");
	}
	
	
	*/
	
	
}