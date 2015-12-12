package es.udc.ws.app.model.reserva;

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
 * A partial implementation of
 * <code>SQLReservaDAO</code> that leaves
 * <code>create(Connection, Movie)</code> as abstract.
 */
public abstract class AbstractSqlReservaDao implements SqlReservaDao {

    protected AbstractSqlReservaDao() {
    }

    @Override
    public Reserva find(Connection connection, Long reservaId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT ofertaId, reservaId, "
                + " emaulUs, nConta, estadoRes FROM Reserva WHERE reservaId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, reservaId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(reservaId,
                        Reserva.class.getName());
            }

            /* Get results. */
            i = 1;
            Long ofertaId = resultSet.getLong(i++);
            String emailUs = resultSet.getString(i++);
            String nConta = resultSet.getString(i++);

            /* Return reserva. */
            return new Reserva(emailUs, nConta, ofertaId );         

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<Reserva> findByOid(Connection connection, Long ofertaId){

        /* Create "queryString". */
        String queryString = "SELECT ofertaId, reservaId, "
                + " emaulUs, nConta, estadoRes, dataCrea FROM Reserva WHERE ofertaId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, ofertaId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Reserva> reservas = new ArrayList<Reserva>();
            
            while(resultSet.next()) {
                /* Get results. */
                i = 1;
                Long ofertaID = resultSet.getLong(i++);
                Long reservaId = resultSet.getLong(i++);
                String emailUs = resultSet.getString(i++);
                String nConta = resultSet.getString(i++);
                boolean estadoRes = resultSet.getBoolean(i++);
                Calendar dataCrea = Calendar.getInstance();
                dataCrea.setTime(resultSet.getTimestamp(i++));
            	reservas.add(new Reserva(reservaId,emailUs,estadoRes, nConta,ofertaID, dataCrea));
            }


            /* Return reserva. */
            return reservas;       

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    @Override
    public List<Reserva> findByKeywords(Connection connection, String keywords) {

        /* Create "queryString". */
        String[] words = keywords != null ? keywords.split(" ") : null;
        String queryString = "SELECT ofertaId, reservaId, "
                + " emaulUs, nConta, estadoRes FROM Reserva";
        if (words != null && words.length > 0) {
            queryString += " WHERE";
            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                    queryString += " AND";
                }
                queryString += " LOWER(reservaID) LIKE LOWER(?)";
            }
        }
        queryString += " ORDER BY reservaID";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            if (words != null) {
                /* Fill "preparedStatement". */
                for (int i = 0; i < words.length; i++) {
                    preparedStatement.setString(i + 1, "%" + words[i] + "%");
                }
            }

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Reserva> reservas = new ArrayList<Reserva>();

            while (resultSet.next()) {

                int i = 1;
                Long ofertaID = new Long(resultSet.getLong(i++));
                String emailUs = resultSet.getString(i++);
                String nConta = resultSet.getString(i++);

                reservas.add(new Reserva(emailUs, nConta, ofertaID));

            }

            /* Return reservas. */
            return reservas;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Connection connection, Reserva reserva)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE reserva" + " SET emaulUs = ?, "
                +"SET nConta = ?, SET estadoRes = ? FROM Reserva WHERE reservaId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, reserva.getemailUs());
            preparedStatement.setString(i++, reserva.getnconta());
            preparedStatement.setBoolean(i++, reserva.getestadoRes());
           
            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(reserva.getReservaId(),
                    Reserva.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Connection connection, Long reservaID)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Reserva WHERE reservaID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, reservaID);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(reservaID,
                        Reserva.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
