package com.groot.mongodb.dao.impl;

import com.groot.commons.dto.BaseDTO;
import com.groot.mongodb.base.BaseMongoTemplate;
import com.groot.mongodb.dao.BaseInterface;
import com.groot.utils.ObjectUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by subDong on 2015/1/8.
 */
public abstract class BaseRepository<T extends BaseDTO, ID extends Serializable> implements BaseInterface<T, ID> {

    public MongoTemplate getMongoTemplate() {
        return BaseMongoTemplate.getSeoMongo();
    }

    public MongoTemplate getSystemMongoTemplate() {
        return BaseMongoTemplate.getSysMong();
    }


    protected <S> List<T> convert(List<S> entities) {
        Objects.requireNonNull(entities);
        List<T> list = new ArrayList<>(entities.size());
        for (S entity : entities) {
            T dto = ObjectUtils.convert(entity, getDTOClass());
            list.add(dto);
        }
        return list;
    }

    protected <S, D> List<D> convertByClass(List<S> entities, Class<D> clz) {
        Objects.requireNonNull(entities);
        List<D> list = new ArrayList<>(entities.size());
        for (S entity : entities) {
            D dto = ObjectUtils.convert(entity, clz);
            list.add(dto);
        }
        return list;
    }

    @Override
    public T save(T dto) {
        Object entity = ObjectUtils.convert(dto, getEntityClass());
        getMongoTemplate().save(entity);
        return dto;
    }

    @Override
    public T findOne(ID id) {
        Object entity = getMongoTemplate().findById(id, getEntityClass());
        return ObjectUtils.convert(entity, getDTOClass());
    }



}
