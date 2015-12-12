package es.udc.ws.app.client.service.soap;


import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.soap.wsdl.*;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.util.Calendar;
import java.util.List;
import javax.xml.ws.BindingProvider;

public class SoapClientOfertaService implements ClientOfertaService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
        "SoapClientOfertaService.endpointAddress";

    private String endpointAddress;

    private OfertasProvider ofertasProvider;

    public SoapClientOfertaService() {
        init(getEndpointAddress());
    }

    private void init(String moviesProviderURL) {
        OfertasProviderService stockQuoteProviderService =
                new OfertasProviderService();
        ofertasProvider = stockQuoteProviderService
                .getOfertasProviderPort();
        ((BindingProvider) ofertasProvider).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                moviesProviderURL);
    }

    @Override
    public Long addOferta(OfertaDto oferta)
            throws InputValidationException {
        try {
            return ofertasProvider.addOferta(OfertaDtoToSoapOfertaDtoConversor
                    .toSoapOfertaDto(oferta));
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateOferta(OfertaDto oferta)
            throws InputValidationException, InstanceNotFoundException,BadStateException {
        try {

            ofertasProvider.updateOferta(OfertaDtoToSoapOfertaDtoConversor
                    .toSoapOfertaDto(oferta));
            
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch(SoapBadStateException ex){
        	throw new BadStateException(
        			ex.getFaultInfo().getOfertaId(),
        			ex.getFaultInfo().getEstado());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removeOferta(Long ofertaId)
            throws InstanceNotFoundException, BadStateException {
        try {
            ofertasProvider.removeOferta(ofertaId);
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch(SoapBadStateException ex){
        	throw new BadStateException(
        			ex.getFaultInfo().getOfertaId(),
        			ex.getFaultInfo().getEstado());
        }
    }

    @Override
    public List<OfertaDto> findOfertas(String keywords) {
        return OfertaDtoToSoapOfertaDtoConversor.toOfertaDtos(
                    ofertasProvider.findOfertas(keywords));
    }

    @Override
    public Long buyOferta(Long ofertaId, String userId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException,
            OfertaExpirationException,MaxNReservaException, BadStateException,
            ReservaEmailException{
        try {
        	return ofertasProvider.buyOferta(ofertaId, userId, creditCardNumber);
        } catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        } catch (SoapOfertaExpirationException ex) {
        	
            throw new OfertaExpirationException(
                    ex.getFaultInfo().getOfertaId(),
                    ex.getFaultInfo().getDataFin().toGregorianCalendar());
            
        }catch (SoapMaxNReservaException ex) {
            throw new MaxNReservaException(
                    ex.getFaultInfo().getOfertaId(),
                    ex.getFaultInfo().getMaxI());
        }catch(SoapBadStateException ex){
        	throw new BadStateException(
        			ex.getFaultInfo().getOfertaId(),
        			ex.getFaultInfo().getEstado());
        } catch (SoapReservaEmailException ex) {
            throw new ReservaEmailException(
                    ex.getFaultInfo().getMail());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public ReservaDto findReserva(Long ReservaId)
    		throws InstanceNotFoundException{
    	try{
    		return  ReservaDtoToSoapReservaDtoConversor.toReservaDto(
    				ofertasProvider.findReserva(ReservaId));
    	}catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    	
    }
    @Override
    public void reclamarOferta(Long ReservaId)
    		throws InstanceNotFoundException, InputValidationException,BadStateException
    		,OfertaExpirationException{
    	try{
    		ofertasProvider.reclamarOferta(ReservaId);
    	}catch (SoapInputValidationException ex) {
            throw new InputValidationException(ex.getMessage());
        } catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }catch(SoapBadStateException ex){
        	throw new BadStateException(
        			ex.getFaultInfo().getOfertaId(),
        			ex.getFaultInfo().getEstado());
        }catch(SoapOfertaExpirationException ex){
        	throw new OfertaExpirationException(
        			ex.getFaultInfo().getOfertaId(),
        			ex.getFaultInfo().getDataFin().toGregorianCalendar());
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public List<ReservaDto> obtenerReservas(Long ofertaId,Boolean estado)
    		throws InstanceNotFoundException{
    	try{
    		return  ReservaDtoToSoapReservaDtoConversor.toReservaDtos(
    				ofertasProvider.obtenerReservas(ofertaId,estado));
    	}catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }
    @Override
    public OfertaDto findOferta(Long ofertaId)
    		throws InstanceNotFoundException{
    	try{
    		return OfertaDtoToSoapOfertaDtoConversor.toOfertaDto(
                    ofertasProvider.findOferta(ofertaId));
    	}catch (SoapInstanceNotFoundException ex) {
            throw new InstanceNotFoundException(
                    ex.getFaultInfo().getInstanceId(),
                    ex.getFaultInfo().getInstanceType());
        }
    }
    private String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

}
