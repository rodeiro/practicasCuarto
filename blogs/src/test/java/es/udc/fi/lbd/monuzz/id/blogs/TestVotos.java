package es.udc.fi.lbd.monuzz.id.blogs;

import static org.junit.Assert.*;


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
@ContextConfiguration({"/spring-config.xml", "/test-spring-config.xml"})
@ActiveProfiles("test")
public class TestVotos {

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
	public void testCrearBorrarVoto() {
		//T1. Crear un voto para una entrada existente
		Voto miVoto = new Voto(testUtils.usuario1, testUtils.entradaA1, true);
		usuarioService.votar(miVoto);
		Entrada entradaVotada = blogService.buscarEntradaPorId(testUtils.entradaA1.getIdEntrada());
		assertEquals(entradaVotada.getVotos().size(), 1);
		assertEquals(entradaVotada.getVotos().get(0), miVoto);
		
		//T2. Eliminar un voto para una entrada existente
		usuarioService.anular(miVoto);
		Entrada entradaVotadaAhora = blogService.buscarEntradaPorId(testUtils.entradaA1.getIdEntrada());
		assertEquals(entradaVotadaAhora.getVotos().size(), 0);
		
	}
/*
	@Test
	public void testBorrarEntradaConVotos() {
		//T1. Crear una entrada nueva para un blog existente voto para una entrada existente
		Articulo miArticulo=new Articulo("Articulo nuevo", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", testUtils.blog1);		
		blogService.publicarEntrada(miArticulo);
		//Crear un voto para el nuevo artículo
		Voto miVoto = new Voto(testUtils.usuario1, miArticulo, true);
		usuarioService.votar(miVoto);
		//Eliminar el articulo y sus votos
		blogService.borrarEntrada(miArticulo);
		// Comprobar que el artículo fue borrado correctamente
		assertNull(blogService.buscarEntradaPorId(testUtils.entradaA1.getIdEntrada()));
	}
*/
}
