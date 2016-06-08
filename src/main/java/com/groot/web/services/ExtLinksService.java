package com.groot.web.services;

import com.groot.commons.dto.ExtLinksDTO;

import java.util.Map;

/**
 * Created by subDong on 2015/1/8.
 */
public interface ExtLinksService  {
    ExtLinksDTO save(ExtLinksDTO dto);
    ExtLinksDTO findOne(String id);

    /**
     * 根据参数查询当个实体，可以通用
     * @param params
     * @return
     */
    ExtLinksDTO findParams(Map<String,Object> params);

    /**
     * 根据url地址查询数据库中最近一条数据的外链数量
     * @param url
     * @return
     */
    int getNearExtLinksCount(String url);
}
