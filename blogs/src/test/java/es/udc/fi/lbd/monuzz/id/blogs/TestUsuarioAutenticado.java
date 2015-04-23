package es.udc.fi.lbd.monuzz.id.blogs;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import es.udc.fi.lbd.monuzz.id.blogs.TestUtils;
import es.udc.fi.lbd.monuzz.id.blogs.model.*;
import es.udc.fi.lbd.monuzz.id.blogs.services.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TestUsuarioAutenticado {

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
		log.info ("Datos creados con éxito");
	}

	@After
	public void tearDown() throws Exception {
		log.info ("Eliminando datos para caso de prueba: " + this.getClass().getName());
		testUtils.eliminaSetDatosPrueba();  
		log.info ("Datos eliminados con éxito");
	}


	@Test
	public void testCrearBorrarBlog() {
		
		// T1 Crear un blog nuevo para un usuario ya existente
		Blog miBlog=new Blog ("Blog Nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), testUtils.usuario1);
		assertNull(miBlog.getIdBlog());
		blogService.crearBlog(miBlog);
		// Comprobar que el blog se ha grabado en la BD 
		assertNotNull(miBlog.getIdBlog());
		assertEquals(blogService.buscarBlogPorId(miBlog.getIdBlog()), miBlog);
		//Comprobar que se ha añadido a la lista del usuario, y que se recupera AL PRiNCIPIO de la lista
		List<Blog> lista = blogService.listarBlogsUsuario(testUtils.usuario1);
		assertEquals(lista.size(), testUtils.usuario1.getBlogs().size()+1);
		assertEquals(lista.get(0),miBlog);
		
		// T2 Probar rechazo duplicados (clave natural: usuario + timestamp)
		boolean duplicado = true;
		try{ blogService.crearBlog(miBlog); } catch (Exception e) { duplicado=false; }
		assertFalse(duplicado);
		
		// T3 Probar borrado del blog 
		blogService.borrarBlog(miBlog);
		assertNull(blogService.buscarBlogPorId(miBlog.getIdBlog()));
		
	}

	@Test
	public void testActualizarBlog() {
		
		// T1 Crear un blog nuevo para un usuario ya existente
		Blog miBlog=new Blog ("Blog Radiantemente Nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), testUtils.usuario1);
		blogService.crearBlog(miBlog);
		//Actualizar su nombre
		miBlog.setTitulo("Blog Radiantemente Nuevo v2.0");
		blogService.modificarBlog(miBlog);
		//Comprobar que ha sido actualizado
		assertEquals(blogService.buscarBlogPorId(miBlog.getIdBlog()).getTitulo(), miBlog.getTitulo());

		blogService.borrarBlog(miBlog);
	}
	

	@Test
	public void testCrearBorrarEntrada() {
		
		// T1 Crear un articulo nuevo para un blog ya existente
		Articulo miArticulo=new Articulo("Articulo nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", testUtils.blog1);		
		assertNull(miArticulo.getIdEntrada());
		blogService.publicarEntrada(miArticulo);
		assertNotNull(miArticulo.getIdEntrada());
		assertEquals(blogService.buscarEntradaPorId(miArticulo.getIdEntrada()), miArticulo);

		// T2 Crear un enlace nuevo para un blog ya existente
		try { Thread.sleep(testUtils.timeout); } catch (InterruptedException e) {}
		EnlaceAContenido miEnlace = new EnlaceAContenido("Enlace interesante", new Timestamp(Calendar.getInstance().getTimeInMillis()), "http://www.udc.es", testUtils.tipoHTML, testUtils.blog1);
		assertNull(miEnlace.getIdEntrada());
		blogService.publicarEntrada(miEnlace);
		assertNotNull(miEnlace.getIdEntrada());
		assertEquals(blogService.buscarEntradaPorId(miEnlace.getIdEntrada()), miEnlace);
		
		// T3 Comprobar que las entradas se han a la lista del blog
		List<Entrada> lista = blogService.listarEntradasBlog(testUtils.blog1);
		assertEquals(lista.size(), testUtils.blog1.getEntradas().size()+2);		
		// Comprobar que las nuevas entradas se recuperan al principio (orden inverso de fecha)
		assertEquals(lista.get(0),miEnlace);
		assertEquals(lista.get(1),miArticulo);
		
		// T4 Comprobar que os tipos son correctos
		assertTrue (lista.get(0) instanceof EnlaceAContenido);
		assertTrue (lista.get(1) instanceof Articulo);
		
		// T5 Comprobar duplicados (clave natural: blog + timestamp)
		boolean duplicado = true;
		try{ blogService.publicarEntrada(miArticulo); } catch (Exception e) { duplicado=false; }
		assertFalse(duplicado);
		duplicado=true;
		try{ blogService.publicarEntrada(miEnlace); } catch (Exception e) { duplicado=false; }
		assertFalse(duplicado);
			
		// T6 Probar borrado de entradas
		blogService.borrarEntrada(miArticulo);
		blogService.borrarEntrada(miEnlace);
		assertNull(blogService.buscarEntradaPorId(miArticulo.getIdEntrada()));
		assertNull(blogService.buscarEntradaPorId(miEnlace.getIdEntrada()));
	}

	@Test
	public void testActualizarEntrada() {
		
		// T1 Crear un articulo y un enlace nuevos para un blog ya existente
		Articulo miArticulo=new Articulo("Articulo radiantemente nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", testUtils.blog1);		
		blogService.publicarEntrada(miArticulo);
		EnlaceAContenido miEnlace = new EnlaceAContenido("Enlace nuevo e interesante", new Timestamp(Calendar.getInstance().getTimeInMillis()), "http://www.udc.es", testUtils.tipoHTML, testUtils.blog1);
		blogService.publicarEntrada(miEnlace);
				
		//Actualizar datos
		miArticulo.setTitulo("Articulo radiantemente nuevo v2.0");
		miArticulo.setTexto("Este es el nuevo texto");
		blogService.modificarEntrada(miArticulo);
		
		miEnlace.setTitulo("Enlace aun mas nuevo e interesante");
		miEnlace.setUrl("http://www.udc.es/nuevo");
		blogService.modificarEntrada(miEnlace);

		// Comprobar que han sido actualizados en la BD
		// Asegurarse que funcionan cambios en pros de superclase Y TAMBIEN subclases
		assertEquals(blogService.buscarEntradaPorId(miArticulo.getIdEntrada()).getTitulo(), miArticulo.getTitulo());
		assertEquals(((Articulo)blogService.buscarEntradaPorId(miArticulo.getIdEntrada())).getTexto(), miArticulo.getTexto());
		assertEquals(blogService.buscarEntradaPorId(miEnlace.getIdEntrada()).getTitulo(), miEnlace.getTitulo());
		assertEquals(((EnlaceAContenido)blogService.buscarEntradaPorId(miEnlace.getIdEntrada())).getUrl(), miEnlace.getUrl());

		blogService.borrarEntrada(miArticulo);
		blogService.borrarEntrada(miEnlace);
	}

	@Test
	public void testBorrarBlogConEntradas() {
		
		// T1 Crear un blog nuevo para un usuario ya existente
		Blog miBlog=new Blog ("Blog Nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), testUtils.usuario1);
		blogService.crearBlog(miBlog);
		

		//Crear dos entradas nuevas en ese blog
		try { Thread.sleep(testUtils.timeout); } catch (InterruptedException e) {}
		Articulo miArticulo=new Articulo("Articulo radiantemente nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", miBlog);		
		miBlog.getEntradas().add(0,miArticulo);
		blogService.publicarEntrada(miArticulo);
		try { Thread.sleep(testUtils.timeout); } catch (InterruptedException e) {}
		EnlaceAContenido miEnlace = new EnlaceAContenido("Enlace nuevo e interesante", new Timestamp(Calendar.getInstance().getTimeInMillis()), "http://www.udc.es", testUtils.tipoHTML, miBlog);
		miBlog.getEntradas().add(0,miEnlace);
		blogService.publicarEntrada(miEnlace);

		//Borrar el blog (y sus entradas)
		blogService.borrarBlog(miBlog);

		//Comprobar que todo ha sido borrado como se debe
		assertNull(blogService.buscarEntradaPorId(miArticulo.getIdEntrada()));
		assertNull(blogService.buscarEntradaPorId(miEnlace.getIdEntrada()));
		assertNull(blogService.buscarBlogPorId(miBlog.getIdBlog()));

	}

}
