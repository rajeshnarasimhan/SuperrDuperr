package com.deltaa.superrduperr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deltaa.superrduperr.entity.Item;

/**
 * Repository interface for Item Entity
 * @author rajesh
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
