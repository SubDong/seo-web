//package com.groot.webmagic.seo.score.impl;
//
//import com.groot.utils.ChineseUtils;
//import com.groot.webmagic.seo.score.SiteScore;
//import com.groot.webmagic.seo.score.vo.ScoreItem;
//import org.joda.time.Instant;
//import org.joda.time.Years;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import us.codecraft.webmagic.ResultItems;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//*
//* 网站质量评分
//* Created by caoyi on 2014-12-4.
//
//
//public class SiteScoreImpl implements SiteScore {
//
//    private String url;
//
//    public SiteScoreImpl() {}
//    public SiteScoreImpl(ResultItems resultItems) {
//        this.url = resultItems.getRequest().getUrl();
//    }
//
//*
//     * 2.4
//     * 网站域名中包含目标关键字
//     *
//     * @param docs   整站document对象
//     * @param domain 域名
//     * @return
//
//
//    @Override
//    public ScoreItem scoreDomainAge(Set<Document> docs, String domain) {
//        int siteIncludeCount = 0;//记录包含目标关键字次数
//        double source = 0.0;
//        ScoreItem scoreItem = new ScoreItem("网站域名中包含目标关键字", "2.4'");
//        Set<String> targetKeywordPY = ChineseUtils.getChineseTKD(ChineseUtils.executeTkd(docs));
//        for (String tar : targetKeywordPY) {
//            if (domain.contains(tar)) {
//                siteIncludeCount++;
//            }
//        }
//        if (siteIncludeCount == 0) {
//            scoreItem.setProblem("域名中不包含目标关键字");
//            scoreItem.setDesc("如有必要提高排名，请在域名中加入目标关键字的拼音缩写");
//        } else {
//            source = (siteIncludeCount * 1.0 / targetKeywordPY.size()) * 3.6;
//            scoreItem.setScore(source);
//        }
//        return scoreItem;
//    }
//
//*
//     * 2.1 网站更新的频率
//     * 爬取网站多次后，可以做此项检查
//     * 分值：5
//
//
//    @Override
//    public ScoreItem scoreUpdateFrequency() {
//        return null;
//    }
//
//*
//     * 2.2 网站收录数量
//     * 以百度的搜录数量为计算依据
//     * 分值：4
//
//
//    @Override
//    public ScoreItem scoreQuantitySearchEngineInclude(String url) {
//        ScoreItem scoreItems = new ScoreItem("网站收录数量", "2.2");
//        scoreItems.setDesc("为找到此结果或域名不存在");
//        scoreItems.setScore(0.0);
//
//        String urlString;
//        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
//        Matcher m = p.matcher(url);
//        if (m.find()) {
//            urlString = m.group();
//        } else {
//            return scoreItems;
//        }
//
//        try {
//            String number;
//            //综合查询地址
//            String inquiryUrl = "http://www.baidu.com/s?ie=utf-8&word=site%3A" + urlString;
//
//            Document doc = Jsoup.connect(inquiryUrl).timeout(5000).get();
//
//            Element element = doc.getElementById("container");
//            Elements elements = element.getElementsByClass("nums");
//            if (elements.size() == 0) {
//                return scoreItems;
//            }
//            Pattern pattern = Pattern.compile("[\\d,]+");
//            Matcher mss = pattern.matcher(elements.get(0).toString());
//            if (mss.find()) {
//                number = mss.group();
//            } else {
//                return scoreItems;
//            }
//
//            double newNumber = Double.parseDouble(number.replaceAll(",|，", ""));
//
//            ScoreItem scoreItem = new ScoreItem("网站收录数量", "2.2");
//
//            if (newNumber <= 3000) {
//                scoreItem.setScore(1.0);
//                scoreItem.setDesc("收录数得分 差");
//            } else if (newNumber <= 6000) {
//                scoreItem.setScore(2.0);
//                scoreItem.setDesc("收录数得分 中");
//            } else if (newNumber <= 9000) {
//                scoreItem.setScore(3.0);
//                scoreItem.setDesc("收录数得分 良");
//            } else if (newNumber > 9000) {
//                scoreItem.setScore(4.0);
//                scoreItem.setDesc("收录数得分 优");
//            }
//
//            return scoreItem;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//*
//     * 2.3 网站主题与目标关键字具有相关性
//     * 可以理解为网站各页面与关键字相关性的一个汇总处理
//     * 分值：3.8
//
//
//    @Override
//    public ScoreItem scoreRelationOfSiteAndKeywords() {
//        return null;
//    }
//
//*
//     * 2.5 域名年龄
//     * 对域名的注册时间评分，注册时间可通过 whois 查询
//     * 分值：3.6
//
//
//    @Override
//    public ScoreItem scoreAgeOfDomainName(String url) {
//
//        String urlString;
//        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
//        Matcher m = p.matcher(url);
//        if (m.find()) {
//            urlString = m.group();
//        } else {
//            return null;
//        }
//
//        //
//        ScoreItem scoreItems = new ScoreItem("域名年龄", "SITEM2_5");
//        scoreItems.setDesc("此域名未被注册或属于未知域名");
//        scoreItems.setScore(0.0);
//        //
//        String inquiryUrl = "http://whois.chinaz.com/?Domain=" + urlString;
//
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(inquiryUrl).get();
//        } catch (IOException e) {
//            return scoreItems;
//        }
//
//        Element elements = doc.getElementById("whoisinfo");
//        if (elements == null) {
//            return scoreItems;
//        }
//        String dateString = elements.toString();
//
//        String newDate = "";
//        int d = dateString.indexOf("创建时间");
//        if (d == -1) {
//            d = dateString.indexOf("注册时间");
//            if (d == -1) {
//                return scoreItems;
//            }
//        }
//        newDate = dateString.substring(d + 5, d + 16);
//        newDate = newDate.replace("年", "-").replace("月", "-").replace("日", "").trim();
//        int years = Years.yearsBetween(Instant.parse(newDate), Instant.now()).getYears();
//
//        double result = ((years >= 6.2) ? 1.0 : (years / 6.2)) * 3.6;
//
//        ScoreItem scoreItem = new ScoreItem("域名年龄", "SITEM2_5");
//        scoreItem.setScore(result);
//        scoreItem.setDesc("");
//        return scoreItem;
//    }
//
//*
//     * 2.6 网站的结构层次
//     * 网站目录深度情况，目录越深，分值越低
//     * 分值：3.4
//
//
//    @Override
//    public ScoreItem scoreArchiOfSite() {
//        return null;
//    }
//
//*
//     * 2.7 域名后缀
//     * 网站可信度情况。一般 .edu, .gov 的域名分值较高
//     * 分值：3.2
//
//
//    @Override
//    public ScoreItem scorePostfixOfDomainName(String url) {
//
//        String urlString;
//        String unnamed = "";
//        String unnamedOne = "";
//        double fraction = 0.0;
//        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
//        Matcher m = p.matcher(url);
//        if (m.find()) {
//            urlString = m.group();
//        } else {
//            return null;
//        }
//        Pattern pattern = Pattern.compile("(\\.)+\\w+$");
//        Matcher matcher = pattern.matcher(urlString);
//        if (matcher.find()) {
//            unnamed = matcher.group();
//        }
//        ScoreItem scoreItem = new ScoreItem("域名后缀", "SITEM2_7");
//        if (".edu, .gov, .cn, .com".contains(unnamed)) {
//            fraction = 3.2;
//            scoreItem.setDesc("");
//        } else {
//            unnamedOne = urlString.substring(urlString.indexOf(".") + 1);
//            if ("qq.com, sina.com.cn, baidu.com, souhu.com".contains(unnamedOne)) {
//                fraction = 2.5;
//                scoreItem.setProblem("域名只属于是知名域名未是顶级后戳名，顶级后戳包含.edu, .gov, .cn, .com");
//                scoreItem.setDesc("TODO");
//            } else {
//                fraction = 0.0;
//                scoreItem.setProblem("域名不属于知名域名也不属于顶级后戳，顶级后戳包含.edu, .gov, .cn, .com");
//                scoreItem.setDesc("TODO");
//            }
//        }
//
//
//        scoreItem.setScore(fraction);
//
//        return scoreItem;
//    }
//
//    @Override
//    public List<ScoreItem> calculate() {
//        List<ScoreItem> result = new ArrayList<>();
//
//        result.add(scoreAgeOfDomainName(url));
//        result.add(scorePostfixOfDomainName(url));
//        result.add(scoreQuantitySearchEngineInclude(url));
//
//        return result;
//    }
//}
