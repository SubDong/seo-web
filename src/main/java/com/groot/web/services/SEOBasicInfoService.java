package com.groot.web.services;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2015/1/30.
 */
public interface SEOBasicInfoService {
    /**
     * 网站基本信息
     *
     * @param url
     * @return
     */
    List<Map<String, String>> getWebsiteBasic(String url);

    /**
     * 搜索引擎基本信息
     *
     * @param url
     * @return
     */
    List<Map<String, String>> getSearchInfo(String url);

    /**
     * 网页标签信息
     *
     * @param url
     * @return
     */
    List<Map<String, String>> getContentInfo(String url);
}
