package hu.kuru.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ArticleCategoryRepo extends JpaRepository<ArticleCategory, Long>{

	void delete(ArticleCategory category);
	
	ArticleCategory findByCode(String code)

	ArticleCategory findByName(String name)
	
	@Query("select case when (count(a) > 0) then true else false end from ArticleCategory a where a.code = ?1")
	Boolean existCode(String code)
}
