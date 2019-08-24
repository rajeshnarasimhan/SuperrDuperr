package com.deltaa.superrduperr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deltaa.superrduperr.entity.SuperDuper;

/**
 * Repository interface for SuperDuper Entity
 * @author rajesh
 */
@Repository
public interface SuperDuperRepository extends JpaRepository<SuperDuper, Long>{

}
