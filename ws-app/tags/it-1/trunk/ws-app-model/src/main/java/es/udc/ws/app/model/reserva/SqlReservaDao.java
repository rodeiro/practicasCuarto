package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.util.List;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Date;

public interface SqlReservaDao {

    public Reserva create(Connection connection, Reserva reserva);

    public Reserva find(Connection connection, Long reservaId)
            throws InstanceNotFoundException;

    public List<Reserva> findByOid(Connection connection, Long ofertaId);
    public List<Reserva> findByKeywords(Connection connection,
            String keywords);
    public void update(Connection connection, Reserva Reserva)
            throws InstanceNotFoundException;

    public void remove(Connection connection, Long reservaId)
            throws InstanceNotFoundException;
}
