package es.udc.ws.app.model.ofertaservice;

import es.udc.ws.app.exceptions.ReservaExpirationException;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;

public interface OfertaService {

	public Oferta addOferta(String nomeO,String descripcion,
			Calendar dataIni,Calendar dataFin, Calendar dataDis,
			float costR, float costD, int MaxI) throws InputValidationException;

	public void updateOferta(Oferta oferta) throws InputValidationException,
			InstanceNotFoundException,BadStateException;

	public void removeOferta(Long ofertaId) throws InstanceNotFoundException,
	BadStateException;

	public Oferta findOferta(Long ofertaId) throws InstanceNotFoundException;

	public List<Oferta> findOfertas(String keywords, Calendar dataFin, 
			String estadoOferta);

	public Long buyOferta(Long ofertaId, String emailUs, String conta)
			throws InstanceNotFoundException, InputValidationException,
			MaxNReservaException, BadStateException;

	public Reserva findReserva(Long reservaId) throws InstanceNotFoundException,
			ReservaExpirationException;

	public void reclamarOferta(Long reservaId) throws InstanceNotFoundException,ReservaExpirationException, InputValidationException, BadStateException;

	public List<Reserva> ObtenerReservas(Long ofertaId, Boolean estado) throws InstanceNotFoundException,
	ReservaExpirationException;

}
