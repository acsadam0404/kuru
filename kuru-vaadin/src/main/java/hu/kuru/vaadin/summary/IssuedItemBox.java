package hu.kuru.vaadin.summary;

import hu.kuru.ServiceLocator;
import hu.kuru.bean.ItemBean;
import hu.kuru.customer.Customer;
import hu.kuru.item.Item;
import hu.kuru.item.ItemService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.alump.masonry.MasonryLayout;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class IssuedItemBox extends CustomComponent {

	private MasonryLayout layout;

	private IssuedItemBox(List<Item> itemList) {
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

	private List<ItemBean> getItemList(List<Item> itemList) {
		List<ItemBean> beanList = new ArrayList<>();
		for (Item item : itemList) {
			beanList.add(new ItemBean(item.getArticle().getCode(), item.getArticle().getName(), item.getAmount() + " "
					+ item.getArticle().getUnit()));
		}
		return beanList;
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

		Button outBtn = new OutButton(panel, itemList);
		box.addComponent(outBtn);
		box.setComponentAlignment(outBtn, Alignment.TOP_RIGHT);
		DateField date = new DateField("Első rendelés");
		date.setEnabled(false);
		date.setResolution(Resolution.SECOND);
		date.setValue(itemList.get(0).getCreateDate());
		box.addComponent(date);
		box.addComponent(buildTable(itemList));
		panel.setContent(box);
		layout.addComponent(panel, MasonryLayout.DOUBLE_WIDE_STYLENAME);
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

	public static IssuedItemBox createBoxItems(List<Item> itemList) {
		return new IssuedItemBox(itemList);
	}

	private static class ItemDateComparator implements Comparator<Item> {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getCreateDate().compareTo(o2.getCreateDate());
		}
	}

	private class OutButton extends Button {
		private OutButton(final Panel panel, final List<Item> itemList) {
			super("Kiadás");
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					ServiceLocator.getBean(ItemService.class).issueItems(itemList);
					layout.removeComponent(panel);
				}
			});
		}
	}

}
