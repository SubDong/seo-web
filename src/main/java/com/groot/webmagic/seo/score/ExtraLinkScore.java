package com.groot.webmagic.seo.score;

import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.jsoup.nodes.Document;

import java.util.Set;

/**
 * Created by caoyi on 2014-12-24.
 */
public interface ExtraLinkScore extends SeoScore {
    /**
     * 3.1 外部链接的锚文字包含目标关键字
     * 外部链接（外链）指其他网站到目标网站的链接。「站长工具」能查到相关信息
     * 分值：5.5
     */
    ScoreItem scoreKeywordsInExtLinks(Set<Document> docs, String url);

    /**
     * 3.2 网站的外部链接流行度、广泛度
     * 外链的本身的数量和来源数量越多，分值越高
     * 分值：5.5
     */
    ScoreItem scorePopulationOfExtLinks(String url);

    /**
     * 3.3 网站的外部链接页面内容与关键词的相关性
     * 爬取外链相关页面，并检查内容和关键词的相关性
     * 分值：4.7
     */
    ScoreItem scoreRelationOfPagesAndExtLinks();

    /**
     * 3.4 外部链接来源页面本身的外部链接数量
     * 爬取外链相关页面，并检查其外链数量，值越大，评分越高
     * 分值：4.3
     */
    ScoreItem scoreQuantityOfExtLinksOfExLinks();

    /**
     * 3.5 网站新外部链接产生的速率
     * 周期性查询外链数，并计算，可到速率值。更新越快，评分越高
     * 分值：4.2
     */
    ScoreItem scoreExtLinksFrequency(String url);

    /**
     * 3.6 外部链接的链接的年龄
     * 外链的添加时间。可以最早爬取到的时间来计算
     * 分值：3.8
     */
    ScoreItem scoreAgeofExtLinks(String url);

    /**
     * 3.7 外部链接周围的文字与目标关键字具有相关性
     * 分析外链所在页面，外链出现的位置。在正文中的评分较高
     * 分值：3.8
     */
    ScoreItem scoreArroundOfExtLinks();

}
