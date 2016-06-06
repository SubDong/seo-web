package com.groot.web.services.impl;

import com.groot.commons.ScoreDomainConstant;
import com.groot.commons.dto.ExtLinksDTO;
import com.groot.utils.KeywordUtils;
import com.groot.utils.PageUtils;
import com.groot.web.services.DomainScoreItemsService;
import com.groot.web.services.ExtLinksService;
import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.joda.time.Instant;
import org.joda.time.Years;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SubDong on 2015/1/30.
 */
@Service
public class DomainScoreItemsImpl implements DomainScoreItemsService {
    @Resource
    private ExtLinksService extLinksService;

    @Override
    public ScoreItem scoreQuantitySearchEngineInclude(String url) {

        ScoreItem scoreItems = new ScoreItem("网站收录数量", ScoreDomainConstant.NAME_SEO2_2);
        scoreItems.setProblem("未找到此结果或域名不存在");
        scoreItems.setDesc("无");
        scoreItems.setScore(0.0);

        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);
        if (m.find()) {
            urlString = m.group();
        } else {
            return scoreItems;
        }

        String number;
        //综合查询地址
        String inquiryUrl = "http://www.baidu.com/s?ie=utf-8&word=site%3A" + urlString;

        int i = 0;
        Elements elements = null;
        while (i <= 3) {
            try {
                Document doc = Jsoup.connect(inquiryUrl).get();
                Element element = doc.getElementById("container");
                elements = element.getElementsByClass("nums");
                i = 4;
            } catch (Exception e) {
                i++;
            }
        }

        if (elements.size() == 0) {
            return scoreItems;
        }
        Pattern pattern = Pattern.compile("[\\d,]+");
        Matcher mss = pattern.matcher(elements.get(0).toString());
        if (mss.find()) {
            number = mss.group();
        } else {
            return scoreItems;
        }

        double newNumber = Double.parseDouble(number.replaceAll(",|，", ""));

        ScoreItem scoreItem = new ScoreItem("网站收录数量", ScoreDomainConstant.NAME_SEO2_2);

        if (newNumber <= 3000) {
            scoreItem.setScore(1.0);
            scoreItem.setDesc("优化网站尽量让搜索引擎对你的网站进行收录");
            scoreItem.setProblem("收录数得分 差");
        } else if (newNumber <= 6000) {
            scoreItem.setScore(2.0);
            scoreItem.setDesc("优化网站尽量让搜索引擎对你的网站进行收录");
            scoreItem.setProblem("收录数得分 中");
        } else if (newNumber <= 9000) {
            scoreItem.setScore(3.0);
            scoreItem.setDesc("优化网站尽量让搜索引擎对你的网站进行收录");
            scoreItem.setProblem("收录数得分 良");
        } else if (newNumber > 9000) {
            scoreItem.setScore(4.0);
            scoreItem.setDesc("无");
            scoreItem.setProblem("无");
        }

