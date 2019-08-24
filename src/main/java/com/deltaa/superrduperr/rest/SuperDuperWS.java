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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.deltaa.superrduperr.bean.request.SuperDuperBean;
import com.deltaa.superrduperr.business.SuperDuperService;
import com.deltaa.superrduperr.entity.SuperDuper;
import com.deltaa.superrduperr.rest.util.PaginationUtil;
import com.deltaa.superrduperr.rest.util.ReponseUtil;

/**
 * REST controller class for SuperDuper Management
 * @author rajesh
 */
@RestController
@RequestMapping("/api/v1")
public class SuperDuperWS {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SuperDuperWS.class);
	
	private SuperDuperService superDuperService;

	@Autowired
	public void setSuperDuperService(SuperDuperService superDuperService) {
		this.superDuperService = superDuperService;
	}
	
	/**
	* GET  /super-duper : get all the SuperDuper Items page by page.
	* @param pageable the pagination information
	* @return the ResponseEntity with status 200 (OK) and the list of SuperDuper Items in body
	*/
	@GetMapping("/super-dupers")
	public ResponseEntity<List<SuperDuper>> getAll(Pageable pageable, HttpServletRequest request) {
		LOGGER.info("REST request to get a page of SuperDuper Items");
		Page<SuperDuper> page = superDuperService.findAll(pageable);
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI());
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, uriBuilder.toUriString());
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	/**
	* POST  /super-duper/add : add SuperDuper to entity.
	* @param SuperDuper
	* @return the ResponseEntity with status 200 (OK) and the list of SuperDuper Items in body
	*/
	@PostMapping("/super-duper/save")
	public ResponseEntity<SuperDuper> saveSuperDuper(@Valid @RequestBody SuperDuper superDuper) {
		LOGGER.info("REST request to add SuperDuper==[{}]", superDuper);
		superDuper = superDuperService.saveSuperDuper(superDuper);
		return new ResponseEntity<>(superDuper, HttpStatus.OK);
	}
	
	/**
	* POST  /super-duper/add-items : add items to SuperDuper entity.
	* @param SuperDuperBean
	* @return the ResponseEntity with status 200 (OK) and the list of SuperDuper Items in body
	*/
	@PostMapping("/super-duper/add-items")
	public ResponseEntity<SuperDuper> addItemsToSuperDuper(@Valid @RequestBody SuperDuperBean superDuperBean) {
		LOGGER.info("REST request to add items to SuperDuper==[{}]", superDuperBean);
		Optional<SuperDuper> superDuper = superDuperService.addItems(superDuperBean);
		return ReponseUtil.wrapOrNotFound(superDuper);
	}
}