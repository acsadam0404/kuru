package hu.kuru.rest

import hu.kuru.bill.Bill
import hu.kuru.bill.BillRepo
import hu.kuru.customer.Customer
import hu.kuru.customer.CustomerRepo
import hu.kuru.item.Item
import hu.kuru.item.ItemRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/bills")
@RestController
class BillController {
    @Autowired
    BillRepo billRepo
    @Autowired
    ItemRepo itemRepo

    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getItemsByBill(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<List<Item>>(itemRepo.findByBill(id), HttpStatus.OK)
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getBill(
            @PathVariable(value = "id") Long id) {
        return new ResponseEntity<Bill>(billRepo.findOne(id), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getBills() {
        return new ResponseEntity<List<Bill>>(billRepo.findAll(), HttpStatus.OK)
    }
}
