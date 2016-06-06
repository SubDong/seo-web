//package com.groot.web.services.impl;
//
//import com.groot.utils.PageUtils;
//import com.groot.web.services.ScoreService;
//import com.groot.webmagic.seo.score.SiteScore;
//import com.groot.webmagic.seo.score.impl.SiteScoreImpl;
//import com.groot.webmagic.seo.score.vo.ScoreItem;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by caoyi on 2015-1-7.
// */
//@Service
//public class ScoreServiceImpl implements ScoreService {
//
//    /**
//     * 计算网页质量
//     *
//     * @param url
//     * @return
//     */
//    @Override
//    public List<ScoreItem> calcPageScore(String url) {
//        // 调用 MapReduce 计算
//
//        return null;
//    }
//
//    /**
//     * 计算网站质量
//     *
//     * @param url
//     * @return
//     */
//    @Override
//    public List<ScoreItem> calcSiteScore(String url) {
//        List<ScoreItem> result = new ArrayList<>();
//
//        SiteScore siteScore = new SiteScoreImpl();
//        result.add(siteScore.scoreAgeOfDomainName(url));
//        result.add(siteScore.scorePostfixOfDomainName(url));
//        result.add(siteScore.scoreQuantitySearchEngineInclude(url));
//
//        PageUtils.modifyScoreForFront(result);
//
//        return result;
//    }
//
//    /**
//     * 计算外链评分
//     *
//     * @param url
//     * @return
//     */
//    @Override
//    public List<ScoreItem> clacExtraLinkScore(String url) {
//        return null;
//    }
//}
