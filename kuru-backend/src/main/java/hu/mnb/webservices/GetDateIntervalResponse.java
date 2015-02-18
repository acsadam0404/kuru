
package hu.mnb.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


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
 *         &lt;element name="GetDateIntervalResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getDateIntervalResult"
})
@XmlRootElement(name = "GetDateIntervalResponse")
public class GetDateIntervalResponse
    implements Equals, HashCode
{

    @XmlElement(name = "GetDateIntervalResult")
    protected String getDateIntervalResult;

    /**
     * Gets the value of the getDateIntervalResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetDateIntervalResult() {
        return getDateIntervalResult;
    }

    /**
     * Sets the value of the getDateIntervalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetDateIntervalResult(String value) {
        this.getDateIntervalResult = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theGetDateIntervalResult;
            theGetDateIntervalResult = this.getGetDateIntervalResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getDateIntervalResult", theGetDateIntervalResult), currentHashCode, theGetDateIntervalResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetDateIntervalResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetDateIntervalResponse that = ((GetDateIntervalResponse) object);
        {
            String lhsGetDateIntervalResult;
            lhsGetDateIntervalResult = this.getGetDateIntervalResult();
            String rhsGetDateIntervalResult;
            rhsGetDateIntervalResult = that.getGetDateIntervalResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getDateIntervalResult", lhsGetDateIntervalResult), LocatorUtils.property(thatLocator, "getDateIntervalResult", rhsGetDateIntervalResult), lhsGetDateIntervalResult, rhsGetDateIntervalResult)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

}
