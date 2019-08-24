package com.deltaa.superrduperr.business;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deltaa.superrduperr.bean.request.SuperDuperBean;
import com.deltaa.superrduperr.entity.SuperDuper;

/**
 * Service interface for SuperDuper Entity
 * @author rajesh
 */
public interface SuperDuperService {
	
	/**
	 * find all records page by page
	 * @param pagination info
	 * @return page of SuperDuper records
	 */
	Page<SuperDuper> findAll(Pageable pageable);
	
	/**
	 * save superduper to the entity
	 * @param superduper entity to save
	 * @return saved superduper
	 */
	SuperDuper saveSuperDuper(SuperDuper superDuper);
	
	/**
	 * add items to superduper entity
	 * @param SuperDuperBean
	 * @return saved superduper
	 */
	Optional<SuperDuper> addItems(SuperDuperBean superDuperBean);
}
