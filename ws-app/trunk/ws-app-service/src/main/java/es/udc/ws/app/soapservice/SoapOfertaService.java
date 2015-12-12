package es.udc.ws.app.soapservice;


import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.*;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
    name="OfertasProvider",
    serviceName="OfertasProviderService",
    targetNamespace="http://soap.ws.udc.es/"
)
public class SoapOfertaService {
	public SoapOfertaService(){
	
	}
    @WebMethod(
        operationName="addOferta"
    )
    public Long addOferta(@WebParam(name="ofertaDto") OfertaDto ofertaDto)
            throws SoapInputValidationException {
        Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
        try {
            return OfertaServiceFactory.getService().addOferta(oferta.getNome(),
            		oferta.getDescripcion(),oferta.getDataini(), 
            		oferta.getDatafin(), oferta.getDatadis(), oferta.getCosteR(), oferta.getCosteD(), 
            		oferta.getMax(), oferta.getMaxRes()).getOfertaId();
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }
    }

    @WebMethod(
        operationName="updateOferta"
    )
    public void updateOferta(@WebParam(name="ofertaDto") OfertaDto ofertaDto)
            throws SoapInputValidationException, SoapInstanceNotFoundException,
            	SoapBadStateException {

        Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
        try {
            OfertaServiceFactory.getService().updateOferta(oferta);
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        } catch (BadStateException ex) {
            throw new SoapBadStateException(
                    new SoapBadStateExceptionInfo(ex.getOfertaId()));  
        }
    }

    @WebMethod(
        operationName="removeOferta"
    )
    public void removeOferta(@WebParam(name="ofertaId") Long ofertaId)
            throws SoapInstanceNotFoundException, SoapBadStateException {
        try {
            OfertaServiceFactory.getService().removeOferta(ofertaId);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(
                    ex.getInstanceId(), ex.getInstanceType()));
        }catch (BadStateException ex) {
            throw new SoapBadStateException(
                    new SoapBadStateExceptionInfo(ex.getOfertaId()));  
        }
    }

    @WebMethod(
        operationName="findOfertas"
    )
    public List<OfertaDto> findOfertas(
            @WebParam(name="keywords") String keywords) {

   	 	List<Oferta> ofertas1= new ArrayList<Oferta>();
    	List<Oferta> ofertas =
                OfertaServiceFactory.getService().findOfertas(keywords, null, null);

        for (Oferta of:ofertas){
        	Calendar now =Calendar.getInstance();
        	if (of.getDatafin().before(now)){
        		ofertas1.add(of);
        	}
        }
        return OfertaToOfertaDtoConversor.toOfertaDtos(ofertas1);
    }

    @WebMethod(
        operationName="buyOferta"
    )
    public Long buyOferta(@WebParam(name="ofertaId")  Long ofertaId,
                         @WebParam(name="userId")   String userId,
                         @WebParam(name="creditCardNumber") String creditCardNumber)
            throws SoapInstanceNotFoundException, SoapInputValidationException, SoapOfertaExpirationException,
            SoapMaxNReservaException, SoapBadStateException, SoapReservaEmailException {
        try {
            Long reserva = OfertaServiceFactory.getService().buyOferta(ofertaId, userId, creditCardNumber);
            return reserva;
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        } catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        } catch (BadStateException ex) {
            throw new SoapBadStateException(
                    new SoapBadStateExceptionInfo(ex.getOfertaId()));  
        }catch (OfertaExpirationException ex) {
            throw new SoapOfertaExpirationException(
                    new SoapOfertaExpirationExceptionInfo(ex.getOfertaId()
                    		,ex.getDataFin()));  
        }catch (MaxNReservaException ex) {
            throw new SoapMaxNReservaException(
                    new SoapMaxNReservaExceptionInfo(ex.getOfertaId()
                    		,ex.getMaxI()));  
        }catch (ReservaEmailException ex) {
            throw new SoapReservaEmailException(
                    new SoapReservaEmailExceptionInfo(ex.getMail()));  
        }
    }

    @WebMethod(
        operationName="findReserva"
    )
    public ReservaDto findReserva(@WebParam(name="reservaId") Long reservaId)
            throws SoapInstanceNotFoundException{

        try {
            Reserva reserva = OfertaServiceFactory.getService().findReserva(reservaId);
            return ReservaToReservaDtoConversor.toReservaDto(reserva);
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        }
    }
    @WebMethod(
            operationName="reclamarOferta"
        )
    public void reclamarOferta(@WebParam(name="reservaId")Long ReservaId)
    		throws SoapInstanceNotFoundException, SoapInputValidationException
    		,SoapBadStateException,SoapOfertaExpirationException{
    	try {
            OfertaServiceFactory.getService().reclamarOferta(ReservaId);
            
        } catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        }catch (InputValidationException ex) {
            throw new SoapInputValidationException(ex.getMessage());
        }catch (BadStateException ex) {
            throw new SoapBadStateException(
                    new SoapBadStateExceptionInfo(ex.getOfertaId()));  
        }catch (OfertaExpirationException ex) {
            throw new SoapOfertaExpirationException(
                    new SoapOfertaExpirationExceptionInfo(ex.getOfertaId(),ex.getDataFin()));  
        }
    }
    @WebMethod(
            operationName="ObtenerReservas"
        )
    public List<ReservaDto> ObtenerReservas (@WebParam(name="ofertaId")  Long ofertaId,
    									  @WebParam(name="estado")  Boolean estado)
    			throws SoapInstanceNotFoundException{
    	 	
    	try{
    	List<Reserva> reservas =
                OfertaServiceFactory.getService().obtenerReservas(ofertaId,estado);
    	return ReservaToReservaDtoConversor.toReservaDtos(reservas);
    	} catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
        }
    	
    	
    }
    @WebMethod(
            operationName="findOferta"
        )
    public OfertaDto findOferta(@WebParam(name="ofertaId")  Long ofertaId)
    		throws SoapInstanceNotFoundException{
    	try{
    		Oferta oferta = OfertaServiceFactory.getService().findOferta(ofertaId);
    		return OfertaToOfertaDtoConversor.toOfertaDto(oferta);
    	}catch (InstanceNotFoundException ex) {
            throw new SoapInstanceNotFoundException(
                    new SoapInstanceNotFoundExceptionInfo(ex.getInstanceId(),
                        ex.getInstanceType()));
    	}
    	
    }	
}