package hu.kuru.item

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
@EqualsAndHashCode(includes = ["bill"])
class Item extends BaseEntity {

	private static ItemRepo repo

	Item() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ItemRepo)
		}
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	Bill bill
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	Article article
	@NotNull
	long amount
	@NotNull
	Date createDate
	Date outDate

	static List<Item> findByBill(long billId) {
		repo.findByBill(billId)
	}

	static List<Item> findIssuedItems() {
		repo.findIssuedItems()
	}

	static Long countDailyIncome() {
		repo.countDailyIncome()
	}

	static Long countOpenBillSummary() {
		repo.countOpenBillSummary()
	}
}
