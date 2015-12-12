
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.app.dto.OfertaDto;

@XmlRootElement(name = "findOfertaResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findOfertaResponse", namespace = "http://soap.ws.udc.es/")
public class FindOfertaResponse {

    @XmlElement(name = "return", namespace = "")
    private OfertaDto _return;

    /**
     * 
     * @return
     *     returns OfertaDto
     */
    public OfertaDto getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(OfertaDto _return) {
        this._return = _return;
    }

}
