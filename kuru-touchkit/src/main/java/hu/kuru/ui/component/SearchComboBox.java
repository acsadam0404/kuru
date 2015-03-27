package hu.kuru.ui.component;

import hu.kuru.customer.Customer;

import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Kereső mező
 * 
 * @author 
 */
public class SearchComboBox extends ComboBox {
	
	public SearchComboBox(List<Customer> customerList) {
		setImmediate(true);
		setCaption(null);
		setWidth("300px");
		setIcon(FontAwesome.SEARCH);
		setItemCaptionPropertyId("name");
		addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
		addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
	}


}