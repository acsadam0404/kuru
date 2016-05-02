package hu.kuru.item

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator
import hu.kuru.article.Article
import hu.kuru.bill.Bill

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "item")
class Item extends BaseEntity {

	private static ItemRepo repo

	Item() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ItemRepo)
		}
	}

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	Bill bill
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	Article article
	
	@NotNull
	long amount
	
	@NotNull
	Date createDate
	
	Date outDate

	static List<Item> findByBill(Long billId) {
		repo.findByBill(billId)
	}

	static List<Item> findIssuedItems() {
		repo.findIssuedItems()
	}

	static long countDailyIncome() {
		repo.countDailyIncome()
	}

	static long countOpenBillSummary() {
		repo.countOpenBillSummary()
	}

	Item save() {
		repo.save(this)
	}

	Item save(double changedSum) {
		if(0 != changedSum) {
			repo.save(this)
			Bill bill = this.bill
			bill.setSum(bill.getSum() + changedSum)
			bill.save()
		}
		return this
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result + ((article == null) ? 0 : article.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((outDate == null) ? 0 : outDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.is(obj))
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (amount != other.amount)
			return false;
		if (article == null) {
			if (other.article != null)
				return false;
		}
		else if (!article.equals(other.article))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		}
		else if (!createDate.equals(other.createDate))
			return false;
		if (outDate == null) {
			if (other.outDate != null)
				return false;
		}
		else if (!outDate.equals(other.outDate))
			return false;
		return true;
	}
	
	
}
