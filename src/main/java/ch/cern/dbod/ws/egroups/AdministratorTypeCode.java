/*
 * Copyright (C) 2015, CERN
 * This software is distributed under the terms of the GNU General Public
 * Licence version 3 (GPL Version 3), copied verbatim in the file "LICENSE".
 * In applying this license, CERN does not waive the privileges and immunities
 * granted to it by virtue of its status as Intergovernmental Organization
 * or submit itself to any jurisdiction.
 */

package ch.cern.dbod.ws.egroups;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdministratorTypeCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdministratorTypeCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="StaticEgroup"/>
 *     &lt;enumeration value="DynamicEgroup"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdministratorTypeCode")
@XmlEnum
public enum AdministratorTypeCode {

    @XmlEnumValue("StaticEgroup")
    STATIC_EGROUP("StaticEgroup"),
    @XmlEnumValue("DynamicEgroup")
    DYNAMIC_EGROUP("DynamicEgroup");
    private final String value;

    AdministratorTypeCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AdministratorTypeCode fromValue(String v) {
        for (AdministratorTypeCode c: AdministratorTypeCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
