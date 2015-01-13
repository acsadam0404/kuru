package hu.kuru.rest;

import hu.kuru.article.Article;
import hu.kuru.customer.Customer;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

	@ResponseBody
	@RequestMapping(value = "/customers/{code}", method = RequestMethod.GET)
	public Customer findByName(@PathVariable("code") String code){
		return Customer.findByCode(code);
	}

	@ResponseBody
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> findAll() {
		return Customer.findAll();
	}
	
	@ResponseBody
	@RequestMapping(value = "/customers/{code}/balance", method = RequestMethod.GET)
	public long getBalance(@PathVariable("code") String code) {
		return 3L;
	}
	
	@ResponseBody
	@RequestMapping(value = "/customers/{code}/orders", method = RequestMethod.POST)
	public void addOrder(@PathVariable("code") String code, @PathVariable("amount") long amount) {
		// TODO rendelés felvételére
	}
	
	@ResponseBody
	@RequestMapping(value = "/customers/{code}/orders", method = RequestMethod.GET)
	public void getOrders(@PathVariable("code") String code) {
		// TODO új státuszú rendelések lekérdezésére
	}
	
	@ResponseBody
	@RequestMapping(value = "/customers/{code}/orders/{date}", method = RequestMethod.PUT)
	public void issueOrder(@PathVariable("code") String code, @PathVariable("date") Date date) {
		// TODO új státuszú rendelések kiadott állapotba tétele
	}
	
}
