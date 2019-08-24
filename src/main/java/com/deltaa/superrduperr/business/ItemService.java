package com.deltaa.superrduperr.business;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deltaa.superrduperr.bean.request.IdListBean;
import com.deltaa.superrduperr.bean.request.ReminderBean;
import com.deltaa.superrduperr.bean.request.TagBean;
import com.deltaa.superrduperr.entity.Item;

/**
 * Service interface for Item Entity
 * @author rajesh
 */
public interface ItemService {
	
	/**
	 * find all Item records page by page
	 * @param pagination info
	 * @return page of Item records
	 */
	Page<Item> findAll(Pageable pageable);
	
	/**
	 * mark item as completed by itemId
	 * @param itemId
	 * @return updated item
	 */
	Optional<Item> markItemAsCompleted(Long itemId);
	
	/**
	 * delete item by itemId
	 * @param itemId
	 * @return deleted item
	 */
	Optional<Item> deleteItem(Long itemId);
	
	/**
	 * delete items by itemIds
	 * @param itemIds
	 * @return deleted items
	 */
	List<Item> deleteItems(IdListBean idListBean);
	
	/**
	 * restore a deleted item by itemId
	 * @param itemId
	 * @return restored item
	 */
	Optional<Item> restoreItem(Long itemId);
	
	/**
	 * restore items by itemIds
	 * @param itemIds
	 * @return deleted items
	 */
	List<Item> restoreItems(IdListBean idListBean);
	
	/**
	 * tag item with info
	 * @param TagBean
	 * @return updated item
	 */
	Optional<Item> tagItem(TagBean tag);
	
	/**
	 * add reminder to a item
	 * @param ReminderBean
	 * @return updated item
	 */
	Optional<Item> addReminderToItem(ReminderBean reminder);
	
}
