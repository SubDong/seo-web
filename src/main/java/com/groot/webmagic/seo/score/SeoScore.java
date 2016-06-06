package com.groot.webmagic.seo.score;

import com.groot.webmagic.seo.score.vo.ScoreItem;

import java.util.List;

/**
 * Created by caoyi on 2014-12-4.
 */
public interface SeoScore {
    List<ScoreItem> calculate();
}
