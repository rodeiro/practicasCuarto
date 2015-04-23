package es.udc.fi.lbd.monuzz.id.blogs.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.udc.fi.lbd.monuzz.id.blogs.daos.BlogDAO;
import es.udc.fi.lbd.monuzz.id.blogs.daos.EntradaDAO;
import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
import es.udc.fi.lbd.monuzz.id.blogs.model.Entrada;
import es.udc.fi.lbd.monuzz.id.blogs.model.Usuario;


@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false)
public class BlogServiceImpl implements BlogService{

	
	static Logger log = Logger.getLogger("blogs");
	
	@Autowired
	BlogDAO blogDAO;
	
	@Autowired
	EntradaDAO entradaDAO;
	
	
	@Override
	@Transactional(value = "miTransactionManager")
	public void crearBlog(Blog miBlog) {
		try{
			blogDAO.create(miBlog);
			log.info("Blog creado");
		}catch (DataAccessException e){
			log.error("Fallo durante la creación del blog");
			throw e;
		}		
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void modificarBlog(Blog miBlog) {
		try{
			blogDAO.update(miBlog);
			log.info("Blog actualizado");
		}catch(DataAccessException e){
			log.error("Fallo durante el actualizado del blog");
			throw e;
		}		
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void borrarBlog(Blog miBlog) {
		try{
			blogDAO.remove(miBlog);
			log.info("Blog borrado");
		}catch(DataAccessException e){
			log.error("Fallo durante el borrado del blog");
			throw e;
		}		
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void publicarEntrada(Entrada miEntrada) {
		try{
			entradaDAO.create(miEntrada);
			log.info("Entrada creada");
		}catch (DataAccessException e){
			log.error("Fallo durante la creación de la entrada");
			throw e;
		}				
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void modificarEntrada(Entrada miEntrada) {
		try{
			entradaDAO.update(miEntrada);
			log.info("Entrada actualizada");
		}catch(DataAccessException e){
			log.error("Error actualizando la entrada");
			throw e;
		}				
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public void borrarEntrada(Entrada miEntrada) {
		try{
			entradaDAO.remove(miEntrada);
			log.info("Entrada borrada");
		}catch(DataAccessException e){
			log.error("Error borrando la entrada");
			throw e;
		}			
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public Blog buscarBlogPorId(Long id) {
		return blogDAO.findById(id);
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public Entrada buscarEntradaPorId(Long id) {
		return entradaDAO.findById(id);
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public List<Blog> listarBlogs() {
		return blogDAO.findAll();
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public List<Blog> listarBlogsUsuario(Usuario miUsuario) {
		return blogDAO.findByUsuario(miUsuario);
	}

	@Override
	@Transactional(value = "miTransactionManager")
	public List<Entrada> listarEntradasBlog(Blog miBlog) {
		return entradaDAO.findByBlog(miBlog);
	}

}
