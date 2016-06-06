package com.groot.web.services;

import com.groot.webmagic.seo.score.vo.ScoreItem;

import java.util.List;

/**
 * Created by SubDong on 2015/1/30.
 */
public interface DomainScoreItemsService {

    /**
     * 2.2 网站收录数量
     * 以百度的搜录数量为计算依据
     * 分值：4
     *
     * @param url
     * @return
     */
    public ScoreItem scoreQuantitySearchEngineInclude(String url);

    /**
     * 2.4 网站域名中包含目标关键字
     * 网站域名中包含目标关键字
     *
     * @param url 网站域名
     * @return
     */
    public ScoreItem scoreDomainName(String url);

    /**
     * 2.5 域名年龄
     * 对域名的注册时间评分，注册时间可通过 whois 查询
     * 分值：3.6
     *
     * @param url
     * @return
     */
    public ScoreItem scoreAgeOfDomainName(String url);

    /**
     * 2.7 域名后缀
     * 网站可信度情况。一般 .edu, .gov 的域名分值较高
     * 分值：3.2
     *
     * @param url
     * @return
     */
    public ScoreItem scorePostfixOfDomainName(String url);

    /**
     * 3.1 外部链接的锚文字包含目标关键字
     * 外部链接（外链）指其他网站到目标网站的链接。「站长工具」能查到相关信息
     * 分值：5.5
     *
     * @param url
     * @return
     */
    public ScoreItem scoreKeywordsInExtLinks(String url);

    /**
     * 3.2 网站的外部链接流行度、广泛度
     * 外链的本身的数量和来源数量越多，分值越高
     * 分值：5.5
     *
     * @param url
     * @return
     */
    public ScoreItem scorePopulationOfExtLinks(String url);

    /**
     * 3.5 网站新外部链接产生的速率
     * 周期性查询外链数，并计算，可到速率值。更新越快，评分越高
     * 分值：4.2
     *
     * @param url
     * @return
     */
    public ScoreItem scoreExtLinksFrequency(String url);

    /**
     * 3.6 外部链接的链接的年龄
     * 外链的添加时间。可以最早爬取到的时间来计算
     * 分值：3.8
     *
     * @param url
     * @return
     */
    public ScoreItem scoreAgeofExtLinks(String url);

    /**
     * 获取所有评分项信息
     *
     * @param url
     * @return
     */
    List<ScoreItem> calculate(String url);
}
