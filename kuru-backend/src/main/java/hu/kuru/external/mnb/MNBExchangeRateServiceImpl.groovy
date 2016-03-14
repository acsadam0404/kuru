package hu.kuru.external.mnb

import hu.mnb.webservices.GetCurrentExchangeRatesRequestBody
import hu.mnb.webservices.GetCurrentExchangeRatesResponseBody
import hu.mnb.webservices.MNBArfolyamServiceSoap;

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.tempuri.MNBArfolyamServiceSoapImpl

@Service
class MNBExchangeRateServiceImpl implements MNBExchangeRateService{
	private static final Logger logger = LoggerFactory.getLogger(MNBExchangeRateServiceImpl);

	@Override
	List<ExchangeRate> getExchangeRates() throws MNBServiceException {
		String responseXml = null
		try {
			MNBArfolyamServiceSoapImpl s = new MNBArfolyamServiceSoapImpl();
			MNBArfolyamServiceSoap mnbArfolyamServiceSoap12 = s.getCustomBindingMNBArfolyamServiceSoap();
			GetCurrentExchangeRatesResponseBody response =  mnbArfolyamServiceSoap12.getCurrentExchangeRates(new GetCurrentExchangeRatesRequestBody());
			responseXml = response.getGetCurrentExchangeRatesResult().getValue()
		}
		catch (Exception ex) {
			logger.error("MNB webservice hívás nem sikerült!", ex);
			throw new MNBServiceException(ex);
		}
		def ers = processXml(responseXml)
		return ers.findAll() { it.curr == 'USD' || it.curr == 'EUR' || it.curr == 'GBP' }
	}

	/**
	 * sample xml:
	 <MNBCurrentExchangeRates>
	 <Day date="2014-05-20">
	 <Rate curr="AUD" unit="1">206,54</Rate>
	 <Rate curr="BGN" unit="1">156,02</Rate>
	 </Day>
	 </MNBCurrentExchangeRates>
	 * @param xml
	 * @return
	 */
	def processXml(xml) {
		def MNBCurrentExchangeRates = new XmlSlurper().parseText(xml)
		/* ez erősen függ a struktúrától. mivel nincs xsd xmlsurpler a legjobb módszer */
		def ers = MNBCurrentExchangeRates.Day.Rate
		List<ExchangeRate> rates = []
		ers.each {
			rates << new ExchangeRate(value: it.text(), curr : it.@curr.text(), unit : it.@unit.text())
		}
		return rates
	}
}
