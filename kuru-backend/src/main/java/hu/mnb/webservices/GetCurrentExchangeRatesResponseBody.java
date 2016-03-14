
package hu.mnb.webservices;

import org.jvnet.jaxb2_commons.lang.*;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetCurrentExchangeRatesResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCurrentExchangeRatesResponseBody">
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
@XmlType(name = "GetCurrentExchangeRatesResponseBody", propOrder = {
    "getCurrentExchangeRatesResult"
})
public class GetCurrentExchangeRatesResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetCurrentExchangeRatesResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getCurrentExchangeRatesResult;

    /**
     * Gets the value of the getCurrentExchangeRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetCurrentExchangeRatesResult() {
        return getCurrentExchangeRatesResult;
    }

    /**
     * Sets the value of the getCurrentExchangeRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetCurrentExchangeRatesResult(JAXBElement<String> value) {
        this.getCurrentExchangeRatesResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetCurrentExchangeRatesResult;
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
        if (!(object instanceof GetCurrentExchangeRatesResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrentExchangeRatesResponseBody that = ((GetCurrentExchangeRatesResponseBody) object);
        {
            JAXBElement<String> lhsGetCurrentExchangeRatesResult;
            lhsGetCurrentExchangeRatesResult = this.getGetCurrentExchangeRatesResult();
            JAXBElement<String> rhsGetCurrentExchangeRatesResult;
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
