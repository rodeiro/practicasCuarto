
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.app.dto.OfertaDto;

@XmlRootElement(name = "updateOferta", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateOferta", namespace = "http://soap.ws.udc.es/")
public class UpdateOferta {

    @XmlElement(name = "ofertaDto", namespace = "")
    private OfertaDto ofertaDto;

    /**
     * 
     * @return
     *     returns OfertaDto
     */
    public OfertaDto getOfertaDto() {
        return this.ofertaDto;
    }

    /**
     * 
     * @param ofertaDto
     *     the value for the ofertaDto property
     */
    public void setOfertaDto(OfertaDto ofertaDto) {
        this.ofertaDto = ofertaDto;
    }

}
