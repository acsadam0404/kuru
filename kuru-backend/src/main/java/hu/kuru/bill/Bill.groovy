package hu.kuru.bill

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator
import hu.kuru.customer.Customer
import hu.kuru.item.Item

import javax.persistence.CascadeType;
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "bill")
@EqualsAndHashCode
class Bill extends BaseEntity {

	private static BillRepo repo

	Bill() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(BillRepo)
		}
	}

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	Customer customer
	@NotNull
	Date openDate
	Date closeDate
	Long sum
	@NotNull
	String currency
	//TODO: visszairni Lazy.re kell egy query ahol be lesz fetchelve mert lazy loading kĂĽlĂ¶nben
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "bill")
	List<Item> items
	static List<Bill> findByCustomer(long customerId) {
		repo.findByCustomer(customerId)
	}

	static Long countOpenBills() {
		repo.countOpenBills()
	}

	Bill save() {
		repo.save(this)
	}
	
	boolean isClosed() {
		closeDate != null
	}
	
	static Bill getOpenBillByCustomerId(Long customerId) {
		repo.getOpenBillByCustomerId(customerId)	
	}
}
