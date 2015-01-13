package hu.kuru.item

import java.util.List;

import javax.inject.Qualifier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ItemRepo extends JpaRepository<Item, Long>{

	@Query("select i from Item i where i.bill.id = ?1")
	List<Item> findByBill(long billId)
	
}
