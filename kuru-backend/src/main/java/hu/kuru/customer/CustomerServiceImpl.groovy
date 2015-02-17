package hu.kuru.customer

import hu.kuru.bill.BillRepo
import hu.kuru.item.ItemRepo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.google.common.base.Preconditions

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
		Preconditions.checkArgument(id != null, "Nem létezik ilyen ügyfél!")
		Preconditions.checkArgument(!billRepo.hasOpenBillByCustomer(id), "A törölni kívánt ügyfélnek létezik nyitott számlája.")
		List<Long> billList = billRepo.findIdsByCustomer(id)
		if(billList != null) {
			itemRepo.deleteByBillId(billList)
			billRepo.deleteByIds(billList)
		}
		customerRepo.delete(id)
	}
}
