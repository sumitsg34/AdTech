package com.codingchallenge.adtech.repository;

import com.codingchallenge.adtech.entity.db.InstallEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstallRepository extends CrudRepository<InstallEntity, UUID> {
}
