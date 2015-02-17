
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
 *         &lt;element name="GetCurrencyUnitsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getCurrencyUnitsResult"
})
@XmlRootElement(name = "GetCurrencyUnitsResponse")
public class GetCurrencyUnitsResponse
    implements Equals, HashCode
{

    @XmlElement(name = "GetCurrencyUnitsResult")
    protected String getCurrencyUnitsResult;

    /**
     * Gets the value of the getCurrencyUnitsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCurrencyUnitsResult() {
        return getCurrencyUnitsResult;
    }

    /**
     * Sets the value of the getCurrencyUnitsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCurrencyUnitsResult(String value) {
        this.getCurrencyUnitsResult = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theGetCurrencyUnitsResult;
            theGetCurrencyUnitsResult = this.getGetCurrencyUnitsResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getCurrencyUnitsResult", theGetCurrencyUnitsResult), currentHashCode, theGetCurrencyUnitsResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetCurrencyUnitsResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrencyUnitsResponse that = ((GetCurrencyUnitsResponse) object);
        {
            String lhsGetCurrencyUnitsResult;
            lhsGetCurrencyUnitsResult = this.getGetCurrencyUnitsResult();
            String rhsGetCurrencyUnitsResult;
            rhsGetCurrencyUnitsResult = that.getGetCurrencyUnitsResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getCurrencyUnitsResult", lhsGetCurrencyUnitsResult), LocatorUtils.property(thatLocator, "getCurrencyUnitsResult", rhsGetCurrencyUnitsResult), lhsGetCurrencyUnitsResult, rhsGetCurrencyUnitsResult)) {
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
