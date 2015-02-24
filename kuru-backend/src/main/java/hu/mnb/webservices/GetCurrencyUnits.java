
package hu.mnb.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="currencyNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "currencyNames"
})
@XmlRootElement(name = "GetCurrencyUnits")
public class GetCurrencyUnits
    implements Equals, HashCode
{

    protected String currencyNames;

    /**
     * Gets the value of the currencyNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyNames() {
        return currencyNames;
    }

    /**
     * Sets the value of the currencyNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyNames(String value) {
        this.currencyNames = value;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theCurrencyNames;
            theCurrencyNames = this.getCurrencyNames();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "currencyNames", theCurrencyNames), currentHashCode, theCurrencyNames);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof GetCurrencyUnits)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final GetCurrencyUnits that = ((GetCurrencyUnits) object);
        {
            String lhsCurrencyNames;
            lhsCurrencyNames = this.getCurrencyNames();
            String rhsCurrencyNames;
            rhsCurrencyNames = that.getCurrencyNames();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "currencyNames", lhsCurrencyNames), LocatorUtils.property(thatLocator, "currencyNames", rhsCurrencyNames), lhsCurrencyNames, rhsCurrencyNames)) {
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