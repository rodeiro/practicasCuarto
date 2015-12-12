package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao {

    @Override
    public Reserva create(Connection connection, Reserva reserva) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Reserva"
                + " (ofertaID, reservaID, emailUS, nconta, estadoRes)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, reserva.getOfid());
            preparedStatement.setString(i++, reserva.getemailUs());
            preparedStatement.setString(i++, reserva.getnconta());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long reservaId = resultSet.getLong(1);

            /* Return movie. */
            return new Reserva(reservaId,reserva.getemailUs(), reserva.getnconta(),
            		reserva.getOfid());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
