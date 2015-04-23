package es.udc.fi.lbd.monuzz.id.blogs.services;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.blogs.model.*;

public interface BlogService {
	void crearBlog(Blog miBlog);
	void modificarBlog(Blog miBlog);
	void borrarBlog(Blog miBlog);
	void publicarEntrada(Entrada miEntrada);
	void modificarEntrada(Entrada miEntrada);
	void borrarEntrada(Entrada miEntrada);
	Blog buscarBlogPorId (Long id);
	Entrada buscarEntradaPorId (Long id);
	List<Blog> listarBlogs();
	List<Blog> listarBlogsUsuario(Usuario miUsuario);
	List<Entrada> listarEntradasBlog(Blog miBlog);
}
