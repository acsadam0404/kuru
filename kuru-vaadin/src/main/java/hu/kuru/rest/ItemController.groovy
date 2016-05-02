package hu.kuru.rest

import hu.kuru.ServiceLocator
import hu.kuru.bill.Bill
import hu.kuru.bill.BillRepo
import hu.kuru.enums.Currency
import hu.kuru.external.mnb.ExchangeRate
import hu.kuru.external.mnb.MNBExchangeRateService
import hu.kuru.external.mnb.MNBServiceException
import hu.kuru.item.Item
import hu.kuru.item.ItemRepo
import hu.kuru.vaadin.component.KNotification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/items")
class ItemController {
    @Autowired
    ItemRepo itemRepo
    @Autowired
    BillRepo billRepo

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getItemById(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<Item>(itemRepo.findOne(id), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getItems() {
        return new ResponseEntity<List<Item>>(itemRepo.findAll(), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity addItem(@RequestBody Item item) {
        item.save(getChangedSum(item.getArticle().getPrice() * item.getAmount(), item.bill))
        return new ResponseEntity<Item>(item, HttpStatus.CREATED)
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity deleteItemById(@PathVariable("id") Long id) {
        itemRepo.delete(id)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT)
    }

    private double getChangedSum(double sum, Bill bill) {
        double result = 0;
        String currency = bill.getCurrency();
        try {
            if (!Currency.HUF.name().equals(currency)) {
                List<ExchangeRate> list = ServiceLocator.getBean(
                        MNBExchangeRateService.class).getExchangeRates();
                if (Currency.EUR.name().equals(currency)) {
                    result = sum / getRate(list, Currency.EUR.name());
                } else if (Currency.GBP.name().equals(currency)) {
                    result = sum / getRate(list, Currency.GBP.name());
                } else if (Currency.USD.name().equals(currency)) {
                    result = sum / getRate(list, Currency.USD.name());
                }
            } else {
                return sum;
            }
        } catch (MNBServiceException e) {
            new KNotification("Sikertelen MNB árfolyam lekérdezés.").withDescription("A árak forintban jelennek meg.");
            return 0;
        }
        return result;
    }

    private double getRate(List<ExchangeRate> list, String name)
            throws MNBServiceException {
        for (ExchangeRate exchangeRate : list) {
            if (exchangeRate.getCurr().equals(name)) {
                return Double
                        .valueOf(exchangeRate.getValue().replace(",", "."));
            }
        }
        throw new MNBServiceException();
    }

}
