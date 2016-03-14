
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
 * <p>Java class for GetDateIntervalResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetDateIntervalResponseBody">
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
@XmlType(name = "GetDateIntervalResponseBody", propOrder = {
    "getDateIntervalResult"
})
public class GetDateIntervalResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetDateIntervalResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getDateIntervalResult;

    /**
     * Gets the value of the getDateIntervalResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetDateIntervalResult() {
        return getDateIntervalResult;
    }

    /**
     * Sets the value of the getDateIntervalResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetDateIntervalResult(JAXBElement<String> value) {
        this.getDateIntervalResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetDateIntervalResult;
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
        if (!(object instanceof GetDateIntervalResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetDateIntervalResponseBody that = ((GetDateIntervalResponseBody) object);
        {
            JAXBElement<String> lhsGetDateIntervalResult;
            lhsGetDateIntervalResult = this.getGetDateIntervalResult();
            JAXBElement<String> rhsGetDateIntervalResult;
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
