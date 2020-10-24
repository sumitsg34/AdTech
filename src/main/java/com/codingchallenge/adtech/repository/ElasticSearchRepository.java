package com.codingchallenge.adtech.repository;

import com.codingchallenge.adtech.entity.elasticsearch.ElasticEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;


public interface ElasticSearchRepository extends ElasticsearchRepository<ElasticEvent, UUID> {

   //

}
