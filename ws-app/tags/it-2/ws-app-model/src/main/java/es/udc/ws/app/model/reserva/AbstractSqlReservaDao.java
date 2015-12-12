package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.oferta.Oferta;
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
                + " emailUs, nConta, estadoRes FROM Reserva WHERE reservaId = ?";

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
            i++;
            String emailUs = resultSet.getString(i++);
            String nConta = resultSet.getString(i++);

            /* Return reserva. */
            return new Reserva(emailUs, nConta, ofertaId );         

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
	public List<Reserva> findByIE(Connection connection,Long ofertId,Boolean estado){
		
		String queryString = "SELECT reservaId, estadoRes,"
                + " emailUs, nconta, ofertaId,  dataCrea FROM Reserva";
		
		if (ofertId!=null){
			queryString += " WHERE ofertaId = ?";
		}
		if (estado!=null){
			if (ofertId == null){
				queryString += " WHERE";
			}else{
				queryString += " AND";
			}
			queryString += " estadores = ?";
		}
		queryString += " ORDER BY emailUs";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i=1;
			if (ofertId != null) {
				/* Fill "preparedStatement". */
					preparedStatement.setLong(i++, ofertId);
			}
			if (estado != null)
				/* Fill "preparedStatement". */

	            if (estado)
	            	preparedStatement.setInt(i++,1);
	            else
	            	preparedStatement.setInt(i++,0);
			

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read movies. */
			List<Reserva> reservas = new ArrayList<Reserva>();

			while(resultSet.next()) {
                /* Get results. */
                i = 1;
                Long reservaId = resultSet.getLong(i++);
                int estadoR = resultSet.getInt(i++);
                boolean estadoRes;
                if (estadoR == 0)
                	estadoRes = false;
                else
                	estadoRes = true;
                
                String emailUs = resultSet.getString(i++);
                String nConta = resultSet.getString(i++);
                Long ofertaID = resultSet.getLong(i++);
                Calendar dataCrea = Calendar.getInstance();
                dataCrea.setTime(resultSet.getTimestamp(i++));
            	reservas.add(new Reserva(reservaId,emailUs,estadoRes, nConta,ofertaID, dataCrea));
            }

			/* Return ofertas. */
			return reservas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    @Override
    public List<Reserva> findByOid(Connection connection, Long ofertaId){

        /* Create "queryString". */
        String queryString = "SELECT ofertaId, reservaId, "
                + " emailUs, nConta, estadoRes, dataCrea FROM Reserva WHERE ofertaId = ?";

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
                boolean estadoRes;
                int estadoR = resultSet.getInt(i++);
                if (estadoR == 0)
                	estadoRes = false;
                else 
                	estadoRes = true;
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
    public List<Reserva> findByEmail(Connection connection, String email) {

        /* Create "queryString". */
        String queryString = "SELECT reservaId, ofertaId,"
                + " emailUs, nconta, estadores, dataCrea FROM Reserva";
        if (email!=null)
        	queryString += " WHERE emailUS=?";       
        
        queryString += " ORDER BY reservaId";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
        	
        	 /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, email);
           /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read movies. */
            List<Reserva> reservas = new ArrayList<Reserva>();

            while (resultSet.next()) {
                i = 1;               
                Long reservaId = resultSet.getLong(i++);
                Long ofertaID = resultSet.getLong(i++);
                String emailUs = resultSet.getString(i++);
                String nConta = resultSet.getString(i++);

                boolean estadoRes;
                int estadoR = resultSet.getInt(i++);
                if (estadoR == 0)
                	estadoRes = false;
                else 
                	estadoRes = true;
                
                Calendar dataCrea = Calendar.getInstance();
                dataCrea.setTime(resultSet.getTimestamp(i++));
            	reservas.add(new Reserva(reservaId,emailUs,estadoRes, nConta,ofertaID, dataCrea));
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
        String queryString = "UPDATE Reserva" + " SET emailUs = ?, "
                +" nconta = ?, estadores = ? WHERE reservaId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, reserva.getemailUs());
            preparedStatement.setString(i++, reserva.getnconta());
            if (reserva.getestadoRes())
            	preparedStatement.setInt(i++,1);
            else
            	preparedStatement.setInt(i++,0);
            preparedStatement.setLong(i++, reserva.getReservaId());
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
