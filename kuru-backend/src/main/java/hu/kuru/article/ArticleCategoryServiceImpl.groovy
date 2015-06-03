package hu.kuru.article

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.google.common.base.Preconditions
import com.google.common.base.Strings

@Service
@Transactional
class ArticleCategoryServiceImpl implements ArticleCategoryService {

	@Autowired
	private ArticleCategoryRepo articleCategoryRepo 
	
	@Override
	public void save(ArticleCategory articleCategory) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(articleCategory.code), "Kötelező cikkcsoport kódot megadni!")
		Preconditions.checkArgument(!Strings.isNullOrEmpty(articleCategory.name), "Kötelező cikkcsoport nevet megadni!")
		Preconditions.checkArgument(!Strings.isNullOrEmpty(articleCategory.icon), "Kötelező ikont választani!")
		Preconditions.checkArgument(!articleCategoryRepo.existCode(articleCategory.code), "Már létezik cikkcsoport ezzel a kóddal!")
		articleCategory.save()
	}

	@Override
	public void delete(Long articleCategoryId) {
		Preconditions.checkArgument(articleCategoryId != null, "Nem lett kiválasztva cikkcsoport a törléshez!")
		Preconditions.checkArgument(!Article.isExistReferenceToArticleCategory(articleCategoryId), "A cikkcsoport nem törölhető! Van rá hivatkozás!")
		ArticleCategory articleCategory = ArticleCategory.findOne(articleCategoryId)
		Preconditions.checkArgument(articleCategory != null, "Nem létezik ilyen cikkcsoport!")
		articleCategory.delete(articleCategory)
	}

}
