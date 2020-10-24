package com.codingchallenge.adtech.mapper;

import com.codingchallenge.adtech.entity.db.AdsEntity;
import com.codingchallenge.adtech.entity.db.DeliveryEntity;
import com.codingchallenge.adtech.model.DeliveryEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper implements Mapper<DeliveryEntity, DeliveryEvent> {

    @Override
    public DeliveryEntity toEntity(DeliveryEvent model) {
        DeliveryEntity entity = new DeliveryEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    @Override
    public DeliveryEvent toModel(DeliveryEntity entity) {
        DeliveryEvent model = new DeliveryEvent();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
