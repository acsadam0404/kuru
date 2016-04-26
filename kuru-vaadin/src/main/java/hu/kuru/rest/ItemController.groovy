package hu.kuru.rest

import hu.kuru.bill.Bill
import hu.kuru.bill.BillRepo
import hu.kuru.item.Item
import hu.kuru.item.ItemRepo
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
        itemRepo.save(item)
        Bill bill = billRepo.findById(item.getBill().id)
        bill.setSum(bill.getSum() + item.getAmount()*item.getArticle().getPrice())
        billRepo.save(bill)
        return new ResponseEntity<Item>(item, HttpStatus.CREATED)
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    ResponseEntity deleteItemById(@PathVariable("id") Long id) {
        itemRepo.delete(id)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT)
    }

}
