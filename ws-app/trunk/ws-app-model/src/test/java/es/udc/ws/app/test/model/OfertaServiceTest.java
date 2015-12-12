package es.udc.ws.app.test.model;


import static es.udc.ws.app.model.util.ModelConstants.MAX_MAX;
import static es.udc.ws.app.model.util.ModelConstants.MAX_PRICE;
import static es.udc.ws.app.model.util.ModelConstants.OFERTA_DATA_SOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaService;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.model.reserva.SqlReservaDao;
import es.udc.ws.app.model.reserva.SqlReservaDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
public class OfertaServiceTest {

	private final long NON_EXISTENT_OFERTA_ID = -1;
	private final long NON_EXISTENT_RESERVA_ID = -1;
	private final String USER_ID = "ws-user";
	
	private final Calendar dataIni = Calendar.getInstance();
	private  Calendar dataDis = Calendar.getInstance();
	private  Calendar dataFin = Calendar.getInstance();
	

	private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	private final String INVALID_CREDIT_CARD_NUMBER = "";

	private static OfertaService ofertaService = null;
	
	private static SqlReservaDao reservaDao = null;

	@BeforeClass
	public static void init() {

		/*
		 * Create a simple data source and add it to "DataSourceLocator" (this
		 * is needed to test "es.udc.ws.ofertas.model.ofertaservice.OfertaService"
		 */
		DataSource dataSource = new SimpleDataSource();

		/* Add "dataSource" to "DataSourceLocator". */
		DataSourceLocator.addDataSource(OFERTA_DATA_SOURCE, dataSource);

		ofertaService = OfertaServiceFactory.getService();

		reservaDao = SqlReservaDaoFactory.getDao();

	}

	private Oferta getValidOferta(String title) {
		return new Oferta("Oferta description",Calendar.getInstance(), Calendar.getInstance(),
				Calendar.getInstance(),100.0F,90.0F,100,"Oferta1",0);
	}

	private Oferta getValidOferta() {
		return getValidOferta("Oferta title");
	}

