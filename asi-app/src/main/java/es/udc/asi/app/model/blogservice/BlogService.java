package es.udc.asi.app.model.blogservice;

import java.sql.SQLException;
import java.util.List;

import es.udc.asi.app.model.blog.Blog;
import es.udc.asi.app.model.entrada.Entrada;
import es.udc.asi.app.model.util.InstanceNotFoundException;


public interface BlogService{
	
	public List<Blog> ConsultarBlogs() throws InstanceNotFoundException, SQLException;
	
	public Blog VerBlog(Long blogId) throws InstanceNotFoundException, SQLException;
	
	public List<Entrada> VerListaEntradas(Long blogId) throws InstanceNotFoundException, SQLException;
	
	public Entrada VerEntrada(Long entradaId) throws InstanceNotFoundException, SQLException;

}
