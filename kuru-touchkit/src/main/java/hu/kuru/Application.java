package hu.kuru;

import hu.kuru.article.Article;
import hu.kuru.article.ArticleRepo;
import hu.kuru.customer.Customer;
import hu.kuru.customer.CustomerRepo;
import hu.kuru.item.ItemRepo;
import hu.kuru.user.User;
import hu.kuru.user.UserRepo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring boot entry point
 * 
 * @author 
 *
 */
@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ImportResource("/META-INF/bus-context.xml")
public class Application {
	
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ItemRepo itemRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}
	
	@PostConstruct
	public void init() {
		Article.setRepo(articleRepo);
		User.setRepo(userRepo);
		Customer.setRepo(customerRepo);
	}

}
