package com.codingchallenge.adtech.mapper;

import com.codingchallenge.adtech.entity.db.ClickEntity;
import com.codingchallenge.adtech.model.ClickEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class ClickMapper implements Mapper<ClickEntity, ClickEvent> {

    @Override
    public ClickEntity toEntity(ClickEvent model) {
        ClickEntity entity = new ClickEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    @Override
    public ClickEvent toModel(ClickEntity entity) {
        ClickEvent model = new ClickEvent();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
