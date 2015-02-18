
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
@XmlType(name = "", propOrder = {
    "getCurrenciesResult"
})
@XmlRootElement(name = "GetCurrenciesResponse")
public class GetCurrenciesResponse
    implements Equals, HashCode
{

    @XmlElement(name = "GetCurrenciesResult")
    protected String getCurrenciesResult;

    /**
     * Gets the value of the getCurrenciesResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetCurrenciesResult() {
        return getCurrenciesResult;
    }

    /**
     * Sets the value of the getCurrenciesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetCurrenciesResult(String value) {
        this.getCurrenciesResult = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theGetCurrenciesResult;
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
        if (!(object instanceof GetCurrenciesResponse)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrenciesResponse that = ((GetCurrenciesResponse) object);
        {
            String lhsGetCurrenciesResult;
            lhsGetCurrenciesResult = this.getGetCurrenciesResult();
            String rhsGetCurrenciesResult;
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
