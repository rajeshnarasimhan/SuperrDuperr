package com.deltaa.superrduperr.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deltaa.superrduperr.bean.request.IdListBean;
import com.deltaa.superrduperr.bean.request.ReminderBean;
import com.deltaa.superrduperr.bean.request.TagBean;
import com.deltaa.superrduperr.business.ItemService;
import com.deltaa.superrduperr.entity.Item;
import com.deltaa.superrduperr.entity.Reminder;
import com.deltaa.superrduperr.entity.Tag;
import com.deltaa.superrduperr.enums.ItemStatus;
import com.deltaa.superrduperr.repository.ItemRepository;

/**
 * Service class implementation for Item Entity
 * @author rajesh
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	private ItemRepository itemRepository;
	
	@Autowired
	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	/**
	 * find all Item records page by page
	 * @param pagination info
	 * @return page of Item records
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Item> findAll(Pageable pageable) {
		LOGGER.info("Request to find all Customer Phone");
		return itemRepository.findAll(pageable);
	}

	/**
	 * mark item as completed by itemId
	 * @param itemId
	 * @return Optional containing marked Item or Empty optional if Item not found for the id
	 */
	@Override
	@Transactional
	public Optional<Item> markItemAsCompleted(Long itemId) {
		LOGGER.info("Request to mark Item as Completed by Id==[{}]", itemId);
		Optional<Item> itemOpt = itemRepository.findById(itemId);
		if(itemOpt.isPresent()) {
			LOGGER.info("Found Item to mark as complete. Id==[{}]", itemId);
			Item item = itemOpt.get();
			item.setStatus(ItemStatus.COMPLETED);
			item = itemRepository.saveAndFlush(item);
			return Optional.of(item);
		}
		return Optional.empty();
	}

	/**
	 * delete item by itemId
	 * @param itemId
	 * @return Optional containing deleted Item or Empty optional if Item not found for the id
	 */
	@Override
	@Transactional
	public Optional<Item> deleteItem(Long itemId) {
		LOGGER.info("Request to delete Item by Id==[{}]", itemId);
		Optional<Item> itemOpt = itemRepository.findById(itemId);
		if(itemOpt.isPresent()) {
			LOGGER.info("Found Item to Delete. Id==[{}]", itemId);
			Item item = itemOpt.get();
			item.setDeleted(true);
			item = itemRepository.saveAndFlush(item);
			return Optional.of(item);
		}
		return Optional.empty();
	}
	
	/**
	 * delete items by itemIds
	 * @param itemIds
	 * @return list of items deleted
	 */
	@Override
	@Transactional
	public List<Item> deleteItems(IdListBean idListBean) {
		LOGGER.info("Request to delete Items by Ids==[{}]", idListBean);
		List<Item> deletedItems = new ArrayList<Item>();
		idListBean.getIds().forEach( idBean -> {
			Long id = Long.valueOf(idBean.getId());
			Optional<Item> itemOpt = deleteItem(id);
			if(itemOpt.isPresent()) {
				deletedItems.add(itemOpt.get());
			}
		});
		return deletedItems;
	}

	/**
	 * restore a deleted item by itemId
	 * @param itemId
	 * @return Optional containing restored Item or Empty optional if Item not found for the id
	 */
	@Override
	@Transactional
	public Optional<Item> restoreItem(Long itemId) {
		LOGGER.info("Request to restore Item by Id==[{}]", itemId);
		Optional<Item> itemOpt = itemRepository.findById(itemId);
		if(itemOpt.isPresent()) {
			LOGGER.info("Found Item to Restore. Id==[{}]", itemId);
			Item item = itemOpt.get();
			item.setDeleted(false);
			item = itemRepository.saveAndFlush(item);
			return Optional.of(item);
		}
		return Optional.empty();
	}
	
	/**
	 * restore items by itemIds
	 * @param itemIds
	 * @return list of items restored
	 */
	@Override
	@Transactional
	public List<Item> restoreItems(IdListBean idListBean) {
		LOGGER.info("Request to restore Items by Ids==[{}]", idListBean);
		List<Item> restoredItems = new ArrayList<Item>();
		idListBean.getIds().forEach( idBean -> {
			Long id = Long.valueOf(idBean.getId());
			Optional<Item> itemOpt = restoreItem(id);
			if(itemOpt.isPresent()) {
				restoredItems.add(itemOpt.get());
			}
		});
		return restoredItems;
	}

	/**
	 * tag item with info
	 * @param TagBean
	 * @return updated item
	 */
	@Override
	@Transactional
	public Optional<Item> tagItem(TagBean tag) {
		LOGGER.info("Request to tag Item. Tag==[{}]", tag);
		Optional<Item> itemOpt = itemRepository.findById(tag.getItemId());
		if(itemOpt.isPresent()) {
			LOGGER.info("Found Item to add Tag. Item==[{}]", tag.getItemId());
			Item item = itemOpt.get();
			item.addTag(new Tag()
					.setTagDesc(tag.getTagDesc())
					.setItem(item));
			item = itemRepository.saveAndFlush(item);
			return Optional.of(item);
		}
		return Optional.empty();
	}
	
	/**
	 * add reminder to item with info
	 * @param ReminderBean
	 * @return updated item
	 */
	@Override
	@Transactional
	public Optional<Item> addReminderToItem(ReminderBean reminder) {
		LOGGER.info("Request to add Reminder to Item. Reminder==[{}]", reminder);
		Optional<Item> itemOpt = itemRepository.findById(reminder.getItemId());
		if(itemOpt.isPresent()) {
			LOGGER.info("Found Item to add Reminder. Item==[{}]", reminder.getItemId());
			Item item = itemOpt.get();
			item.addReminder(new Reminder()
					.setDesc(reminder.getDesc())
					.setDatetime(reminder.getDatetime())
					.setItem(item));
			item = itemRepository.saveAndFlush(item);
			return Optional.of(item);
		}
		return Optional.empty();
	}
}