        return scoreItem;
    }

    @Override
    public ScoreItem scoreDomainName(String url) {
        ScoreItem scoreItem = new ScoreItem("网站域名中包含目标关键字", ScoreDomainConstant.NAME_SEO2_4);
        int siteIncludeCount = 0;//记录包含目标关键字次数
        double source = 0.0;
        if (!url.contains("http://")) {
            url = "http://" + url;
        }
        String html = PageUtils.getPhantomJsStr(url);
        if (!html.equals("HTTP request failed!") && html != null) {
            Document doc = Jsoup.parse(html);
            Set<String> targetKeywordCH = PageUtils.getTargetKeywords(doc);//获取中文的目标关键字
            Set<String> targetKeywordPY = KeywordUtils.getPinyinTKD(targetKeywordCH);//转换为拼音的目标关键字
            for (String tar : targetKeywordPY) {
                if (url.contains(tar)) {
                    siteIncludeCount++;
                }
            }
            if (siteIncludeCount == 0) {
                scoreItem.setProblem("域名中不包含目标关键字");
                scoreItem.setDesc("如有必要提高排名，请在域名中加入目标关键字的拼音缩写");
            } else {
                source = (siteIncludeCount * 1.0 / targetKeywordPY.size()) * 3.6;
                DecimalFormat df = new DecimalFormat("######0.00");
                source = Double.parseDouble(df.format(source));
                scoreItem.setScore(source);
                if (source < 3.6) {
                    scoreItem.setProblem("URL 包含的目标关键字数量少于目标关键字");
                    scoreItem.setDesc("URL中包含的目标关键字需大于或等于你的目标目标关键字");
                } else {
                    scoreItem.setProblem("无");
                    scoreItem.setDesc("无");
                }
            }
        } else {
            scoreItem.setProblem("域名中不包含目标关键字");
            scoreItem.setDesc("如有必要提高排名，请在域名中加入目标关键字的拼音缩写");
        }

        return scoreItem;
    }

    @Override
    public ScoreItem scoreAgeOfDomainName(String url) {
        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);
        if (m.find()) {
            urlString = m.group();
        } else {
            return null;
        }

        //
        ScoreItem scoreItems = new ScoreItem("域名年龄", ScoreDomainConstant.NAME_SEO2_5);
        scoreItems.setProblem("此域名未被注册或属于未知域名");
        scoreItems.setDesc("无");
        scoreItems.setScore(0.0);
        //
        String inquiryUrl = "http://whois.chinaz.com/?Domain=" + urlString;

        Document doc = null;
        try {
            doc = Jsoup.connect(inquiryUrl).get();
        } catch (IOException e) {
            return scoreItems;
        }

        Element elements = doc.getElementById("whoisinfo");
        if (elements == null) {
            return scoreItems;
        }
        String dateString = elements.toString();

        String newDate = "";
        int d = dateString.indexOf("创建时间");
        if (d == -1) {
            d = dateString.indexOf("注册时间");
            if (d == -1) {
                return scoreItems;
            }
        }
        newDate = dateString.substring(d + 5, d + 16);
        newDate = newDate.replace("年", "-").replace("月", "-").replace("日", "").trim();
        int years = Years.yearsBetween(Instant.parse(newDate), Instant.now()).getYears();

        double result = ((years >= 6.2) ? 1.0 : (years / 6.2)) * 3.6;
        DecimalFormat df = new DecimalFormat("######0.00");
        result = Double.parseDouble(df.format(result));
        ScoreItem scoreItem = new ScoreItem("域名年龄", ScoreDomainConstant.NAME_SEO2_5);
        scoreItem.setScore(result);
        if (result < 3.6) {
            scoreItem.setProblem("域名年限过短");
        } else {
            scoreItem.setProblem("无");
        }
        scoreItem.setDesc("无");
        return scoreItem;
    }

    @Override
    public ScoreItem scorePostfixOfDomainName(String url) {
        ScoreItem returnNuLL = new ScoreItem("域名后缀", ScoreDomainConstant.NAME_SEO2_7);
        returnNuLL.setProblem("无");
        returnNuLL.setScore(0.0);
        String urlString;
        String unnamed = "", unnamedOne = "";
        double fraction = 0.0;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);
        if (m.find()) {
            urlString = m.group();
        } else {
            returnNuLL.setProblem("url无效");
            return returnNuLL;
        }
        Pattern pattern = Pattern.compile("(\\.)+\\w+$");
        Matcher matcher = pattern.matcher(urlString);
        if (matcher.find()) {
            unnamed = matcher.group();
        } else {
            returnNuLL.setProblem("后戳名无效");
            return returnNuLL;
        }
        ScoreItem scoreItem = new ScoreItem("域名后缀", ScoreDomainConstant.NAME_SEO2_7);
        if (".edu, .gov, .cn, .com, .org".contains(unnamed)) {
            fraction = 3.2;
            scoreItem.setDesc("无");
            scoreItem.setProblem("无");
        } else {
            unnamedOne = urlString.substring(urlString.indexOf(".") + 1);
            String[] strings = "qq.com, sina.com.cn, baidu.com, souhu.com".split("，|,");
            for (String string : strings) {
                if ("".contains(unnamedOne)) {
                    fraction = 2.5;
                    scoreItem.setProblem("域名只属于是知名域名未是顶级后戳名，顶级后戳包含.edu, .gov, .cn, .com, .org");
                    scoreItem.setDesc("建议更换为顶级或知名的后戳名");
                }
            }
            if (fraction == 0.0) {
                scoreItem.setProblem("域名不属于知名域名也不属于顶级后戳，顶级后戳包含.edu, .gov, .cn, .com, .org");
                scoreItem.setDesc("建议更换为顶级或知名的后戳名");
            }

        }

        scoreItem.setScore(fraction);

        return scoreItem;
    }

    //3.1 外部链接的锚文字包含目标关键字
    @Override
    public ScoreItem scoreKeywordsInExtLinks(String url) {
        ScoreItem scoreItem = new ScoreItem("外部链接的锚文字包含目标关键字", ScoreDomainConstant.NAME_SEO3_1);
        String html = PageUtils.getPhantomJsStr(url);
        if (html != null) {
            Document doc = Jsoup.parse(html);
            double score = 0.0;
            Set<String> allExtLinksMarkText = PageUtils.getAllSiteExtLinksKeywords(url);
            Set<String> targetKeywords = PageUtils.getTargetKeywords(doc);
            for (String tar : targetKeywords) {
                for (String all : allExtLinksMarkText) {
                    if (tar.equals(all)) {
                        score = 5.5;
                        break;
                    }
                }
            }
            if (score != 0) {
                scoreItem.setScore(score);
                scoreItem.setProblem("无");
                scoreItem.setDesc("无");
            } else {
                scoreItem.setProblem("外部链接描文本没有包含任何本站点的目标关键字");
                scoreItem.setDesc("建议优化外部链接中的title，description，keyword对应的meta标签");
            }
        } else {
            scoreItem.setProblem("没有找到此任何页面");
            scoreItem.setDesc("请输入你的正确url");
            scoreItem.setScore(0.0);
        }
        return scoreItem;
    }

    @Override
    public ScoreItem scorePopulationOfExtLinks(String url) {
        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);

        ScoreItem returnNull = new ScoreItem("网站的外部链接广泛度", ScoreDomainConstant.NAME_SEO3_2);
        returnNull.setScore(0.0);

        if (m.find()) {
            urlString = m.group();
        } else {
            returnNull.setProblem("URL不正确");
            returnNull.setDesc("输入查询的URL不正确");
            return returnNull;
        }

        //外链数地址
        String inquiryUrl = "http://www.baidu.com/s?wd=domain%3A" + urlString;
        //外链来源数地址
        String urlAiZhan = "http://link.aizhan.com/?url=" + urlString;

        int i = 0;
        Elements elements = null;
        while (i <= 3) {
            try {
                Document doc = Jsoup.connect(inquiryUrl).get();
                //获取外链数
                Element element = doc.getElementById("container");
                elements = element.getElementsByClass("nums");
                i = 4;
            } catch (Exception e) {
                i++;
            }
        }


        String jsStr = PageUtils.getPhantomJsStr(urlAiZhan);

        Document document = Jsoup.parse(jsStr);

        String number = "", numberAi;

        Pattern pattern = Pattern.compile("[\\d,]+");
        if (elements != null && elements.size() != 0) {
            Matcher mss = pattern.matcher(elements.get(0).toString());
            if (mss.find()) {
                number = mss.group();
            } else {
                returnNull.setProblem("无外链数");
                returnNull.setDesc("未捕获到外链数，请添加你的外链");
                return returnNull;
            }
        } else {
            returnNull.setProblem("无外链数");
            returnNull.setDesc("未捕获到外链数，请添加你的外链");
            return returnNull;
        }
        //获取外链来源数
        Element elementAi = document.getElementById("infoCount");
        Pattern patt = Pattern.compile("[\\d,]+");
        if (elementAi != null) {
            Matcher matcher = patt.matcher(elementAi.toString());
            if (matcher.find()) {
                numberAi = matcher.group();
            } else {
                returnNull.setProblem("无外部连接来源数");
                returnNull.setDesc("未捕获到外部链接来源数，让你的网站多出现在其他网站中");
                return returnNull;
            }
        } else {
            returnNull.setProblem("无外部连接来源数");
            returnNull.setDesc("未捕获到外部链接来源数，让你的网站多出现在其他网站中");
            return returnNull;
        }

        //外链数
        double newNumber = Double.parseDouble(number.replaceAll(",|，", ""));
        //外链来源数
        double newNumberAi = Double.parseDouble(numberAi.replaceAll(",|，", ""));

        DecimalFormat df = new DecimalFormat("######0.00");
        //最终得分
        double newNumbers = ((newNumber >= 3000) ? 1.0 : newNumber / 3000) * 0.5;
        double newNumberAis = ((newNumberAi > 500) ? 1.0 : newNumberAi / 500) * 0.5;
        String finalScore = df.format((newNumbers + newNumberAis) * 5.5);

        ScoreItem score = new ScoreItem("网站的外部链接广泛度", ScoreDomainConstant.NAME_SEO3_2);

        score.setScore(Double.parseDouble(finalScore));

        if (newNumber >= 3000) {
            score.setProblem("外链数: 无");
            score.setDesc("外链数: 无");
        } else if (newNumber >= 1000) {
            score.setProblem("外链数得分 良");
            score.setDesc("外链数较少，建议多添加外链");
        } else {
            score.setProblem("外链数得分 差");
            score.setDesc("外链数较少，建议多添加外链");
        }
        if (newNumberAi >= 500) {
            score.setProblem(score.getProblem() + "；外链来源数: 无");
            score.setDesc(score.getDesc() + "；外链来源数: 无");
        } else if (newNumberAi >= 100) {
            score.setProblem(score.getProblem() + "；外链来源数得分 良");
            score.setDesc(score.getDesc() + "；外链来源数较少，建议优化外链来源");
        } else {
            score.setProblem(score.getProblem() + "；外链来源数得分 差");
            score.setDesc(score.getDesc() + "；外链来源数较少，建议优化外链来源");
        }

        return score;
    }

    @Override
    public ScoreItem scoreExtLinksFrequency(String url) {
        ScoreItem score = new ScoreItem("网站新外部链接产生的速率", ScoreDomainConstant.NAME_SEO3_5);
        String purl = "http://link.aizhan.com/?url=" + url + "&r=site%2Findex&linktext=";
        int nowExtLinksCount = PageUtils.getExtLinksCount(purl);
        ExtLinksDTO dto = extLinksService.findParams(new HashMap<String, Object>() {{
            put("url", url);
        }});
        if (dto != null) {
            int nearExtLinksCount = extLinksService.getNearExtLinksCount(url);
            if (nowExtLinksCount - nearExtLinksCount < 0) {
                score.setProblem("网站外链更新速率程如增长");
                score.setDesc("定时更新外链数量");
            } else {
                double bfb = (nowExtLinksCount - nearExtLinksCount) / (nearExtLinksCount == 0 ? 1 : nearExtLinksCount) * 100;
                if (bfb < 5) {
                    score.setScore(0.7);
                    score.setProblem("增长速率小于5%");
                    score.setDesc("建议定期增加外链数");
                } else if (bfb > 5 && bfb < 10) {
                    score.setScore(1.4);
                    score.setProblem("增长速率大于5%小于10%");
                    score.setDesc("建议定期增加外链数");
                } else if (bfb > 10 && bfb < 15) {
                    score.setScore(2.1);
                    score.setProblem("增长速率大于10%小于15%");
                    score.setDesc("建议定期增加外链数");
                } else if (bfb > 15 && bfb < 20) {
                    score.setScore(2.8);
                    score.setProblem("增长速率大于15%小于20%");
                    score.setDesc("建议定期增加外链数");
                } else if (bfb > 20 && bfb < 25) {
                    score.setScore(3.5);
                    score.setProblem("增长速率大于20%小于25%");
                    score.setDesc("建议定期增加外链数");
                } else if (bfb > 25) {
                    score.setScore(4.2);
                    score.setProblem("无");
                    score.setDesc("无");
                }
            }
        } else {
            ExtLinksDTO saveDto = new ExtLinksDTO();
            saveDto.setExtLinksCount(nowExtLinksCount);
            saveDto.setSaveDate(new Date());
            saveDto.setUrl(url);
            extLinksService.save(saveDto);
            score.setProblem("系统尚未收录该网站，该评分需要两次数据比较才能得出结果！");
            score.setDesc("下次SEO查询时将得出结果");
        }
        return score;
    }

    //3.6 外部链接的链接的年龄
    @Override
    public ScoreItem scoreAgeofExtLinks(String url) {
        ScoreItem scoreTask = new ScoreItem("外部链接的链接的年龄", ScoreDomainConstant.NAME_SEO3_6);
        double score = 0.0;
        int showCount = 0;
        double totalScore = 0.0;
        try {
            String backUrl = url;
            if (!url.contains("http://")) {
                url = "http://" + url;
            }
            Set<String> extLinks = new HashSet<>();
            Document extLinkDoc = Jsoup.connect(url).get();
            extLinks = PageUtils.getExtLinks(extLinkDoc, backUrl);

            if (extLinks.size() > 0) {
                for (String ur : extLinks) {
                    Matcher m = Pattern.compile(",?(\\w+\\.(com|net|org|cn|hk|edu|cc|tv))").matcher(ur);
                    if (m.find()) {
                        ur = m.group(1);
                    }
                    String whoisUrl = "http://whois.aizhan.com/" + ur;
                    //String whoisUrl = "http://whois.www.net.cn/whois/domain/" + ur;
                    String html = PageUtils.getPhantomJsStr(whoisUrl);
                    Document doc = Jsoup.parse(html, "UTF-8");
                    Element div = doc.getElementById("whoisContentHead");
                    if (div != null) {
                        String divStr = div.text();
                        double betweenDays = 0;
                        String dateStr = null;
                        if (divStr.contains("创建时间") && divStr.contains("过期时间")) {
                            dateStr = divStr.substring(divStr.indexOf("创建时间"), divStr.indexOf("过期时间")).split(":")[1].trim();
                        } else if (divStr.contains("创建时间") && !divStr.contains("过期时间")) {
                            dateStr = divStr.substring(divStr.indexOf("创建时间")).split(":")[1].trim();
                        }
                        if (dateStr != null) {
                            Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
                            Matcher matcher = pattern.matcher(dateStr);
                            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                            if (matcher.find()) {
                                dateStr = matcher.group();
                            } else {
                                dateStr = formatDate.format(new Date());
                            }
                            try {
                                Date date = formatDate.parse(dateStr);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date());
                                long time1 = cal.getTimeInMillis();
                                cal.setTime(date);
                                long time2 = cal.getTimeInMillis();
                                betweenDays = ((time1 - time2) * 1.0) / (1000 * 3600 * 24);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (betweenDays < 365) {
                                score = 2.0;
                            } else if (betweenDays > 365 && betweenDays < 365 * 2) {
                                score = 2.4;
                            } else if (betweenDays > 365 * 2 && betweenDays < 365 * 3) {
                                score = 2.8;
                            } else if (betweenDays > 365 * 3 && betweenDays < 365 * 4) {
                                score = 3.2;
                            } else {
                                score = 3.8;
                            }
                            showCount++;
                            totalScore = totalScore + score;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        totalScore = Double.parseDouble(df.format(totalScore));
        if (totalScore == 0 && showCount == 0) {
            scoreTask.setProblem("未找到外部链接，请设置外部链接");
            scoreTask.setDesc("设置外部链接");
        } else {
            scoreTask.setScore(Double.parseDouble(df.format(totalScore / showCount)));
            if (scoreTask.getScore() <= 2.0) {
                scoreTask.setProblem("外链平均年龄不足一年");
            } else if (scoreTask.getScore() > 2.0 && scoreTask.getScore() <= 2.4) {
                scoreTask.setProblem("外链平均年龄不足二年");
            } else if (scoreTask.getScore() > 2.4 && scoreTask.getScore() <= 2.8) {
                scoreTask.setProblem("外链平均年龄不足三年");
            } else if (scoreTask.getScore() > 2.8 && scoreTask.getScore() <= 3.2) {
                scoreTask.setProblem("外链平均年龄不足四年");
            } else if (scoreTask.getScore() > 3.2 && scoreTask.getScore() <= 3.8) {
                scoreTask.setProblem("无");
            } else {
                scoreTask.setProblem("无");
            }
            scoreTask.setDesc("无");
        }
        return scoreTask;
    }

    @Override
    public List<ScoreItem> calculate(String url) {
        List<ScoreItem> scoreItems = new ArrayList<>();

        //2.2 网站收录数量
        scoreItems.add(scoreQuantitySearchEngineInclude(url));
        //2.4 网站域名中包含目标关键字
        scoreItems.add(scoreDomainName(url));
        //2.5 域名年龄
        scoreItems.add(scoreAgeOfDomainName(url));
        //2.7 域名后缀
        scoreItems.add(scorePostfixOfDomainName(url));
        //3.1 外部链接的锚文字包含目标关键字
        scoreItems.add(scoreKeywordsInExtLinks(url));
        //3.2 网站的外部链接流行度、广泛度
        scoreItems.add(scorePopulationOfExtLinks(url));
        //3.5 网站新外部链接产生的速率
//        scoreItems.add(scoreExtLinksFrequency(url));
        //3.6 外部链接的链接的年龄
        scoreItems.add(scoreAgeofExtLinks(url));

        return scoreItems;
    }
}
