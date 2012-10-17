
package ch.cern.dod.ws.egroups;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsageCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UsageCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EgroupsOnly"/>
 *     &lt;enumeration value="SecurityMailing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UsageCode", namespace = "https://cra-ws.cern.ch/cra-ws/cra/")
@XmlEnum
public enum UsageCode {

    @XmlEnumValue("EgroupsOnly")
    EGROUPS_ONLY("EgroupsOnly"),
    @XmlEnumValue("SecurityMailing")
    SECURITY_MAILING("SecurityMailing");
    private final String value;

    UsageCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UsageCode fromValue(String v) {
        for (UsageCode c: UsageCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
