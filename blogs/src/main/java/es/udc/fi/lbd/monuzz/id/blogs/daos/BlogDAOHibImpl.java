package es.udc.fi.lbd.monuzz.id.blogs.daos;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
import es.udc.fi.lbd.monuzz.id.blogs.model.Entrada;
/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
//import java.sql.Timestamp;
//import java.sql.Date;*/
import es.udc.fi.lbd.monuzz.id.blogs.model.Usuario;

@Repository
public class BlogDAOHibImpl implements BlogDAO {

		
	@Autowired
	SessionFactory factory;

	@Override
	public Long create(Blog miBlog) {
		if (miBlog.getIdBlog()!=null){
			throw new RuntimeException("Intento de publicar un blog ya persistente");
		}
		Long idMiBlog = (Long) factory.getCurrentSession().save(miBlog);
		return idMiBlog;
	}
		

	@Override
	public void update(Blog miBlog) {
		factory.getCurrentSession().update(miBlog);
	}
		
		
		
		


	@Override
	public void remove(Blog miBlog) {
		//miBlog= (Blog) factory.getCurrentSession().get(Blog.class, miBlog.getIdBlog());
		factory.getCurrentSession().delete(miBlog);
	}

	
	@Override
	public List<Blog> findAll() {
		
		Criteria criteria = factory.getCurrentSession().createCriteria(Blog.class);
		criteria.addOrder(Order.desc("fechaDeCreacion"));
		List<Blog> listaBlogs = (List<Blog>) criteria.list();
		return listaBlogs;
	}
		
		

	@Override
	public List<Blog> findByUsuario(Usuario miUsuario) {
		Criteria criteria = factory.getCurrentSession().createCriteria(Blog.class);
		criteria.add(Restrictions.eq("autor.idUsuario", miUsuario.getIdUsuario()));
		criteria.addOrder(Order.desc("fechaDeCreacion"));
		List<Blog>  misBlogs = (List<Blog>) criteria.list();
		return misBlogs;
	}
		


	@Override
	public Blog findById(Long Id) {
		Criteria criteria = factory.getCurrentSession().createCriteria(Blog.class);
		criteria.add(Restrictions.eq("idBlog", Id));
		if (criteria.list().isEmpty()) return null;
		Blog  miBlog = (Blog) criteria.list().get(0);
		return miBlog;
	}
		

}
