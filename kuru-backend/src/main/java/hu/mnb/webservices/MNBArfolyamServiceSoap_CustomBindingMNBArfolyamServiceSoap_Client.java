
package hu.mnb.webservices;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */


import org.tempuri.MNBArfolyamServiceSoapImpl;

import javax.xml.namespace.QName;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 3.1.3
 * 2015-09-26T09:22:13.433+02:00
 * Generated source version: 3.1.3
 * 
 */
public final class MNBArfolyamServiceSoap_CustomBindingMNBArfolyamServiceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "MNBArfolyamServiceSoapImpl");

    private MNBArfolyamServiceSoap_CustomBindingMNBArfolyamServiceSoap_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = MNBArfolyamServiceSoapImpl.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        MNBArfolyamServiceSoapImpl ss = new MNBArfolyamServiceSoapImpl(wsdlURL, SERVICE_NAME);
        MNBArfolyamServiceSoap port = ss.getCustomBindingMNBArfolyamServiceSoap();  
        
        {
        System.out.println("Invoking getCurrentExchangeRates...");
        GetCurrentExchangeRatesRequestBody _getCurrentExchangeRates_getCurrentExchangeRates = null;
        try {
            GetCurrentExchangeRatesResponseBody _getCurrentExchangeRates__return = port.getCurrentExchangeRates(_getCurrentExchangeRates_getCurrentExchangeRates);
            System.out.println("getCurrentExchangeRates.result=" + _getCurrentExchangeRates__return);

        } catch (MNBArfolyamServiceSoapGetCurrentExchangeRatesStringFaultFaultMessage e) { 
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetCurrentExchangeRates_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getCurrencyUnits...");
        GetCurrencyUnitsRequestBody _getCurrencyUnits_getCurrencyUnits = null;
        try {
            GetCurrencyUnitsResponseBody _getCurrencyUnits__return = port.getCurrencyUnits(_getCurrencyUnits_getCurrencyUnits);
            System.out.println("getCurrencyUnits.result=" + _getCurrencyUnits__return);

        } catch (MNBArfolyamServiceSoapGetCurrencyUnitsStringFaultFaultMessage e) {
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetCurrencyUnits_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getCurrencies...");
        GetCurrenciesRequestBody _getCurrencies_getCurrencies = null;
        try {
            GetCurrenciesResponseBody _getCurrencies__return = port.getCurrencies(_getCurrencies_getCurrencies);
            System.out.println("getCurrencies.result=" + _getCurrencies__return);

        } catch (MNBArfolyamServiceSoapGetCurrenciesStringFaultFaultMessage e) {
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetCurrencies_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getDateInterval...");
        GetDateIntervalRequestBody _getDateInterval_getDateInterval = null;
        try {
            GetDateIntervalResponseBody _getDateInterval__return = port.getDateInterval(_getDateInterval_getDateInterval);
            System.out.println("getDateInterval.result=" + _getDateInterval__return);

        } catch (MNBArfolyamServiceSoapGetDateIntervalStringFaultFaultMessage e) { 
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetDateInterval_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getInfo...");
        GetInfoRequestBody _getInfo_getInfo = null;
        try {
            GetInfoResponseBody _getInfo__return = port.getInfo(_getInfo_getInfo);
            System.out.println("getInfo.result=" + _getInfo__return);

        } catch (MNBArfolyamServiceSoapGetInfoStringFaultFaultMessage e) {
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetInfo_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getExchangeRates...");
        GetExchangeRatesRequestBody _getExchangeRates_getExchangeRates = null;
        try {
            GetExchangeRatesResponseBody _getExchangeRates__return = port.getExchangeRates(_getExchangeRates_getExchangeRates);
            System.out.println("getExchangeRates.result=" + _getExchangeRates__return);

        } catch (MNBArfolyamServiceSoapGetExchangeRatesStringFaultFaultMessage e) { 
            System.out.println("Expected exception: MNBArfolyamServiceSoap_GetExchangeRates_StringFault_FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
