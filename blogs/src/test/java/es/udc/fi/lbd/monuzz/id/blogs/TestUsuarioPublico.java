package es.udc.fi.lbd.monuzz.id.blogs;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.udc.fi.lbd.monuzz.id.blogs.model.*;
import es.udc.fi.lbd.monuzz.id.blogs.services.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-config.xml", "classpath:/test-spring-config.xml"})
@ActiveProfiles("test")
public class TestUsuarioPublico {
	
	private Logger log = Logger.getLogger("blogs");

	@Autowired
	private TestUtils testUtils;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private BlogService blogService;

	@Before
	public void setUp() throws Exception { 
		log.info ("Inicializando datos para caso de prueba: " + this.getClass().getName());
		testUtils.creaSetDatosPrueba();
		log.info ("Datos inicializados con éxito");
	}

	@After
	public void tearDown() throws Exception {
		log.info ("Eliminando datos para caso de prueba: " + this.getClass().getName());
		testUtils.eliminaSetDatosPrueba();  
		log.info ("Datos eliminados con éxito");
	}
	
	
	
	@Test
	public void pruebanada(){
		assert(true);
		
	}
	
	
	@Test
	public void testRegistro_y_Login() {
		
		// T1. Creamos usuario y lo registramos 
		Usuario miUsuario =  new Usuario("usuarioTest", "test13", "Nikito", "Nipongo", "Camacho", "Nikito Nipongo");
		usuarioService.registrar(miUsuario);
		assertNotNull(miUsuario.getIdUsuario());

		// T2. Comprobamos que la autenticación funciona
		Usuario miUsuarioAutenticado = usuarioService.autenticar(miUsuario.getNombreDeUsuario(), miUsuario.getPassword());
		assertNotNull(miUsuarioAutenticado);
		assertEquals(miUsuario, miUsuarioAutenticado);
		
		// T3. Probar autenticación erronea
		Usuario miFallidoUsuarioAutenticado = usuarioService.autenticar(miUsuario.getNombreDeUsuario(), "patata");
		assertNull(miFallidoUsuarioAutenticado);
		
		// T4. Probar usuario duplicado
		Boolean duplicado=false;
		try {usuarioService.registrar(miUsuario);} catch (Exception e) {duplicado=true;}
		assertTrue(duplicado);
		
		// T5. Probar baja
		usuarioService.borrar(miUsuario);
		assertNull (usuarioService.autenticar(miUsuario.getNombreDeUsuario(), miUsuario.getPassword()));			
	}

	@Test
	public void testListarBlogs() {
		
		// T1 Cargar todos los blogs
		List<Blog> listaBlogs = blogService.listarBlogs();
		// Comprobar que están todos
		assertEquals(listaBlogs.size(), 4);
		// Comprobar orden (más reciente primero)
		assertEquals(listaBlogs.get(0), testUtils.blog3);
		assertEquals(listaBlogs.get(1), testUtils.blog2);
		assertEquals(listaBlogs.get(2), testUtils.blogA);
		assertEquals(listaBlogs.get(3), testUtils.blog1);
		
		//T2 Cargar los blogs de un usuario
		Usuario miUsuario = usuarioService.autenticar(testUtils.usuario1.getNombreDeUsuario(), testUtils.usuario1.getPassword()); 
		List<Blog> listaBlogsUsuario = blogService.listarBlogsUsuario(miUsuario);
		// Comprobar que están todos
		assertEquals(listaBlogsUsuario.size(), 3);
		// Comprobar orden (más reciente primero)
		assertEquals(listaBlogsUsuario.get(0), testUtils.blog3);
		assertEquals(listaBlogsUsuario.get(1), testUtils.blog2);
		assertEquals(listaBlogsUsuario.get(2), testUtils.blog1);
		
	}
	

}
