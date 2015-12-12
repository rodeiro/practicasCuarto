package es.udc.ws.app.model.ofertaservice;
import static es.udc.ws.app.model.util.ModelConstants.MAX_MAX;

import static es.udc.ws.app.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import javax.sql.DataSource;

import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.ReservaEmailException;
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
			float costR, float costD, Integer MaxI,int maxres) throws InputValidationException {

		Oferta oferta = new Oferta(descripcion,dataIni,dataFin,dataDis,costR,costD,
				MaxI,nomeO,maxres);
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
		    
			try (Connection connection = dataSource.getConnection()) {
				Oferta oferta1=ofertaDao.find(connection,oferta.getOfertaId());
				
				if (oferta1.getEstado().equals("Creado")){
					try {
						
						/* Prepare connection. */
						connection
								.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
						connection.setAutoCommit(false);
						oferta.setMaxRes(oferta1.getMaxRes());

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
				}else{
					throw new BadStateException(oferta.getOfertaId(),oferta.getEstado());
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		
}

	

	@Override
	public void removeOferta(Long ofertaId) throws InstanceNotFoundException,
	BadStateException {
		
		
		
		try (Connection connection = dataSource.getConnection()) {
			Oferta oferta = ofertaDao.find(connection,ofertaId);
			
			if (oferta.getEstado().equals("Creado") || oferta.getEstado().equals("Liberado")  ){
				try {
		
					/* Prepare connection. */
					connection
							.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					connection.setAutoCommit(false);
		
					/* Do work. */
					List<Reserva> listReservas = new ArrayList<Reserva>();
					listReservas = reservaDao.findByOid(connection, ofertaId);
					for(Reserva r : listReservas){
						reservaDao.remove(connection,r.getReservaId());
					}
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
					
					ListaOfertas = ofertaDao.findByKDE(connection, keywords,
							estadoOferta,dataFin);
				
				
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	return ListaOfertas;
	}

	@Override
	public Long buyOferta(Long ofertaId, String mailUs, String conta)
			throws InstanceNotFoundException, InputValidationException, OfertaExpirationException,
			MaxNReservaException, BadStateException, ReservaEmailException {

		PropertyValidator.validateCreditCard(conta);		

		try (Connection connection = dataSource.getConnection()) {
			Oferta oferta = ofertaDao.find(connection, ofertaId);

			if ((oferta.getMax() == null) || (oferta.getMaxRes()<oferta.getMax())){
				try {
					
					/* Prepare connection. */
					connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
					connection.setAutoCommit(false);
					Calendar now = Calendar.getInstance();
					if ((oferta.getDatafin()).after(now)){
							
							/* Do work. */
							if (oferta.getEstado().equals("Creado"))
								oferta.setEstado("Comprometido");
						
							oferta.setResS();							
							ofertaDao.update(connection,oferta);
							Reserva reserva = null;
							List<Reserva> reservas1 = reservaDao.findByIE(connection, ofertaId, null);
							for (Reserva r : reservas1){
								if (mailUs.equals(r.getemailUs())){
									throw new ReservaEmailException(mailUs);
								}
							}
							reserva = reservaDao.create(connection, new Reserva( mailUs,
										conta,ofertaId));
							
							/* Commit. */
							connection.commit();
							
							return reserva.getReservaId();
					
					}else{
						throw new OfertaExpirationException(oferta.getOfertaId(),oferta.getDatafin());	
					}
				
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
	public Reserva findReserva(Long reservaId) throws InstanceNotFoundException{

		try (Connection connection = dataSource.getConnection()) {
			Reserva reserva = reservaDao.find(connection, reservaId);
			return reserva;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}


	@Override
	public List<Reserva> ObtenerReservas(Long ofertaId, Boolean estado) 
			throws InstanceNotFoundException{
		
		
		List <Reserva> listaRes = new ArrayList<Reserva>();
		try (Connection connection = dataSource.getConnection()) {
				listaRes = reservaDao.findByIE(connection, ofertaId,estado);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			return listaRes;
			
	}

	@Override
	public void reclamarOferta(Long reservaId) throws InstanceNotFoundException,
	InputValidationException, BadStateException,OfertaExpirationException{
		try (Connection connection = dataSource.getConnection()) {
			Reserva reserva = reservaDao.find(connection, reservaId);
			Long ofertaid = reserva.getOfid();
			Oferta oferta = ofertaDao.find(connection,ofertaid);
			if (!oferta.getEstado().equals("Comprometido")){
				throw new BadStateException(oferta.getOfertaId(),oferta.getEstado());
			}else{
				Calendar now = Calendar.getInstance();
				if (!oferta.getDatadis().before(now)){
					List<Reserva> listaRes= this.ObtenerReservas(ofertaid,false); 
					
					int i = 0;
					while (listaRes.get(i).getReservaId()!=reservaId){
						i++;
					}
					listaRes.get(i).setestadoRes(true);
					reservaDao.update(connection, listaRes.get(i));
					boolean completa=true;
					for(int x=0;x<listaRes.size();x++){
						if (!listaRes.get(x).getestadoRes()){
							completa=false;
						}
				
					}				
					if (completa) {
					oferta.setEstado("Liberado");
					}	
					oferta.setResR();
					ofertaDao.update(connection,oferta);
				}else{
					throw new OfertaExpirationException (oferta.getOfertaId(),oferta.getDatadis());
				}
			}	
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	

}
