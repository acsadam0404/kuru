package hu.kuru.ui.view;

import hu.kuru.KuruUI;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
import hu.kuru.ui.component.SearchField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Pincér főképernyő (az a tartalom ami bejelentkezéskor a mainviewforwaiter
 * contentje)
 *
 */
public class UserListForWaiter extends CustomComponent {

	public UserListForWaiter() {
		setSizeFull();
		setCompositionRoot(build());
		refresh();
	}

	public static final String NAME = "UserListViewForWaiter";
	private Table customerTable;

	private Component createSearchField() {
		SearchField searchField = new SearchField();
		searchField.addTextChangeListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				// TODO: sokkal szebben kell ennél !!!
				List<Customer> customerList = Customer.findAll();
				List<Customer> newCustomerList = new ArrayList<Customer>();
				for (Customer customer : customerList) {
					if (customer.getName().toLowerCase().contains(event.getText().toLowerCase())) {
						newCustomerList.add(customer);
					}
				}
				BeanItemContainer<Customer> container = (BeanItemContainer) customerTable.getContainerDataSource();
				container.removeAllItems();
				container.addAll(newCustomerList);

			}
		});
		return searchField;
	}

	/**
	 * Logout gomb felépítését végző függvény
	 * 
	 * @return
	 */
	private Component createLogoutButton() {
		Button logoutButton = new Button("Kijelentkezés");

		logoutButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Authentication.logout("username");
			}
		});

		return logoutButton;
	}

	/**
	 * Vendégeket tartalmazó táblázat felépítése
	 * 
	 * @return
	 */
	private Component build() {
		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();

		HorizontalLayout actions = new HorizontalLayout();
		actions.addComponent(createSearchField());
		actions.addComponent(createLogoutButton());

		customerTable = new Table();
		customerTable.setSizeFull();
		customerTable.setPageLength(0);
		Container container = new BeanItemContainer<Customer>(Customer.class);
		customerTable.setContainerDataSource(container);
		customerTable.setVisibleColumns("name", "code");

		customerTable.setColumnHeader("name", "Vendég neve");
		customerTable.setColumnHeader("code", "Vendég kódja");
		customerTable.setColumnHeader("buttons", "");

		customerTable.addGeneratedColumn("buttons", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout buttonLayout = new HorizontalLayout();
				buttonLayout.setSpacing(true);

				// cikkek képernyőre navigálást végző gomb
				Button articlesButton = new Button();
				articlesButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO: nem jó itt kivenni eggyel lejebb is ez van
						Customer customer = (Customer) ((BeanItem) source.getItem(itemId)).getBean();
						Map<String, Object> params = new HashMap<>();
						params.put("code", customer.getCode());
						KuruUI.getCurrent().getNavigator().navigateTo(ArticleViewForWaiter.NAME, params);
					}
				});
				articlesButton.setIcon(FontAwesome.BOOK);
				buttonLayout.addComponent(articlesButton);
				buttonLayout.setComponentAlignment(articlesButton, Alignment.MIDDLE_RIGHT);

				// számlák képernyőre navigálást végző ikon
				Button billsButton = new Button();
				billsButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						Customer customer = (Customer) ((BeanItem) source.getItem(itemId)).getBean();
						Map<String, Object> params = new HashMap<>();
						params.put("code", customer.getCode());
						KuruUI.getCurrent().getNavigator().navigateTo(BillsViewForWaiter.NAME, params);
					}
				});
				billsButton.setIcon(FontAwesome.BRIEFCASE);
				buttonLayout.addComponent(billsButton);
				buttonLayout.setComponentAlignment(billsButton, Alignment.MIDDLE_RIGHT);

				return buttonLayout;
			}
		});

		root.addComponent(customerTable);
		return root;
	}

	private void refresh() {
		BeanItemContainer<Customer> container = (BeanItemContainer) customerTable.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Customer.findAll());
	}

}
