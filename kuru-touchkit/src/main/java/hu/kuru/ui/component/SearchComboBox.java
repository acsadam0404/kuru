package hu.kuru.ui.component;

import hu.kuru.ServiceLocator;
import hu.kuru.customer.Customer;
import hu.kuru.customer.CustomerRepo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.vaadin.data.util.BeanItemContainer;
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