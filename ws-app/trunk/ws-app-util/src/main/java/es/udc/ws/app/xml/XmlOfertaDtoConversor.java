package es.udc.ws.app.xml;

import es.udc.ws.app.dto.OfertaDto;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlOfertaDtoConversor {

    public final static Namespace XML_NS =
            Namespace.getNamespace("http://ws.udc.es/ofertas/xml");

    public static Document toXml(OfertaDto oferta)
            throws IOException {

        Element ofertaElement = toJDOMElement(oferta);
        return new Document(ofertaElement);
    }

    public static Document toXml(List<OfertaDto> oferta)
            throws IOException {

        Element ofertasElement = new Element("ofertas", XML_NS);
        for (int i = 0; i < oferta.size(); i++) {
            OfertaDto xmlOfertaDto = oferta.get(i);
            Element ofertaElement = toJDOMElement(xmlOfertaDto);
            ofertasElement.addContent(ofertaElement);
        }

        return new Document(ofertasElement);
    }

    public static OfertaDto toOferta(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            return toOferta(rootElement);
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<OfertaDto> toOfertas(InputStream ofertaXml)
            throws ParsingException {
        try {

            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(ofertaXml);
            Element rootElement = document.getRootElement();

            if(!"ofertas".equalsIgnoreCase(rootElement.getName())) {
                throw new ParsingException("Unrecognized element '"
                    + rootElement.getName() + "' ('ofertas' expected)");
            }
            @SuppressWarnings("unchecked")
			List<Element> children = rootElement.getChildren();
            List<OfertaDto> ofertaDtos = new ArrayList<>(children.size());
            for (int i = 0; i < children.size(); i++) {
                Element element = children.get(i);
                ofertaDtos.add(toOferta(element));
            }

            return ofertaDtos;
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static Element toJDOMElement(OfertaDto oferta) {

    	Element ofertaElement = new Element("oferta", XML_NS);
    	
        if (oferta.getOfertaId() != null) {
            Element identifierElement = new Element("ofertaId", XML_NS);
            identifierElement.setText(oferta.getOfertaId().toString());
            ofertaElement.addContent(identifierElement);
        }

        Element descriptionElement = new Element("description", XML_NS);
        descriptionElement.setText(oferta.getDescripcion());
        ofertaElement.addContent(descriptionElement);      
        
        Element nomeO = new Element("nomeO", XML_NS);
        nomeO.setText(oferta.getNomeO());
        ofertaElement.addContent(nomeO);        
       
        Element costeRElement = new Element("costeReal", XML_NS);
        costeRElement.setText(Float.toString(oferta.getCosteR()));
        ofertaElement.addContent(costeRElement);

        Element costeDElement = new Element("costeDescuento", XML_NS);
        costeDElement.setText(Float.toString(oferta.getCosteD()));
        ofertaElement.addContent(costeDElement);
        
        if (oferta.getDataini() != null) {
            Element DataIniElement = getDataini(
            		oferta.getDataini());
            ofertaElement.addContent(DataIniElement);
        }
        if (oferta.getDatafin() != null) {
            Element DataFinElement = getDatafin(
            		oferta.getDatafin());
            ofertaElement.addContent(DataFinElement);
        }
        if (oferta.getDatadis() != null) {
            Element DataDisElement = getDatadis(
            		oferta.getDatadis());
            ofertaElement.addContent(DataDisElement);
        }
    	
        Element maxElement = new Element("maxPlazas", XML_NS);
        String maxEl = "";
        if (oferta.getMaxi() == null){
        	maxEl="null";
        }else{
        	maxEl = Integer.toString(oferta.getMaxi());
        }
        maxElement.setText(maxEl);
        ofertaElement.addContent(maxElement);

        return ofertaElement;
    }

    private static OfertaDto toOferta(Element ofertaElement)
            throws ParsingException, DataConversionException, ParseException {
        if (!"oferta".equals(
        		ofertaElement.getName())) {
            throw new ParsingException("Unrecognized element '"
                    + ofertaElement.getName() + "' ('oferta' expected)");
        }
        Element identifierElement = ofertaElement.getChild("ofertaId", XML_NS);
        Long identifier = null;

        if (identifierElement != null) {
            identifier = Long.valueOf(identifierElement.getTextTrim());
        }

        String description = ofertaElement
                .getChildTextNormalize("description", XML_NS);

        String nome = ofertaElement.getChildTextNormalize("nomeO", XML_NS);

        float costeD = Float.valueOf(
        		ofertaElement.getChildTextTrim("costeDescuento", XML_NS));

        float costeR = Float.valueOf(
        		ofertaElement.getChildTextTrim("costeReal", XML_NS));
        
        Integer maxi = null;
        if (ofertaElement.getChildTextTrim("maxPlazas", XML_NS).equals("null")){
        	maxi = null;
        }else{
        	maxi = Integer.valueOf(ofertaElement.getChildTextTrim("maxPlazas", XML_NS));
        }
       
        Calendar DataIni = getExpirationDate(ofertaElement.getChild(
                "dataini", XML_NS));
        Calendar DataFin = getExpirationDate(ofertaElement.getChild(
                "datafin", XML_NS));
        Calendar DataDis = getExpirationDate(ofertaElement.getChild(
                "datadis", XML_NS));
        
        return new OfertaDto(identifier, description, DataIni,
        		DataFin, DataDis,costeD,costeR,maxi,nome);
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

    private static Element getDataini(Calendar expirationDate) {

        Element releaseDateElement = new Element("dataini", XML_NS);
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
    private static Element getDatafin(Calendar expirationDate) {

        Element releaseDateElement = new Element("datafin", XML_NS);
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
    private static Element getDatadis(Calendar expirationDate) {

        Element releaseDateElement = new Element("datadis", XML_NS);
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
}
