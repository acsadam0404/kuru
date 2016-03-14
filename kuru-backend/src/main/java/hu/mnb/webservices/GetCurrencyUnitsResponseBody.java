
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
 * <p>Java class for GetCurrencyUnitsResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCurrencyUnitsResponseBody">
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
@XmlType(name = "GetCurrencyUnitsResponseBody", propOrder = {
    "getCurrencyUnitsResult"
})
public class GetCurrencyUnitsResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetCurrencyUnitsResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getCurrencyUnitsResult;

    /**
     * Gets the value of the getCurrencyUnitsResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetCurrencyUnitsResult() {
        return getCurrencyUnitsResult;
    }

    /**
     * Sets the value of the getCurrencyUnitsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetCurrencyUnitsResult(JAXBElement<String> value) {
        this.getCurrencyUnitsResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetCurrencyUnitsResult;
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
        if (!(object instanceof GetCurrencyUnitsResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrencyUnitsResponseBody that = ((GetCurrencyUnitsResponseBody) object);
        {
            JAXBElement<String> lhsGetCurrencyUnitsResult;
            lhsGetCurrencyUnitsResult = this.getGetCurrencyUnitsResult();
            JAXBElement<String> rhsGetCurrencyUnitsResult;
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
