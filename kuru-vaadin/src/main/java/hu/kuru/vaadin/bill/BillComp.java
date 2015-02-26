package hu.kuru.vaadin.bill;

import hu.kuru.ServiceLocator;
import hu.kuru.UIExceptionHandler;
import hu.kuru.bill.Bill;
import hu.kuru.bill.BillRepo;
import hu.kuru.customer.Customer;
import hu.kuru.customer.CustomerRepo;
import hu.kuru.customer.CustomerService;
import hu.kuru.eventbus.AddBillEvent;
import hu.kuru.eventbus.AddCustomerEvent;
import hu.kuru.eventbus.BillClosedEvent;
import hu.kuru.eventbus.ItemAddedEvent;
import hu.kuru.vaadin.component.KWindow;
import hu.kuru.vaadin.customer.CustomerMaintComp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.alump.masonry.MasonryLayout;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BillComp extends CustomComponent {

	private MasonryLayout billsLayout;
	private SearchComboBox searchCombo;
	private Button addBillBtn;
	private ModifyButton addCustomerBtn;
	private ModifyButton modifyBtn;
	private DeleteButton deleteBtn;

	private Map<Customer, List<BillBox>> customerMap;
	private List<BillBox> openBills;

	public BillComp() {
		setSizeFull();
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);
		List<Customer> customerList = ServiceLocator.getBean(CustomerRepo.class).findAll(new Sort(Direction.ASC, "name"));
		main.addComponent(buildHeader(customerList));
		Component billsLayout = buildBillsLayout(customerList);
		main.addComponent(billsLayout);
		main.setExpandRatio(billsLayout, 1f);
		Responsive.makeResponsive(main);
		return main;
	}

	private Component buildBillsLayout(List<Customer> customerList) {
		billsLayout = new MasonryLayout();
		billsLayout.setSizeFull();
		customerMap = new HashMap<>();
		openBills = new ArrayList<BillBox>();
		for (Customer customer : customerList) {
			addCustomerToLayout(customer, true);
		}
		reBuildBillLayout(null);

		Panel billsPanel = new Panel(billsLayout);
		billsPanel.setSizeFull();
		billsPanel.setStyleName(ValoTheme.PANEL_BORDERLESS);
		return billsPanel;
	}

	private Component buildHeader(List<Customer> customerList) {
		HorizontalLayout header = new HorizontalLayout();
		header.setSizeUndefined();
		header.setWidth("100%");
		header.setSpacing(true);

		addBillBtn = new AddBillButton();
		addCustomerBtn = new ModifyButton("Új ügyfél", true);
		modifyBtn = new ModifyButton("Ügyfél módosítás", false);
		deleteBtn = new DeleteButton("Ügyfél törlés");
		searchCombo = new SearchComboBox(customerList);

		addBillBtn.setEnabled(false);
		modifyBtn.setEnabled(false);
		deleteBtn.setEnabled(false);

		header.addComponent(searchCombo);
		header.addComponent(addBillBtn);
		header.addComponent(addCustomerBtn);
		header.addComponent(modifyBtn);
		header.addComponent(deleteBtn);
		header.setExpandRatio(searchCombo, 1f);

		return header;
	}

	private void addCustomerToLayout(Customer customer, boolean isFirstLoading) {
		List<Bill> billList = ServiceLocator.getBean(BillRepo.class).findByCustomer(customer.getId());
		List<BillBox> components = new ArrayList<>();
		if (billList != null) {
			Collections.sort(billList, new BillComparator());
			for (Bill bill : billList) {
				BillBox box = BillBox.buildBillBox(bill);
				if (isFirstLoading && bill.getCloseDate() == null) {
					openBills.add(box);
				}
				components.add(box);
			}
		}
		customerMap.put(customer, components);
	}

	private void removeBoxFromList(List<BillBox> list, Bill bill) {
		for (BillBox box : list) {
			if (box.getCurrentBill().getId().equals(bill.getId())) {
				list.remove(box);
				break;
			}
		}
		list.add(BillBox.buildBillBox(bill));
	}

	@Subscribe
	public void onItemAdded(ItemAddedEvent event) {
		Bill bill = ServiceLocator.getBean(BillRepo.class).findById(event.getBillId());
		Customer customer = bill.getCustomer();
		removeBoxFromList(customerMap.get(customer), bill);
		removeBoxFromList(openBills, bill);
		Collections.sort(customerMap.get(customer), new BillBoxComparator());
		Collections.sort(openBills, new BillBoxComparator());
		reBuildBillLayout((Customer) (searchCombo.getValue() != null ? searchCombo.getValue() : null));
	}

	@Subscribe
	public void onBillClosed(BillClosedEvent event) {
		openBills.remove(event.getBillBox());
		addCustomerToLayout(event.getCustomer(), false);
		reBuildBillLayout(searchCombo.getValue() != null ? event.getCustomer() : null);
	}

	@Subscribe
	public void onBillAdded(AddBillEvent event) {
		BillBox box = BillBox.buildBillBox(event.getBill());
		openBills.add(box);
		customerMap.get(event.getBill().getCustomer()).add(box);
		reBuildBillLayout(event.getBill().getCustomer());
	}

	@Subscribe
	public void onAddCustomer(AddCustomerEvent event) {
		if (event.isNew()) {
			customerMap.put(event.getCustomer(), new ArrayList<BillBox>());
		} else {
			setBoxCaptions(event.getCustomer());
		}
		searchCombo.refresh();
		searchCombo.setValue(event.getCustomer());
	}

	private void setBoxCaptions(Customer customer) {
		for (BillBox component : customerMap.get(customer)) {
			component.setCaption(customer.getName());
		}
	}

	private void reBuildBillLayout(Customer value) {
		billsLayout.removeAllComponents();
		if (value != null) {
			for (BillBox component : customerMap.get(value)) {
				billsLayout.addComponent(component, component.getCurrentBill().isClosed() ? "border-red" : "border-green");
			}
		} else {
			for (BillBox component : openBills) {
				billsLayout.addComponent(component, "border-green");
			}
		}
	}

	private class ModifyButton extends Button {
		private ModifyButton(final String caption, final boolean isNew) {
			super(caption);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					final KWindow window = new KWindow(caption);
					Customer customer = (Customer) searchCombo.getValue();
					CustomerMaintComp comp = isNew ? CustomerMaintComp.createNew() : CustomerMaintComp.fromCustomer(customer);
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
				}
			});
		}
	}

	private class SearchComboBox extends ComboBox {
		private SearchComboBox(List<Customer> customerList) {
			setImmediate(true);
			setCaption(null);
			setWidth("300px");
			setIcon(FontAwesome.SEARCH);
			setItemCaptionPropertyId("name");
			addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
			addStyleName(ValoTheme.COMBOBOX_ALIGN_RIGHT);
			setContainerDataSource(new BeanItemContainer<Customer>(Customer.class, customerList));
			addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(Property.ValueChangeEvent event) {
					reBuildBillLayout((Customer) getValue());
					setButtonsEnabled(getValue() != null);
				}
			});
		}

		public void refresh() {
			setContainerDataSource(new BeanItemContainer<>(Customer.class, ServiceLocator.getBean(CustomerRepo.class).findAll(
					new Sort(Direction.ASC, "name"))));
		}

	}

	private class AddBillButton extends Button {
		private AddBillButton() {
			super("Új számla");
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Customer customer = (Customer) searchCombo.getValue();
					final KWindow window = new KWindow("Új számla");
					BillAdditionalComp comp = BillAdditionalComp.fromCustomer(customer);
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
				}
			});
		}
	}

	private class DeleteButton extends Button {
		private DeleteButton(String caption) {
			super(caption);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					try {
						Customer customer = (Customer) searchCombo.getValue();
						ServiceLocator.getBean(CustomerService.class).deleteCustomer(customer.getId());
						searchCombo.setContainerDataSource(new BeanItemContainer<>(Customer.class, ServiceLocator.getBean(
								CustomerRepo.class).findAll(new Sort(Direction.ASC, "name"))));
					} catch (Exception e) {
						UIExceptionHandler.handleException(e);
					}
				}
			});
		}
	}

	private void setButtonsEnabled(boolean enabled) {
		addBillBtn.setEnabled(enabled);
		modifyBtn.setEnabled(enabled);
		deleteBtn.setEnabled(enabled);
	}

	private static class BillComparator implements Comparator<Bill> {

		@Override
		public int compare(Bill o1, Bill o2) {
			return o1.getOpenDate().compareTo(o2.getOpenDate());
		}

	}

	private static class BillBoxComparator implements Comparator<BillBox> {

		@Override
		public int compare(BillBox o1, BillBox o2) {
			return o1.getCurrentBill().getOpenDate().compareTo(o2.getCurrentBill().getOpenDate());
		}

	}

}
