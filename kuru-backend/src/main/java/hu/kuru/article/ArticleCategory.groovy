package hu.kuru.article

import groovy.transform.EqualsAndHashCode;
import hu.kuru.BaseEntity
import hu.kuru.ServiceLocator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Sort

@Entity
@Table(name = "articlecategory")
@EqualsAndHashCode(includes = ["code"])
class ArticleCategory extends BaseEntity {

	private static ArticleCategoryRepo repo

	public static void setRepo(ArticleCategoryRepo repo) {
		this.repo = repo
	}
	ArticleCategory() {
		if (ServiceLocator.loaded && !repo)  {
			repo = ServiceLocator.getBean(ArticleCategoryRepo)
		}
	}

	@NotNull
	String code
	@NotNull
	String name
	@NotNull
	String icon

	static ArticleCategory findByCode(String code) {
		repo.findByCode(code)
	}

	static ArticleCategory findByName(String name) {
		repo.findByName(name)
	}

	static ArticleCategory findOne(Long id) {
		repo.findOne(id)
	}

	static List<ArticleCategory> findAll() {
		repo.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	ArticleCategory save() {
		repo.save(this)
	}

	@Override
	String toString() {
		name
	}
	
	void delete(ArticleCategory category) {
		repo.delete(category)
	}
}
