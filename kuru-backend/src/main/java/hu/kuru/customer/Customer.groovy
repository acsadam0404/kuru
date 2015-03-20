package hu.kuru.customer

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator
import hu.kuru.bill.Bill

import javax.persistence.Column;
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "customer")
@EqualsAndHashCode(includes = ["code"])
class Customer extends BaseEntity {

	private static CustomerRepo repo

	/**
	 * setrepo kéne helyette
	 */
	@Deprecated
	Customer() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(CustomerRepo)
		}
	}
	
	static void setRepo(CustomerRepo repo) {
		this.repo = repo;
	}

	@NotNull
	@Column(unique = true)
	String code
	@NotNull
	String name
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	List<Bill> bills

	static Customer findByCode(String code) {
		repo.findByCode(code)
	}

	static Customer findByName(String name) {
		repo.findByName(name)
	}

	static List<Customer> findAll() {
		repo.findAll();
	}

	Customer save() {
		repo.save(this)
	}

	@Override
	String toString() {
		code + " - " + name
	}
}
