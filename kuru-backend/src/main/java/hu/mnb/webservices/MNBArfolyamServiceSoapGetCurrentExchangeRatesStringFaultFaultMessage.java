
package hu.mnb.webservices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.3
 * 2015-09-26T09:22:13.508+02:00
 * Generated source version: 3.1.3
 */

@WebFault(name = "string", targetNamespace = "http://schemas.microsoft.com/2003/10/Serialization/")
public class MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage extends Exception {
    
    private String string;

    public MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage() {
        super();
    }
    
    public MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage(String message) {
        super(message);
    }
    
    public MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage(String message, String string) {
        super(message);
        this.string = string;
    }

    public MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage(String message, String string, Throwable cause) {
        super(message, cause);
        this.string = string;
    }

    public String getFaultInfo() {
        return this.string;
    }
}
