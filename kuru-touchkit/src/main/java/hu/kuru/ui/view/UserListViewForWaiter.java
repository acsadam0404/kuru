package hu.kuru.ui.view;

import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
import hu.kuru.ui.component.SearchField;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

/**
 * Pincér főképernyő (az a tartalom ami bejelentkezéskor a mainviewforwaiter
 * contentje)
 * 
 * @author
 *
 */
public class UserListViewForWaiter extends NavigationView {

	private NavigationManager manager;
	private Table customerTable;
	private Authentication authentication;

	public UserListViewForWaiter(NavigationManager manager) {
		super();
		authentication = new Authentication();
		this.manager = manager;
		this.setSizeFull();
		this.setLeftComponent(createSearchField());
		this.setRightComponent(createLogoutButton());
		this.setContent(buildContent());
		this.refresh();
	}

	/**
	 * Keresőmező felépítését végző függvény
	 * 
	 * @return
	 */
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
				authentication.logout("username");
			}
		});

		return logoutButton;
	}

	/**
	 * Vendégeket tartalmazó táblázat felépítése
	 * 
	 * @return
	 */
	private Component buildContent() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		customerTable = new Table();
		customerTable.setSizeFull();
		customerTable.setWidth("80%");
		Container container = new BeanItemContainer<Customer>(Customer.class);
		customerTable.setContainerDataSource(container);
		customerTable.setVisibleColumns(Customer.NAME, Customer.CODE);

		customerTable.setColumnHeader(Customer.NAME, "Vendég neve");
		customerTable.setColumnHeader(Customer.CODE, "Vendég kódja");
		customerTable.setColumnHeader("buttons", "");

		customerTable.addGeneratedColumn("buttons", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout buttonLayout = new HorizontalLayout();
				buttonLayout.setSpacing(true);

				// cikkek képernyőre navigálást végző gomb
				Button navigateToArticleViewButton = new Button();
				navigateToArticleViewButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO: nem jó itt kivenni eggyel lejebb is ez van
						Customer customer = (Customer) ((BeanItem) source.getItem(itemId)).getBean();
						UserListViewForWaiter.this.manager.navigateTo(new ArticleViewForWaiter(manager, customer));
					}
				});
				navigateToArticleViewButton.setIcon(FontAwesome.BOOK);
				buttonLayout.addComponent(navigateToArticleViewButton);
				buttonLayout.setComponentAlignment(navigateToArticleViewButton, Alignment.MIDDLE_RIGHT);

				// számlák képernyőre navigálást végző ikon
				Button navigateToBillsViewButton = new Button();
				navigateToBillsViewButton.addClickListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						Customer customer = (Customer) ((BeanItem) source.getItem(itemId)).getBean();
						UserListViewForWaiter.this.manager.navigateTo(new BillsViewForWaiter(manager, customer.getId()));
					}
				});
				navigateToBillsViewButton.setIcon(FontAwesome.BRIEFCASE);
				buttonLayout.addComponent(navigateToBillsViewButton);
				buttonLayout.setComponentAlignment(navigateToBillsViewButton, Alignment.MIDDLE_RIGHT);

				return buttonLayout;
			}
		});

		layout.addComponent(customerTable);
		layout.setComponentAlignment(customerTable, Alignment.TOP_CENTER);
		return layout;
	}

	/**
	 * Tartalom betöltése a táblázatba
	 */
	private void refresh() {
		BeanItemContainer<Customer> container = (BeanItemContainer) customerTable.getContainerDataSource();
		container.removeAllItems();
		container.addAll(Customer.findAll());
	}
}
