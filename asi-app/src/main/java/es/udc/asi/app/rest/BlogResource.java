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
import es.udc.asi.app.model.util.InstanceNotFoundException;


@Component
@Path("/blog")
public class BlogResource{
	
	public BlogResource() {
					}

/*
public void setBlogDAO(SqlBlogDao blogDao) {
	this.blogDao = blogDao;
}*/

@Autowired
private BlogService blogservice;

@Autowired
private AdminService adminservice;

// READ
@GET
@Path("findall")
@Produces(MediaType.TEXT_XML)
public List<Blog> findAll() throws InstanceNotFoundException, SQLException {
				return blogservice.ConsultarBlogs();
	
}
	
//READ
@GET
@Path("find/{id}")
@Produces(MediaType.TEXT_XML)
public Blog find(@PathParam("id") Long id) throws InstanceNotFoundException, SQLException {
				return blogservice.VerBlog(id);
}


//CREATE
@POST
@Path("create")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_XML)
public Response create(@FormParam("userId") Long userId,
		@FormParam("titulo") String titulo) throws SQLException {
	
				Calendar fecha = Calendar.getInstance();
				Blog b = new Blog( userId, titulo, fecha);
				b = adminservice.CrearBlog(b);	
	
				Response response = Response.ok(b).build();
	
				return response;
}


//UPDATE
@POST
@Path("update/{id}")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_XML)
public Response update(@FormParam("blogId") Long blogId, @FormParam("userId") Long userId,
	@FormParam("titulo") String titulo) throws SQLException, InstanceNotFoundException {

				Calendar fecha = Calendar.getInstance();
				Blog b = new Blog(blogId, userId, titulo, fecha);
				adminservice.ModificarBlog(b);
				Response response = Response.ok(b).build();
				return response;
}



// DELETE
@DELETE
@Path("delete/{id}")
public void delete(@PathParam("id") Long id) throws InstanceNotFoundException, SQLException {
				adminservice.BorrarBlog(new Long(id));
}



}