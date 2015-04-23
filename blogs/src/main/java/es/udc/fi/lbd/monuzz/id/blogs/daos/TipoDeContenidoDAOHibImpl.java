package es.udc.fi.lbd.monuzz.id.blogs.daos;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.udc.fi.lbd.monuzz.id.blogs.model.TipoDeContenido;


@Repository
public class TipoDeContenidoDAOHibImpl implements TipoDeContenidoDAO {

	
	
	@Autowired
	SessionFactory factory;
	
	
	
	@Override
	public Long create(TipoDeContenido miTipo) {
		Long idMiTipo = (Long) factory.getCurrentSession().save(miTipo);
		return idMiTipo;
	}

	
	@Override
	public void remove(TipoDeContenido miTipo) {
		factory.getCurrentSession().delete(miTipo);		
	}

	@Override
	public TipoDeContenido findById(Long Id) {
		Criteria criteria = factory.getCurrentSession().createCriteria(TipoDeContenido.class);
		criteria.add(Restrictions.eq("idTipoContenido", Id));
		TipoDeContenido  miTipo = (TipoDeContenido) criteria.list().get(0);
		return miTipo;
	}

}
