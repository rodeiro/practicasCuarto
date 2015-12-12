
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.app.dto.ReservaDto;

@XmlRootElement(name = "ObtenerReservasResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObtenerReservasResponse", namespace = "http://soap.ws.udc.es/")
public class ObtenerReservasResponse {

    @XmlElement(name = "return", namespace = "")
    private List<ReservaDto> _return;

    /**
     * 
     * @return
     *     returns List<ReservaDto>
     */
    public List<ReservaDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<ReservaDto> _return) {
        this._return = _return;
    }

}
