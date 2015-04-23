package es.udc.fi.lbd.monuzz.id.blogs.daos;

import java.util.List;

import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
import es.udc.fi.lbd.monuzz.id.blogs.model.Entrada;

public interface EntradaDAO {
	Long create(Entrada miEntrada);
	void update (Entrada miEntrada);
	void remove (Entrada miEntrada);
	Entrada findById(Long id);
	List<Entrada> findByBlog (Blog miBlog);
}

