package hu.kuru.article

import hu.kuru.item.ItemRepo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.google.common.base.Preconditions
import com.google.common.base.Strings

@Service
@Transactional
class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepo articleRepo;

	@Autowired
	private ItemRepo itemRepo;

	@Override
	@Transactional
	public void save(Article article) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(article.code), "Kötelező cikk kódot megadni!")
		Preconditions.checkArgument(!Strings.isNullOrEmpty(article.name), "Kötelező cikk nevet megadni!")
		Preconditions.checkArgument(!Strings.isNullOrEmpty(article.icon), "Kötelező ikont választani!")
		Preconditions.checkArgument(!Strings.isNullOrEmpty(article.unit), "Kötelező mértékegységet választani!")
		if(isNew(article)) {
			Preconditions.checkArgument(!articleRepo.existCode(article.code), "Már létezik cikk ezzel a kóddal!")
			Preconditions.checkArgument(!articleRepo.existName(article.name), "Már létezik cikk ezzel a névvel!")
			article.setId(null)
			article.setActive(true)
			article.save()
		} else {
			Preconditions.checkArgument(itemRepo.isIssuedByArticleId(article.id), "A módosítani kívánt cikk szerepel a kiadandó tételek listáján!")
			Preconditions.checkArgument(itemRepo.isClosedByArticleId(article.id), "A módosítani kívánt cikk szerepel nyitott számlán!")
			Preconditions.checkArgument(!articleRepo.existMoreThanOneCode(article.code, article.id), "Már létezik cikk ezzel a kóddal!")
			Preconditions.checkArgument(!articleRepo.existMoreThanOneName(article.name, article.id), "Már létezik cikk ezzel a névvel!")
			// régi cikk logikai törlése
			article.setActive(false)
			article.save()
			// új cikk mentése
			article.setId(null)
			article.setActive(true)
			article.save()
		}
	}

	private boolean isNew(Article article) {
		article.id == null
	}

	@Override
	@Transactional
	public void delete(Long articleId) {
		Preconditions.checkArgument(articleId != null, "Nem lett kiválasztva cikk a törléshez!")
		Preconditions.checkArgument(itemRepo.isIssuedByArticleId(articleId), "A törölni kívánt cikk szerepel a kiadandó tételek listáján!")
		Preconditions.checkArgument(itemRepo.isClosedByArticleId(articleId), "A törölni kívánt cikk szerepel nyitott számlán!")
		Article article = Article.findOne(articleId)
		Preconditions.checkArgument(article != null, "Nem létezik ilyen cikk!")
		article.setActive(false)
		article.save()
	}
}
