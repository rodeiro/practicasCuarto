package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.ofertaservice.OfertaServiceFactory;
import es.udc.ws.app.serviceutil.OfertaToOfertaDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.util.servlet.ServletUtils;

@SuppressWarnings("serial")
public class OfertaServlet extends HttpServlet{
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        OfertaDto xmloferta;
        try {
            xmloferta = XmlOfertaDtoConversor
                    .toOferta(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                        new InputValidationException(ex.getMessage())),
                    null);

            return;

        }
        Oferta oferta = OfertaToOfertaDtoConversor.toOferta(xmloferta);
        try {
            oferta = OfertaServiceFactory.getService().addOferta(oferta.getNome(),
            		oferta.getDescripcion(),oferta.getDataini(),oferta.getDatafin(),
            		oferta.getDatadis(),oferta.getCosteR(),oferta.getCosteD(),
            		oferta.getMax(),oferta.getMaxRes());
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())),
                    null);
            return;
        }
        OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);

        String ofertaURL = req.getRequestURL().append("/").append(
                oferta.getOfertaId()).toString();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Location", ofertaURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                XmlOfertaDtoConversor.toXml(ofertaDto), headers);
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
                        "unable to find oferta id")), null);
            return;
        }
        Long ofertaId;
        String ofertaIdAsString = requestURI.substring(idx + 1);
        try {
            ofertaId = Long.valueOf(ofertaIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to parse oferta id '" + 
                    ofertaIdAsString + "'")), null);
            return;
        }        
        
        OfertaDto ofertaDto;
        try {
            ofertaDto = XmlOfertaDtoConversor
                    .toOferta(req.getInputStream());
        } catch (ParsingException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, 
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException(ex.getMessage())), 
                    null);
            return;
            
        }
        Oferta oferta = OfertaToOfertaDtoConversor.toOferta(ofertaDto);
        oferta.setOfertaId(ofertaId);
        try {
            OfertaServiceFactory.getService().updateOferta(oferta);
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
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, 
                null, null);        
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        int idx = requestURI.lastIndexOf('/');
        if (idx <= 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to find oferta id")), null);
            return;
        }
        Long ofertaId;
        String ofertaIdAsString = requestURI.substring(idx + 1);
        try {
            ofertaId = Long.valueOf(ofertaIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    XmlExceptionConversor.toInputValidationExceptionXml(
                    new InputValidationException("Invalid Request: " + 
                        "unable to parse oferta id '" + ofertaIdAsString + "'")),
                    null);
            
            return;
        }
        try {
            OfertaServiceFactory.getService().removeOferta(ofertaId);
        } catch (InstanceNotFoundException ex) {
           ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                   XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                    new InstanceNotFoundException(
                            ex.getInstanceId().toString(),
                    ex.getInstanceType())), null);
            return;
        }catch (BadStateException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND, 
                    XmlExceptionConversor.toBadStateExceptionXml(
                new BadStateException(
                    ex.getOfertaId(), ex.getEstado().toString())),
                    null);       
            return;
        }
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, 
                null, null);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null || path.length() == 0 || "/".equals(path)) {
            String keyWords = req.getParameter("keywords");
            List<Oferta> ofertas = OfertaServiceFactory.getService()
                    .findOfertas(keyWords,null,null);
	    List<Oferta> oferta1 = new ArrayList<>();
        	for (Oferta of:ofertas){
                	Calendar now =Calendar.getInstance();
                	if (of.getDatafin().after(now)){
                		oferta1.add(of);
                	}
        	} 
            List<OfertaDto> ofertaDtos = OfertaToOfertaDtoConversor
                    .toOfertaDtos(oferta1);
               	
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    XmlOfertaDtoConversor.toXml(ofertaDtos), null);
        } else {
            String ofertaIdAsString = path.endsWith("/") && path.length() > 2 ? 
                    path.substring(1, path.length() - 1) : path.substring(1);
            Long ofertaId;
            try {
                ofertaId = Long.valueOf(ofertaIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, 
                		HttpServletResponse.SC_BAD_REQUEST,
                        XmlExceptionConversor.toInputValidationExceptionXml(
                        new InputValidationException("Invalid Request: " +
                            "parameter 'ofertaId' is invalid '" + 
                            ofertaIdAsString + "'")),
                        null);

                return;
            }
             Oferta oferta;
            try {
                oferta = OfertaServiceFactory.getService().findOferta(ofertaId);
            } catch (InstanceNotFoundException ex) {
                ServletUtils.writeServiceResponse(resp, 
                		HttpServletResponse.SC_NOT_FOUND, 
                        XmlExceptionConversor.toInstanceNotFoundExceptionXml(
                new InstanceNotFoundException(
                    ex.getInstanceId().toString(), ex.getInstanceType())),
                    null);
                return;
            }
            OfertaDto ofertaDto = OfertaToOfertaDtoConversor.toOfertaDto(oferta);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                XmlOfertaDtoConversor.toXml(ofertaDto), null);
        }
    }    
    
}
