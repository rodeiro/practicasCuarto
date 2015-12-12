package es.udc.ws.app.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import es.udc.ws.app.exceptions.MaxNReservaException;

public class XmlExceptionConversor {

    public final static String CONVERSION_PATTERN =
    		"dd/MM/yyyy HH:mm";

    public final static Namespace XML_NS = XmlOfertaDtoConversor.XML_NS;

    public static InputValidationException
            fromInputValidationExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element message = rootElement.getChild("message", XML_NS);

            return new InputValidationException(message.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static InstanceNotFoundException
            fromInstanceNotFoundExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element instanceId = rootElement.getChild("instanceId", XML_NS);
            Element instanceType =
                    rootElement.getChild("instanceType", XML_NS);

            return new InstanceNotFoundException(instanceId.getText(),
                    instanceType.getText());
        } catch (JDOMException | IOException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static ReservaEmailException
            fromReservaEmailExceptionXml(InputStream ex)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ex);
            Element rootElement = document.getRootElement();

            Element Email = rootElement.getChild("email", XML_NS);           
                       
            return new ReservaEmailException(Email.getText());
        } catch (JDOMException | IOException |NumberFormatException e) {
            throw new ParsingException(e);
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
	public static BadStateException fromBadStateExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element OfertaID = rootElement.getChild("ofertaId", XML_NS);
		    Element estado = rootElement.getChild("estadoO", XML_NS);
		
		    return new BadStateException( Long.parseLong(OfertaID.getTextTrim()),
		    		estado.getText());
		} catch (JDOMException | IOException |NumberFormatException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
	}
	public static MaxNReservaException fromMaxNReservaExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element OfertaID = rootElement.getChild("ofertaId", XML_NS);
		    Element maxI = rootElement.getChild("maxi", XML_NS);
		
		    return new MaxNReservaException( Long.parseLong(OfertaID.getTextTrim()),
		    		Integer.parseInt(maxI.getTextTrim()));
		} catch (JDOMException | IOException | NumberFormatException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
	}	
	public static OfertaExpirationException fromOfertaExpirationExceptionXml(InputStream ex)
		    throws ParsingException {
		try {
		
		    SAXBuilder builder = new SAXBuilder();
		    Document document = builder.build(ex);
		    Element rootElement = document.getRootElement();
		
		    Element OfertaID = rootElement.getChild("ofertaId", XML_NS);
		    Element expirationDate = rootElement
                    .getChild("dataFin", XML_NS);

            Calendar calendar = null;
            if (expirationDate != null) {
                SimpleDateFormat sdf =
                        new SimpleDateFormat(CONVERSION_PATTERN,
                        Locale.ENGLISH);
                calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(expirationDate.getText()));
            }
		
		    return new OfertaExpirationException( Long.parseLong(OfertaID.getTextTrim()),
		    		calendar);
		} catch (JDOMException | IOException | ParseException |
                NumberFormatException e) {
		    throw new ParsingException(e);
		} catch (Exception e) {
		    throw new ParsingException(e);
		}
	}	
    public static Document toInputValidationExceptionXml(
                InputValidationException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InputValidationException", XML_NS);

        Element messageElement = new Element("message", XML_NS);
        messageElement.setText(ex.getMessage());
        exceptionElement.addContent(messageElement);

        return new Document(exceptionElement);
    }

    public static Document toInstanceNotFoundExceptionXml (
                InstanceNotFoundException ex)
            throws IOException {

        Element exceptionElement =
                new Element("InstanceNotFoundException", XML_NS);

        if(ex.getInstanceId() != null) {
            Element instanceIdElement = new Element("instanceId", XML_NS);
            instanceIdElement.setText(ex.getInstanceId().toString());

            exceptionElement.addContent(instanceIdElement);
        }

        if(ex.getInstanceType() != null) {
            Element instanceTypeElement = new Element("instanceType", XML_NS);
            instanceTypeElement.setText(ex.getInstanceType());

            exceptionElement.addContent(instanceTypeElement);
        }
        return new Document(exceptionElement);
    }
	    public static Document toReservaEmailExceptionXml(
	    		ReservaEmailException ex)
	        throws IOException {
	
	    Element exceptionElement =
	            new Element("ReservaEmailException", XML_NS);
	
	    Element emailElement = new Element("email", XML_NS);
	    emailElement.setText(ex.getMail());
	    exceptionElement.addContent(emailElement);
	
	    return new Document(exceptionElement);
	}
    public static Document toBadStateExceptionXml (
    		BadStateException ex)
            throws IOException {

        Element exceptionElement =
                new Element("BadStateException", XML_NS);

        if(ex.getOfertaId() != null) {
            Element ofertaIdElement = new Element("ofertaId", XML_NS);
            ofertaIdElement.setText(ex.getOfertaId().toString());
            exceptionElement.addContent(ofertaIdElement);
        }

        if(ex.getEstado() != null) {
            
        	Element ofertaIdElement = new Element("estadoO", XML_NS);
        	ofertaIdElement.setText(ex.getEstado().toString());
            exceptionElement.addContent(ofertaIdElement);
        }

        return new Document(exceptionElement);
    }
    public static Document toMaxNReservaExceptionXml (
    		MaxNReservaException ex)
            throws IOException {

        Element exceptionElement =
                new Element("MaxNReservaException", XML_NS);

        if(ex.getOfertaId() != null) {
            Element ofertaIdElement = new Element("ofertaId", XML_NS);
            ofertaIdElement.setText(ex.getOfertaId().toString());
            exceptionElement.addContent(ofertaIdElement);
        }

        if(ex.getMaxI() != null) {
            
        	Element maxIElement = new Element("maxi", XML_NS);
        	maxIElement.setText(ex.getMaxI().toString());
            exceptionElement.addContent(maxIElement);
        }

        return new Document(exceptionElement);
    }
	    public static Document toOfertaExpirationExceptionXml (
	    		OfertaExpirationException ex)
	        throws IOException {
	
	    Element exceptionElement =
	            new Element("OfertaExpirationException", XML_NS);
	
	    if(ex.getOfertaId() != null) {
	        Element ofertaIdElement = new Element("ofertaId", XML_NS);
	        ofertaIdElement.setText(ex.getOfertaId().toString());
	        exceptionElement.addContent(ofertaIdElement);
	    }
	
	    if(ex.getDataFin() != null) {
	        SimpleDateFormat dateFormatter =
	                new SimpleDateFormat(CONVERSION_PATTERN,
	                    Locale.ENGLISH);
	
	        Element expirationDateElement = new
	                Element("dataFin", XML_NS);
	        expirationDateElement.setText(dateFormatter.format(
	                ex.getDataFin().getTime()));
	
	        exceptionElement.addContent(expirationDateElement);
	    }
	
	    return new Document(exceptionElement);
	}
}