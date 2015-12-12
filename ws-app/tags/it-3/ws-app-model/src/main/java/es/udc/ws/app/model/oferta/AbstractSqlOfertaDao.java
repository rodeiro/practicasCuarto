package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

/**
 * A partial implementation of <code>SQLMovieDAO</code> that leaves
 * <code>create(Connection, Movie)</code> as abstract.
 */
public abstract class AbstractSqlOfertaDao implements SqlOfertaDao {

	protected AbstractSqlOfertaDao() {
	}

	@Override
	public Oferta find(Connection connection, Long ofertaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "SELECT descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO ,maxres "
				+ "FROM Oferta WHERE ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId.longValue());

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				throw new InstanceNotFoundException(ofertaId,
						Oferta.class.getName());
			}

			/* Get results. */
			i = 1;

			String descripcion = resultSet.getString(i++);
			Calendar dataini = Calendar.getInstance();
			dataini.setTime(resultSet.getTimestamp(i++));
			Calendar datafin = Calendar.getInstance();
			datafin.setTime(resultSet.getTimestamp(i++));
			Calendar dataDis = Calendar.getInstance();
			dataDis.setTime(resultSet.getTimestamp(i++));
			float costeR = resultSet.getFloat(i++);
			float costeD = resultSet.getFloat(i++);
		
			Integer	maximo = resultSet.getInt(i++);
			if (resultSet.wasNull()){
				
				maximo = null;
			}
			
			String nomeO = resultSet.getString(i++);
			String estadO = resultSet.getString(i++);
			int maxres = resultSet.getInt(i++);
			/* Return oferta. */
			
			return new Oferta(ofertaId, descripcion, dataini, datafin, dataDis,
					costeR, costeD, maximo, nomeO, estadO, maxres);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public List<Oferta> findByKDE(Connection connection, String keywords,
			String estado, Calendar dataFin) {

		/* Create "queryString". */
		String[] words = keywords != null ? keywords.split(" ") : null;
		String queryString = "SELECT ofertaId,descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO, maxres "
				+ "FROM Oferta ";

		if ((words != null && words.length > 0) || (estado != null)
				|| (dataFin != null)) {
			queryString += " WHERE";
		}
		boolean hasCondition = false;
		if (words != null && words.length > 0) {
			hasCondition = true;
			for (int i = 0; i < words.length; i++) {
				if (i > 0) {
					queryString += " AND";
				}
				queryString += " ( LOWER(nomeO) LIKE LOWER(?) OR "
						+ " LOWER(descripcion) LIKE LOWER(?) )";
			}

		}
		if (estado != null) {
			if (hasCondition) {
				queryString += " AND";
			} else {
				hasCondition = true;
			}
			queryString += " estadO = ?";
			
		}
		if (dataFin != null) {
			if (hasCondition) {
				queryString += " AND";
			} 
			queryString += " datafin = ?";
			
		}

		queryString += " ORDER BY nomeO";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			int j = 1;
			if (words != null) {
				/* Fill "preparedStatement". */

				for (int i = 0; i < words.length; i++) {
					preparedStatement.setString(j++, "%" + words[i] + "%");					
					preparedStatement.setString(j++, "%" + words[i] + "%");
					

				}

			}

			if (estado != null)
				/* Fill "preparedStatement". */
				preparedStatement.setString(j++, estado);
			if (dataFin != null) {
				/* Fill "preparedStatement". */
				Timestamp datafin = new Timestamp(dataFin.getTime().getTime());
				preparedStatement.setTimestamp(j++, datafin);
			}

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read movies. */
			List<Oferta> ofertas = new ArrayList<Oferta>();

			while (resultSet.next()) {
				int i = 1;
				Long ofertaId = new Long(resultSet.getLong(i++));
				String descripcion = resultSet.getString(i++);
				Calendar dataini = Calendar.getInstance();
				dataini.setTime(resultSet.getTimestamp(i++));
				Calendar datafin = Calendar.getInstance();
				datafin.setTime(resultSet.getTimestamp(i++));
				Calendar dataDis = Calendar.getInstance();
				dataDis.setTime(resultSet.getTimestamp(i++));
				float costeR = resultSet.getFloat(i++);
				float costeD = resultSet.getFloat(i++);
				Integer maxi = resultSet.getInt(i++);
				String nomeO = resultSet.getString(i++);
				String estadO = resultSet.getString(i++);
				int maxres = resultSet.getInt(i++);
				ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
						dataDis, costeR, costeD, maxi, nomeO, estadO, maxres));

			}

			/* Return ofertas. */
			return ofertas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Oferta> findAll(Connection connection) {
		/* Create "queryString". */

		String queryString = "SELECT * from Oferta";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read ofertas. */
			List<Oferta> ofertas = new ArrayList<Oferta>();

			while (resultSet.next()) {

				int i = 1;
				Long ofertaId = new Long(resultSet.getLong(i++));
				String descripcion = resultSet.getString(i++);
				Calendar dataini = Calendar.getInstance();
				dataini.setTime(resultSet.getTimestamp(i++));
				Calendar datafin = Calendar.getInstance();
				datafin.setTime(resultSet.getTimestamp(i++));
				Calendar dataDis = Calendar.getInstance();
				dataDis.setTime(resultSet.getTimestamp(i++));
				float costeR = resultSet.getFloat(i++);
				float costeD = resultSet.getFloat(i++);
				Integer maxi = resultSet.getInt(i++);
				String nomeO = resultSet.getString(i++);
				String estadO = resultSet.getString(i++);
				int maxres = resultSet.getInt(i++);
				ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
						dataDis, costeR, costeD, maxi, nomeO, estadO, maxres));

			}

			/* Return ofertas. */
			return ofertas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(Connection connection, Oferta oferta)
			throws InstanceNotFoundException {
		
		
		/* Create "queryString". */
		String queryString = "UPDATE Oferta"
				+ " SET descripcion = ?, dataini = ?, datafin = ?, datadis= ?,"
				+ "costeR = ?, costeD = ?, maxi = ?, nomeO = ?,estadO = ?, maxres = ?"
				+ " WHERE ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, oferta.getDescripcion());
			Timestamp dateini = oferta.getDataini() != null ? new Timestamp(
					oferta.getDataini().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, dateini);
			Timestamp datafin = oferta.getDatafin() != null ? new Timestamp(
					oferta.getDatafin().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, datafin);
			Timestamp datadis = oferta.getDatadis() != null ? new Timestamp(
					oferta.getDatadis().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, datadis);

			preparedStatement.setFloat(i++, oferta.getCosteR());
			preparedStatement.setFloat(i++, oferta.getCosteD());
			if (oferta.getMax() == null){
				preparedStatement.setNull(i++, java.sql.Types.INTEGER);
			}else
				preparedStatement.setInt(i++, oferta.getMax());
			preparedStatement.setString(i++, oferta.getNome());
			preparedStatement.setString(i++, oferta.getEstado());		
			preparedStatement.setInt(i++, oferta.getMaxRes());
			preparedStatement.setLong(i++, oferta.getOfertaId());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(oferta.getOfertaId(),
						Oferta.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, Long ofertaId)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM Oferta WHERE" + " ofertaId = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, ofertaId);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(ofertaId,
						Oferta.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
