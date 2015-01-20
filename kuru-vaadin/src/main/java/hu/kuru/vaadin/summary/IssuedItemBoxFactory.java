package hu.kuru.vaadin.summary;

import hu.kuru.customer.Customer;
import hu.kuru.item.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class IssuedItemBoxFactory extends CustomComponent {

	private MasonryLayout layout;

	private IssuedItemBoxFactory(List<Item> itemList) {
		setCompositionRoot(buildBoxLayout(itemList));
	}

	private Component buildBoxLayout(List<Item> itemList) {
		Map<Customer, List<Item>> map = getCustomerMap(itemList);
		layout = new MasonryLayout();
		layout.setSizeFull();
		for (Customer customer : map.keySet()) {
			buildBox(customer, map.get(customer));
		}
		return layout;
	}

	private void buildBox(Customer customer, List<Item> itemList) {
		Collections.sort(itemList, new ItemDateComparator());
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");

		final Panel panel = new Panel(customer.getCode() + " - " + customer.getName());
		panel.setSizeFull();
		panel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		panel.addStyleName("itembox");

		VerticalLayout box = new VerticalLayout();
		box.setSizeFull();
		box.setSpacing(true);
		box.setMargin(true);

		GridLayout grid = new GridLayout(3, itemList.size());
		grid.setSizeFull();

		for (Item item : itemList) {
			grid.addComponents(new Label(item.getArticle().getCode()), new Label(item.getArticle().getName()), new Label(item.getAmount()
					+ " db"));
		}

		Button outBtn = new Button("Kiadás");
		box.addComponent(outBtn);
		box.setComponentAlignment(outBtn, Alignment.TOP_RIGHT);
		box.addComponent(new Label("Első rendelés: " + format.format(itemList.get(0).getCreateDate())));
		box.addComponent(grid);
		panel.setContent(box);

		layout.addComponent(panel, MasonryLayout.DOUBLE_WIDE_STYLENAME);

		outBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				layout.removeComponent(panel);
			}
		});
	}

	private Map<Customer, List<Item>> getCustomerMap(List<Item> itemList) {
		Map<Customer, List<Item>> map = new HashMap<>();
		for (Item item : itemList) {
			Customer customer = item.getBill().getCustomer();
			if (!map.containsKey(customer)) {
				map.put(customer, new ArrayList<Item>());
			}
			map.get(customer).add(item);
		}
		return map;
	}

	public static IssuedItemBoxFactory createBoxItems(List<Item> itemList) {
		return new IssuedItemBoxFactory(itemList);
	}

	private class ItemDateComparator implements Comparator<Item> {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getCreateDate().compareTo(o2.getCreateDate());
		}
	}

}
