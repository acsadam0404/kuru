package hu.kuru.article

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "article")
@EqualsAndHashCode(includes = ["code"])
class Article extends BaseEntity {

	private static ArticleRepo repo

	public static void setRepo(ArticleRepo repo) {
		this.repo = repo
	}
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
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	ArticleCategory articleCategory

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
	
	static List<Article> findAllActiveByCategoryId(Long id) {
		repo.findAllActiveByCategoryId(id);
	}

	Article save() {
		repo.save(this)
	}
	
	static isExistReferenceToArticleCategory(Long categoryId) {
		repo.isExistReferenceToArticleCategory(categoryId)
	}
	
	void delete(Article article) {
		repo.delete(article)
	}

	@Override
	String toString() {
		code + ", " + name + ", " + price + ", " +description
	}
}
