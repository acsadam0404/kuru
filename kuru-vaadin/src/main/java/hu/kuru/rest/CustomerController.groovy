package hu.kuru.rest

import hu.kuru.bill.Bill
import hu.kuru.bill.BillRepo
import hu.kuru.customer.Customer
import hu.kuru.customer.CustomerRepo

/**
 * Created by tamasturcsek on 2016.03.28..
 */
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequestMapping(value = "/customers")
@RestController
class CustomerController {
    @Autowired
    CustomerRepo customerRepo
    @Autowired
    BillRepo billRepo

    @RequestMapping(value = "/{code}/bills", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getBillsByCustomer(
            @PathVariable(value = "code") String code) {
        return new ResponseEntity<List<Bill>>(billRepo.findByCustomer(customerRepo.findByCode(code)), HttpStatus.OK)
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCustomer(
            @PathVariable(value = "code") String code) {
        return new ResponseEntity<Customer>(customerRepo.findByCode(code), HttpStatus.OK)
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getCustomers() {
        return new ResponseEntity<List<Customer>>(customerRepo.findAll(), HttpStatus.OK)
    }

}