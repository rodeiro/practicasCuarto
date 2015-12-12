package es.udc.ws.app.model.reserva;

import java.sql.Connection;
import java.util.List;

import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import java.sql.Date;

public interface SqlReservaDao {

    public Reserva create(Connection connection, Reserva reserva) throws ReservaEmailException;

    public Reserva find(Connection connection, Long reservaId)
            throws InstanceNotFoundException;
    public List<Reserva> findByIE(Connection connection,Long ofertaId,Boolean estado);
    public List<Reserva> findByOid(Connection connection, Long ofertaId);
    public List<Reserva> findByEmail(Connection connection, String email);
    public void update(Connection connection, Reserva Reserva)
            throws InstanceNotFoundException;

    public void remove(Connection connection, Long reservaId)
            throws InstanceNotFoundException;
}
