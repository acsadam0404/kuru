package hu.kuru.article

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepo extends JpaRepository<Article, Long>{

	Article findByCode(String code)
	
	Article findByName(String name)
	
}
