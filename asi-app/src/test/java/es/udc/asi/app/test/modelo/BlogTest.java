package es.udc.asi.app.test.modelo;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import es.udc.asi.app.model.blogservice.BlogService;
//import main.java.es.udc.asi.app.model.blogservice.BlogService;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.entrada.SqlEntradaDao;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.user.*;
//import main.java.es.udc.asi.app.model.userservice.UserService;

	//private static SqlUserDao userDao = null;
	
public class BlogTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");
	SqlBlogDao blogDAO = (SqlBlogDao) context.getBean("BlogDAO");
	SqlEntradaDao entradaDAO = (SqlEntradaDao) context.getBean("EntradaDAO");
	//UserService userSer = (UserService) context.getBean("UserService");
	AdminService adminSer = (AdminService) context.getBean("AdminService");
	BlogService blogSer = (BlogService) context.getBean("BlogService");

	
	
	

	@Test
	public void testFindAllBlogs() throws InstanceNotFoundException, SQLException {
		
		//Crear usuario
		
		User addedUser=userDAO.create(new User("Matt", "Damon", "Smith", "Mathew", "pass", "MD"));
		
		//Blog

		Calendar fecha = Calendar.getInstance();
		Blog blog1 = blogDAO.create(new Blog(addedUser.getUserId(), "Cine", fecha));

		Calendar fecha2 = Calendar.getInstance();
		Blog blog2 = blogDAO.create(new Blog(addedUser.getUserId(), "Cocina", fecha2));
		
	
		Calendar fecha3 = Calendar.getInstance();
		Blog blog3 = blogDAO.create(new Blog(addedUser.getUserId(), "Moda", fecha3));
		
		
		List<Blog> foundBlogs = new ArrayList<Blog>();
		
		foundBlogs = blogSer.ConsultarBlogs();
		
		assertEquals(3, foundBlogs.size());

		// Clear Database
		blogDAO.remove(blog1.getBlogId());
		blogDAO.remove(blog2.getBlogId());
		blogDAO.remove(blog3.getBlogId());
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
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Cocina", fechaB));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"Verano",fechaE,1,
				"Texto de prueba", "url", tipoE));
		
		Entrada encontrada = blogSer.VerEntrada(entrada.getEntradaId());
		
		assertEquals(encontrada, entrada);

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		

	}
	
	
	
	@Test
	public void testEntradasBlog() throws InstanceNotFoundException, 
	FileNotFoundException, SQLException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Will", "Shakespeare", "K", "W.", "pass", "w."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Obras", fechaB));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"Hamlet",fechaE,0,
				"Texto", "url", tipoE));
		
		
		Calendar fechaE2 = Calendar.getInstance();
		Entrada entrada2 = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"Macbeth",fechaE2,0,
				"Texto", "url", tipoE));
		List <Entrada> entradas =	blogSer.VerListaEntradas(addedBlog.getBlogId());
		assertEquals(entradas.size(), 2);

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.remove(entrada2.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		
	}
	

	
	

	
}