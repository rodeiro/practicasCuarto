package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.dto.OfertaDto;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import java.util.Date;


public class OfertaDtoToSoapOfertaDtoConversor {
    
   
    public static es.udc.ws.app.client.service.soap.wsdl.OfertaDto
    toSoapOfertaDto(OfertaDto oferta) throws DatatypeConfigurationException {
        es.udc.ws.app.client.service.soap.wsdl.OfertaDto soapOfertaDto = 
                new es.udc.ws.app.client.service.soap.wsdl.OfertaDto();
        soapOfertaDto.setOfertaId(oferta.getOfertaId());
        soapOfertaDto.setDescripcion(oferta.getDescripcion());     
        soapOfertaDto.setNomeO(oferta.getNomeO());
        Date cDateini = oferta.getDataini().getTime();
        GregorianCalendar cini = new GregorianCalendar();
        cini.setTime(cDateini);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cini);
        soapOfertaDto.setDataini(date2);
        Date cDatefin = oferta.getDatafin().getTime();
        GregorianCalendar cfin = new GregorianCalendar();
        cfin.setTime(cDatefin);
        XMLGregorianCalendar date3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cfin);
        soapOfertaDto.setDatafin(date3);
        Date cDatedis = oferta.getDatadis().getTime();
        GregorianCalendar cdis = new GregorianCalendar();
        cdis.setTime(cDatedis);
        XMLGregorianCalendar date4 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cdis);
        soapOfertaDto.setDatadis(date4);
        soapOfertaDto.setCosteD(oferta.getCosteD());
        soapOfertaDto.setCosteR(oferta.getCosteR());
        soapOfertaDto.setMaxi(oferta.getMaxi());
        return soapOfertaDto;
    }    
    
    public static OfertaDto toOfertaDto(
            es.udc.ws.app.client.service.soap.wsdl.OfertaDto oferta) {
        return new OfertaDto(oferta.getOfertaId() , oferta.getDescripcion(), 
                oferta.getDataini().toGregorianCalendar(), oferta.getDatafin().toGregorianCalendar(),
                oferta.getDatadis().toGregorianCalendar(),
                oferta.getCosteR(),oferta.getCosteD(),oferta.getMaxi(),oferta.getNomeO());
    }     
    
    public static List<OfertaDto> toOfertaDtos(
            List<es.udc.ws.app.client.service.soap.wsdl.OfertaDto> oferta) {
        List<OfertaDto> ofertaDtos = new ArrayList<>(oferta.size());
        for (int i = 0; i < oferta.size(); i++) {
            es.udc.ws.app.client.service.soap.wsdl.OfertaDto ofer = 
                    oferta.get(i);
            ofertaDtos.add(toOfertaDto(ofer));
            
        }
        return ofertaDtos;
    }    
    
}