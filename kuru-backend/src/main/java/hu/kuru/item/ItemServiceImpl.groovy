package hu.kuru.item

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import com.google.common.base.Preconditions

@Service
@Transactional
class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepo repo;

	@Override
	public void issueItems(List<Item> itemList) {
		Preconditions.checkArgument(itemList != null, "Kiadandó tételek nem léteznek!")
		for(Item item : itemList) {
			item.setOutDate(new Date())
			item.save()
		}
	}
}
