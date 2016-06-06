//package com.groot.webmagic.seo.score.impl;
//
//import com.groot.commons.dto.ExtLinksDTO;
//import com.groot.utils.PageUtils;
//import com.groot.web.services.ExtLinksService;
//import com.groot.webmagic.seo.score.ExtraLinkScore;
//import com.groot.webmagic.seo.score.vo.ScoreItem;
//import org.jsoup.nodes.Document;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 外部链接评分
// * <p/>
// * Created by caoyi on 2014-12-4.
// */
//public class ExtraLinkScoreImpl implements ExtraLinkScore {
//    @Resource
//    private ExtLinksService extLinksService;
//
//    /**
//     * 3.1 外部链接的锚文字包含目标关键字
//     * 外部链接（外链）指其他网站到目标网站的链接。「站长工具」能查到相关信息
//     * 分值：5.5
//     */
//    @Override
//    public ScoreItem scoreKeywordsInExtLinks(Set<Document> docs, String url){
//        ScoreItem scoreItem=new ScoreItem("外部链接的锚文字包含目标关键字","3.1");
//        double score=0;
//        Set<String> allExtLinksMarkText= PageUtils.getAllSiteExtLinksKeywords(url,1);
//        for (Document doc : docs) {
//            Set<String> targetKeywords = PageUtils.getTargetKeywords(doc);
//            for (String tar:targetKeywords){
//                for (String all:allExtLinksMarkText){
//                    if(tar.equals(all)){
//                        score=score+5.5;
//                    }
//                }
//            }
//        }
//        if(score!=0){
//            double finalScore=score/ docs.size();
//            scoreItem.setScore(finalScore);
//        }else{
//            scoreItem.setProblem("没有任何外链描文本包含本站点的目标关键字");
//            scoreItem.setDesc("TODO");
//            scoreItem.setDesc("请设置网页title，keyword，description对应的meta标签");
//        }
//        return scoreItem;
//    }
//
//    /**
//     * 3.2 网站的外部链接流行度、广泛度
//     * 外链的本身的数量和来源数量越多，分值越高
//     * 分值：5.5
//     */
//    @Override
//    public ScoreItem scorePopulationOfExtLinks(String url) {
//
//        String urlString;
//        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
//        Matcher m = p.matcher(url);
//
//        ScoreItem returnNull = new ScoreItem("网站的外部链接流行度、广泛度","3.2");
//        returnNull.setScore(0.0);
//
//        if (m.find()) {
//            urlString = m.group();
//        } else {
//            returnNull.setProblem("URL不正确");
//            returnNull.setDesc("输入查询的URL不正确");
//            return returnNull;
//        }
//
//        //外链数地址
//        String inquiryUrl = "http://www.baidu.com/s?wd=domain%3A" + urlString;
//        //外链来源数地址
//        String urlAiZhan = "http://link.aizhan.com/?url=" + urlString;
//
//        try {
//            Document doc = Jsoup.connect(inquiryUrl).timeout(5000).get();
//
//            String s = PageUtils.getPhantomJsStr(urlAiZhan);
//
//            Document document = Jsoup.parse(s);
//
//            String number, numberAi;
//            //获取外链数
//            Element element = doc.getElementById("container");
//            Elements elements = element.getElementsByClass("nums");
//            Pattern pattern = Pattern.compile("[\\d,]+");
//            Matcher mss = pattern.matcher(elements.get(0).toString());
//            if (mss.find()) {
//                number = mss.group();
//            } else {
//                returnNull.setProblem("无外链数");
//                returnNull.setDesc("未捕获到外链数");
//                return returnNull;
//            }
//            //获取外链来源数
//            Element elementAi = document.getElementById("infoCount");
//            Pattern patt = Pattern.compile("[\\d,]+");
//            Matcher matcher = patt.matcher(elementAi.toString());
//            if (matcher.find()) {
//                numberAi = matcher.group();
//            } else {
//                returnNull.setDesc("未捕获到外链来源数");
//                return returnNull;
//            }
//            //外链数
//            double newNumber = Double.parseDouble(number.replaceAll(",|，", ""));
//            //外链来源数
//            double newNumberAi = Double.parseDouble(numberAi.replaceAll(",|，", ""));
//
//            DecimalFormat df = new DecimalFormat("######0.000");
//            //最终得分
//            double newNumbers = ((newNumber >= 3000) ? 1.0 : newNumber / 3000) * 0.5;
//            double newNumberAis = ((newNumberAi > 500) ? 1.0 : newNumberAi / 500) * 0.5;
//            String finalScore = df.format((newNumbers + newNumberAis) * 5.5);
//
//            ScoreItem scoreItem = new ScoreItem("网站的外部链接流行度、广泛度","3.2");
//
//            scoreItem.setScore(Double.parseDouble(finalScore));
//
//            if(newNumber >= 3000){
//                scoreItem.setDesc("外链数得分 优");
//            }else if(newNumber >=1000){
//                scoreItem.setDesc("外链数得分 良");
//            }else{
//                scoreItem.setDesc("外链数得分 差");
//            }
//            if(newNumberAi >= 500){
//                scoreItem.setDesc(scoreItem+";外链来源数得分 优");
//            }else if(newNumberAi >= 100){
//                scoreItem.setDesc(scoreItem+";外链来源数得分 良");
//            }else{
//                scoreItem.setDesc(scoreItem+";外链来源数得分 差");
//            }
//
//            return scoreItem;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return returnNull;
//    }
//
//    /**
//     * 3.3 网站的外部链接页面内容与关键词的相关性
//     * 爬取外链相关页面，并检查内容和关键词的相关性
//     * 分值：4.7
//     */
//    @Override
//    public ScoreItem scoreRelationOfPagesAndExtLinks() {
//        return null;
//    }
//
//    /**
//     * 3.4 外部链接来源页面本身的外部链接数量
//     * 爬取外链相关页面，并检查其外链数量，值越大，评分越高
//     * 分值：4.3
//     */
//    @Override
//    public ScoreItem scoreQuantityOfExtLinksOfExLinks() {
//        return null;
//    }
//
//    /**
//     * 3.5 网站新外部链接产生的速率
//     * 周期性查询外链数，并计算，可到速率值。更新越快，评分越高
//     * 分值：4.2
//     */
//    @Override
//    public ScoreItem scoreExtLinksFrequency(String url) {
//        ScoreItem scoreItem=new ScoreItem("网站新外部链接产生的速率","3.5");
//        int nowExtLinksCount=PageUtils.getExtLinksCount(url);
//        ExtLinksDTO dto=extLinksService.findParams(new HashMap<String,Object>(){{put("url",url);}});
//        if(dto!=null){
//            int nearExtLinksCount=extLinksService.getNearExtLinksCount(url);
//            if(nowExtLinksCount-nearExtLinksCount<0){
//                scoreItem.setProblem("网站外链更新速率程如增长");
//                scoreItem.setDesc("定时更新外链数量");
//            }else{
//               double bfb= (nowExtLinksCount-nearExtLinksCount)/nearExtLinksCount*100;
//                if(bfb<5){
//                    scoreItem.setScore(0.7);
//                    scoreItem.setDesc("增长速率小于5%");
//                }else if(bfb>5&&bfb<10){
//                    scoreItem.setScore(1.4);
//                    scoreItem.setDesc("增长速率大于5%小于10%");
//                }else if(bfb>10&&bfb<15){
//                    scoreItem.setScore(2.1);
//                    scoreItem.setDesc("增长速率大于10%小于15%");
//                }else if(bfb>15&&bfb<20){
//                    scoreItem.setScore(2.8);
//                    scoreItem.setDesc("增长速率大于15%小于20%");
//                }else if (bfb>20&&bfb<25){
//                    scoreItem.setScore(3.5);
//                    scoreItem.setDesc("增长速率大于20%小于15%");
//                }else if (bfb>25){
//                    scoreItem.setScore(4.2);
//                    scoreItem.setDesc("增长速率大于20%");
//                }
//            }
//        }else{
//            scoreItem.setProblem("系统尚未收录该网站，该评分需要两次数据比较才能得出结果，下次SEO查询时将得出结果！");
//        }
//        return scoreItem;
//    }
//
//    /**
//     * 3.6 外部链接的链接的年龄
//     * 外链的添加时间。可以最早爬取到的时间来计算
//     * 分值：3.8
//     */
//    @Override
//    public ScoreItem scoreAgeofExtLinks(String url) {
//        ScoreItem scoreItem = new ScoreItem("外部链接的链接的年龄", "3.6");
//        //TODO 代码已经可以实现，但是有点不现实，没多大用，测试代码已经通过
//        double score = 0.0;
//        int showCount = 0;
//        double totalScore = 0.0;
//        try {
//            String backUrl = url;
//            if (!url.contains("http://")) {
//                url = "http://" + url;
//            }
//            Set<String> extLinks = new HashSet<>();
//            Document extLinkDoc = Jsoup.connect(url).get();
//            extLinks = PageUtils.getExtLinks(extLinkDoc, backUrl);
//
//            if (extLinks.size() > 0) {
//                for (String ur : extLinks) {
//                    Matcher m = Pattern.compile(",?(\\w+\\.(com|net|org|cn|hk||edu|cc|tv))").matcher(ur);
//                    if (m.find()) {
//                        ur = m.group(1);
//                    }
//                    String whoisUrl = "http://whois.aizhan.com/domain=" + ur + "?domain=" + ur;
//                    String html = PageUtils.getPhantomJsStr(whoisUrl);
//                    Document doc = Jsoup.parse(html, "UTF-8");
//                    Element div = doc.getElementById("whoisContentHead");
//                    if (div != null) {
//                        String divStr = div.text();
//                        double betweenDays = 0;
//                        if (divStr.contains("创建时间") && divStr.contains("过期时间")) {
//                            String dateStr = divStr.substring(divStr.indexOf("创建时间"), divStr.indexOf("过期时间")).split(":")[1].trim();
//                            Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
//                            Matcher matcher = pattern.matcher(dateStr);
//                            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
//                            if (matcher.find()) {
//                                dateStr = matcher.group();
//                            } else {
//                                dateStr = formatDate.format(new Date());
//                            }
//                            try {
//                                Date date = formatDate.parse(dateStr);
//                                Calendar cal = Calendar.getInstance();
//                                cal.setTime(new Date());
//                                long time1 = cal.getTimeInMillis();
//                                cal.setTime(date);
//                                long time2 = cal.getTimeInMillis();
//                                betweenDays = ((time1 - time2) * 1.0) / (1000 * 3600 * 24);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            if (betweenDays < 365) {
//                                betweenDays = 2.0;
//                            } else if (betweenDays > 365 && betweenDays < 365 * 4) {
//                                betweenDays = 2.7;
//                            } else if (betweenDays > 365 * 4 && betweenDays < 365 * 6) {
//                                betweenDays = 3.4;
//                            } else if (betweenDays > 365 * 6 && betweenDays < 365 * 8) {
//                                betweenDays = 3.4;
//                            } else {
//                                betweenDays = 3.8;
//                            }
//                        }
//                        showCount++;
//                        totalScore = totalScore + betweenDays;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (score == 0 || showCount == 0) {
//            scoreItem.setProblem("外部链接未找到，请设置外部链接");
//            scoreItem.setDesc("设置外部链接");
//        } else {
//            scoreItem.setScore((score / showCount));
//        }
//        return scoreItem;
//
//    }
//
//    /**
//     * 3.7 外部链接周围的文字与目标关键字具有相关性
//     * 分析外链所在页面，外链出现的位置。在正文中的评分较高
//     * 分值：3.8
//     */
//    @Override
//    public ScoreItem scoreArroundOfExtLinks() {
//        return null;
//    }
//
//    @Override
//    public List<ScoreItem> calculate() {
//        return null;
//    }
//}
