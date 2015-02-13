package hu.kuru.customer

import hu.kuru.bill.BillRepo
import hu.kuru.item.ItemRepo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ItemRepo itemRepo

	@Autowired
	private BillRepo billRepo

	@Autowired
	private CustomerRepo customerRepo

	@Override
	public void deleteCustomer(Long id) {
		List<Long> billList = billRepo.findIdsByCustomer(id)
		itemRepo.deleteByBillId(billList)
		billRepo.deleteByIds(billList)
		customerRepo.delete(id)
	}
}
