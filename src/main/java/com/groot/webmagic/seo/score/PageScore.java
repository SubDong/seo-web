package com.groot.webmagic.seo.score;

import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Set;

/**
 * Created by caoyi on 2014-12-24.
 */
public interface PageScore extends SeoScore {
    /**
     * 1.1 标题（Title）中包含目标关键字
     * 页面的 title 标签中包含目标关键字的情况
     * 分值：6
     */
    ScoreItem scoreKeywordsInTitle(Set<String> targetKeywords,Document doc);

    /**
     * 1.2 网页内容的质量（原创、独一无二）
     * 信任百度的数据，搜索结果中，排在第一位的，认定为原创
     * 分值：5
     */
    ScoreItem scoreContentQuality(String title, String url);

    /**
     * 1.3 目标关键词在网页内容上的应用
     * 网页内容中包含目标关键字的情况
     * 分值：4.5
     *
     */
    ScoreItem scoreKeywordsInContent(Set<String> keywords, Document contents);

    /**
     * 1.4 网页的导出链接的质量和相关性
     * 本站指向外展的链接称为导出链接。检查 a 标签中的关键字情况
     * 分值：4.2
     *
     */
    ScoreItem scoreLinksQualityAndRelationship(Set<String> targetKeywords, Document doc,String domain);

    /**
     * 1.5 页面的年龄
     * 可以用百度的最后收录时间计算页面年龄
     * 分值：4
     * 暂不使用
     *TODO 按照评分文档中的需求这样取出来,并不是页面年龄且此评分想对用户或者SEO人员的意义并不大，所以暂时不启用此方法
     */
    @Deprecated
    ScoreItem scoreAgeOfPage(String htmlurl);


    /**
     * 1.6 目标关键词包含在H1标签中
     * H1 标签中包含目标关键字的情况
     * 分值：4
     *
     */
    ScoreItem scoreKeywordsInHeadOne(Set<String> keywords, Document contents);

    /**
     * 1.7 Url中包含目标关键字
     * 仅对英文有效。即 a 标签 href 值对关键词的包含情况
     * 分值：3.6
     *
     */
    ScoreItem scoreKeywordsInUrl(Set<String> targetKeywords,  Document doc);

    /**
     * 1.8 在Strong等标签中的包含目标关键字
     * Strong 标签中包含目标关键字的情况
     * 分值：3.4
     *
     */
    ScoreItem scoreKeywordsInStrong(Set<String> keywords,  Document contents);


    /**
     * 1.9 图片的alt属性中包含目标关键字
     * img 标签 alt 属性包含目标关键字的情况
     * 分值：3.1
     *
     */
    ScoreItem scoreKeywordsInImgAlt(Set<String> keywords,  Document contents);

    /**
     * 1.10 描述标签中包含目标关键字
     * meta[@name=”description”] 标签的 content 值包含目标关键字的情况
     * 分值：2.4
     *
     */
    ScoreItem scoreKeywordsInDescription(Set<String> targetKeywords,  Document doc);


    /**
     * 1.11 关键字标签中包含目标关键字
     * meta[@name=”keywords”] 标签的 content 值包含目标关键字的情况
     * 分值：1.4
     *
     */
    ScoreItem scoreGoalInKeywords(Set<String> targetKeywords,  Document doc);

}
