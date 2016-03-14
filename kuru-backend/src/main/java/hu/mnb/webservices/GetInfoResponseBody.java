
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
 * <p>Java class for GetInfoResponseBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInfoResponseBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetInfoResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetInfoResponseBody", propOrder = {
    "getInfoResult"
})
public class GetInfoResponseBody
    implements Equals, HashCode
{

    @XmlElementRef(name = "GetInfoResult", namespace = "http://www.mnb.hu/webservices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getInfoResult;

    /**
     * Gets the value of the getInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetInfoResult() {
        return getInfoResult;
    }

    /**
     * Sets the value of the getInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetInfoResult(JAXBElement<String> value) {
        this.getInfoResult = ((JAXBElement<String> ) value);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            JAXBElement<String> theGetInfoResult;
            theGetInfoResult = this.getGetInfoResult();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "getInfoResult", theGetInfoResult), currentHashCode, theGetInfoResult);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetInfoResponseBody)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetInfoResponseBody that = ((GetInfoResponseBody) object);
        {
            JAXBElement<String> lhsGetInfoResult;
            lhsGetInfoResult = this.getGetInfoResult();
            JAXBElement<String> rhsGetInfoResult;
            rhsGetInfoResult = that.getGetInfoResult();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "getInfoResult", lhsGetInfoResult), LocatorUtils.property(thatLocator, "getInfoResult", rhsGetInfoResult), lhsGetInfoResult, rhsGetInfoResult)) {
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
