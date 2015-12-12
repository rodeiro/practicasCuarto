
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "removeOferta", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeOferta", namespace = "http://soap.ws.udc.es/")
public class RemoveOferta {

    @XmlElement(name = "ofertaId", namespace = "")
    private Long ofertaId;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getOfertaId() {
        return this.ofertaId;
    }

    /**
     * 
     * @param ofertaId
     *     the value for the ofertaId property
     */
    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

}
