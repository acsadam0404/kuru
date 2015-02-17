
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
 *         &lt;element name="GetExchangeRatesResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getExchangeRatesResult"
})
@XmlRootElement(name = "GetExchangeRatesResponse")
public class GetExchangeRatesResponse
    implements Equals, HashCode
{

    @XmlElement(name = "GetExchangeRatesResult")
    protected String getExchangeRatesResult;

    /**
     * Gets the value of the getExchangeRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetExchangeRatesResult() {
        return getExchangeRatesResult;
    }

    /**
     * Sets the value of the getExchangeRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetExchangeRatesResult(String value) {
        this.getExchangeRatesResult = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theGetExchangeRatesResult;
            theGetExchangeRatesResult = this.getGetExchangeRatesResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getExchangeRatesResult", theGetExchangeRatesResult), currentHashCode, theGetExchangeRatesResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetExchangeRatesResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetExchangeRatesResponse that = ((GetExchangeRatesResponse) object);
        {
            String lhsGetExchangeRatesResult;
            lhsGetExchangeRatesResult = this.getGetExchangeRatesResult();
            String rhsGetExchangeRatesResult;
            rhsGetExchangeRatesResult = that.getGetExchangeRatesResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getExchangeRatesResult", lhsGetExchangeRatesResult), LocatorUtils.property(thatLocator, "getExchangeRatesResult", rhsGetExchangeRatesResult), lhsGetExchangeRatesResult, rhsGetExchangeRatesResult)) {
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
