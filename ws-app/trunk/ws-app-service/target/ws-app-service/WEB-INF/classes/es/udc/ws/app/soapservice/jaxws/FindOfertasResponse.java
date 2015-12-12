
package es.udc.ws.app.soapservice.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import es.udc.ws.app.dto.OfertaDto;

@XmlRootElement(name = "findOfertasResponse", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findOfertasResponse", namespace = "http://soap.ws.udc.es/")
public class FindOfertasResponse {

    @XmlElement(name = "return", namespace = "")
    private List<OfertaDto> _return;

    /**
     * 
     * @return
     *     returns List<OfertaDto>
     */
    public List<OfertaDto> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<OfertaDto> _return) {
        this._return = _return;
    }

}
