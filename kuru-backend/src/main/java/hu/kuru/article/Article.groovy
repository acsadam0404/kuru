package hu.kuru.article

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "article")
@EqualsAndHashCode(includes = ["code"])
class Article extends BaseEntity {

	private static ArticleRepo repo

	Article() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ArticleRepo)
		}
	}

	@NotNull
	String code
	@NotNull
	String name
	@NotNull
	long price
	@NotNull
	String icon
	@NotNull
	String unit
	String description
	@NotNull
	boolean active

	static Article findByCode(String code) {
		repo.findByCode(code)
	}

	static Article findByName(String name) {
		repo.findByName(name)
	}

	static Article findOne(Long id) {
		repo.findOne(id)
	}

	static List<Article> findAll() {
		repo.findAll();
	}

	static List<Article> findAllActive() {
		repo.findAllActive()
	}

	Article save() {
		repo.save(this)
	}

	@Override
	String toString() {
		code + ", " + name + ", " + price + ", " +description
	}
}
