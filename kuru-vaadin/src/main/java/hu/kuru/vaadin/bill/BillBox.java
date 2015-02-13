package hu.kuru.vaadin.bill;

import hu.kuru.UIEventBus;
import hu.kuru.bean.ItemBean;
import hu.kuru.bill.Bill;
import hu.kuru.eventbus.BillClosedEvent;
import hu.kuru.eventbus.EventBusAttachListener;
import hu.kuru.eventbus.EventBusDetachListener;
import hu.kuru.eventbus.ItemAddedEvent;
import hu.kuru.item.Item;
import hu.kuru.vaadin.component.KWindow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BillBox extends CustomComponent {

	private VerticalLayout main;
	private Bill currentBill;
	private boolean closed;

	private BillBox(Bill bill) {
		currentBill = bill;
		closed = bill.getCloseDate() != null;
		setCompositionRoot(buildBox());
	}

	private Component buildBox() {
		main = new VerticalLayout();
		main.setMargin(true);
		main.addStyleName("articlebox");
		main.setSizeFull();
		main.setSpacing(true);
		main.addComponent(buildLayout());
		return main;
	}

	private Component buildTable(List<Item> itemList) {
		Table table = new Table();
		table.setWidth("99%");
		table.setContainerDataSource(new BeanItemContainer<>(ItemBean.class, getItemList(itemList)));
		table.setColumnHeader("name", "Név");
		table.setColumnHeader("code", "Kód");
		table.setColumnHeader("amount", "Mennyiség");
		table.setColumnAlignment("amount", Align.RIGHT);
		table.setVisibleColumns("code", "name", "amount");
		table.setPageLength(itemList.size());
		return table;
	}

	@Subscribe
	public void onItemAdded(ItemAddedEvent event) {
		if (currentBill.getId().equals(event.getBillId())) {
			setCompositionRoot(buildBox());
		}
	}

	private Component buildLayout() {
		VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setSpacing(true);
		box.setMargin(true);
		List<Item> itemList = Item.findByBill(currentBill.getId());
		box.addComponent(buildHeader());
		box.addComponent(buildTable(itemList));
		box.addComponent(buildFooter(getSum(itemList)));
		return box;
	}

	private Component buildHeader() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		DateField openDate = new DateField("Nyitás dátuma");
		DateField closeDate = new DateField("Zárás dátuma");
		openDate.setEnabled(false);
		closeDate.setEnabled(false);
		openDate.setResolution(Resolution.SECOND);
		closeDate.setResolution(Resolution.SECOND);
		openDate.setValue(currentBill.getOpenDate());
		closeDate.setValue(currentBill.getCloseDate());
		layout.addComponent(openDate);
		layout.addComponent(closeDate);
		return layout;
	}

	private int getSum(List<Item> itemList) {
		int sum = 0;
		for (Item item : itemList) {
			sum += item.getAmount() * item.getArticle().getPrice();
		}
		return sum;
	}

	private Component buildFooter(int sum) {
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSizeFull();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		HorizontalLayout priceLayout = new HorizontalLayout();

		buttonLayout.setSpacing(true);
		if (!closed) {
			buttonLayout.addComponent(new AddItemButton());
			buttonLayout.addComponent(new CloseButton());
		}

		Label price = new Label("Ár: " + sum + " Ft");
		price.setStyleName(ValoTheme.LABEL_BOLD);
		priceLayout.addComponent(price);

		footer.addComponent(buttonLayout);
		footer.addComponent(priceLayout);
		footer.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
		footer.setComponentAlignment(priceLayout, Alignment.MIDDLE_RIGHT);
		return footer;
	}

	private List<ItemBean> getItemList(List<Item> itemList) {
		List<ItemBean> beanList = new ArrayList<>();
		for (Item item : itemList) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item.getArticle().getName(), item.getAmount() + " db"));
		}
		return beanList;
	}

	public static BillBox buildBillBox(Bill bill) {
		BillBox comp = new BillBox(bill);
		comp.addAttachListener(new EventBusAttachListener(comp));
		comp.addDetachListener(new EventBusDetachListener(comp));
		return comp;
	}

	private class CloseButton extends Button {
		private CloseButton() {
			super("Lezárás");
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					currentBill.setCloseDate(new Date());
					currentBill.save();
					closed = true;
					UIEventBus.post(new BillClosedEvent(currentBill.getCustomer()));
				}
			});
		}
	}

	private class AddItemButton extends Button {
		public AddItemButton() {
			super("Új tétel");
			addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					final KWindow window = new KWindow("Új tétel");
					ItemAdditionalComp comp = ItemAdditionalComp.fromBill(currentBill);
					comp.setWindow(window);
					window.setContent(comp);
					UI.getCurrent().addWindow(window);
				}
			});
		}
	}

	public boolean isClosed() {
		return closed;
	}

}
