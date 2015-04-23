package es.udc.asi.app.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.entrada.Entrada;

public class EntradaResourceClient {
	
	public static void main(String args[]) {
		
		Client client = Client.create(new DefaultClientConfig());
		URI uri = UriBuilder.fromUri(
			"http://localhost:8080/asi-app/example/entrada").build();
		WebResource resource = client.resource(uri);
		
		// CREATE
		Calendar fecha = Calendar.getInstance();
		
		Entrada b = new Entrada( (long)1770, "Cliente", fecha, 1, "TEXTO","http://www.twitter.com",(long)467);
		
		Form form = new Form();
		form.add("blogId", b.getBlog());
		form.add("titulo", b.getTitulo());
		form.add("fecha",b.getFechaPublicacion());
		form.add("tipo", b.getTipo());
		form.add("texto",b.getTexto());
		form.add("url", b.getUrl());
		form.add("tipoEnlace",b.getTipoId());
		
		ClientResponse response = resource.path("create").accept(
				MediaType.TEXT_XML).post(ClientResponse.class, form);
		
		System.out.println("INSERTADO: " + response.getEntity(Entrada.class));
	
		/*
	// UPDATE
			
			form = new Form();
			form.add("id", 664);
			form.add("blogId", 1761);
			form.add("titulo", "MODIFICADO");
			form.add("fecha",fecha);
			form.add("tipo", 0);
			form.add("texto", "TEXTO");
			form.add("url", null);
			form.add("tipoEnlace",null);
			
		 response = resource.path("update").path("664").accept(
					MediaType.TEXT_XML).post(ClientResponse.class, form);
			
			System.out.println("ACTUALIZADO: " + response.getEntity(Entrada.class));
		
		*/
		
		
		// READ
		List<Entrada> entradas = resource.path("findall").path("1759").accept(
				MediaType.TEXT_XML).get(new GenericType<List<Entrada>>() {});
		
		System.out.println("===== Lista de entradas =====");
		for (Entrada es : entradas)
			System.out.println(es); 
		
		
		// READ
		Entrada entrada;
		entrada = resource.path("find").path("659").accept(
				MediaType.TEXT_XML).get(Entrada.class);
		
		System.out.println("===== Entrada 659 =====");
		System.out.println(entrada);
		
		/*
		// DELETE
		resource.path("delete").path("665").delete();
		*/
		
	}

}