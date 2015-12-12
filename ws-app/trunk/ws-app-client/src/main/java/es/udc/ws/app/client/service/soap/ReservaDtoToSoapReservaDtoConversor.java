package es.udc.ws.app.client.service.soap;

import es.udc.ws.app.dto.ReservaDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class ReservaDtoToSoapReservaDtoConversor {
    
    public static es.udc.ws.app.client.service.soap.wsdl.ReservaDto 
            toSoapReservaDto(ReservaDto reserva) throws DatatypeConfigurationException {
        es.udc.ws.app.client.service.soap.wsdl.ReservaDto soapReservaDto = 
                new es.udc.ws.app.client.service.soap.wsdl.ReservaDto();
        soapReservaDto.setReservaId(reserva.getReservaId());
        soapReservaDto.setEmailUs(reserva.getEmailUs());
        soapReservaDto.setNconta(reserva.getNconta());
        soapReservaDto.setOfertaId(reserva.getOfertaId());
        Date cDatecrea = reserva.getDataCrea().getTime();
        GregorianCalendar crea = new GregorianCalendar();
        crea.setTime(cDatecrea);
        XMLGregorianCalendar date4 = DatatypeFactory.newInstance().newXMLGregorianCalendar(crea);
        soapReservaDto.setDataCrea(date4);
        return soapReservaDto;
    }    
    
    public static ReservaDto toReservaDto(
            es.udc.ws.app.client.service.soap.wsdl.ReservaDto reserva) {
        return new ReservaDto(reserva.getReservaId(), reserva.getEmailUs(), 
                reserva.getNconta(), reserva.getOfertaId(), 
                reserva.getDataCrea().toGregorianCalendar(),reserva.isEstado());
    }     
    
    public static List<ReservaDto> toReservaDtos(
            List<es.udc.ws.app.client.service.soap.wsdl.ReservaDto> reserva) {
        List<ReservaDto> reservaDtos = new ArrayList<>(reserva.size());
        for (int i = 0; i < reserva.size(); i++) {
            es.udc.ws.app.client.service.soap.wsdl.ReservaDto res = 
                    reserva.get(i);
            reservaDtos.add(toReservaDto(res));
            
        }
        return reservaDtos;
    }    
    
}