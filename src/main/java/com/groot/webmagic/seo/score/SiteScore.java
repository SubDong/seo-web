package com.groot.webmagic.seo.score;

import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * Created by caoyi on 2014-12-24.
 */
public interface SiteScore extends SeoScore {
    /**
     * 2.1 网站更新的频率
     * 爬取网站多次后，可以做此项检查
     * 分值：5
     */
    ScoreItem scoreUpdateFrequency();

    /**
     * 2.2 网站收录数量
     * 以百度的搜录数量为计算依据
     * 分值：4
     */
    ScoreItem scoreQuantitySearchEngineInclude(String url);

    /**
     * 2.3 网站主题与目标关键字具有相关性
     * 可以理解为网站各页面与关键字相关性的一个汇总处理
     * 分值：3.8
     */
    ScoreItem scoreRelationOfSiteAndKeywords();

    /**
     * 2.4 网站域名中包含目标关键字
     * 仅对英文有效。域名中包含目标关键字的情况
     * 分值：3.6
     */
    ScoreItem scoreDomainAge(Set<Document> doc,String domain);

    /**
     * 2.5 域名年龄
     * 对域名的注册时间评分，注册时间可通过 whois 查询
     * 分值：3.6
     */
    ScoreItem scoreAgeOfDomainName(String url);

    /**
     * 2.6 网站的结构层次
     * 网站目录深度情况，目录越深，分值越低
     * 分值：3.4
     */
    ScoreItem scoreArchiOfSite();

    /**
     * 2.7 域名后缀
     * 网站可信度情况。一般 .edu, .gov 的域名分值较高
     * 分值：3.2
     */
    ScoreItem scorePostfixOfDomainName(String url);
}
