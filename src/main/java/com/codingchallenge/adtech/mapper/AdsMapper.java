package com.codingchallenge.adtech.mapper;

import com.codingchallenge.adtech.entity.db.AdsEntity;
import com.codingchallenge.adtech.model.AdsEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AdsMapper implements Mapper<AdsEntity, AdsEvent> {

    @Override
    public AdsEntity toEntity(AdsEvent model) {
        AdsEntity adsEntity = new AdsEntity();
        BeanUtils.copyProperties(model, adsEntity);
        return adsEntity;
    }

    @Override
    public AdsEvent toModel(AdsEntity entity) {
        AdsEvent adsEvent = new AdsEvent();
        BeanUtils.copyProperties(entity, adsEvent);
        return adsEvent;
    }
}
