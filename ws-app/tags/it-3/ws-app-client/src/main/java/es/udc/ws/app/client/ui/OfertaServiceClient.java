package es.udc.ws.app.client.ui;

import es.udc.ws.app.client.service.ClientOfertaService;
import es.udc.ws.app.client.service.ClientOfertaServiceFactory;
import es.udc.ws.app.dto.OfertaDto;
import es.udc.ws.app.dto.ReservaDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.app.exceptions.BadStateException;
import es.udc.ws.app.exceptions.MaxNReservaException;
import es.udc.ws.app.exceptions.ReservaEmailException;
import es.udc.ws.app.exceptions.OfertaExpirationException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OfertaServiceClient {

    public static void main(String[] args) {

        if(args.length == 0) {
            printUsageAndExit();
        }
        ClientOfertaService clientOfertaService =
                ClientOfertaServiceFactory.getService();
        if("-a".equalsIgnoreCase(args[0])) {
            validateArgs(args, 9, new int[] {5 , 6});
            
            // [add] OfertaServiceClient -a <ofertaId> <Descripcion> <dataini> <datafin> <datadis> <costeR> <costeD> <Nome>

            try {
            	SimpleDateFormat dataini = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date dini = dataini.parse(args[2]);
            	Calendar datainicio = new GregorianCalendar();
            	datainicio.setTime(dini);
            	
            	SimpleDateFormat datafin = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date dfin= datafin.parse(args[3]);
            	Calendar dtfin = new GregorianCalendar();
            	dtfin.setTime(dfin);
            	
            	SimpleDateFormat datadis = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date ddis = datadis.parse(args[4]);
            	Calendar ddisfrute = new GregorianCalendar();
            	ddisfrute.setTime(ddis);
            	Integer integer;
            	if (args[7].equals("null")){
            		integer = null;
            	}else{
            		integer = Integer.valueOf(args[7]);
            	}
           	
                Long ofertaId = clientOfertaService.addOferta( new OfertaDto (null ,
                        args[1], datainicio, dtfin, ddisfrute, Float.valueOf(args[5]),
                        Float.valueOf(args[6]), integer, args[8]));

                System.out.println("Oferta " + ofertaId + " created sucessfully");

            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-r".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [remove] OfertaServiceClient -r <ofertaId>

            try {
                clientOfertaService.removeOferta(Long.parseLong(args[1]));

                System.out.println("Oferta with id " + args[1] +
                        " removed sucessfully");

            } catch (NumberFormatException | InstanceNotFoundException | BadStateException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-u".equalsIgnoreCase(args[0])) {
           validateArgs(args, 10, new int[] {1, 6, 7});

           // [update] OfertaServiceClient -u <ofertaId> <Descripcion> <dataini> <datafin> <datadis> <costeR> <costeD> <Nome>

           try {
             	SimpleDateFormat dataini = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date dini = dataini.parse(args[3]);
            	Calendar datainicio = new GregorianCalendar();
            	datainicio.setTime(dini);
            	
            	SimpleDateFormat datafin = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date dfin= datafin.parse(args[4]);
            	Calendar dtfin = new GregorianCalendar();
            	dtfin.setTime(dfin);
            	
            	SimpleDateFormat datadis = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            	Date ddis = datadis.parse(args[5]);
            	Calendar ddisfrute = new GregorianCalendar();
            	ddisfrute.setTime(ddis);
            	Integer integer;
            	if (args[8].equals("null")){
            		integer = null;
            	}else{
            		integer = Integer.valueOf(args[8]);
            	}
                clientOfertaService.updateOferta(new OfertaDto (Long.valueOf(args[1]) ,
                        args[2], datainicio, dtfin, ddisfrute, Float.valueOf(args[6]),
                        Float.valueOf(args[7]), integer, args[9]));

                System.out.println("Oferta " + args[1] + " updated sucessfully");

            } catch (NumberFormatException | InputValidationException |
                     InstanceNotFoundException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-f".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {});

            // [find] OfertaServiceClient -f <keywords>

            try {
                List<OfertaDto> ofertas = clientOfertaService.findOfertas(args[1]);
                System.out.println("Found " + ofertas.size() +
                        " ofertas(s) with keywords '" + args[1] + "'");
                for (int i = 0; i < ofertas.size(); i++) {
                    OfertaDto ofertaDto = ofertas.get(i);
                    System.out.println("Id: " + ofertaDto.getOfertaId() +
                            " Descripcion: " + ofertaDto.getDescripcion() +
                            " Data inicio: " + ofertaDto.getDataini() +
                            " Data fin: " + ofertaDto.getDatafin() +
                            " Data disfrute: " + ofertaDto.getDatadis() +
                            " Coste Real: " + ofertaDto.getCosteR() + 
                            " Coste Descontado: " + ofertaDto.getCosteD() +
                            " Nome: " + ofertaDto.getNomeO() +
                            " Número plazas: " + ofertaDto.getMaxi());
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-b".equalsIgnoreCase(args[0])) {
            validateArgs(args, 4, new int[] {1});

            // [buy] OfertaServiceClient -b <ofertaId> <email> <creditCardNumber>

            Long reservaId;
            try {
                reservaId = clientOfertaService.buyOferta(Long.parseLong(args[1]),
                        args[2], args[3]);

                System.out.println("Oferta " + args[1] +
                        " purchased sucessfully with sale number " +
                        reservaId);

            } catch (NumberFormatException | InstanceNotFoundException | BadStateException 
            		| ReservaEmailException | InputValidationException | OfertaExpirationException | 
            		MaxNReservaException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else if("-fr".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            // [find Reservas] OfertaServiceClient -fr <ReservaId>

            try {
                ReservaDto reserva =
                        clientOfertaService.findReserva(Long.parseLong(args[1]));

                System.out.println(" Email user: " +reserva.getEmailUs()  +
                        " Credit card Number: "  +reserva.getNconta() +
                        " Oferta:" + reserva.getOfertaId() +
                        " Creation date:" + reserva.getDataCrea());
                
            } catch (NumberFormatException | InstanceNotFoundException  ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }else if("-ro".equalsIgnoreCase(args[0])) {
        	validateArgs(args, 2, new int[] {1});
        	try{
        		clientOfertaService.reclamarOferta(Long.parseLong(args[1]));
        		System.out.println ("Oferta whith Id:" + args[1]+
        				"succesfully reclaimed");
        	}catch (NumberFormatException | InstanceNotFoundException 
        			| InputValidationException | BadStateException | OfertaExpirationException ex) {
                ex.printStackTrace(System.err);
            }catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }else if ("-o".equalsIgnoreCase(args[0])){
        	try{
        		Boolean state;
        		if (args[2].equals("null")){
            		state = null;
            	}else if (args[2].equals("true")){
            		state = true;
            	}else{
            		state = false;
            	}
        		validateArgs(args, 3, new int[] {1});
        		List<ReservaDto> reservas = clientOfertaService.obtenerReservas(Long.parseLong(args[1]),
        				state);
        		for (ReservaDto reserva : reservas) {
                    
        			 System.out.println("Reserva:"+reserva.getReservaId()+
        					 " Email user: " +reserva.getEmailUs()  +
                             " Credit card Number: "  +reserva.getNconta() +
                             " Oferta:" + reserva.getOfertaId() +
                             " Creation date:" + reserva.getDataCrea());
                }
        	
        	}catch (NumberFormatException | InstanceNotFoundException   ex) {
                ex.printStackTrace(System.err);
            }catch (Exception ex) {
            ex.printStackTrace(System.err);
        	}
        }else if ("-fo".equalsIgnoreCase(args[0])){
        	try{
        		validateArgs(args, 2, new int[] {1});
        		OfertaDto ofertaDto = clientOfertaService.findOferta(Long.parseLong(args[1]));
        		System.out.println("Id: " + ofertaDto.getOfertaId() +
                        " Descripcion: " + ofertaDto.getDescripcion() +
                        " Data inicio: " + ofertaDto.getDataini() +
                        " Data fin: " + ofertaDto.getDatafin() +
                        " Data disfrute: " + ofertaDto.getDatadis() +
                        " Coste Real: " + ofertaDto.getCosteR() + 
                        " Coste Descontado: " + ofertaDto.getCosteD() +
                        " Nome: " + ofertaDto.getNomeO() +
                        " Número plazas: " + ofertaDto.getMaxi());
        }catch (NumberFormatException | InstanceNotFoundException  ex) {
            ex.printStackTrace(System.err);
        }catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        }
    }

    public static void validateArgs(String[] args, int expectedArgs,
                                    int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsageAndExit();
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsageAndExit();
            }
        }
    }

    public static void printUsageAndExit() {
        printUsage();
        System.exit(-1);
    }

    public static void printUsage() {
        System.err.println("Usage:\n" +
                "    [add]    OfertaServiceClient -a <descricion> <dataini> <datafin> <datadis><cosr><cosd><maxi><nome>\n" +
                "    [remove] OfertaServiceClient -r <ofertaId>\n" +
                "    [update] OfertaServiceClient -u <ofertaId> <descricion> <dataini> <datafin> <datadis><cosr><cosd><maxi><nome>\n" +
                "    [find]   OfertaServiceClient -f <keywords>\n" +
                "    [buy]    OfertaServiceClient -b <ofertaId> <emailUs><nconta>\n" +
                "    [findReserva]    OfertaServiceClient -fr <reservaId>\n"+
                "	 [reclamarOferta] OfertaServiceClient -ro <reservaId>\n"+
                "	 [obtnerReservas] OfertaServiceClient -o <ofertaId><estado>\n"+
                "	 [findOferta] OfertaServiceClient -fo <ofertaId>\n");
        
    }

}
