package es.udc.ws.app.client.service;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.ReservaEmailException;






import java.util.Calendar;
import java.util.List;

public interface ClientOfertaService {

    public Long addOferta(OfertaDto oferta)
            throws InputValidationException;

    public void updateOferta(OfertaDto oferta)
            throws InputValidationException, InstanceNotFoundException,BadStateException;

    public void removeOferta(Long ofertaId) 
    		throws InstanceNotFoundException,BadStateException;

    public List<OfertaDto> findOfertas(String keywords);

    public Long buyOferta(Long ofertaId, String emailUs, String conta)
            throws InstanceNotFoundException, InputValidationException,
            OfertaExpirationException,MaxNReservaException, BadStateException,
            ReservaEmailException;
    
    public ReservaDto findReserva(Long ReservaId)
        		throws InstanceNotFoundException;
    
    public void reclamarOferta(Long ReservaId)
    		throws InstanceNotFoundException, InputValidationException,BadStateException
    		,OfertaExpirationException;
    
    public List<ReservaDto> obtenerReservas(Long ofertaId,Boolean estado)
    		throws InstanceNotFoundException;
    
    public OfertaDto findOferta(Long ofertaId)
    		throws InstanceNotFoundException;
    /*public String getOfertaUrl(Long reservaId)
            throws InstanceNotFoundException, ReservaExpirationException;*/

}