	private Oferta createOferta(Oferta oferta) {

		Oferta addedOferta = null;
		dataDis.add(Calendar.DATE, 10);
		dataFin.add(Calendar.DATE,2);
		
		
		try {
			addedOferta = ofertaService.addOferta("NomeOferta","Oferta description",
					dataIni, dataFin,
					dataDis,100.0f,90.0f,100,0);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return addedOferta;

	}
	private List<Oferta> createOfertas(List<Oferta> ofertas) {	

		Oferta ofer =null;
		List<Oferta> lista =new ArrayList<Oferta>();
		try {
			
			for (Oferta of:ofertas){
				
				ofer =ofertaService.addOferta(of.getNome(),of.getDescripcion(),
					dataIni, dataFin,
					dataDis,100.0f,90.0f,100,0);
				
				lista.add(ofer);
			}
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return lista;
	}
	private void removeOferta(Long ofertaId) throws BadStateException,
		InstanceNotFoundException {

		Oferta oferta = ofertaService.findOferta(ofertaId);
		try {
			ofertaService.removeOferta(ofertaId);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		} catch (BadStateException e) {
			throw new BadStateException(ofertaId,oferta.getEstado());
		}

	}

	private void removeReserva(Long reservaId) {
		
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
	
				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				/* Do work. */
				reservaDao.remove(connection, reservaId);
				
				/* Commit. */
				connection.commit();
	
			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
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

	private void updateReserva(Reserva reserva) {
		
		DataSource dataSource = DataSourceLocator
				.getDataSource(OFERTA_DATA_SOURCE);
		
		try (Connection connection = dataSource.getConnection()) {

			try {
	
				/* Prepare connection. */
				connection
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.setAutoCommit(false);
	
				/* Do work. */
				reservaDao.update(connection, reserva);
				
				/* Commit. */
				connection.commit();
	
			} catch (InstanceNotFoundException e) {
				connection.commit();
				throw new RuntimeException(e);
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

	@Test
	public void testAddOfertaAndFindOferta() throws InputValidationException,
			InstanceNotFoundException, BadStateException {

		Oferta oferta = getValidOferta();
		Oferta addedOferta = null;

		addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
				oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
				oferta.getMax(),oferta.getMaxRes());
		Oferta foundOferta = ofertaService.findOferta(addedOferta.getOfertaId());

		assertEquals(addedOferta, foundOferta);

		// Clear Database
		removeOferta(addedOferta.getOfertaId());

	}

		
	@Test
	public void testAddInvalidOferta() throws BadStateException, InstanceNotFoundException {

		Oferta oferta = getValidOferta();
		Oferta addedOferta = null;
		boolean exceptionCatched = false;

		try {
			// Check oferta title not null
			oferta.setNome(null);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true; 
			}
			assertTrue(exceptionCatched);

			// Check oferta title not empty
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setNome("");
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta runtime >= 0
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setMax((int) -1);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta max
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setCosteR((float)-1.0);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta description not null
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setDescripcion(null);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta price >= 0
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setCosteR((float) -1.0f);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta price <= MAX_PRICE
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setCosteR((float) (MAX_PRICE + 1.0f));
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
			//CosteD
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setCosteD((float) -1.0f);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax(),oferta.getMaxRes());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);
		
			
			//Comprobar que una oferta se crea con Estado=creado
			oferta = getValidOferta();
			assertEquals("Creado", oferta.getEstado());
			
			
		} finally {
			if (!exceptionCatched) {
				// Clear Database
				removeOferta(addedOferta.getOfertaId());
			}
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentOferta() throws InstanceNotFoundException {
		ofertaService.findOferta(NON_EXISTENT_OFERTA_ID);
	
	}

	@Test
	public void testUpdateOferta() throws InputValidationException,
			InstanceNotFoundException, BadStateException {

		Oferta oferta = createOferta(getValidOferta());
		try {

			oferta.setNome("NomeOferta");
			oferta.setCosteR((float) 80.0f);
			oferta.setCosteD((float) 70.0f);
			oferta.setDescripcion("new description");
			oferta.setMax(100);

			ofertaService.updateOferta(oferta);

			Oferta updatedOferta = ofertaService.findOferta(oferta.getOfertaId());
			System.err.println(oferta);
			System.err.println(updatedOferta);

			assertEquals(oferta, updatedOferta);

		} finally {
			// Clear Database
			removeOferta(oferta.getOfertaId());
		}

	}

	@Test(expected = InputValidationException.class)
	public void testUpdateInvalidOferta() throws InputValidationException,
			InstanceNotFoundException, BadStateException {

		Oferta oferta = createOferta(getValidOferta());
		try {
			// Check oferta max not null
			oferta = ofertaService.findOferta(oferta.getOfertaId());
			oferta.setMax((int) -1);
			ofertaService.updateOferta(oferta);
		} finally {
			// Clear Database
			removeOferta(oferta.getOfertaId());
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateNonExistentOferta() throws InputValidationException,
			InstanceNotFoundException, BadStateException {

		Oferta oferta = getValidOferta();
		oferta.setOfertaId(NON_EXISTENT_OFERTA_ID);
		ofertaService.updateOferta(oferta);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveOferta() throws InstanceNotFoundException, BadStateException {

		Oferta oferta = createOferta(getValidOferta());
		boolean exceptionCatched = false;
		try {
			ofertaService.removeOferta(oferta.getOfertaId());
		} catch (InstanceNotFoundException e) {
			exceptionCatched = true;
		}
		assertTrue(!exceptionCatched);

		ofertaService.findOferta(oferta.getOfertaId());

	}



	@Test
	public void testFindOfertas() throws BadStateException, InstanceNotFoundException {

		// Add ofertas
		List<Oferta> ofertas = new LinkedList<Oferta>();
		Oferta oferta1= new Oferta("oferta 1",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta1",0);
		Oferta oferta2= new Oferta("oferta 2",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta2",0);
		Oferta oferta3= new Oferta("oferta 3",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta3",0);
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta3);
		List<Oferta> listaO =createOfertas(ofertas);
		
		try {
			//Proba devolucion todo
			List<Oferta> foundOfertas = ofertaService.findOfertas(null,null,null);
					
			assertEquals(listaO,foundOfertas);
			
			foundOfertas = ofertaService.findOfertas("Oferta",
					dataFin,"Creado");
			
			assertEquals(listaO,foundOfertas);
			
			//Comprobacion que atopa solo unha
			foundOfertas = ofertaService.findOfertas("oferta 2",
					null,null);
			assertEquals(1, foundOfertas.size());

     		assertEquals(listaO.get(1), foundOfertas.get(0));

			foundOfertas = ofertaService.findOfertas("oferta 67",
					null,"Comprometido");
			assertEquals(0, foundOfertas.size());
		} finally {
			
			// Clear Database
			for (Oferta of : listaO) {
				removeOferta(of.getOfertaId());
			}
		}
		
	}
	@Test
	public void testFindOferta() throws BadStateException, InstanceNotFoundException,
		InputValidationException {

		Oferta oferta = createOferta(getValidOferta());
		Oferta addedOferta = null;

		try {
			addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
					oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
					oferta.getMax(),oferta.getMaxRes());
			Oferta foundOferta = ofertaService.findOferta(addedOferta.getOfertaId());
			assertEquals(addedOferta, foundOferta);			
		} finally {
			// Clear Database
				removeOferta(addedOferta.getOfertaId());
				removeOferta(oferta.getOfertaId());
		}
		
	}	
	
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNotExistantOferta() throws BadStateException, InstanceNotFoundException,
	InputValidationException {
	
		Oferta foundOferta = ofertaService.findOferta(NON_EXISTENT_OFERTA_ID);
	}
	
	@Test(expected = InputValidationException.class)
	public void testBuyOfertaWithInvalidCreditCard() throws 
		InputValidationException, InstanceNotFoundException,
			OfertaExpirationException,MaxNReservaException,BadStateException,
			ReservaEmailException{

		Oferta oferta = createOferta(getValidOferta());
		try {
			Long reservaID = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
					INVALID_CREDIT_CARD_NUMBER);
			
		} finally {
			/* Clear database. */
			removeOferta(oferta.getOfertaId());
		}

	}
	@Test(expected = MaxNReservaException.class)
	public void testBuyOfertaWithInvalidMaxreserva() throws 
		InputValidationException, InstanceNotFoundException,
			OfertaExpirationException,MaxNReservaException,BadStateException,
			ReservaEmailException{

		Oferta ofer =ofertaService.addOferta("ofertamax","oferta con maximo",
				dataIni, dataFin,dataDis,100.0f,90.0f,100,100);
		try {
			Long reservaID = ofertaService.buyOferta(ofer.getOfertaId(), USER_ID,
					VALID_CREDIT_CARD_NUMBER);
			
		} finally {
			/* Clear database. */
			removeOferta(ofer.getOfertaId());
		}

	}
	@Test(expected = BadStateException.class)
	public void testBuyOfertaWithInvalidState() throws 
		InputValidationException, InstanceNotFoundException,
			OfertaExpirationException,MaxNReservaException,BadStateException,
			ReservaEmailException{
		
		Oferta oferta = createOferta(getValidOferta());
		Long reservaId = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
				VALID_CREDIT_CARD_NUMBER);
		 
		try{
			removeOferta(oferta.getOfertaId());
		}finally{
			ofertaService.reclamarOferta(reservaId);
			removeReserva(reservaId);
			removeOferta(oferta.getOfertaId());
		}
	}
	@Test(expected = InstanceNotFoundException.class)
	public void testBuyNonExistentOferta() throws InputValidationException,
			InstanceNotFoundException, OfertaExpirationException, MaxNReservaException,
			BadStateException, ReservaEmailException {

		ofertaService.buyOferta(NON_EXISTENT_OFERTA_ID, USER_ID,
				VALID_CREDIT_CARD_NUMBER);

	}
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNonExistentReserva() throws InstanceNotFoundException{

		ofertaService.findReserva(NON_EXISTENT_RESERVA_ID);

	}
	@Test(expected = ReservaEmailException.class)
	public void testbuyOfertasecondtime()throws InputValidationException,
	InstanceNotFoundException, OfertaExpirationException, MaxNReservaException,
	BadStateException, ReservaEmailException{
		
		Oferta oferta = createOferta(getValidOferta());
		Long reservaId = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
				VALID_CREDIT_CARD_NUMBER);
		try{
			reservaId = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
				VALID_CREDIT_CARD_NUMBER);
		}finally{
			ofertaService.reclamarOferta(reservaId);
			removeReserva(reservaId);
			removeOferta(oferta.getOfertaId());
		}
	
	}
	@Test(expected = OfertaExpirationException.class)
	public void testReclamarOfertawithBadExpiration() throws InputValidationException,
		InstanceNotFoundException, BadStateException, OfertaExpirationException,
		MaxNReservaException, ReservaEmailException{
		
		Oferta oferta1 = getValidOferta();		
		Oferta oferta = createOferta(oferta1);
		Calendar ddis = Calendar.getInstance();
		Calendar dini = oferta.getDataini();
		Calendar dfin = oferta.getDatafin();
		dfin.add(Calendar.DATE, -50);
		dini.add(Calendar.DATE, -50);
		ddis.add(Calendar.DATE, -50);
		
		oferta.setDataDis(ddis);
		oferta.setDataFin(dfin);
		oferta.setDataIni(dini);
		ofertaService.updateOferta(oferta);
		try{
		Long reservaId = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
				VALID_CREDIT_CARD_NUMBER);
		}finally{
			removeOferta(oferta.getOfertaId());
			
		}
	}
	@Test(expected = BadStateException.class)
	public void testReclamarOfertawhitReservaReclaimed() throws InputValidationException,
		InstanceNotFoundException, BadStateException, OfertaExpirationException,
		MaxNReservaException, ReservaEmailException{
		
		Oferta oferta1 = getValidOferta();		
		Oferta oferta = createOferta(oferta1);
		Long reservaId = ofertaService.buyOferta(oferta.getOfertaId(), USER_ID,
				VALID_CREDIT_CARD_NUMBER);
		ofertaService.reclamarOferta(reservaId);
		try{
			ofertaService.reclamarOferta(reservaId);
		}finally{
			removeOferta(oferta.getOfertaId());
			
		}
	}
}	

