package es.udc.fi.lbd.monuzz.id.blogs.daos;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
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
public class UsuarioDAOHibImpl implements UsuarioDAO {

		
	@Autowired
	private SessionFactory factory;
	
	@Override
	public Long create(Usuario miUsuario) {
		if (miUsuario.getIdUsuario()!=null){
				throw new RuntimeException("Intento de alta usuario ya persistente");
		}
		Long id = (Long) factory.getCurrentSession().save(miUsuario);
		return id;
	}

		

	
	
	@Override
	public void remove(Usuario miUsuario) {
		factory.getCurrentSession().delete(miUsuario);
	}
		
		

	
	
	@Override
	public void update(Usuario miUsuario) {
		factory.getCurrentSession().update(miUsuario);
	}

	
	
	@Override
	public List<Usuario> findAll() {
		/*Criteria criteria = factory.getCurrentSession().createCriteria(Usuario.class);
		criteria.addOrder(Order.desc("nombre"));
		criteria.setProjection(Projections.distinct(Projections.property("idUsuario")));
		List <Usuario> listaUsuarios = (List<Usuario>) criteria.list();*/
		Query query = factory.getCurrentSession().createQuery("from Usuario");
		List <Usuario> listaUsuarios = (List<Usuario>) query.list();
		return listaUsuarios;
	}
	

	@Override
	public Usuario findByNombreDeUsuario(String nombreDeUsuario) {
	
		Criteria criteria = factory.getCurrentSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("nombreDeUsuario", nombreDeUsuario));
		if (criteria.list().isEmpty()) return null;
		Usuario miUsuario = (Usuario) criteria.list().get(0);
		return miUsuario;
	}
		
	
	
}