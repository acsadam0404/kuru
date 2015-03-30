package hu.kuru.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Számla generáláshoz szükséges bean (XML generálás a birt rest apinak), ilyen típusú listát tartalmaz a számla
 * 
 * @author 
 *
 */
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListForClosedBill {

	 	@XmlElement(name = "article")
	    private List<ArticleForClosedBill> articles = null;
	 
	    public List<ArticleForClosedBill> getArticles() {
	        return articles;
	    }
	 
	    public void setArticles(List<ArticleForClosedBill> articles) {
	        this.articles = articles;
	    }
}
