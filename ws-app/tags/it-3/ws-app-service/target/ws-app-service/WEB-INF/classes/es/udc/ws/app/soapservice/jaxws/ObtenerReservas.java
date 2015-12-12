
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ObtenerReservas", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObtenerReservas", namespace = "http://soap.ws.udc.es/", propOrder = {
    "ofertaId",
    "estado"
})
public class ObtenerReservas {

    @XmlElement(name = "ofertaId", namespace = "")
    private Long ofertaId;
    @XmlElement(name = "estado", namespace = "")
    private Boolean estado;

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

    /**
     * 
     * @return
     *     returns Boolean
     */
    public Boolean getEstado() {
        return this.estado;
    }

    /**
     * 
     * @param estado
     *     the value for the estado property
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
