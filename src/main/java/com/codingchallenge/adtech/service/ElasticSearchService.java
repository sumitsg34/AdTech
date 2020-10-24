package com.codingchallenge.adtech.service;

import com.codingchallenge.adtech.entity.elasticsearch.ElasticEvent;
import com.codingchallenge.adtech.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void saveDocument(ElasticEvent elasticEvent) throws PlatformException {
        try {
            elasticsearchRestTemplate.save(elasticEvent);
        } catch (DataAccessException accessException) {
            logger.error("failed to save delivery event in elasticsearch", accessException);
            throw new PlatformException(accessException);
        }
    }
}
