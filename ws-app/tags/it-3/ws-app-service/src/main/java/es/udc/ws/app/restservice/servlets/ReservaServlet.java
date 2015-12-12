package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpStatus;

import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.model.reserva.Reserva;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.app.serviceutil.ReservaToReservaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class ReservaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ofertaIdParameter = req.getParameter("ofertaId");
        if (ofertaIdParameter == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " +
                        "parameter 'reservaId' is mandatory")), null);
            return;
        }
        Long ofertaId;
        try {
            ofertaId = Long.valueOf(ofertaIdParameter);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " +
                        "parameter 'reservaId' is invalid '" +
                        ofertaIdParameter + "'")),
                    null);

            return;
        }
        String email = req.getParameter("email");
        if (email == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " +
                        "parameter 'userId' is mandatory")), null);
            return;
        }
        String creditCardNumber = req.getParameter("creditCardNumber");
        if (creditCardNumber == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: "+
                        "parameter 'creditCardNumber' is mandatory")), null);

            return;
        }
        Long reservaId;
        Reserva reserva;
        try {
            reservaId = OfertaServiceFactory.getService()
                    .buyOferta(ofertaId, email, creditCardNumber);
            reserva = OfertaServiceFactory.getService().findReserva(reservaId);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                    new InstanceNotFoundException(ex.getInstanceId()
                        .toString(),ex.getInstanceType())), null);
            return;
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())), null);
            return;
        }catch (OfertaExpirationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    XmlExceptionConversor.toOfertaExpirationExceptionXml(ex), null);

            return;
        }catch (BadStateException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    XmlExceptionConversor.toBadStateExceptionXml(ex), null);

            return;
        }catch (MaxNReservaException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toMaxNReservaExceptionXml(ex), null);

            return;
        }catch (ReservaEmailException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    XmlExceptionConversor.toReservaEmailExceptionXml(ex), null);

            return;
        }
        ReservaDto reservaDto = ReservaToReservaDtoConversor.toReservaDto(reserva);
        String reservaURL = req.getRequestURL().append("/").append(
        		reservaId).toString();

        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", reservaURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                XmlReservaDtoConversor.toResponse(reservaDto), headers);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String requestURI = req.getRequestURI();
        int idx = requestURI.lastIndexOf('/');
        if (idx <= 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to find reserva id")), null);
            return;
        }
        Long reservaId;
        String reservaIdAsString = requestURI.substring(idx + 1);
        try {
            reservaId = Long.valueOf(reservaIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to parse reserva id '" + 
                    reservaIdAsString + "'")), null);
            return;
        }        

        try {
            OfertaServiceFactory.getService().reclamarOferta(reservaId);
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())), 
                    null);
            return;
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, 
                    XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                new InstanceNotFoundException(
                    ex.getInstanceId().toString(), ex.getInstanceType())),
                    null);       
            return;
        }catch (BadStateException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, 
                    XmlExceptionConversor.toBadStateExceptionXml(
                new BadStateException(
                    ex.getOfertaId(), ex.getEstado().toString())),
                    null);       
            return;
        }catch (OfertaExpirationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    XmlExceptionConversor.toOfertaExpirationExceptionXml(ex), null);

            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, 
                null, null);        
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null || path.length() == 0 || "/".equals(path)) {
        	String estadoO = req.getParameter("estado");
            String ofertaId = req.getParameter("ofertaId");
            Boolean state;
            if (estadoO.equals("null")){
            	 state = null;
            }else{
            	state = Boolean.valueOf(estadoO);
            }
            List<Reserva> reservas;
            try{
            	reservas = OfertaServiceFactory.getService()
                    .obtenerReservas(Long.valueOf(ofertaId),state);
              
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, 
                        XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                    new InstanceNotFoundException(
                        ex.getInstanceId().toString(), ex.getInstanceType())),
                        null);       
                return;
            }
            List<ReservaDto> ReservaDtos = ReservaToReservaDtoConversor
                    .toReservaDtos(reservas);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    XmlReservaDtoConversor.toXml(ReservaDtos), null);
            return;
       
        } else {
            String reservaIdAsString = path.endsWith("/") && path.length() > 2 ? 
                    path.substring(1, path.length() - 1) : path.substring(1);
            
            Long reservaId;
            try {
                reservaId = Long.valueOf(reservaIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, 
                		HttpServletResponse.SC_BAD_REQUEST,
                        XmlExceptionConversor.toInputValidationExceptionXml(
                        new InputValidationException("Invalid Request: " +
                            "parameter 'reservaId' is invalid '" + 
                            reservaIdAsString + "'")),
                        null);

                return;
            }
             Reserva reserva;
            try {
                reserva = OfertaServiceFactory.getService().findReserva(reservaId);
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, 
                		HttpServletResponse.SC_NOT_FOUND, 
                        XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                new InstanceNotFoundException(
                    ex.getInstanceId().toString(), ex.getInstanceType())),
                    null);
                return;
            }
            ReservaDto reservaDto = ReservaToReservaDtoConversor.toReservaDto(reserva);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                XmlReservaDtoConversor.toXml(reservaDto), null);
            return;
        }
    }    
}