package hu.kuru.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Számla generáláshoz szükséges bean (XML generálás a birt rest apinak)
 * 
 * @author
 *
 */
@XmlRootElement(name = "bill")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClosedBill {

	@XmlElement(name = "items")
	private ItemListForClosedBill itemListForClosedBill;

	public ItemListForClosedBill getItemListForClosedBill() {
		return itemListForClosedBill;
	}

	public void setItemListForClosedBill(ItemListForClosedBill itemListForClosedBill) {
		this.itemListForClosedBill = itemListForClosedBill;
	}

}
