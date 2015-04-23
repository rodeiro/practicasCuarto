package es.udc.fi.lbd.monuzz.id.blogs.daos;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.udc.fi.lbd.monuzz.id.blogs.model.Blog;
import es.udc.fi.lbd.monuzz.id.blogs.model.Entrada;


@Repository
public class EntradaDAOHibImpl implements EntradaDAO {

		
	@Autowired
	SessionFactory factory;

	@Override
	public Long create(Entrada miEntrada) {
		if (miEntrada.getIdEntrada()!=null){
			throw new RuntimeException("Intento de publicar una entrada ya persistente");
	}
		Long idMiEntrada = (Long) factory.getCurrentSession().save(miEntrada);
		return idMiEntrada;
	}

	@Override
	public void update(Entrada miEntrada) {
		factory.getCurrentSession().update(miEntrada);		
	}

	@Override
	public void remove(Entrada miEntrada) {
		factory.getCurrentSession().delete(miEntrada);
	}

	@Override
	public Entrada findById(Long id) {
		Criteria criteria = factory.getCurrentSession().createCriteria(Entrada.class);
		criteria.add(Restrictions.eq("idEntrada", id));
		if (criteria.list().isEmpty()) return null;
		Entrada  miEntrada = (Entrada) criteria.list().get(0);
		return miEntrada;
	}

	@Override
	public List<Entrada> findByBlog(Blog miBlog) {
		Criteria criteria = factory.getCurrentSession().createCriteria(Entrada.class);
		criteria.add(Restrictions.eq("blog.idBlog", miBlog.getIdBlog()));
		criteria.addOrder(Order.desc("fechaDePublicacion"));
		List<Entrada>  misEntradas = (List<Entrada>) criteria.list();
		return misEntradas;
	}

	

}