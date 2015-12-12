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
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO "
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
			int maxi = resultSet.getInt(i++);
			String nomeO = resultSet.getString(i++);
			String estadO = resultSet.getString(i++);

			/* Return oferta. */
			return new Oferta(ofertaId, descripcion, dataini, datafin, dataDis,
					costeR, costeD, maxi, nomeO, estadO);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Oferta> findByKeywords(Connection connection, String keywords) {

		/* Create "queryString". */
		String[] words = keywords != null ? keywords.split(" ") : null;
		String queryString = "SELECT ofertaId,descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO "
				+ "FROM Oferta ";

		if (words != null && words.length > 0) {
			queryString += " WHERE";
			for (int i = 0; i < words.length; i++) {
				if (i > 0) {
					queryString += " AND";
				}
				queryString += " LOWER(nomeO) OR LOWER(descripcion) LIKE LOWER(?)";
			}

		}
		queryString += " ORDER BY nomeO";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			if (words != null) {
				/* Fill "preparedStatement". */
				for (int i = 0; i < words.length; i++) {
					preparedStatement.setString(i +1, "%" + words[i] + "%");
					
				}
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
				int maxi = resultSet.getInt(i++);
				String nomeO = resultSet.getString(i++);
				String estadO = resultSet.getString(i++);

				ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
						dataDis, costeR, costeD, maxi, nomeO, estadO));

			}

			/* Return movies. */
			return ofertas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	@Override
	public List<Oferta> findAll (Connection connection){
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
			int maxi = resultSet.getInt(i++);
			String nomeO = resultSet.getString(i++);
			String estadO = resultSet.getString(i++);

			ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
					dataDis, costeR, costeD, maxi, nomeO, estadO));

		}

		/* Return ofertas. */
		return ofertas;

	} catch (SQLException e) {
		throw new RuntimeException(e);
	}

	}
	@Override
	public List<Oferta> findByEstado(Connection connection, String estado) {

		/* Create "queryString". */

		String queryString = "SELECT ofertaId,descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO "
				+ "FROM Oferta WHERE estadO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			if (estado != null)
				/* Fill "preparedStatement". */
				preparedStatement.setString(1, estado);
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
				int maxi = resultSet.getInt(i++);
				String nomeO = resultSet.getString(i++);
				String estadO = resultSet.getString(i++);

				ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
						dataDis, costeR, costeD, maxi, nomeO, estadO));

			}

			/* Return movies. */
			return ofertas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Oferta> findByDatafin(Connection connection, Calendar dataFin) {
		String queryString = "SELECT ofertaId,descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO "
				+ "FROM Oferta WHERE datafin = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			Timestamp Datafin =new Timestamp( dataFin.getTime().getTime());
			preparedStatement.setTimestamp(1,Datafin);
				
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
				int maxi = resultSet.getInt(i++);
				String nomeO = resultSet.getString(i++);
				String estadO = resultSet.getString(i++);

				ofertas.add(new Oferta(ofertaId, descripcion, dataini, datafin,
						dataDis, costeR, costeD, maxi, nomeO, estadO));
			}

			
			/* Return movies. */
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
				+ "costeR = ?, costeD = ?, maxi = ?, nomeO = ?,estadO = ?"
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
			preparedStatement.setInt(i++, oferta.getMax());
			preparedStatement.setString(i++, oferta.getNome());
			preparedStatement.setString(i++, oferta.getEstado());
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
