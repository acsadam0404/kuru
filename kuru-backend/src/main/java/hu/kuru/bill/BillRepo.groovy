package hu.kuru.bill

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BillRepo extends JpaRepository<Bill, Long> {

	@Query("select b from Bill b join fetch b.customer where b.customer.id = ?1")
	List<Bill> findByCustomer(Long customerId)

	@Query("select b.id from Bill b where b.customer.id = ?1")
	List<Long> findIdsByCustomer(Long customerId)

	@Query("select b from Bill b join fetch b.customer where b.id = ?1")
	Bill findById(Long billId)

	@Query("select count(b) from Bill b where b.closeDate is null")
	Long countOpenBills();

	@Modifying
	@Transactional
	@Query("delete from Bill b where b.id in ?1")
	void deleteByIds(List<Long> billIds);

	@Query("select case when (count(b) > 0) then true else false end from Bill b where b.customer.id = ?1 and b.closeDate is null")
	Boolean hasOpenBillByCustomer(Long customerId)
}
