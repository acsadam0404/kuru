package hu.kuru.rest

import hu.kuru.article.Article
import hu.kuru.article.ArticleCategory
import hu.kuru.article.ArticleCategoryRepo
import hu.kuru.article.ArticleRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RequestMapping(value = "/articlecategories")
@RestController
class ArticleCategoryController {
    @Autowired
    ArticleCategoryRepo articleCategoryRepo
    @Autowired
    ArticleRepo articleRepo

    @RequestMapping(value = "/{id}/articles", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getArticlesByArticleCategory(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<List<Article>>(articleRepo.findByArticleCategoryId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getArticleCategory(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<ArticleCategory>(articleCategoryRepo.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getArticleCategories() {
        return new ResponseEntity<List<ArticleCategory>>(articleCategoryRepo.findAll(), HttpStatus.OK);
    }

}
