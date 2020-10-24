package com.codingchallenge.adtech.repository;

import com.codingchallenge.adtech.entity.db.AdsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdsRepository extends CrudRepository<AdsEntity, UUID> {
}
