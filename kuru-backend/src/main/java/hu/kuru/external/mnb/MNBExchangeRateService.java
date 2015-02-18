package hu.kuru.external.mnb;

import java.util.List;

public interface MNBExchangeRateService {
	/**
	 * @return Visszaadja a releváns (esetünkben HUF és EUR) devizákhoz tartozó
	 *         középárfolyamokat.
	 */
	List<ExchangeRate> getExchangeRates() throws MNBServiceException;
}
