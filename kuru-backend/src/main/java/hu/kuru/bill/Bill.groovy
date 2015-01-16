package hu.kuru.bill

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator
import hu.kuru.customer.Customer

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "bill")
@EqualsAndHashCode(includes = ["customer"])
class Bill extends BaseEntity {

	private static BillRepo repo

	Bill() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(BillRepo)
		}
	}

	@NotNull
	Customer customer
	@NotNull
	Date openDate
	Date closeDate
	@NotNull
	String currency

	static List<Bill> findByCustomer(long customerId) {
		repo.findByCustomer(customerId)
	}
}
