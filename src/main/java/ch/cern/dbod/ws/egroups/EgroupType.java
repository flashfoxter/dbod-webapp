/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ws.egroups;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EgroupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EgroupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Aliases" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Type" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}EgroupTypeCode"/>
 *         &lt;element name="Status" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}StatusCode" minOccurs="0"/>
 *         &lt;element name="BlockingReason" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}BlockingReasonCode" minOccurs="0"/>
 *         &lt;element name="Usage" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}UsageCode"/>
 *         &lt;element name="Topic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExpiryDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Owner" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}UserType"/>
 *         &lt;element name="AdministratorEgroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Privacy" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}PrivacyType"/>
 *         &lt;element name="Selfsubscription" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}SelfsubscriptionType"/>
 *         &lt;element name="SelfsubscriptionEgroups" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}SelfsubscriptionEgroupType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Members" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}MemberType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EmailProperties" type="{https://foundservices.cern.ch/ws/egroups/v1/schema/EgroupsServicesSchema}EmailPropertiesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EgroupType", propOrder = {
    "name",
    "aliases",
    "id",
    "type",
    "status",
    "blockingReason",
    "usage",
    "topic",
    "description",
    "comments",
    "expiryDate",
    "owner",
    "administratorEgroup",
    "privacy",
    "selfsubscription",
    "selfsubscriptionEgroups",
    "members",
    "emailProperties"
})
public class EgroupType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Aliases")
    protected List<String> aliases;
    @XmlElement(name = "ID")
    protected Long id;
    @XmlElement(name = "Type", required = true)
    protected EgroupTypeCode type;
    @XmlElement(name = "Status")
    protected StatusCode status;
    @XmlElement(name = "BlockingReason")
    protected BlockingReasonCode blockingReason;
    @XmlElement(name = "Usage", required = true)
    protected UsageCode usage;
    @XmlElement(name = "Topic")
    protected String topic;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "ExpiryDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar expiryDate;
    @XmlElement(name = "Owner", required = true)
    protected UserType owner;
    @XmlElement(name = "AdministratorEgroup")
    protected String administratorEgroup;
    @XmlElement(name = "Privacy", required = true)
    protected PrivacyType privacy;
    @XmlElement(name = "Selfsubscription", required = true)
    protected SelfsubscriptionType selfsubscription;
    @XmlElement(name = "SelfsubscriptionEgroups")
    protected List<SelfsubscriptionEgroupType> selfsubscriptionEgroups;
    @XmlElement(name = "Members")
    protected List<MemberType> members;
    @XmlElement(name = "EmailProperties")
    protected EmailPropertiesType emailProperties;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the aliases property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aliases property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAliases().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAliases() {
        if (aliases == null) {
            aliases = new ArrayList<String>();
        }
        return this.aliases;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setID(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link EgroupTypeCode }
     *     
     */
    public EgroupTypeCode getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link EgroupTypeCode }
     *     
     */
    public void setType(EgroupTypeCode value) {
        this.type = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusCode }
     *     
     */
    public StatusCode getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCode }
     *     
     */
    public void setStatus(StatusCode value) {
        this.status = value;
    }

    /**
     * Gets the value of the blockingReason property.
     * 
     * @return
     *     possible object is
     *     {@link BlockingReasonCode }
     *     
     */
    public BlockingReasonCode getBlockingReason() {
        return blockingReason;
    }

    /**
     * Sets the value of the blockingReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlockingReasonCode }
     *     
     */
    public void setBlockingReason(BlockingReasonCode value) {
        this.blockingReason = value;
    }

    /**
     * Gets the value of the usage property.
     * 
     * @return
     *     possible object is
     *     {@link UsageCode }
     *     
     */
    public UsageCode getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsageCode }
     *     
     */
    public void setUsage(UsageCode value) {
        this.usage = value;
    }

    /**
     * Gets the value of the topic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Sets the value of the topic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopic(String value) {
        this.topic = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpiryDate(XMLGregorianCalendar value) {
        this.expiryDate = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link UserType }
     *     
     */
    public UserType getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserType }
     *     
     */
    public void setOwner(UserType value) {
        this.owner = value;
    }

    /**
     * Gets the value of the administratorEgroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdministratorEgroup() {
        return administratorEgroup;
    }

    /**
     * Sets the value of the administratorEgroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdministratorEgroup(String value) {
        this.administratorEgroup = value;
    }

    /**
     * Gets the value of the privacy property.
     * 
     * @return
     *     possible object is
     *     {@link PrivacyType }
     *     
     */
    public PrivacyType getPrivacy() {
        return privacy;
    }

    /**
     * Sets the value of the privacy property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivacyType }
     *     
     */
    public void setPrivacy(PrivacyType value) {
        this.privacy = value;
    }

    /**
     * Gets the value of the selfsubscription property.
     * 
     * @return
     *     possible object is
     *     {@link SelfsubscriptionType }
     *     
     */
    public SelfsubscriptionType getSelfsubscription() {
        return selfsubscription;
    }

    /**
     * Sets the value of the selfsubscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link SelfsubscriptionType }
     *     
     */
    public void setSelfsubscription(SelfsubscriptionType value) {
        this.selfsubscription = value;
    }

    /**
     * Gets the value of the selfsubscriptionEgroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selfsubscriptionEgroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelfsubscriptionEgroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelfsubscriptionEgroupType }
     * 
     * 
     */
    public List<SelfsubscriptionEgroupType> getSelfsubscriptionEgroups() {
        if (selfsubscriptionEgroups == null) {
            selfsubscriptionEgroups = new ArrayList<SelfsubscriptionEgroupType>();
        }
        return this.selfsubscriptionEgroups;
    }

    /**
     * Gets the value of the members property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the members property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMembers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MemberType }
     * 
     * 
     */
    public List<MemberType> getMembers() {
        if (members == null) {
            members = new ArrayList<MemberType>();
        }
        return this.members;
    }

    /**
     * Gets the value of the emailProperties property.
     * 
     * @return
     *     possible object is
     *     {@link EmailPropertiesType }
     *     
     */
    public EmailPropertiesType getEmailProperties() {
        return emailProperties;
    }

    /**
     * Sets the value of the emailProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailPropertiesType }
     *     
     */
    public void setEmailProperties(EmailPropertiesType value) {
        this.emailProperties = value;
    }

}
