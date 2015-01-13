package hu.kuru.customer

import java.util.List;

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;
import hu.kuru.article.Article;
import hu.kuru.article.ArticleRepo;

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

@Entity
@Table(name = "customer")
@EqualsAndHashCode(includes = ["code"])
class Customer extends BaseEntity {
	
	private static CustomerRepo repo
	
		Customer() {
			if (ServiceLocator.loaded && !repo)  {
				repo = ServiceLocator.getBean(CustomerRepo)
			}
		}
	
	@NotNull
	String code
	@NotNull
	String name
	
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
		code + ", " + name
	}
	
}
