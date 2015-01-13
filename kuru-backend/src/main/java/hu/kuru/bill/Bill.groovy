package hu.kuru.bill

import java.util.List;

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;
import hu.kuru.customer.Customer;
import hu.kuru.item.ItemRepo;

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Entity
@Table(name = "item")
@EqualsAndHashCode(includes = ["bill"])
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
	@NotNull
	boolean closed
	
	static List<Bill> findByCustomer(long customerId) {
		repo.findByCustomer(customerId)
	}
	
}
