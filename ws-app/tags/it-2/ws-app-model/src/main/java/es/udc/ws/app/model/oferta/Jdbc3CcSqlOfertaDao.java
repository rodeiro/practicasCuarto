package es.udc.ws.app.model.oferta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;

public class Jdbc3CcSqlOfertaDao extends AbstractSqlOfertaDao {

	@Override
	public Oferta create(Connection connection, Oferta oferta) {

		/* Create "queryString". */
		String queryString = "INSERT INTO Oferta"
				+ " (descripcion, dataini, datafin,"
				+ " dataDis, costeR, costeD, maxi, nomeO, estadO ,maxres )"
				+ " VALUES (?, ?, ?, ? ,? ,? ,? ,? ,?,?)";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				queryString, Statement.RETURN_GENERATED_KEYS)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, oferta.getDescripcion());
			Timestamp dateini = oferta.getDataini() != null ? new Timestamp(
					oferta.getDataini().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, dateini);
			Timestamp datefin = oferta.getDatafin() != null ? new Timestamp(
					oferta.getDatafin().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, datefin);
			Timestamp datedis = oferta.getDatadis() != null ? new Timestamp(
					oferta.getDatadis().getTime().getTime()) : null;
			preparedStatement.setTimestamp(i++, datedis);
			preparedStatement.setFloat(i++, oferta.getCosteR());
			preparedStatement.setFloat(i++, oferta.getCosteD());
			if (oferta.getMax()==null){
				preparedStatement.setNull(i++, java.sql.Types.INTEGER);
			}else
				preparedStatement.setInt(i++, oferta.getMax());
			preparedStatement.setString(i++, oferta.getNome());
			preparedStatement.setString(i++, oferta.getEstado());
			preparedStatement.setInt(i++, oferta.getMaxRes());
			/* Execute query. */
			preparedStatement.executeUpdate();

			/* Get generated identifier. */
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				throw new SQLException(
						"JDBC driver did not return generated key.");
			}
			Long ofertaId = resultSet.getLong(1);

			/* Return oferta. */
			return new Oferta(ofertaId, oferta.getDescripcion(),
					oferta.getDataini(), oferta.getDatafin(),
					oferta.getDatadis(), oferta.getCosteR(),
					oferta.getCosteD(),oferta.getMax(), oferta.getNome(),
					oferta.getEstado(),oferta.getMaxRes());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}