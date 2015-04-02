package hu.kuru.ui.component;

import hu.kuru.ServiceLocator;
import hu.kuru.bill.Bill;
import hu.kuru.bill.BillRepo;
import hu.kuru.customer.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class BillComponent extends CustomComponent {

	private HorizontalLayout billsLayout;
	private List<BillBox> bills;
	private String customerCode;

	public BillComponent(String customerCode) {
		setSizeFull();
		this.customerCode = customerCode;
		setCompositionRoot(build());
	}

	private Component build() {
		VerticalLayout main = new VerticalLayout();
		main.setSizeFull();
		main.setSpacing(true);
		main.setMargin(true);

		billsLayout = new HorizontalLayout();
		billsLayout.setSizeFull();

		List<Bill> billList = ServiceLocator.getBean(BillRepo.class).findByCustomer(Customer.findByCode(customerCode));
		bills = new ArrayList<BillBox>();
		if (billList != null) {
			Collections.sort(billList, new BillComparator());
			for (Bill bill : billList) {
				BillBox box = new BillBox(bill);
				if (bill.getCloseDate() == null) {
					bills.add(box);
				}
			}
		}

		for (BillBox billBox : bills) {
			billsLayout.addComponent(billBox);
		}
		main.addComponent(billsLayout);
		Responsive.makeResponsive(main);
		return billsLayout;
	}

	/**
	 * Számlák comparatora
	 * 
	 * @author
	 *
	 */
	private static class BillComparator implements Comparator<Bill> {

		@Override
		public int compare(Bill o1, Bill o2) {
			return o1.getOpenDate().compareTo(o2.getOpenDate());
		}

	}

}