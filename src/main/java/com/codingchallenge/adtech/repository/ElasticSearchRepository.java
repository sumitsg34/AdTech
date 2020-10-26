package com.codingchallenge.adtech.repository;

import com.codingchallenge.adtech.entity.elasticsearch.ElasticEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticEvent, UUID> {

}
