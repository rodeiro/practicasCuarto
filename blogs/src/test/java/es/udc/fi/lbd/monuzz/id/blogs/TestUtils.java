package es.udc.fi.lbd.monuzz.id.blogs;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.fi.lbd.monuzz.id.blogs.model.*;
import es.udc.fi.lbd.monuzz.id.blogs.services.*;

public class TestUtils {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TipoDeContenidoService tipoService;
	
	public final long timeout = 50;
	
	public Usuario usuario1;
	public Usuario usuario2;
	public Blog blog1;
	public Blog blog2;
	public Blog blog3;
	public Blog blogA;
	
	public Entrada entrada11;
	public Entrada entrada12;
	public Entrada entrada13;
	public Entrada entrada21;
	public Entrada entradaA1;
	
	public TipoDeContenido tipoHTML;
	public TipoDeContenido tipoPlain;
	public TipoDeContenido tipoMP3;

	
	public void creaSetDatosPrueba() {

		tipoHTML = new TipoDeContenido("Html", "text/html", null);
		tipoPlain = new TipoDeContenido("Plain text", "text/plain", null);
		tipoMP3 = new TipoDeContenido("MP3", "audio/mp3", null);
		
		tipoService.registrar(tipoHTML);
		tipoService.registrar(tipoPlain);
		tipoService.registrar(tipoMP3);
		
		usuario1 = new Usuario ("monuzz", "monuzz13", "Mon", "LÃ³pez", "RodrÃ­guez", "Mon LÃ³pez");
		usuario2 = new Usuario ("mi", "mi13", "Miguel", "RodrÃ­guez", "Penabad", "Miguel Penabad");
		
		blog1 = new Blog ("Blog1", new Timestamp(Calendar.getInstance().getTimeInMillis()), usuario1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		blogA = new Blog ("BlogA", new Timestamp(Calendar.getInstance().getTimeInMillis()), usuario2);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		blog2 = new Blog ("Blog2", new Timestamp(Calendar.getInstance().getTimeInMillis()), usuario1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		blog3 = new Blog ("Blog3", new Timestamp(Calendar.getInstance().getTimeInMillis()), usuario1);
		
		usuario1.getBlogs().add(0,blog1);
		usuario1.getBlogs().add(0,blog2);
		usuario1.getBlogs().add(0,blog3);
		usuario2.getBlogs().add(0,blogA);
		
		entrada11 = new Articulo("Entrada 1.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", blog1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		entrada12 = new Articulo("Entrada 1.2", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", blog1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		entrada13 = new EnlaceAContenido("Entrada 1.3", new Timestamp(Calendar.getInstance().getTimeInMillis()), "http://www.udc.es", tipoHTML, blog1);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		entrada21 = new Articulo("Entrada 2.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), "Esta es una entrada", blog2);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		entradaA1 = new EnlaceAContenido("Entrada A.1", new Timestamp(Calendar.getInstance().getTimeInMillis()), "http://www.udc.es/laudeamus.mp3", tipoMP3, blogA);
		try { Thread.sleep(timeout); } catch (InterruptedException e) {}
		
		blog1.getEntradas().add(0,entrada11);
		blog1.getEntradas().add(0,entrada12);
		blog1.getEntradas().add(0,entrada13);		
		blog2.getEntradas().add(0,entrada21);
		blogA.getEntradas().add(0,entradaA1);

		// Necesaria propagacion activada en mappings para que esto funcione
		usuarioService.registrar(usuario1);
		usuarioService.registrar(usuario2);
	}
	
	public void eliminaSetDatosPrueba() {

		// Necesaria propagacion activada en mappings para que esto funcione
		for (Usuario u:usuarioService.listarUsuarios()){
			usuarioService.borrar(u);
		}
		
		tipoService.borrar(tipoHTML);
		tipoService.borrar(tipoPlain);
		tipoService.borrar(tipoMP3);
	}
}
