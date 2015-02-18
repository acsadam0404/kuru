
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
 *         &lt;element name="GetCurrentExchangeRatesResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getCurrentExchangeRatesResult"
})
@XmlRootElement(name = "GetCurrentExchangeRatesResponse")
public class GetCurrentExchangeRatesResponse
    implements Equals, HashCode
{

    @XmlElement(name = "GetCurrentExchangeRatesResult")
    protected String getCurrentExchangeRatesResult;

    /**
     * Gets the value of the getCurrentExchangeRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCurrentExchangeRatesResult() {
        return getCurrentExchangeRatesResult;
    }

    /**
     * Sets the value of the getCurrentExchangeRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCurrentExchangeRatesResult(String value) {
        this.getCurrentExchangeRatesResult = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theGetCurrentExchangeRatesResult;
            theGetCurrentExchangeRatesResult = this.getGetCurrentExchangeRatesResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getCurrentExchangeRatesResult", theGetCurrentExchangeRatesResult), currentHashCode, theGetCurrentExchangeRatesResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetCurrentExchangeRatesResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrentExchangeRatesResponse that = ((GetCurrentExchangeRatesResponse) object);
        {
            String lhsGetCurrentExchangeRatesResult;
            lhsGetCurrentExchangeRatesResult = this.getGetCurrentExchangeRatesResult();
            String rhsGetCurrentExchangeRatesResult;
            rhsGetCurrentExchangeRatesResult = that.getGetCurrentExchangeRatesResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getCurrentExchangeRatesResult", lhsGetCurrentExchangeRatesResult), LocatorUtils.property(thatLocator, "getCurrentExchangeRatesResult", rhsGetCurrentExchangeRatesResult), lhsGetCurrentExchangeRatesResult, rhsGetCurrentExchangeRatesResult)) {
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
