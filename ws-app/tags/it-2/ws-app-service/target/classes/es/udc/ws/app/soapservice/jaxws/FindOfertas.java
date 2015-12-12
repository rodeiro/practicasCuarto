
package es.udc.ws.app.soapservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findOfertas", namespace = "http://soap.ws.udc.es/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findOfertas", namespace = "http://soap.ws.udc.es/")
public class FindOfertas {

    @XmlElement(name = "keywords", namespace = "")
    private String keywords;

    /**
     * 
     * @return
     *     returns String
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 
     * @param keywords
     *     the value for the keywords property
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
