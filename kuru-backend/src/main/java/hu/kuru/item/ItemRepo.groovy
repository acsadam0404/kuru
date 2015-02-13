package hu.kuru.item

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ItemRepo extends JpaRepository<Item, Long>{

	@Query("select i from Item i join fetch i.article where i.bill.id = ?1")
	List<Item> findByBill(Long billId)

	@Query("select i from Item i join fetch i.article join fetch i.bill b join fetch b.customer where i.outDate is null")
	List<Item> findIssuedItems();

	@Query("select SUM(i.article.price * i.amount) from Item i where i.bill.closeDate is null")
	Long countOpenBillSummary();

	@Query("select SUM(i.article.price * i.amount) from Item i where i.bill.closeDate > current_date()")
	Long countDailyIncome();

	@Modifying
	@Transactional
	@Query("delete from Item i where i.bill.id in ?1")
	void deleteByBillId(List<Long> billIds);
}
