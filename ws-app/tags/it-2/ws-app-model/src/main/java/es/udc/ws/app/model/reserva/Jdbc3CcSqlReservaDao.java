package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class Jdbc3CcSqlReservaDao extends AbstractSqlReservaDao {

    @Override
    public Reserva create(Connection connection, Reserva reserva)throws ReservaEmailException {

    	
    	 String queryString = "SELECT ofertaId FROM Reserva WHERE emailUS = ? AND ofertaId=?";    	 

    	 try (PreparedStatement preparedStatement = connection.prepareStatement(
                 queryString)) {
    		 int j = 1;
    		 
    		 preparedStatement.setString(j++, "%" + reserva.getemailUs() + "%");
    		 preparedStatement.setString(j++, "%" + reserva.getOfid() + "%");
    		 
    		 ResultSet resultSet = preparedStatement.executeQuery();
    		 
    		
    	} catch (SQLException e) {
        throw new RuntimeException(e);
    	}
        /* Create "queryString". */
         queryString = "INSERT INTO Reserva"
                + " (emailUS, nconta,ofertaID, estadores,dataCrea)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                        queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            int i = 1;
            
            preparedStatement.setString(i++, reserva.getemailUs());
            preparedStatement.setString(i++, reserva.getnconta());
            preparedStatement.setLong(i++, reserva.getOfid());
            
            if (reserva.getestadoRes())
            	preparedStatement.setInt(i++,1);
            else
            	preparedStatement.setInt(i++,0);
            
            Timestamp datacrea = reserva.getDataCrea() != null ? new Timestamp(
            		reserva.getDataCrea().getTime().getTime()) : null;
            preparedStatement.setTimestamp(i++, datacrea);		
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
