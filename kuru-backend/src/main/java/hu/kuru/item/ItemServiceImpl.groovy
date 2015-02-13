package hu.kuru.item

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ItemServiceImpl implements ItemService {

	@Override
	public void issueItems(List<Item> itemList) {
		for(Item item : itemList) {
			item.setOutDate(new Date())
			item.save()
		}
	}
}
