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

public class BlogResourceClient {
	
	public static void main(String args[]) {
		
		Client client = Client.create(new DefaultClientConfig());
		URI uri = UriBuilder.fromUri(
			"http://localhost:8080/asi-app/example/blog").build();
		WebResource resource = client.resource(uri);
			
		// CREATE
		Calendar fecha = Calendar.getInstance();
		/*
		Blog b = new Blog( (long)1680, "Cliente", fecha);
		
		Form form = new Form();
		form.add("userId", b.getUserId());
		form.add("titulo", b.getTitulo());
		form.add("fecha",b.getFechaCreacion());
		
		ClientResponse response = resource.path("create").accept(
				MediaType.TEXT_XML).post(ClientResponse.class, form);
		
		System.out.println("INSERTADO: " + response.getEntity(Blog.class));
		
		
	// UPDATE
			
			form = new Form();
			form.add("blogId",1770);
			form.add("userId", 1680);
			form.add("titulo", "OTRA VEZ");
			form.add("fecha",fecha);
			
			 response = resource.path("update").path("1770").accept(
					MediaType.TEXT_XML).post(ClientResponse.class, form);
			
			System.out.println("ACTUALIZADO: " + response.getEntity(Blog.class));
		*/
		
		// READ
		List<Blog> blogs = resource.path("findall").accept(
				MediaType.TEXT_XML).get(new GenericType<List<Blog>>() {});
		
		System.out.println("===== Lista de blogs =====");
		for (Blog bs : blogs)
			System.out.println(bs);
	/*
		// READ
		Blog blog;
		blog = resource.path("find").path("1761").accept(
				MediaType.TEXT_XML).get(Blog.class);
		
		System.out.println("===== Blog 1761 =====");
		System.out.println(blog);
	
		// DELETE
		resource.path("delete").path("1769").delete();
*/
		
	}

}