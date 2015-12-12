package es.udc.ws.app.xml;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlReservaDtoConversor {

    public final static Namespace XML_NS = Namespace
            .getNamespace("http://ws.udc.es/reservas/xml");

    public static Document toResponse(ReservaDto reserva)
            throws IOException {

        Element reservaElement = toJDOMElement(reserva);

        return new Document(reservaElement);
    }

    public static ReservaDto toReserva(InputStream reservaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();
            return toReserva(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    public static Document toXml(ReservaDto reserva)
            throws IOException {

        Element ofertaElement = toJDOMElement(reserva);
        return new Document(ofertaElement);
    }
    
    public static Document toXml(List<ReservaDto> reserva)
            throws IOException {

        Element reservasElement = new Element("reservas", XML_NS);
        for (int i = 0; i < reserva.size(); i++) {
            ReservaDto xmlReservaDto = reserva.get(i);
            Element ofertaElement = toJDOMElement(xmlReservaDto);
            reservasElement.addContent(ofertaElement);
        }

        return new Document(reservasElement);
    }
    public static Element toJDOMElement(ReservaDto reserva) {

        Element reservaElement = new Element("reserva", XML_NS);

        if (reserva.getReservaId() != null) {
            Element reservaIdElement = new Element("reservaId", XML_NS);
            reservaIdElement.setText(reserva.getReservaId().toString());
            reservaElement.addContent(reservaIdElement);
        }

        if (reserva.getOfertaId() != null) {
            Element OfertaIdElement = new Element("OfertaId", XML_NS);
            OfertaIdElement.setText(reserva.getOfertaId().toString());
            reservaElement.addContent(OfertaIdElement);
        }

        if (reserva.getNconta() != null) {
            Element nContaElement = new Element("numConta", XML_NS);
            nContaElement.setText(reserva.getNconta());
            reservaElement.addContent(nContaElement);
        }        
     
        if (reserva.getEmailUs() != null) {
            Element eMailElement = new Element("emailUs", XML_NS);
            eMailElement.setText(reserva.getEmailUs());
            reservaElement.addContent(eMailElement);
        }          
     
        if (reserva.getDataCrea() != null) {
            Element expirationDateElement = getExpirationDate(reserva
                    .getDataCrea());
            reservaElement.addContent(expirationDateElement);
        }
        if (reserva.getEstado() != null) {
        	Element estadoElement = new Element("estado", XML_NS);
            estadoElement.setText(reserva.getEstado().toString());
            reservaElement.addContent(estadoElement);
        }
        return reservaElement;
    }

    private static ReservaDto toReserva(Element reservaElement)
            throws ParsingException, DataConversionException,
            NumberFormatException {
        if (!"reserva".equals(reservaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + reservaElement.getName() + "' ('reserva' expected)");
        }
        Element reservaIdElement = reservaElement.getChild("reservaId", XML_NS);
        Long reservaId = null;

        if (reservaIdElement != null) {
            reservaId = Long.valueOf(reservaIdElement.getTextTrim());
        }

        Element ofertaIdElement = reservaElement.getChild("OfertaId", XML_NS);
        Long ofertaId = null;

        if (ofertaIdElement != null) {
        	ofertaId = Long.valueOf(ofertaIdElement.getTextTrim());
        }

        String email = reservaElement.getChildTextTrim("emailUs", XML_NS);
        
        Calendar expirationDate = getExpirationDate(reservaElement.getChild(
                "dataCrea", XML_NS));

        String numConta = reservaElement.getChildTextTrim("numConta", XML_NS);
        
        Element estadoElement = reservaElement.getChild("estado", XML_NS);

        Boolean estado = Boolean.valueOf(estadoElement.getTextTrim());
                
        return new ReservaDto(reservaId, email, numConta,ofertaId,
        		expirationDate, estado);
    }

    private static Calendar getExpirationDate(Element expirationDateElement)
            throws DataConversionException {

        if (expirationDateElement == null) {
            return null;
        }
        int day = expirationDateElement.getAttribute("day").getIntValue();
        int month = expirationDateElement.getAttribute("month").getIntValue();
        int year = expirationDateElement.getAttribute("year").getIntValue();
        int hour = expirationDateElement.getAttribute("hour").getIntValue();
        int minute = expirationDateElement.getAttribute("minute").getIntValue();
        int sec = expirationDateElement.getAttribute("second").getIntValue();

        Calendar releaseDate = Calendar.getInstance();

        releaseDate.set(Calendar.DAY_OF_MONTH, day);
        releaseDate.set(Calendar.MONTH, Calendar.JANUARY + month - 1);
        releaseDate.set(Calendar.YEAR, year);
        releaseDate.set(Calendar.HOUR_OF_DAY, hour);
        releaseDate.set(Calendar.MINUTE, minute);
        releaseDate.set(Calendar.SECOND, sec);

        

        return releaseDate;

    }

    private static Element getExpirationDate(Calendar expirationDate) {

        Element releaseDateElement = new Element("dataCrea", XML_NS);
        int day = expirationDate.get(Calendar.DAY_OF_MONTH);
        int month = expirationDate.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int year = expirationDate.get(Calendar.YEAR);
        int hour = expirationDate.get(Calendar.HOUR_OF_DAY);
        int min = expirationDate.get(Calendar.MINUTE);
        int sec = expirationDate.get(Calendar.SECOND);


        releaseDateElement.setAttribute("day", Integer.toString(day));
        releaseDateElement.setAttribute("month", Integer.toString(month));
        releaseDateElement.setAttribute("year", Integer.toString(year));
        releaseDateElement.setAttribute("hour", Integer.toString(hour));
        releaseDateElement.setAttribute("minute", Integer.toString(min));
        releaseDateElement.setAttribute("second", Integer.toString(sec));


        return releaseDateElement;

    }
    public static List<ReservaDto> toReservas(InputStream reservaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reservaXml);
            Element rootElement = document.getRootElement();

            if(!"reservas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('reservas' expected)");
            }
            @SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
            List<ReservaDto> reservaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                reservaDtos.add(toReserva(element));
            }

            return reservaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
    
}