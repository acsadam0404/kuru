package hu.kuru;

import hu.kuru.vaadin.component.KNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;

public class UIExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(UIExceptionHandler.class);
	private static final String ERROR_MESSAGE = "A folyamat közben hiba adódott!";

	public static void handleException(Throwable e) {
		if (e instanceof IllegalArgumentException) {
			LOG.warn(e.getMessage(), e);
			new KNotification(ERROR_MESSAGE).withDescription(e.getMessage()).showError();
		} else if (e instanceof CommitException || e instanceof InvalidValueException) {
			LOG.warn(e.getMessage(), e);
			new KNotification(ERROR_MESSAGE).withDescription("Ellenőrizze, hogy minden kötelező mezőt helyesen töltött-e ki!").showError();
		} else {
			LOG.warn(e.getMessage(), e);
			new KNotification(ERROR_MESSAGE).showError();
		}
	}

}
