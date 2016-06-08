package com.groot.mongodb.dao;

import com.groot.commons.dto.BaseDTO;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by subDong on 2015/1/8.
 */
public interface BaseInterface<T extends BaseDTO,ID extends Serializable> {
    T save(T dto);
    T findOne(ID id);
    public <E> Class<E> getEntityClass();
    public Class<T> getDTOClass();
}
