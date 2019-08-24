package com.deltaa.superrduperr.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.deltaa.superrduperr.bean.request.IdListBean;
import com.deltaa.superrduperr.bean.request.ReminderBean;
import com.deltaa.superrduperr.bean.request.TagBean;
import com.deltaa.superrduperr.business.ItemService;
import com.deltaa.superrduperr.entity.Item;
import com.deltaa.superrduperr.rest.util.PaginationUtil;
import com.deltaa.superrduperr.rest.util.ReponseUtil;

/**
 * REST controller class for Item Management
 * @author rajesh
 */
@RestController
@RequestMapping("/api/v1")
public class ItemWS {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ItemWS.class);
	
	private ItemService itemService;

	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	/**
	* GET  /items : get all the Items page by page.
	* @param pageable the pagination information
	* @return the ResponseEntity with status 200 (OK) and the list of Item in body
	*/
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAll(Pageable pageable, HttpServletRequest request) {
		LOGGER.info("REST request to get a page of Item");
		Page<Item> page = itemService.findAll(pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, uriBuilder.toUriString());
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	/**
	* GET  /item/mark/{itemId} : mark item as completed by itemId.
	* @param itemId
	* @return the ResponseEntity with status 200 (OK) and updated Item in body
	* if Item not available for the id, then ResponseEntity with status 404 (NOT_FOUND)
	*/
	@GetMapping("/item/mark/{itemId}")
	public ResponseEntity<Item> markItemAsCompleted(@PathVariable Long itemId) {
		LOGGER.info("REST request to mark Item as Completed by ItemId=[{}]", itemId);
		Optional<Item> item = itemService.markItemAsCompleted(itemId);
		return ReponseUtil.wrapOrNotFound(item);
	}
	
	/**
	* GET  /item/delete/{itemId} : delete item by itemId.
	* @param itemId
	* @return the ResponseEntity with status 200 (OK) and updated Item in body
	* if Item not available for the id, then ResponseEntity with status 404 (NOT_FOUND)
	*/
	@GetMapping("/item/delete/{itemId}")
	public ResponseEntity<Item> deleteItem(@PathVariable Long itemId) {
		LOGGER.info("REST request to Delete Item by ItemId=[{}]", itemId);
		Optional<Item> item = itemService.deleteItem(itemId);
		return ReponseUtil.wrapOrNotFound(item);
	}
	
	/**
	* POST  /items/delete : delete items by itemId list.
	* @param idListBean
	* @return the ResponseEntity with status 200 (OK) and updated Items in body
	*/
	@PostMapping("/items/delete")
	public ResponseEntity<List<Item>> deleteItems(@Valid @RequestBody IdListBean idListBean) {
		LOGGER.info("REST request to Delete Items by ItemIds=[{}]", idListBean);
		List<Item> deletedItems = itemService.deleteItems(idListBean);
		return new ResponseEntity<List<Item>>(deletedItems, HttpStatus.OK);
	}
	
	/**
	* GET  /item/restore/{itemId} : restore item by itemId.
	* @param itemId
	* @return the ResponseEntity with status 200 (OK) and updated Item in body
	* if Item not available for the id, then ResponseEntity with status 404 (NOT_FOUND)
	*/
	@GetMapping("/item/restore/{itemId}")
	public ResponseEntity<Item> restoreItem(@PathVariable Long itemId) {
		LOGGER.info("REST request to Restore Item by ItemId=[{}]", itemId);
		Optional<Item> item = itemService.restoreItem(itemId);
		return ReponseUtil.wrapOrNotFound(item);
	}
	
	/**
	* POST  /items/restore : restore items by itemId list.
	* @param idListBean
	* @return the ResponseEntity with status 200 (OK) and updated Items in body
	*/
	@PostMapping("/items/restore")
	public ResponseEntity<List<Item>> restoreItems(@Valid @RequestBody IdListBean idListBean) {
		LOGGER.info("REST request to Delete Items by ItemIds=[{}]", idListBean);
		List<Item> deletedItems = itemService.restoreItems(idListBean);
		return new ResponseEntity<List<Item>>(deletedItems, HttpStatus.OK);
	}
	
	/**
	* POST  /item/add-tag : tag item with the info.
	* @param TagBean
	* @return the ResponseEntity with status 200 (OK) and updated Item in body
	* if Item not available for the id, then ResponseEntity with status 404 (NOT_FOUND)
	*/
	@PostMapping("/item/add-tag")
	public ResponseEntity<Item> tagItem(@Valid @RequestBody TagBean tag) {
		LOGGER.info("REST request to tag Item. Tag=[{}]", tag);
		Optional<Item> item = itemService.tagItem(tag);
		return ReponseUtil.wrapOrNotFound(item);
	}
	
	/**
	* POST  /item/add-reminder : add reminder to item.
	* @param ReminderBean
	* @return the ResponseEntity with status 200 (OK) and updated Item in body
	* if Item not available for the id, then ResponseEntity with status 404 (NOT_FOUND)
	*/
	@PostMapping("/item/add-reminder")
	public ResponseEntity<Item> addReminderToItem(@Valid @RequestBody ReminderBean reminder) {
		LOGGER.info("REST request to add Reminder to Item. Reminder=[{}]", reminder);
		Optional<Item> item = itemService.addReminderToItem(reminder);
		return ReponseUtil.wrapOrNotFound(item);
	}
}