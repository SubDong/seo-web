package com.groot.mongodb.dao;

import com.groot.commons.dto.ExtLinksDTO;

import java.util.Map;

/**
 * Created by subDong on 2015/1/8.
 */
public interface ExtLinksDAO extends BaseInterface<ExtLinksDTO,String> {
    /**
     * 根据参数查询
     * @param params
     * @return
     */
    public ExtLinksDTO findByParams(Map<String,Object> params);
    public int getMaxCount(String url);
    /**
     * 根据url地址查询数据库中最近一条数据的外链数量
     * @param url
     * @return
     */
    int getNearExtLinksCount(String url);
}
