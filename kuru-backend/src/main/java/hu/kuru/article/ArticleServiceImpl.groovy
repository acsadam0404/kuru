package hu.kuru.article

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.google.common.base.Preconditions

@Service
@Transactional
class ArticleServiceImpl implements ArticleService {

	@Override
	public void save(Article article) {
		if(article.getId() != null) {
			article.setActive(false)
			article.save()
		}
		Preconditions.checkArgument(!Article.existCode(article.getCode()), "Már létezik cikk ezzel a kóddal.");
		Preconditions.checkArgument(!Article.existName(article.getName()), "Már létezik cikk ezzel a névvel.");
		article.setId(null)
		article.setActive(true)
		article.save()
	}

	@Override
	public void delete(Long articleId) {
		Article article = Article.findOne(articleId)
		article.setActive(false)
		article.save()
	}
}
