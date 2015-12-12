
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.app.dto.ReservaDto;

@XmlRootElement(name = "findReservaResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findReservaResponse", namespace = "http://soap.ws.udc.es/")
public class FindReservaResponse {

    @XmlElement(name = "return", namespace = "")
    private ReservaDto _return;

    /**
     * 
     * @return
     *     returns ReservaDto
     */
    public ReservaDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ReservaDto _return) {
        this._return = _return;
    }

}
