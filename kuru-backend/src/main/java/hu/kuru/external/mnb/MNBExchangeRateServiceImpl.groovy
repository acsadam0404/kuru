package hu.kuru.external.mnb;

import hu.mnb.webservices.MNBArfolyamService
import hu.mnb.webservices.MNBArfolyamServiceSoap

import org.apache.log4j.Logger
import org.springframework.stereotype.Service

@Service
class MNBExchangeRateServiceImpl implements MNBExchangeRateService{
	private static final Logger logger = Logger.getLogger(MNBExchangeRateServiceImpl);

	@Override
	List<ExchangeRate> getExchangeRates() throws MNBServiceException {
		String responseXml = null
		try {
			MNBArfolyamService s = new MNBArfolyamService();
			MNBArfolyamServiceSoap mnbArfolyamServiceSoap12 = s.getMNBArfolyamServiceSoap12();
			responseXml = mnbArfolyamServiceSoap12.getCurrentExchangeRates();
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
