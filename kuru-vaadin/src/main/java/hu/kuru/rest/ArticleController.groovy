package hu.kuru.rest

import hu.kuru.article.Article
import hu.kuru.article.ArticleRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RequestMapping(value = "/articles")
@RestController
class ArticleController {
    @Autowired
    ArticleRepo articleRepo

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getArticle(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<Article>(articleRepo.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getArticles() {
        return new ResponseEntity<List<Article>>(articleRepo.findAll(), HttpStatus.OK);
    }

}
