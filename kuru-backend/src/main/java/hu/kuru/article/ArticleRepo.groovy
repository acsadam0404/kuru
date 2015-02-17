package hu.kuru.article

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepo extends JpaRepository<Article, Long>{

	Article findByCode(String code)

	Article findByName(String name)

	@Query("select a from Article a where a.active = true order by a.name")
	List<Article> findAllActive()

	@Query("select case when (count(a) > 0) then true else false end from Article a where a.active = true and a.name = ?1")
	Boolean existName(String name)

	@Query("select case when (count(a) > 0) then true else false end from Article a where a.active = true and a.code = ?1")
	Boolean existCode(String code)

	@Query("select case when (count(a) > 0) then true else false end from Article a where a.active = true and a.name = ?1 and a.id <> ?2")
	Boolean existMoreThanOneName(String name, Long articleId)

	@Query("select case when (count(a) > 0) then true else false end from Article a where a.active = true and a.code = ?1 and a.id <> ?2")
	Boolean existMoreThanOneCode(String code, Long articleId)
}
