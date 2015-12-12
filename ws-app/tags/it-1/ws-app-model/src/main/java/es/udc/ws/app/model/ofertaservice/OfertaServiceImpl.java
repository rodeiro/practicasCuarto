package es.udc.ws.app.model.ofertaservice;
//PROBLEMAS: VALIDATION, OFERTA Y RESERVA ID
import static es.udc.ws.app.model.util.ModelConstants.MAX_MAX;
import static es.udc.ws.app.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.ReservaExpirationException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.oferta.SqlOfertaDao;
import es.udc.ws.app.model.oferta.SqlOfertaDaoFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.app.util.validation.PropertyValidator;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;

public class OfertaServiceImpl implements OfertaService {
	/*
	 * IMPORTANT: Some JDBC drivers require "setTransactionIsolation" to
	 * be called before "setAutoCommit".
	 */

	private DataSource dataSource;

	private SqlOfertaDao ofertaDao = null;
	private SqlReservaDao reservaDao = null;

	public OfertaServiceImpl() {
		dataSource = DataSourceLocator.getDataSource(OFERTA_DATA_SOURCE);
		ofertaDao = SqlOfertaDaoFactory.getDao();
		reservaDao = SqlReservaDaoFactory.getDao();
	}
	private List<Oferta> eliminarDuplicados(List<Oferta> list){
	    List<Oferta> listSinDuplicados = new ArrayList<Oferta>();
	    Set<Oferta> set = new LinkedHashSet<Oferta>(list);
	    listSinDuplicados.addAll(set);
	    return listSinDuplicados;
	}
	private void validateOferta(Oferta oferta) throws InputValidationException {

		PropertyValidator.validateMandatoryString("nombre", oferta.getNome());
		PropertyValidator.validateMandatoryString("estado", oferta.getEstado());
		PropertyValidator.validateInt("maximo",	oferta.getMax());
		PropertyValidator.validateMandatoryString("description",
				oferta.getDescripcion());
		PropertyValidator.validateFloat("coste real", oferta.getCosteR()
				);
		PropertyValidator.validateFloat2("coste real", oferta.getCosteR(),0
				,MAX_PRICE);
		PropertyValidator.validateFloat("coste descuento", oferta.getCosteD()
				);
		PropertyValidator.validateFloat2("coste descuento", oferta.getCosteD(),0
				,MAX_PRICE);
		PropertyValidator.validatePastDate("fecha inicio",
				oferta.getDataini());
		PropertyValidator.validateTwoDates("fecha fin",
				oferta.getDatafin(),oferta.getDataini());
		PropertyValidator.validateTwoDates("fecha fin",
				oferta.getDatadis(),oferta.getDataini());
	}

