package com.codingchallenge.adtech.service;

import com.codingchallenge.adtech.constant.ServiceConstants;
import com.codingchallenge.adtech.entity.elasticsearch.ElasticEvent;
import com.codingchallenge.adtech.exception.InvalidInputException;
import com.codingchallenge.adtech.exception.PlatformException;
import com.codingchallenge.adtech.model.Data;
import com.codingchallenge.adtech.model.Interval;
import com.codingchallenge.adtech.model.OverallStatisticsResponse;
import com.codingchallenge.adtech.model.Statistic;
import com.codingchallenge.adtech.model.StatisticsResponse;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codingchallenge.adtech.constant.ServiceConstants.EVENT_TYPE;
import static com.codingchallenge.adtech.entity.elasticsearch.EventTypeEnum.CLICK;
import static com.codingchallenge.adtech.entity.elasticsearch.EventTypeEnum.DELIVERY;
import static com.codingchallenge.adtech.entity.elasticsearch.EventTypeEnum.INSTALL;

@Service
public class StatisticService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticService.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public OverallStatisticsResponse getOverallAdsStatistics(Date start, Date end) throws PlatformException {

        if (start.after(end))
            throw new InvalidInputException("Start date is greater than end date");

        // fetch events
        SearchHits<ElasticEvent> events = fetchEventsFromElasticSearch(start, end);

        // populate stats
        Statistic statistic = buildStatistics(events);

        //build the overall response
        OverallStatisticsResponse overallStatisticsResponse = new OverallStatisticsResponse();
        overallStatisticsResponse.setStatistics(statistic);
        overallStatisticsResponse.setInterval(new Interval(start, end));
        return overallStatisticsResponse;
    }

    private SearchHits<ElasticEvent> fetchEventsFromElasticSearch(Date start, Date end) throws PlatformException {
        SearchHits<ElasticEvent> events;
        try {
            NativeSearchQuery searchQuery = buildAndGetAggregationQuery(start, end);
            events = elasticsearchRestTemplate
                    .search(searchQuery, ElasticEvent.class, IndexCoordinates.of(ServiceConstants.INDEX_NAME));
        } catch (DataAccessException exception) {
            logger.error("failed to get statistics from elastic search due to ", exception);
            throw new PlatformException(exception);
        }
        return events;
    }

    private Statistic buildStatistics(SearchHits<ElasticEvent> events) {
        Statistic statistic = null;
        if (events != null && events.hasAggregations()) {
            ParsedStringTerms termsAggregator = (ParsedStringTerms) events.getAggregations().getAsMap().get(EVENT_TYPE);
            statistic = buildStats(termsAggregator);
        }
        return statistic;
    }

    private Statistic buildStats(ParsedStringTerms termsAggregator) {
        Statistic statistic = new Statistic();
        termsAggregator.getBuckets().forEach(b -> {
            if (DELIVERY.name().equalsIgnoreCase(b.getKeyAsString())) {
                statistic.setDeliveryCount(b.getDocCount());
            } else {
                if (CLICK.name().equalsIgnoreCase(b.getKeyAsString())) {
                    statistic.setClickCount(b.getDocCount());
                } else {
                    if (INSTALL.name().equalsIgnoreCase(b.getKeyAsString())) {
                        statistic.setInstallCount(b.getDocCount());
                    }
                }
            }
        });
        return statistic;
    }

    private NativeSearchQuery buildAndGetAggregationQuery(Date start, Date end) {
        // Aggregation based on event types e.g Delivery, Click etc
        TermsAggregationBuilder aggregation = AggregationBuilders
                .terms(EVENT_TYPE)
                .field(EVENT_TYPE);

        // Range query for event time
        RangeQueryBuilder builder = new RangeQueryBuilder("time");
        builder.gte(start.getTime());
        builder.lte(end.getTime());

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(aggregation)
                .withQuery(builder)
                .withPageable(PageRequest.of(0, 1))
                .build();
        return searchQuery;
    }

    public StatisticsResponse getAdsStatistics(Date start, Date end, List<String> categories) throws PlatformException {
        if (start.after(end))
            throw new InvalidInputException("Start date is greater than end date");
        if (categories.isEmpty())
            throw new InvalidInputException("No categories provided. Atleast one category should be provided");

        // building aggregation query
        NativeSearchQuery searchQuery = buildAggregationQuery(start, end, categories);

        SearchHits<ElasticEvent> events = null;
        try {
            events = elasticsearchRestTemplate
                    .search(searchQuery, ElasticEvent.class, IndexCoordinates.of(ServiceConstants.INDEX_NAME));
        } catch (DataAccessException dataAccessException) {
            logger.error("failed to fetch events from elasticsearch due to ", dataAccessException);
            throw new PlatformException(dataAccessException);
        }
        List<Data> dataList = new ArrayList<>();
        if (events != null && events.hasAggregations()) {
            buildStatistics(events.getAggregations().asMap(), new HashMap<>(), dataList, "");
        }
        //build the overall response
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setInterval(new Interval(start, end));
        statisticsResponse.setData(dataList);
        return statisticsResponse;
    }

    private NativeSearchQuery buildAggregationQuery(Date start, Date end, List<String> categories) {
        // Aggregation
        TermsAggregationBuilder firstAggregation = AggregationBuilders
                .terms(categories.get(0))
                .field(categories.get(0));

        TermsAggregationBuilder temp = firstAggregation;

        // adding remaining aggregations as sub-aggregations
        for (int i = 1; i < categories.size(); i++) {
            TermsAggregationBuilder curr = AggregationBuilders
                    .terms(categories.get(i))
                    .field(categories.get(i));
            temp.subAggregation(curr);
            temp = curr;
        }

        // Aggregation based on event types e.g Delivery, Click etc
        TermsAggregationBuilder eventTypeAggr = AggregationBuilders
                .terms(EVENT_TYPE)
                .field(EVENT_TYPE);
        temp.subAggregation(eventTypeAggr);

        // Range query for event time
        RangeQueryBuilder builder = new RangeQueryBuilder("time");
        builder.gte(start.getTime());
        builder.lte(end.getTime());

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .addAggregation(firstAggregation)
                .withQuery(builder)
                .withPageable(PageRequest.of(0, 1))
                .build();
        return searchQuery;
    }

    private void buildStatistics(Map<String, Aggregation> aggr, Map<String, String> fields, List<Data> dataList, String lastKey) {
        for (Map.Entry<String, Aggregation> entry : aggr.entrySet()) {
            ParsedStringTerms terms = (ParsedStringTerms) entry.getValue();
            //event_type aggregation will be always the last aggregation in the chain
            if (EVENT_TYPE.equalsIgnoreCase(entry.getKey())) {
                Statistic statistic = buildStats(terms);

                // building stats
                Data data = new Data();
                data.setStats(statistic);
                // this is will provide fields aggregate so far
                data.setFields(new HashMap(fields));
                dataList.add(data);

                // remove this bucket
                fields.remove(lastKey);
            } else {
                for (Terms.Bucket b : terms.getBuckets()) {
                    fields.put(entry.getKey(), b.getKeyAsString());
                    buildStatistics(b.getAggregations().getAsMap(), fields, dataList, entry.getKey());
                }
            }
        }
    }


}
