package es.udc.ws.app.client.service.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import es.udc.ws.app.xml.ParsingException;
import es.udc.ws.app.xml.XmlExceptionConversor;
import es.udc.ws.app.xml.XmlOfertaDtoConversor;
import es.udc.ws.app.xml.XmlReservaDtoConversor;
import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class RestClientOfertaService implements ClientOfertaService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "RestClientOfertaService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addOferta(OfertaDto oferta) throws InputValidationException {

        PostMethod method =
                new PostMethod(getEndpointAddress() + "ofertas");
        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            Document document;
            try {
                document = XmlOfertaDtoConversor.toXml(oferta);
                XMLOutputter outputter = new XMLOutputter(
                        Format.getPrettyFormat());
                outputter.output(document, xmlOutputStream);
            } catch (IOException ex) {
                throw new InputValidationException(ex.getMessage());
            }
            ByteArrayInputStream xmlInputStream =
                    new ByteArrayInputStream(xmlOutputStream.toByteArray());
            InputStreamRequestEntity requestEntity =
                    new InputStreamRequestEntity(xmlInputStream,
                    "application/xml");
            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_CREATED, method);
            } catch (InputValidationException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return getIdFromHeaders(method);

        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public void updateOferta(OfertaDto oferta)
            throws InputValidationException, InstanceNotFoundException,
            BadStateException {
        PutMethod method =
                new PutMethod(getEndpointAddress() + "ofertas/"
                + oferta.getOfertaId());
        try {

            ByteArrayOutputStream xmlOutputStream = new ByteArrayOutputStream();
            Document document;
            try {
                document = XmlOfertaDtoConversor.toXml(oferta);
                XMLOutputter outputter = new XMLOutputter(
                        Format.getPrettyFormat());
                outputter.output(document, xmlOutputStream);
            } catch (IOException ex) {
                throw new InputValidationException(ex.getMessage());
            }
            ByteArrayInputStream xmlInputStream =
                    new ByteArrayInputStream(xmlOutputStream.toByteArray());
            InputStreamRequestEntity requestEntity =
                    new InputStreamRequestEntity(xmlInputStream,
                    "application/xml");
            HttpClient client = new HttpClient();
            method.setRequestEntity(requestEntity);

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_NO_CONTENT, method);
            } catch (InputValidationException | InstanceNotFoundException 
            		| BadStateException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public void removeOferta(Long ofertaId) throws InstanceNotFoundException
    , BadStateException {
        DeleteMethod method =
                new DeleteMethod(getEndpointAddress() + "ofertas/" + ofertaId);
        try {
            HttpClient client = new HttpClient();
            int statusCode = client.executeMethod(method);
            validateResponse(statusCode, HttpStatus.SC_NO_CONTENT, method);
        } catch (InstanceNotFoundException ex) {
            throw ex;
        } catch (BadStateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public List<OfertaDto> findOfertas(String keywords) {
        GetMethod method = null;
        try {
            method = new GetMethod(getEndpointAddress() + "ofertas/?keywords="
                    + URLEncoder.encode(keywords, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlOfertaDtoConversor.toOfertas(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public Long buyOferta(Long ofertaId, String emailUs, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException,OfertaExpirationException,
            BadStateException,MaxNReservaException,ReservaEmailException{
        PostMethod method = new PostMethod(getEndpointAddress() + "reservas");
        try {
            method.addParameter("ofertaId", Long.toString(ofertaId));
            method.addParameter("email", emailUs);
            method.addParameter("creditCardNumber", creditCardNumber);

            HttpClient client = new HttpClient();

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_CREATED, method);
            } catch (InputValidationException | InstanceNotFoundException | BadStateException 
            		| MaxNReservaException | OfertaExpirationException | ReservaEmailException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            return getIdFromHeaders(method);
        } finally {
            method.releaseConnection();
        }
    }

    @Override
    public OfertaDto findOferta(Long ofertaId)
            throws InstanceNotFoundException {
        GetMethod method = new GetMethod(getEndpointAddress()
                + "ofertas/" + ofertaId);
        try {

            HttpClient client = new HttpClient();

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (InstanceNotFoundException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            try {
                return XmlOfertaDtoConversor.toOferta(
                        method.getResponseBodyAsStream());
                
            } catch (IOException | ParsingException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
           

        } finally {
            method.releaseConnection();
        }
    }
    @Override
    public ReservaDto findReserva(Long reservaId)
            throws InstanceNotFoundException {
        GetMethod method = new GetMethod(getEndpointAddress()
                + "reservas/" + reservaId);
        try {

            HttpClient client = new HttpClient();

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (InstanceNotFoundException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            try {
                return XmlReservaDtoConversor.toReserva(
                        method.getResponseBodyAsStream());
                
            } catch (IOException | ParsingException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
           

        } finally {
            method.releaseConnection();
        }
    }
    @Override
    public void reclamarOferta(Long reservaId)
            throws InputValidationException, InstanceNotFoundException,
            BadStateException, OfertaExpirationException {
        PutMethod method =
                new PutMethod(getEndpointAddress() + "reservas/"
                + reservaId);
        try {
            
            HttpClient client = new HttpClient();     

            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_NO_CONTENT, method);
            } catch (InputValidationException | InstanceNotFoundException 
            		| BadStateException |OfertaExpirationException  ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            method.releaseConnection();
        }
    }
    @Override
    public List<ReservaDto> obtenerReservas(Long ofertaId , Boolean estado) 
    throws InstanceNotFoundException{
        GetMethod method = null;
        
        try {
        	String state;
        	if (estado == null){
        		 state = "null";
        	}else {
        		state = estado.toString();
        	}

            method = new GetMethod(getEndpointAddress() + "reservas/?ofertaId="
                    + URLEncoder.encode ( Long.valueOf(ofertaId).toString(), "UTF-8")
                    + "&estado=" + URLEncoder.encode(state,
                    		"UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
        try {
            HttpClient client = new HttpClient();
            int statusCode;
            try {
                statusCode = client.executeMethod(method);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                validateResponse(statusCode, HttpStatus.SC_OK, method);
            } catch (InstanceNotFoundException ex) {
                throw ex;
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                return XmlReservaDtoConversor.toReservas(
                        method.getResponseBodyAsStream());
            } catch (ParsingException | IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            method.releaseConnection();
        }
    }
    private synchronized String getEndpointAddress() {

        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager.getParameter(
                    ENDPOINT_ADDRESS_PARAMETER);
        }

        return endpointAddress;
    }

    private void validateResponse(int statusCode,
                                  int expectedStatusCode,
                                  HttpMethod method)
            throws InstanceNotFoundException, OfertaExpirationException,
            InputValidationException, BadStateException, MaxNReservaException,
            ReservaEmailException, ParsingException, IOException {

    	String s = method.getResponseBodyAsString();
    	InputStream in = new ByteArrayInputStream("".getBytes());;
    	if (s != null){
    		in = new ByteArrayInputStream(s.getBytes());
    	}
        String contentType = getResponseHeader(method, "Content-Type");
        boolean isXmlResponse = "application/xml"
                .equalsIgnoreCase(contentType);
        if (!isXmlResponse && statusCode >= 400) {
            throw new RuntimeException("HTTP error; status code = "
                    + statusCode);
        }
        switch (statusCode) {
            case HttpStatus.SC_NOT_FOUND:
            	
                try {
                    throw XmlExceptionConversor
                            .fromInstanceNotFoundExceptionXml(in);
                } catch (ParsingException e) {
                	try {
                		InputStream in1 = new ByteArrayInputStream(s.getBytes());
                		throw XmlExceptionConversor
                                .fromBadStateExceptionXml(in1);
                		} catch (ParsingException e1) {
                			throw new RuntimeException(e1);
                    }
                		
                	
                }
            case HttpStatus.SC_BAD_REQUEST:
                try {
                    throw XmlExceptionConversor
                            .fromInputValidationExceptionXml(in);
                } catch (ParsingException e) {
                	try {
                		InputStream in1 = new ByteArrayInputStream(s.getBytes());
                        throw XmlExceptionConversor
                                .fromMaxNReservaExceptionXml(in1);
                    } catch (ParsingException e1) {
                        throw new RuntimeException(e1);
                    }
                  }
            case HttpStatus.SC_GONE:
                try {
                    throw XmlExceptionConversor
                            .fromOfertaExpirationExceptionXml(in);
                } catch (ParsingException e) {
                    throw new RuntimeException(e);
                }

                
            case HttpStatus.SC_FORBIDDEN :
                try {
                    throw XmlExceptionConversor
                            .fromReservaEmailExceptionXml(in);
                } catch (ParsingException e) {
                    throw new RuntimeException(e);
                }    
            default:
                if (statusCode != expectedStatusCode) {
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
                }
                break;
        }
    }

    private static Long getIdFromHeaders(HttpMethod method) {
        String location = getResponseHeader(method, "Location");
        if (location != null) {
            int idx = location.lastIndexOf('/');
            return Long.valueOf(location.substring(idx + 1));
        }
        return null;
    }

    private static String getResponseHeader(HttpMethod method,
            String headerName) {
        Header[] headers = method.getResponseHeaders();
        for (int i = 0; i < headers.length; i++) {
            Header header = headers[i];
            if (headerName.equalsIgnoreCase(header.getName())) {
                return header.getValue();
            }
        }
        return null;
    }
}