	@Override
	public Oferta addOferta(String nomeO,String descripcion,
			Calendar dataIni,Calendar dataFin, Calendar dataDis,
			float costR, float costD, int MaxI) throws InputValidationException {

		Oferta oferta = new Oferta(descripcion,dataIni,dataFin,dataDis,costR,costD,
				MaxI,nomeO);
		validateOferta(oferta);

		try (Connection connection = dataSource.getConnection()) {
						
			try {

				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				/* Do work. */
				Oferta createdOferta = ofertaDao.create(connection, oferta);
				
				/* Commit. */
				connection.commit();
				
				return createdOferta;

			} catch (SQLException e) {
				connection.rollback();
				throw new RuntimeException(e);
			} catch (RuntimeException|Error e) {
				connection.rollback();
				throw e;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateOferta(Oferta oferta) throws InputValidationException,
			InstanceNotFoundException,BadStateException {

		validateOferta(oferta);
		
		if (oferta.getEstado() == "Creado"){
			try (Connection connection = dataSource.getConnection()) {
				try {
		
					/* Prepare connection. */
					connection
							.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					connection.setAutoCommit(false);
		
					/* Do work. */
					ofertaDao.update(connection, oferta);
					
					/* Commit. */
					connection.commit();
		
				} catch (InstanceNotFoundException e) {
					connection.commit();
					throw e;
				} catch (SQLException e) {
					connection.rollback();
					throw new RuntimeException(e);
				} catch (RuntimeException|Error e) {
					connection.rollback();
					throw e;
				}
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}else{
			throw new BadStateException(oferta.getOfertaId(),oferta.getEstado());
		}
}

	

	@Override
	public void removeOferta(Long ofertaId) throws InstanceNotFoundException,
	BadStateException {
		
	
		try (Connection connection = dataSource.getConnection()) {
			Oferta oferta = ofertaDao.find(connection,ofertaId);
			if (oferta.getEstado() == "Creado" || oferta.getEstado() == "Liberado" ){
				try {
		
					/* Prepare connection. */
					connection
							.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					connection.setAutoCommit(false);
		
					/* Do work. */
					ofertaDao.remove(connection, ofertaId);
					
					/* Commit. */
					connection.commit();
		
				} catch (InstanceNotFoundException e) {
					connection.commit();
					throw e;
				} catch (SQLException e) {
					connection.rollback();
					throw new RuntimeException(e);
				} catch (RuntimeException|Error e) {
					connection.rollback();
					throw e;
				}
		}else{
			throw new BadStateException(oferta.getOfertaId(),oferta.getEstado());
		}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Oferta findOferta(Long ofertaId) throws InstanceNotFoundException {

		try (Connection connection = dataSource.getConnection()) {
			return ofertaDao.find(connection, ofertaId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Oferta> findOfertas (String keywords, Calendar dataFin,
			String estadoOferta)
	{
		List<Oferta> ListaOfertas= new ArrayList<Oferta>();	
		try (Connection connection = dataSource.getConnection()) {
			if(keywords == null && dataFin == null && estadoOferta == null )
			{
				ListaOfertas = ofertaDao.findAll(connection);
				
			}else{
				if (keywords != null){
					
					List<Oferta> ListaOfertasK = ofertaDao.findByKeywords(connection, keywords);
					ListaOfertas.addAll(ListaOfertasK);
				}
				if (dataFin != null){
					
					List<Oferta> ListaOfertasD = ofertaDao.findByDatafin(connection, dataFin);
					ListaOfertas.addAll(ListaOfertasD);
				}	
				if (estadoOferta != null){
					
					List<Oferta> ListaOfertasE = ofertaDao.findByEstado(connection, estadoOferta);
					ListaOfertas.addAll(ListaOfertasE);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return eliminarDuplicados(ListaOfertas);
	}

	@Override
	public Long buyOferta(Long ofertaId, String mailUs, String conta)
			throws InstanceNotFoundException, InputValidationException,
			MaxNReservaException, BadStateException {

		PropertyValidator.validateCreditCard(conta);		

		try (Connection connection = dataSource.getConnection()) {
			Oferta oferta = ofertaDao.find(connection, ofertaId);
			if (oferta.getMax() < MAX_MAX){
				try {
		
					/* Prepare connection. */
					connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					connection.setAutoCommit(false);
		
					/* Do work. */
					if (oferta.getEstado()== "Creado")
						oferta.setEstado("Comprometido");
					updateOferta(oferta);
					Reserva reserva = reservaDao.create(connection, new Reserva( mailUs,
							conta,ofertaId));
					
					/* Commit. */
					connection.commit();
					
					return reserva.getReservaId();
		
				
				} catch (SQLException e) {
					connection.rollback();
					throw new RuntimeException(e);
				} catch (RuntimeException|Error e) {
					connection.rollback();
					throw e;
				}
		}else{
			throw new MaxNReservaException(ofertaId,oferta.getMax());
		}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Reserva findReserva(Long reservaId) throws InstanceNotFoundException,
			ReservaExpirationException {

		try (Connection connection = dataSource.getConnection()) {
			Reserva reserva = reservaDao.find(connection, reservaId);
			return reserva;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}


	@Override
	public List<Reserva> ObtenerReservas(Long ofertaId, Boolean estado) throws InstanceNotFoundException,ReservaExpirationException{
		List <Reserva> Resconestado = new ArrayList<Reserva>();
		List <Reserva> listaRes = new ArrayList<Reserva>();
		try (Connection connection = dataSource.getConnection()) {
				listaRes = reservaDao.findByOid(connection, ofertaId);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		if (estado == null){
			return listaRes;
				
		}else{	
			for(int x=0;x<listaRes.size()-1;x++){
				if (listaRes.get(x).getestadoRes() == estado){
					Resconestado.add(listaRes.get(x));
				}
			}
			return Resconestado;
			
		}
	}
		

	@Override
	public void reclamarOferta(Long reservaId) throws InstanceNotFoundException,ReservaExpirationException,
	InputValidationException, BadStateException{
		try (Connection connection = dataSource.getConnection()) {
			Reserva reserva = reservaDao.find(connection, reservaId);
			reserva = findReserva(reservaId);
			Long ofertaid = reserva.getOfid();
			Oferta oferta = findOferta(ofertaid);
			if (oferta.getEstado() !=  "Comprometido"){
				throw new ReservaExpirationException(reservaId,oferta.getDatadis());
			}else{
				List<Reserva> listaRes= this.ObtenerReservas(ofertaid,true); 
				int i = 0;
				while (listaRes.get(i).getReservaId()!=reservaId){
					i++;
				}
				listaRes.get(i).setestadoRes(false);
				boolean completa=false;
				for(int x=0;x<listaRes.size();x++){
					if (listaRes.get(x).getestadoRes()){
						completa=true;
					}
			
				}
				if (completa) {
					oferta.setEstado("Liberado");
					updateOferta(oferta);
				}	
			
						
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	

}
