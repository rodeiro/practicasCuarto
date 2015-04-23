package es.udc.fi.lbd.monuzz.id.blogs.daos;


import java.util.List;

import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
import es.udc.fi.lbd.monuzz.id.blogs.model.Usuario;

public interface BlogDAO {
	Long create(Blog miBlog);
	void update (Blog miBlog);
	void remove (Blog miBlog);
	List<Blog> findAll ();
	List<Blog> findByUsuario (Usuario miUsuario);
	Blog findById(Long Id);
}
