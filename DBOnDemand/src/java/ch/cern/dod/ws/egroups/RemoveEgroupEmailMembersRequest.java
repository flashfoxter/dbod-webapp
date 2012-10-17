
package ch.cern.dod.ws.egroups;

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
 *         &lt;element name="p_niceUserid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="p_password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="p_egroupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="p_members" type="{https://cra-ws.cern.ch/cra-ws/cra/}EmailMembersType"/>
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
    "pNiceUserid",
    "pPassword",
    "pEgroupName",
    "pMembers"
})
@XmlRootElement(name = "removeEgroupEmailMembersRequest")
public class RemoveEgroupEmailMembersRequest {

    @XmlElement(name = "p_niceUserid", required = true)
    protected String pNiceUserid;
    @XmlElement(name = "p_password", required = true)
    protected String pPassword;
    @XmlElement(name = "p_egroupName", required = true)
    protected String pEgroupName;
    @XmlElement(name = "p_members", required = true)
    protected EmailMembersType pMembers;

    /**
     * Gets the value of the pNiceUserid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPNiceUserid() {
        return pNiceUserid;
    }

    /**
     * Sets the value of the pNiceUserid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPNiceUserid(String value) {
        this.pNiceUserid = value;
    }

    /**
     * Gets the value of the pPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPPassword() {
        return pPassword;
    }

    /**
     * Sets the value of the pPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPPassword(String value) {
        this.pPassword = value;
    }

    /**
     * Gets the value of the pEgroupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPEgroupName() {
        return pEgroupName;
    }

    /**
     * Sets the value of the pEgroupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPEgroupName(String value) {
        this.pEgroupName = value;
    }

    /**
     * Gets the value of the pMembers property.
     * 
     * @return
     *     possible object is
     *     {@link EmailMembersType }
     *     
     */
    public EmailMembersType getPMembers() {
        return pMembers;
    }

    /**
     * Sets the value of the pMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailMembersType }
     *     
     */
    public void setPMembers(EmailMembersType value) {
        this.pMembers = value;
    }

}
