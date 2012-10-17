
package ch.cern.dod.ws.authentication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetRecursiveGroupsForUserResult" type="{https://winservices-soap.web.cern.ch/winservices-soap/Generic/Authentication.asmx}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getRecursiveGroupsForUserResult"
})
@XmlRootElement(name = "GetRecursiveGroupsForUserResponse")
public class GetRecursiveGroupsForUserResponse {

    @XmlElement(name = "GetRecursiveGroupsForUserResult")
    protected ArrayOfString getRecursiveGroupsForUserResult;

    /**
     * Gets the value of the getRecursiveGroupsForUserResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getGetRecursiveGroupsForUserResult() {
        return getRecursiveGroupsForUserResult;
    }

    /**
     * Sets the value of the getRecursiveGroupsForUserResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setGetRecursiveGroupsForUserResult(ArrayOfString value) {
        this.getRecursiveGroupsForUserResult = value;
    }

}
