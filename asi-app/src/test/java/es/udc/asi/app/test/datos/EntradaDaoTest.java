package es.udc.asi.app.test.datos;

//import java.io.File;
import java.io.FileNotFoundException;
//import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import es.udc.asi.app.model.util.*;
import es.udc.asi.app.model.blog.*;
import es.udc.asi.app.model.user.*;
import es.udc.asi.app.model.entrada.*;
	
public class EntradaDaoTest {
	
	
	ApplicationContext context = 
  		new ClassPathXmlApplicationContext("Spring-Module.xml");

	SqlBlogDao blogDAO = (SqlBlogDao) context.getBean("BlogDAO");
	SqlUserDao userDAO = (SqlUserDao) context.getBean("UserDAO");
	SqlEntradaDao entradaDAO = (SqlEntradaDao) context.getBean("EntradaDAO");
	
	
	

	@Test
	public void testAddEntradaAndFindEntrada() throws InstanceNotFoundException, 
	FileNotFoundException {
		
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
		
		Entrada encontrada = entradaDAO.findEntrada(entrada.getEntradaId());
		
		assertEquals(encontrada, entrada);

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		

	}
	
	
	
	

	@Test
	public void testUpdateEntrada() throws InstanceNotFoundException, 
	FileNotFoundException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Marco", "Polo", "K", "MP", "pass", "MP."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Viajes", fechaB));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"China",fechaE,1,
				"Texto de prueba", "url", tipoE));
		
		entrada.setTitulo("Asia");
		entradaDAO.update(entrada);
		Entrada encontrada = entradaDAO.findEntrada(entrada.getEntradaId());
		assertEquals(encontrada.getTitulo(), "Asia");

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		

	}
	
	

	@Test
	public void testEntradasBlog() throws InstanceNotFoundException, 
	FileNotFoundException {
		
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
		List <Entrada> entradas =	entradaDAO.findEntradasBlog(addedBlog.getBlogId());
		
		assertEquals(entradas.size(), 2);

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.remove(entrada2.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		
	}
	
	
	

	@Test
	public void testEntradasUsuario() throws InstanceNotFoundException, 
	FileNotFoundException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Mel", "Gibson", "K", "M.", "pass", "MG."));
		
		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Peliculas", fechaB));
		
		Blog addedBlog2 = null;
		Calendar fechaB2 = Calendar.getInstance();
		addedBlog2 = blogDAO.create(new Blog(addedUser.getUserId(), "Recetas", fechaB2));

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"Hamlet",fechaE,0,
				"Texto", "url", tipoE));
		
		Calendar fechaE2 = Calendar.getInstance();
		Entrada entrada2 = entradaDAO.create(new Entrada(addedBlog2.getBlogId(),"Macbeth",fechaE2,0,
				"Texto", "url", tipoE));
		List <Entrada> entradas =	entradaDAO.findEntradasUsuario(addedUser.getUserId());
		
		assertEquals(entradas.size(), 2);

		// Clear Database
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.remove(entrada2.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		
	}
	
	
	
	

	@Test
	public void testVotar() throws InstanceNotFoundException, 
	FileNotFoundException {
		
		//Usuario
		
		User addedUser=userDAO.create(new User("Brad", "Pitt", "K", "B.", "pass", "BP."));
		User addedUser2=userDAO.create(new User("Angelina", "Jolie", "P", "A.", "pass", "AJ."));

		//Blog
		Blog addedBlog = null;
		Calendar fechaB = Calendar.getInstance();
		addedBlog = blogDAO.create(new Blog(addedUser.getUserId(), "Hijos", fechaB));
		

	//TipoEntrada
			String archivo = "/Users/tirso/Documents/workspace/asi-app/iconoGif.png";
			Long tipoE = entradaDAO.crearTipo("imagen", "gif", archivo);
		
		//Entrada

		Calendar fechaE = Calendar.getInstance();
		Entrada entrada = entradaDAO.create(new Entrada(addedBlog.getBlogId(),"Ni�os",fechaE,1,
				"Texto", "url", null));
		
		//Votaci�n
		 entradaDAO.vote(entrada.getEntradaId(), addedUser2.getUserId(), true);
		

		// Clear Database
		entradaDAO.removeVote(addedUser2.getUserId(), entrada.getEntradaId());
		entradaDAO.remove(entrada.getEntradaId());
		entradaDAO.removeTipo(tipoE);
		blogDAO.remove(addedBlog.getBlogId());
		userDAO.remove(addedUser.getUserId());
		userDAO.remove(addedUser2.getUserId());
		
	}
	
}