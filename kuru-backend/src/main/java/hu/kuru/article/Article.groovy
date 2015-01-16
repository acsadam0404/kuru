package hu.kuru.article

import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;
import hu.kuru.user.UserRepo;

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable

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
	String comment

	static Article findByCode(String code) {
		repo.findByCode(code)
	}

	static Article findByName(String name) {
		repo.findByName(name)
	}

	static List<Article> findAll() {
		repo.findAll();
	}

	Article save() {
		repo.save(this)
	}

	@Override
	String toString() {
		code + ", " + name + ", " + price + ", " + comment
	}
}
