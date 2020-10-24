package com.codingchallenge.adtech.mapper;

public interface Mapper<E, M> {

    E toEntity(M model);

    M toModel(E entity);

}
