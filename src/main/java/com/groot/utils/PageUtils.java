package com.groot.utils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caoyi on 2014-12-26.
 */
public class PageUtils {

    /**
     * 清洗原始的网页文本内容，删除 javascript, css 等与 SEO 无关的内容
     *
     * @param rawText
     * @return
     */
    public static String cleanPage(String rawText) {
        String cleanText = rawText.replaceAll("", "");

        Document doc = Jsoup.parse(rawText, "UTF-8");
        doc.select("script, jscript").remove();
        doc.select("style").remove();

        return doc.toString();
    }

    /**
     * 获取目标关键字
     *
     * @param doc 页面内容
     * @return Created by SubDong on 2014/12/25.
     */
    public static Set<String> getTargetKeywords(Document doc) {

        Set<String> tagetKeywordSet = new HashSet<>();

        String keywords = doc.select("meta[name=keywords]").eq(0).attr("content");
        String desc = doc.select("meta[name=description]").eq(0).attr("content");
        String title = doc.select("title").eq(0).text();
        if (keywords == null || desc == null || title == null) {
            return new HashSet<>();
        }

        String[] keywordsArray = keywords.split(",|，");

        for (String s : keywordsArray) {
            if (desc.contains(s) && title.contains(s)) {
                tagetKeywordSet.add(s);
            }
        }
        return tagetKeywordSet;
    }

    /**
     * 从 URL 和标题中获取的存储路径，可能含有非法字符，需要替换。
     *
     * @param pathStr
     * @return
     */
    public static String modifyPath(String pathStr) {
        return pathStr.replaceAll("\\?|=", "").replaceAll("_", "");
    }

    /**
     * @param url 要获取全部外部链接描文本的url
     * @param
     * @return
     */
    public static Set<String> getAllSiteExtLinksKeywords(String url) {
        Set<String> extMarkKeywords = new HashSet<>();
        String finalUrl = "http://link.aizhan.com/?url=" + url + "&vt=a";
        String html = getPhantomJsStr(finalUrl);
        if(!html.equals("") && !html.equals("HTTP request failed!")){
            Document doc = Jsoup.parse(html, "UTF-8");
            String existHtml=doc.getElementById("backlink_box").html();
            if(!existHtml.contains("没有网站反链")){
                Element pc = doc.getElementById("page_top");
                Element count = pc.select("span").first();
                int nowPage = Integer.parseInt(count.html().split("/")[0]);
                int maxPage = Integer.parseInt(count.html().split("/")[1]);
                if(maxPage==nowPage){
                    Element ele = doc.getElementById("pageList");
                    Elements dl = ele.select(".bg_gray");
                    for (Element el : dl) {
                        String text = el.select("dd").eq(4).select("strong").eq(0).select("a").eq(0).text();
                        if (!text.contains("图片链接") && !text.contains("noText")) {
                            extMarkKeywords.add(text);
                        }
                    }
                }else {
                    for (int i=1;i<maxPage&&i<21;i++){
                        String pageUrl = "http://link.aizhan.com/?url=" + url + "&vt=a&p=" + i;
                        String pageHtml=getPhantomJsStr(pageUrl);
                        if(pageHtml != null && !pageHtml.equals("") && !pageHtml.equals("HTTP request failed!")){
                            Document pageDoc=Jsoup.parse(pageHtml,"UTF-8");
                            Element ele = pageDoc.getElementById("pageList");
                            Elements dl = ele.select(".bg_gray");
                            for (Element el : dl) {
                                String text = el.select("dd").eq(4).select("strong").eq(0).select("a").eq(0).text();
                                if (!text.contains("图片链接") && !text.contains("noText")) {
                                    extMarkKeywords.add(text);
                                }
                            }
                        }
                    }
                }
            }
        }
        return extMarkKeywords;
    }

    /**
     * @param url 获取爱站网js渲染后的html文本
     * @return
     */
    public static String getPhantomJsStr(String url) {
        String returnStr = null;
        if(!url.contains("http://")){
            url = "http://" + url;
        }

        WebClient webClient = new WebClient();        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);

        try {

            HtmlPage page = webClient.getPage(url);
            returnStr = page.asXml();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FailingHttpStatusCodeException ignored){
            if(ignored.getStatusCode() == 404){
                return "";
            } else{
                return "";
            }
        }
        return returnStr;
    }

    /**
     * 将各项的分数修正为两位小数表示, 这个方法应用到分值传给页面之前
     *
     * @param itemList
     * @return
     */
    public static void modifyScoreForFront(List<ScoreItem> itemList) {
        for (ScoreItem item : itemList) {
            double score = item.getScore();
            BigDecimal d = new BigDecimal(score);
            item.setScore(d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    public static int getExtLinksCount(String url) {
        int count = 0;
        String html = getPhantomJsStr(url);
        if (!html.equals("") && html != null && !html.equals("HTTP request failed!")) {
            Document doc = Jsoup.parse(html);
            String existHtml = doc.getElementById("backlink_box").html();
            if(!existHtml.contains("没有网站反链")){
                String countStr = doc.getElementById("infoCount").html();
                if (!countStr.equals("")) {
                    count = Integer.parseInt(countStr);
                }
            }
        }
        return count;
    }

    /**
     * 根据domain 域名过滤外部链接
     *
     * @param doc
     * @param domain
     * @return
     */
    public static Set<String> getExtLinks(Document doc, String domain) {
        Set<String> extLinks = new HashSet<>();
        Elements elements = doc.select("a[href]");
        Pattern p=Pattern.compile("(^javascript:)||#");
        int i=0;
        if (elements.size() > 0) {
            for (Element s : elements) {
                if(i == 50){
                    break;
                }
                Matcher m=p.matcher(s.attr("href"));
                boolean bol = s.attr("href").indexOf("http://") > -1 &&
                        !s.attr("href").contains(domain) &&
                        !m.matches();
                if (bol) {
                    extLinks.add(s.attr("href"));
                    i = extLinks.size();
                }
            }
        }
        return extLinks;
    }
}
