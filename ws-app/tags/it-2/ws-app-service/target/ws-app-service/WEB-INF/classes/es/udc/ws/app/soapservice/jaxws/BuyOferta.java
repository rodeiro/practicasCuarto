
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "buyOferta", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buyOferta", namespace = "http://soap.ws.udc.es/", propOrder = {
    "ofertaId",
    "userId",
    "creditCardNumber"
})
public class BuyOferta {

    @XmlElement(name = "ofertaId", namespace = "")
    private Long ofertaId;
    @XmlElement(name = "userId", namespace = "")
    private String userId;
    @XmlElement(name = "creditCardNumber", namespace = "")
    private String creditCardNumber;

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
     *     returns String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 
     * @param userId
     *     the value for the userId property
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    /**
     * 
     * @param creditCardNumber
     *     the value for the creditCardNumber property
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

}
