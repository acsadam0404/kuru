package hu.kuru.rest

import hu.kuru.article.Article
import hu.kuru.article.ArticleRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController {
    @Autowired
    ArticleRepo articleRepo

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    @ResponseBody
    List<Article> getArticleByCategory(
            @RequestParam(required = false, value = "groupId", defaultValue = "0") Long groupId) {
        if (groupId) {
            return articleRepo.findByArticleCategoryId(groupId)
        }
        articleRepo.findAll()
    }

}
