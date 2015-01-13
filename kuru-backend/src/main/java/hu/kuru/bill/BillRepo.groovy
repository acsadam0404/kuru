package hu.kuru.bill

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BillRepo extends JpaRepository<Bill, Long> {

	@Query("select b from Bill b where b.customer.id = ?1")
	List<Bill> findByCustomer(long customerId)
	
}
