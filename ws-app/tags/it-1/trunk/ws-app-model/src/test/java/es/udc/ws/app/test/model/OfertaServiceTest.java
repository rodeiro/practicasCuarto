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
	//private final long NON_EXISTENT_RESERVA_ID = -1;
	//private final String USER_ID = "ws-user";
	
	private final Calendar dataIni = Calendar.getInstance();
	private  Calendar dataDis = Calendar.getInstance();
	private  Calendar dataFin = Calendar.getInstance();
	

	//private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
	//private final String INVALID_CREDIT_CARD_NUMBER = "";

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
				Calendar.getInstance(),100.0F,90.0F,100,"Oferta1");
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
					dataDis,100.0f,90.0f,100);
		} catch (InputValidationException e) {
			throw new RuntimeException(e);
		}
		return addedOferta;

	}
	private List<Oferta> createOfertas(List<Oferta> ofertas) {

		
		dataDis.add(Calendar.DATE, 10);
		dataFin.add(Calendar.DATE,2);
		Oferta ofer =null;
		List<Oferta> lista =new ArrayList<Oferta>();
		try {
			
			for (Oferta of:ofertas){
				
				ofer =ofertaService.addOferta(of.getNome(),of.getDescripcion(),
					dataIni, dataFin,
					dataDis,100.0f,90.0f,100);
				
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
				oferta.getMax());
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
						oferta.getMax());
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
						oferta.getMax());
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
						oferta.getMax());
			} catch (InputValidationException e) {
				exceptionCatched = true;
			}
			assertTrue(exceptionCatched);

			// Check oferta max
			exceptionCatched = false;
			oferta = getValidOferta();
			oferta.setMax((int) MAX_MAX +1);
			try {
				addedOferta = ofertaService.addOferta(oferta.getNome(),oferta.getDescripcion(),oferta.getDataini(),
						oferta.getDatafin(),oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
						oferta.getMax());
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
						oferta.getMax());
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
						oferta.getMax());
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
						oferta.getMax());
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
						oferta.getMax());
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

	@Test(expected = InstanceNotFoundException.class)
	public void testRemoveNonExistentOferta() throws InstanceNotFoundException,
		BadStateException {

		ofertaService.removeOferta(NON_EXISTENT_OFERTA_ID);

	}

	@Test
	public void testFindOfertas() throws BadStateException, InstanceNotFoundException {

		// Add ofertas
		List<Oferta> ofertas = new LinkedList<Oferta>();
		
		Oferta oferta1= new Oferta("oferta 1",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta1");
		Oferta oferta2= new Oferta("oferta 2",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta2");
		Oferta oferta3= new Oferta("oferta 3",dataIni,dataFin,dataDis,
				100.0f,90.0f,100,"Oferta3");
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
				//System.out.println(ind);
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
					oferta.getMax());
			Oferta foundOferta = ofertaService.findOferta(addedOferta.getOfertaId());
			assertEquals(addedOferta, foundOferta);			
		} finally {
			// Clear Database
				removeOferta(addedOferta.getOfertaId());
				removeOferta(oferta.getOfertaId());
		}
		
	}	
	/*
	@Test(expected = InstanceNotFoundException.class)
	public void testFindNotExistantOferta() throws BadStateException, InstanceNotFoundException,
	InputValidationException {

	Oferta oferta = createOferta(getValidOferta());
	boolean exceptionCatched = false;
	try {
		Oferta foundOferta = ofertaService.findOferta(oferta.getOfertaId());
	}catch (InstanceNotFoundException e) {
		exceptionCatched = true;
	}
	assertTrue(!exceptionCatched);
	}
	*/
}	

