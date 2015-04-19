/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hu.si.vaadin.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBooleanConverter;

/**
 * 
 * @author Thomas Letsch (contact@thomas-letsch.de)
 * 
 */
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {
	private static final Logger logger = LoggerFactory.getLogger(StringToBooleanConverter.class);
	private static final long serialVersionUID = 1L;

	private String formatPattern;

	public StringToBigDecimalConverter(String formatPattern) {
		this.formatPattern = formatPattern;
	}

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws Converter.ConversionException {
		if (StringUtils.isBlank(value)) {
			return null;
		}

		NumberFormat nf = NumberFormat.getInstance(locale);

		BigDecimal result = null;
		try {
			result = new BigDecimal(nf.parse(value).longValue());
		} catch (ParseException e) {
			logger.trace(value, e);
		}
		return result;
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws Converter.ConversionException {
		if (value == null) {
			return null;
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}
		NumberFormat nf = null;
		if (formatPattern != null) {
			nf = new DecimalFormat(formatPattern);
		} else {
			nf = NumberFormat.getInstance(locale);
		}
		return nf.format(value.doubleValue());
	}

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

	public void setFormatPattern(String formatPattern) {
		this.formatPattern = formatPattern;
	}
	
	private static final StringToBigDecimalConverter instance = new StringToBigDecimalConverter();

	private StringToBigDecimalConverter() {
		/* singleton */
	}

	public static StringToBigDecimalConverter getInstance() {
		return instance;
	}

	public String convertToPresentation(BigDecimal result) {
		return convertToPresentation(result, String.class, null);
	}

}