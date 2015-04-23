package es.udc.asi.app.rest;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;



import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.udc.asi.app.model.adminservice.AdminService;
import es.udc.asi.app.model.blog.SqlBlogDao;
import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.blog.JdbcSqlBlogDao;
import es.udc.asi.app.model.blogservice.BlogService;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.util.InstanceNotFoundException;


@Component
@Path("/entrada")
public class EntradaResource{
	
	public EntradaResource() {
	}



@Autowired
private BlogService blogservice;

@Autowired
private AdminService adminservice;

// READ
@GET
@Path("findall/{id}")
@Produces(MediaType.TEXT_XML)
public List<Entrada> findAll(@PathParam("id") Long id) throws InstanceNotFoundException, SQLException {
			return blogservice.VerListaEntradas(id);
	
}
	
//READ
@GET
@Path("find/{entradaId}")
@Produces(MediaType.TEXT_XML)
public Entrada find(@PathParam("entradaId") Long entradaId ) throws InstanceNotFoundException, SQLException {
			return blogservice.VerEntrada(entradaId);
}



//CREATE
@POST
@Path("create")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_XML)
public Response create(@FormParam("blogId") Long blogId,
		@FormParam("titulo") String titulo,
		@FormParam("tipo") int tipo,@FormParam("texto") String texto,
		@FormParam("url") String url,@FormParam("tipoEnlace") Long tipoEnlace) throws SQLException {
			//Long tipoE = (long) 467;
			Calendar fecha = Calendar.getInstance();
			Entrada e = new Entrada(blogId,titulo,fecha, tipo,texto,url, tipoEnlace);
			e = adminservice.CrearEntrada(e);
			
			Response response = Response.ok(e).build();
			return response;
}



//UPDATE
@POST
@Path("update/{id}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_XML)
public Response update(@FormParam("id") Long id,@FormParam("blogId") Long blogId,
		@FormParam("titulo") String titulo,
		@FormParam("tipo") int tipo,@FormParam("texto") String texto,
		@FormParam("url") String url,@FormParam("tipoEnlace") Long tipoEnlace) throws SQLException, InstanceNotFoundException {
			//Long tipoE = (long) 467;
			Calendar fecha = Calendar.getInstance();
			Entrada e = new Entrada(id,blogId,titulo,fecha, tipo,texto,url, tipoEnlace);
			adminservice.ModificarEntrada(e);

			Response response = Response.ok(e).build();
			return response;
}



// DELETE
@DELETE
@Path("delete/{id}")
public void delete(@PathParam("id") Long id) throws InstanceNotFoundException, SQLException {
			
		adminservice.BorrarEntrada(id);
	 
}



}