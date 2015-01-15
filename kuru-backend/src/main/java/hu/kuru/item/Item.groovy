package hu.kuru.item

import java.util.List;

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleRepo;
import hu.kuru.bill.Bill

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Entity
@Table(name = "item")
@EqualsAndHashCode(includes = ["bill"])
class Item extends BaseEntity {

	private static ItemRepo repo
	
		Item() {
			if (ServiceLocator.loaded && !repo)  {
				repo = ServiceLocator.getBean(ItemRepo)
			}
		}
	
	@NotNull
	Bill bill
	@NotNull
	Article article
	@NotNull
	long amount
	@NotNull
	Date outDate
	
	static List<Item> findByBill(long billId) {
		repo.findByBill(billId)
	}
			
}
