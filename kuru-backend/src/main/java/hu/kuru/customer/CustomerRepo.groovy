package hu.kuru.customerimport org.springframework.data.jpa.repository.JpaRepositoryinterface CustomerRepo extends JpaRepository<Customer, Long> {
	Customer findByName(String name)	Customer findByCode(String code)}
