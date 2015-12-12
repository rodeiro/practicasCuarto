package es.udc.ws.app.serviceutil;

import java.util.ArrayList;
import java.util.List;

import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.app.model.oferta.Oferta;
import es.udc.ws.app.model.reserva.Reserva;

public class ReservaToReservaDtoConversor {

    public static List<ReservaDto> toReservaDtos(List<Reserva> reservas) {
        List<ReservaDto> reservaDtos = new ArrayList<>(reservas.size());
        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            reservaDtos.add(toReservaDto(reserva));
        }
        return reservaDtos;
    }
    public static ReservaDto toReservaDto(Reserva reserva) {
        return new ReservaDto(reserva.getReservaId(), reserva.getemailUs(),
        		reserva.getnconta(),reserva.getOfid(),reserva.getDataCrea(),
        		reserva.getestadoRes());
    }
    
}
