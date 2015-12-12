
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "reclamarOferta", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reclamarOferta", namespace = "http://soap.ws.udc.es/")
public class ReclamarOferta {

    @XmlElement(name = "reservaId", namespace = "")
    private Long reservaId;

    /**
     * 
     * @return
     *     returns Long
     */
    public Long getReservaId() {
        return this.reservaId;
    }

    /**
     * 
     * @param reservaId
     *     the value for the reservaId property
     */
    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

}
