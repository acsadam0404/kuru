package hu.kuru.ui.view;

import java.util.HashMap;
import java.util.Map;

import hu.kuru.article.Article;
import hu.kuru.customer.Customer;
import hu.kuru.security.Authentication;
import hu.kuru.ui.component.ShoppingCart;
import hu.kuru.ui.layout.ArticleLayout;
import hu.kuru.util.Pair;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Pincér főképernyő (az a tartalom ami bejelentkezéskor a mainviewforwaiter contentje)
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
		this.setRightComponent(createLogoutButton());
		this.setContent(buildContent());
		this.refresh();
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
		customerTable = new Table();
		customerTable.setSizeFull();
		Container container = new BeanItemContainer<Customer>(Customer.class);
		customerTable.setContainerDataSource(container);
		customerTable.setVisibleColumns(Customer.NAME,Customer.CODE);
		
		customerTable.setColumnHeader(Customer.NAME, "Vendég neve");
		customerTable.setColumnHeader(Customer.CODE, "Vendég kódja");
		customerTable.setColumnHeader("buttons", "");
		
		customerTable.addGeneratedColumn("buttons", new Table.ColumnGenerator() {
			
			@Override
			public Object generateCell(final Table source, final Object itemId, Object columnId) {
				HorizontalLayout buttonLayout = new HorizontalLayout();
				buttonLayout.setSpacing(true);

				//cikkek képernyőre navigálást végző gomb
				Button navigateToArticleViewButton = new Button();
				navigateToArticleViewButton.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						UserListViewForWaiter.this.manager.navigateTo(new ArticleViewForWaiter(manager));
					}
				});
				navigateToArticleViewButton.setIcon(FontAwesome.BOOK);
				buttonLayout.addComponent(navigateToArticleViewButton);
				buttonLayout.setComponentAlignment(navigateToArticleViewButton, Alignment.MIDDLE_RIGHT);
				
				//számlák képernyőre navigálást végző ikon
				Button navigateToBillsViewButton = new Button();
				navigateToBillsViewButton.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						Customer customer = (Customer) ((BeanItem) source.getItem(itemId)).getBean();
						UserListViewForWaiter.this.manager.navigateTo(new BillsViewForWaiter(manager,customer.getId()));
					}
				});
				navigateToBillsViewButton.setIcon(FontAwesome.BRIEFCASE);
				buttonLayout.addComponent(navigateToBillsViewButton);
				buttonLayout.setComponentAlignment(navigateToBillsViewButton, Alignment.MIDDLE_RIGHT);
				
				return buttonLayout;
			}
		});
		
		layout.addComponent(customerTable);
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
