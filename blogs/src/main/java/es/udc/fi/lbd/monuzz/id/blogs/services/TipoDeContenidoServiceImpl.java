package es.udc.fi.lbd.monuzz.id.blogs.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import es.udc.fi.lbd.monuzz.id.blogs.daos.TipoDeContenidoDAO;
import es.udc.fi.lbd.monuzz.id.blogs.model.TipoDeContenido;


@Service
@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT, readOnly=false,value = "miTransactionManager")
public class TipoDeContenidoServiceImpl implements TipoDeContenidoService{

	
	static Logger log = Logger.getLogger("tipos");

	@Autowired
	TipoDeContenidoDAO tipoDAO;
	
	
	@Override
	public void registrar(TipoDeContenido miTipo) {
		try{
			log.info("\n HOLA");
			tipoDAO.create(miTipo);
			log.info("\n ADIOS");
			log.info("Tipo creado");
		}catch (DataAccessException e){
			log.error("Fallo durante el registro del tipo");
			throw e;
		}		
	}

	@Override
	public void borrar(TipoDeContenido miTipo) {
		try{
			tipoDAO.remove(miTipo);
			log.info("Tipo borrado");
		}catch(DataAccessException e){
			log.error("Fallo durante el borrado del tipo");
			throw e;
		}		
	}

	@Override
	public TipoDeContenido BuscarPorId(Long id) {
		return tipoDAO.findById(id);
	}

}
