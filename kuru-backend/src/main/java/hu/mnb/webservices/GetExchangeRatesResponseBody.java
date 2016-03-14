
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
 * <p>Java class for GetExchangeRatesResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetExchangeRatesResponseBody">
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
@XmlType(name = "GetExchangeRatesResponseBody", propOrder = {
    "getExchangeRatesResult"
})
public class GetExchangeRatesResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetExchangeRatesResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getExchangeRatesResult;

    /**
     * Gets the value of the getExchangeRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetExchangeRatesResult() {
        return getExchangeRatesResult;
    }

    /**
     * Sets the value of the getExchangeRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetExchangeRatesResult(JAXBElement<String> value) {
        this.getExchangeRatesResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetExchangeRatesResult;
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
        if (!(object instanceof GetExchangeRatesResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetExchangeRatesResponseBody that = ((GetExchangeRatesResponseBody) object);
        {
            JAXBElement<String> lhsGetExchangeRatesResult;
            lhsGetExchangeRatesResult = this.getGetExchangeRatesResult();
            JAXBElement<String> rhsGetExchangeRatesResult;
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
