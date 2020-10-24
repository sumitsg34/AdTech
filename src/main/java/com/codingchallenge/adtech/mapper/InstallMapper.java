package com.codingchallenge.adtech.mapper;

import com.codingchallenge.adtech.entity.db.InstallEntity;
import com.codingchallenge.adtech.model.InstallEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class InstallMapper implements Mapper<InstallEntity, InstallEvent> {

    @Override
    public InstallEntity toEntity(InstallEvent model) {
        InstallEntity entity = new InstallEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    @Override
    public InstallEvent toModel(InstallEntity entity) {
        InstallEvent model = new InstallEvent();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
