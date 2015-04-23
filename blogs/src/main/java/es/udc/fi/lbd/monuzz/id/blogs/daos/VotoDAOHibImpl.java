package es.udc.fi.lbd.monuzz.id.blogs.daos;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import es.udc.fi.lbd.monuzz.id.blogs.model.Voto;

@Repository
public class VotoDAOHibImpl implements VotoDAO{

	@Autowired
	SessionFactory factory;
	
	
	
	
	@Override
	public void create(Voto miVoto) {
	 factory.getCurrentSession().save(miVoto);
			
	}

	@Override
	public void remove(Voto miVoto) {
		factory.getCurrentSession().delete(miVoto);		
	}

}
