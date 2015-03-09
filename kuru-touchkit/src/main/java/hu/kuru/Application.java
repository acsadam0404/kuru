package hu.kuru;

import hu.kuru.article.Article;
import hu.kuru.article.ArticleRepo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Application {
	@Autowired
	private ArticleRepo articleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}
	
	@PostConstruct
	public void init() {
		Article.setRepo(articleRepo);
	}

}
