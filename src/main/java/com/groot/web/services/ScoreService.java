package com.groot.web.services;

import com.groot.webmagic.seo.score.vo.ScoreItem;

import java.util.List;

/**
 * Created by caoyi on 2015-1-7.
 */
public interface ScoreService {

    /**
     *
     * 计算网页质量
     *
     * @param url
     * @return
     */
    List<ScoreItem> calcPageScore(String url);

    /**
     *
     * 计算网站质量
     *
     * @param url
     * @return
     */
    List<ScoreItem> calcSiteScore(String url);

    /**
     *
     * 计算外链评分
     *
     * @param url
     * @return
     */
    List<ScoreItem> clacExtraLinkScore(String url);

}
