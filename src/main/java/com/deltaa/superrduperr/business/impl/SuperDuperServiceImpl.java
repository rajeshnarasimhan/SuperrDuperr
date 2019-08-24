package com.deltaa.superrduperr.business.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deltaa.superrduperr.bean.request.SuperDuperBean;
import com.deltaa.superrduperr.business.SuperDuperService;
import com.deltaa.superrduperr.entity.Item;
import com.deltaa.superrduperr.entity.SuperDuper;
import com.deltaa.superrduperr.enums.ItemStatus;
import com.deltaa.superrduperr.repository.SuperDuperRepository;

/**
 * Service class implementation for SuperDuper Entity
 * @author rajesh
 */
@Service
@Transactional
public class SuperDuperServiceImpl implements SuperDuperService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(SuperDuperServiceImpl.class);
	
	private SuperDuperRepository superDuperRepository;
	
	@Autowired
	public void setSuperDuperRepository(SuperDuperRepository superDuperRepository) {
		this.superDuperRepository = superDuperRepository;
	}

	/**
	 * find all Item records page by page
	 * @param pagination info
	 * @return page of Item records
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<SuperDuper> findAll(Pageable pageable) {
		LOGGER.info("Request to find all SuperDuper Items List");
		return superDuperRepository.findAll(pageable);
	}

	/**
	 * save superduper to the entity
	 * @param superduper entity to save
	 * @return saved superduper
	 */
	@Override
	public SuperDuper saveSuperDuper(SuperDuper superDuper) {
		LOGGER.info("Request to save the SuperDuper==[{}]", superDuper);
		return superDuperRepository.saveAndFlush(superDuper);
	}

	@Override
	public Optional<SuperDuper> addItems(SuperDuperBean superDuperBean) {
		LOGGER.info("Request to add items to SuperDuper==[{}]", superDuperBean.getSuperDuperId());
		Optional<SuperDuper> superDuperOpt = superDuperRepository.findById(superDuperBean.getSuperDuperId());
		if(superDuperOpt.isPresent()) {
			LOGGER.info("Found SuperDuper to add items. Items==[{}]", superDuperBean.getItems().size());
			final SuperDuper superDuper = superDuperOpt.get();
			superDuperBean.getItems().forEach( item -> {
				LOGGER.info("Adding Item to SuperDuper==[{}]. item==[{}]", superDuperBean.getSuperDuperId(), item);
				superDuper.addItem(new Item()
						.setName(item.getName())
						.setStatus(ItemStatus.fromName(item.getStatus(), ItemStatus.STARTED))
						.setSuperDuper(superDuper));
			});
			return Optional.of(superDuperRepository.saveAndFlush(superDuper));
		}
		return Optional.empty();
	}
}