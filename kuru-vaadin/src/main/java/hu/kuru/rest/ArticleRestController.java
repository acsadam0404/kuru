package hu.kuru.rest;

import java.util.List;

import hu.kuru.article.Article;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestController {

	@ResponseBody
	@RequestMapping(value = "/articles/{name}", method = RequestMethod.GET)
	public Article findByName(@PathVariable("name") String name){
		return Article.findByName(name);
	}

	@ResponseBody
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public List<Article> findAll() {
		return Article.findAll();
	}
	
}
