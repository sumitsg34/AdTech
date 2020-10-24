package com.codingchallenge.adtech.service;

import com.codingchallenge.adtech.entity.db.AdsEntity;
import com.codingchallenge.adtech.entity.db.ClickEntity;
import com.codingchallenge.adtech.entity.db.DeliveryEntity;
import com.codingchallenge.adtech.entity.db.InstallEntity;
import com.codingchallenge.adtech.entity.elasticsearch.ElasticEvent;
import com.codingchallenge.adtech.entity.elasticsearch.EventTypeEnum;
import com.codingchallenge.adtech.exception.DataNotFoundException;
import com.codingchallenge.adtech.exception.PlatformException;
import com.codingchallenge.adtech.mapper.AdsMapper;
import com.codingchallenge.adtech.mapper.ClickMapper;
import com.codingchallenge.adtech.mapper.DeliveryMapper;
import com.codingchallenge.adtech.mapper.InstallMapper;
import com.codingchallenge.adtech.model.AdsEvent;
import com.codingchallenge.adtech.model.ClickEvent;
import com.codingchallenge.adtech.model.DeliveryEvent;
import com.codingchallenge.adtech.model.InstallEvent;
import com.codingchallenge.adtech.repository.AdsRepository;
import com.codingchallenge.adtech.repository.ClickRepository;
import com.codingchallenge.adtech.repository.DeliveryRepository;
import com.codingchallenge.adtech.repository.InstallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IngestService {

    private static final Logger logger = LoggerFactory.getLogger(IngestService.class);

    // Repositories
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private ClickRepository clickRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private InstallRepository installRepository;

    // other service classes
    @Autowired
    private ElasticSearchService elasticSearchService;

    //Utils
    @Autowired
    private AdsMapper adsMapper;
    @Autowired
    private ClickMapper clickMapper;
    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private InstallMapper installMapper;

    public UUID save(AdsEvent ad) throws PlatformException {
        AdsEntity entity = adsMapper.toEntity(ad);
        try {
            entity = adsRepository.save(entity);
            logger.debug("Ads got created in db successfully");
            return entity.getAdId();
        } catch (DataAccessException accessException) {
            logger.error("failed to save ad in db", accessException);
            throw new PlatformException(accessException);
        }
    }

    public void save(DeliveryEvent deliveryEvent) throws PlatformException, DataNotFoundException {
        try {
            //1. check if ad is present
            Optional<AdsEntity> adsEntity = adsRepository.findById(deliveryEvent.getAdId());
            if (!adsEntity.isPresent()) {
                throw new DataNotFoundException("No ad found with the id " + deliveryEvent.getAdId());
            }

            //2. save delivery event if ad is present
            DeliveryEntity deliveryEntity = deliveryMapper.toEntity(deliveryEvent);
            deliveryRepository.save(deliveryEntity);
            logger.debug("successfully saved delivery details in db with deliveryId {}", deliveryEntity.getDeliveryId());

            //3.
            ElasticEvent elasticEvent = new ElasticEvent();
            BeanUtils.copyProperties(deliveryEvent, elasticEvent);
            elasticEvent.setEventId(UUID.randomUUID());
            elasticEvent.setEventType(EventTypeEnum.DELIVERY);
            elasticSearchService.saveDocument(elasticEvent);
        } catch (DataAccessException accessException) {
            logger.error("failed to save delivery event in db", accessException);
            throw new PlatformException(accessException);
        }
    }

    public void save(ClickEvent event) throws PlatformException, DataNotFoundException {
        try {
            //1. check if delivery event is present
            Optional<DeliveryEntity> deliveryEntity = deliveryRepository.findById(event.getDeliveryId());
            if (!deliveryEntity.isPresent()) {
                throw new DataNotFoundException("No delivery found with the id " + event.getDeliveryId());
            }

            //2. save click event if ad is present
            ClickEntity clickEntity = clickMapper.toEntity(event);
            clickRepository.save(clickEntity);
            logger.debug("successfully saved click details in db with clickId {}", clickEntity.getClickId());

            //3.
            ElasticEvent elasticEvent = new ElasticEvent();
            BeanUtils.copyProperties(deliveryEntity.get(), elasticEvent);
            BeanUtils.copyProperties(clickEntity, elasticEvent);

            elasticEvent.setTime(event.getTime());
            elasticEvent.setEventId(UUID.randomUUID());
            elasticEvent.setEventType(EventTypeEnum.CLICK);
            elasticSearchService.saveDocument(elasticEvent);
        } catch (DataAccessException accessException) {
            logger.error("failed to save click event in db", accessException);
            throw new PlatformException(accessException);
        }
    }

    public void save(InstallEvent event) throws PlatformException, DataNotFoundException {
        try {
            //1. check if delivery event is present
            Optional<ClickEntity> clickEntity = clickRepository.findById(event.getClickId());
            if (!clickEntity.isPresent()) {
                throw new DataNotFoundException("No click event found with the id " + event.getClickId());
            }

            Optional<DeliveryEntity> deliveryEntity = deliveryRepository.findById(clickEntity.get().getDeliveryId());
            if (!deliveryEntity.isPresent()) {
                throw new DataNotFoundException("No delivery event found with the id " + clickEntity.get().getDeliveryId());
            }

            //2. save click event if ad is present
            InstallEntity installEntity = installMapper.toEntity(event);
            BeanUtils.copyProperties(clickEntity.get(), clickEntity);
            installRepository.save(installEntity);
            logger.debug("successfully saved install event details in db with clickId {}", installEntity.getInstallId());

            //3.
            ElasticEvent elasticEvent = new ElasticEvent();
            BeanUtils.copyProperties(deliveryEntity.get(), elasticEvent);
            BeanUtils.copyProperties(installEntity, elasticEvent);

            elasticEvent.setTime(event.getTime());
            elasticEvent.setEventId(UUID.randomUUID());
            elasticEvent.setEventType(EventTypeEnum.INSTALL);
            elasticSearchService.saveDocument(elasticEvent);
        } catch (DataAccessException accessException) {
            logger.error("failed to save install event in db", accessException);
            throw new PlatformException(accessException);
        }
    }


    private <T> void buildAndSaveDocument(T object, EventTypeEnum eventTypeEnum) throws PlatformException {
        ElasticEvent elasticEvent = new ElasticEvent();
        BeanUtils.copyProperties(object, elasticEvent);

        elasticEvent.setEventId(UUID.randomUUID());
        elasticEvent.setEventType(eventTypeEnum);

        elasticSearchService.saveDocument(elasticEvent);
    }

}
