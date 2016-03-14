
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
 * <p>Java class for GetCurrenciesResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCurrenciesResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetCurrenciesResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCurrenciesResponseBody", propOrder = {
    "getCurrenciesResult"
})
public class GetCurrenciesResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetCurrenciesResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getCurrenciesResult;

    /**
     * Gets the value of the getCurrenciesResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetCurrenciesResult() {
        return getCurrenciesResult;
    }

    /**
     * Sets the value of the getCurrenciesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetCurrenciesResult(JAXBElement<String> value) {
        this.getCurrenciesResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetCurrenciesResult;
            theGetCurrenciesResult = this.getGetCurrenciesResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getCurrenciesResult", theGetCurrenciesResult), currentHashCode, theGetCurrenciesResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetCurrenciesResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrenciesResponseBody that = ((GetCurrenciesResponseBody) object);
        {
            JAXBElement<String> lhsGetCurrenciesResult;
            lhsGetCurrenciesResult = this.getGetCurrenciesResult();
            JAXBElement<String> rhsGetCurrenciesResult;
            rhsGetCurrenciesResult = that.getGetCurrenciesResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getCurrenciesResult", lhsGetCurrenciesResult), LocatorUtils.property(thatLocator, "getCurrenciesResult", rhsGetCurrenciesResult), lhsGetCurrenciesResult, rhsGetCurrenciesResult)) {
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
