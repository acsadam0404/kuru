package hu.kuru.bill

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator
import hu.kuru.customer.Customer
import hu.kuru.item.Item

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "bill")
class Bill extends BaseEntity {

	private static BillRepo repo

	Bill() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(BillRepo)
		}
	}

	@NotNull
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	Customer customer
	@NotNull
	Date openDate
	Date closeDate
	Long sum
	@NotNull
	String currency
	static List<Bill> findByCustomer(Customer customer) {
		repo.findByCustomer(customer)
	}

	static Long countOpenBills() {
		repo.countOpenBills()
	}

	static Boolean hasOpenBillByCustomer(long customerId) {
		repo.hasOpenBillByCustomer(customerId)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((closeDate == null) ? 0 : closeDate.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((openDate == null) ? 0 : openDate.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
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
		Bill other = (Bill) obj;
		if (closeDate == null) {
			if (other.closeDate != null)
				return false;
		}
		else if (!closeDate.equals(other.closeDate))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		}
		else if (!currency.equals(other.currency))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		}
		else if (!customer.equals(other.customer))
			return false;
		if (openDate == null) {
			if (other.openDate != null)
				return false;
		}
		else if (!openDate.equals(other.openDate))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		}
		else if (!sum.equals(other.sum))
			return false;
		return true;
	}
	
	
}
