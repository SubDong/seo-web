package com.groot.web.services.impl;

import com.groot.utils.PageUtils;
import com.groot.web.services.SEOBasicInfoService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SubDong on 2015/1/7.
 */
@Service
public class SEOBasicInfoImpl implements SEOBasicInfoService {


    @Override
    public List<Map<String, String>> getWebsiteBasic(String url) {

        Map<String, String> stringMap = new HashMap<>();
        List<Map<String, String>> maps = new ArrayList<>();

        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);

        if (m.find()) {
            urlString = m.group();
        } else {
            return null;
        }

        String inquiryUrl = "http://www.aizhan.com/cha/" + urlString + "/";

        String s = PageUtils.getPhantomJsStr(inquiryUrl);

        Document doc = Jsoup.parse(s);

        if (doc != null) {
            String body = doc.getElementsByTag("body").html();
            if (body != null && !body.equals("") && !body.equals("HTTP request failed!") && !body.contains("404 Not Found")) {
                //title 信息
                String title = doc.getElementById("main_title").html();

                String ageUrl = "http://whois.chinaz.com/" + urlString;

                //域名年龄
                String dateTime = doc.getElementById("domain_date").html() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果年龄显示有问题请点击此处查看&nbsp;&nbsp;&nbsp; <a href=" + ageUrl + ">查看</a>";

                Element baidu_date = doc.getElementById("baidu_date");
                String KZ;
                if (baidu_date == null) {
                    KZ = "百度已取消快照时间";
                } else {
                    if ("0000-00-00".equals(baidu_date.html())) {
                        KZ = baidu_date.html("-").toString().replaceAll("style=\"color:#333333\"", "");
                    } else {
                        KZ = baidu_date.toString().replaceAll("style=\"color:#333333\"", "");
                    }
                }


                String baiduKZ = "百度快照：" + KZ;

                String souye = "预计来路：" + doc.getElementById("baidu_ip").html() + " 个IP";

                String wailian = "外链数：<a href='http://link.aizhan.com/?url=" + urlString + "&vt=c' target='_blank'>" + doc.getElementById("backlink_num_1").html() + "</a>";

                //google 权重
                String googlePR = "PR：" + doc.getElementById("main_pr").html();

                //百度第三方权重
                String baiduBR = "百度权重：" + doc.getElementById("baidu_rank").html();

                //世界排名
                String worldRankings = "三月平均：" + doc.getElementById("alexa_3months").html();

                String alexa = "ALEXA数据预估流量：" + doc.getElementById("alexa_IPPV").html();

                ////////百度引量//////////
                //IP来路
                String baiduYL_LU = "预计来路：" + doc.getElementById("baidu_ip") + "个IP";

                //出站链接、首页内链
                String seo_link = doc.getElementById("seo_link").toString();


                stringMap.put("titleData", title);

                stringMap.put("createdate", dateTime);
                stringMap.put("seoInfoKZ", baiduKZ);
                stringMap.put("seoInfoWL", wailian);
                stringMap.put("seoInfoSY", souye);
                stringMap.put("worldRank", worldRankings);
                stringMap.put("worldalexa", alexa);
                stringMap.put("baiduLU", baiduYL_LU);
                stringMap.put("seo_link", seo_link);
                stringMap.put("googlePR", googlePR);
                stringMap.put("baiduBR", baiduBR);
                maps.add(stringMap);
                return maps;
            }
        }
        return maps;
    }

    @Override
    public List<Map<String, String>> getSearchInfo(String url) {

        Map<String, String> stringMap = new HashMap<>();
        List<Map<String, String>> stringListMap = new ArrayList<>();
        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);

        if (m.find()) {
            urlString = m.group();
        } else {
            return null;
        }

        //百度收录数URL
        String inquiryUrl = "http://www.baidu.com/s?wd=site:" + urlString;
        //百度反链数URL
        String backlinks = "http://www.baidu.com/s?wd=domain:" + urlString;
        //360收录数URL
        String inquiryUrl360 = "http://www.haosou.com/s?q=site%3A" + urlString;
        //360反链数URL
        String backlinks360 = "http://www.haosou.com/s?q=%22" + urlString + "%22";

        //搜狗收录数URL
        String inquiryUrlSougou = "http://www.sogou.com/sogou?query=site%3A" + urlString + "&ie=utf8";
        //搜狗反链数URL
        String backlinksSougou = "http://www.sogou.com/web?&query=%22" + urlString + "%22";

        //必应收录数URL
        String inquiryUrlBing = "http://cn.bing.com/search?q=site%3A" + urlString;
        //必应反链数URL
        String backlinksBing = "http://cn.bing.com/search?q=%22" + urlString + "%22";

        List<String> getBaidu = getDataInfo(inquiryUrl, backlinks, "baidu");
        List<String> get360 = getDataInfo(inquiryUrl360, backlinks360, "360");
        List<String> getSougou = getDataInfo(inquiryUrlSougou, backlinksSougou, "sougou");
        List<String> getBing = getDataInfo(inquiryUrlBing, backlinksBing, "bing");

        //添加百度数据
        stringMap.put("searchBD", "百度");
        stringMap.put("searchNum", "1");
        stringMap.put("numberBD", "<a href='http://www.baidu.com/s?wd=site:" + urlString + "' target='_blank'>" + ((getBaidu != null && getBaidu.size() != 0) ? getBaidu.get(0) : "0") + "</a>");
        stringMap.put("backNumberBD", "<a href='http://www.baidu.com/s?wd=domain:" + urlString + "' target='_blank'>" + ((getBaidu != null && getBaidu.size() != 0) ? getBaidu.get(1) : "0") + "</a>");
        stringListMap.add(stringMap);
        stringMap = new HashMap<>();
        //添加360数据
        stringMap.put("searchBD", "360");
        stringMap.put("searchNum", "2");
        stringMap.put("numberBD", "<a href='http://www.haosou.com/s?q=site%3A" + urlString + "' target='_blank'>" + ((get360 != null && get360.size() != 0) ? get360.get(0) : "0") + "</a>");
        stringMap.put("backNumberBD", "<a href='http://www.haosou.com/s?q=%22" + urlString + "%22' target='_blank'>" + ((get360 != null && get360.size() != 0) ? get360.get(1) : "0") + "</a>");
        stringListMap.add(stringMap);

        //添加搜狗数据
        stringMap = new HashMap<>();
        stringMap.put("searchBD", "搜狗");
        stringMap.put("searchNum", "3");
        stringMap.put("numberBD", "<a href='http://www.sogou.com/sogou?query=site%3A" + urlString + "&ie=utf8' target='_blank'>" + ((getSougou != null && getSougou.size() != 0) ? getSougou.get(0) : "0") + "</a>");
        stringMap.put("backNumberBD", "<a href='http://www.sogou.com/web?&query=%22" + urlString + "%22' target='_blank'>" + ((getSougou != null && getSougou.size() != 0) ? getSougou.get(1) : "0") + "</a>");
        stringListMap.add(stringMap);

        //添加必应（Bing）数据
        stringMap = new HashMap<>();
        stringMap.put("searchBD", "必应");
        stringMap.put("searchNum", "5");
        stringMap.put("numberBD", "<a href='http://cn.bing.com/search?q=site%3A" + urlString + "' target='_blank'>" + ((getBing != null && getBing.size() != 0) ? getBing.get(0) : "0") + "</a>");
        stringMap.put("backNumberBD", "<a href='http://cn.bing.com/search?q=%22" + urlString + "%22' target='_blank'>" + ((getBing != null && getBing.size() != 0) ? getBing.get(1) : "0") + "</a>");
        stringListMap.add(stringMap);

        return stringListMap;
    }

    @Override
    public List<Map<String, String>> getContentInfo(String url) {

        Map<String, String> stringMap = new HashMap<>();
        List<Map<String, String>> maps = new ArrayList<>();

        String urlString;
        Pattern p = Pattern.compile("(?<=//|)((\\S)+\\.)+\\w+");
        Matcher m = p.matcher(url);

        if (m.find()) {
            urlString = m.group();
        } else {
            return null;
        }
        /*if(!urlString.startsWith("www")){
            urlString = "www." + urlString;
        }*/
        try {
            Document doc = Jsoup.connect("http://" + urlString).get();

            String keywords = doc.select("meta[name=keywords]").eq(0).attr("content");
            String desc = doc.select("meta[name=description]").eq(0).attr("content");
            String title = doc.select("title").eq(0).text();

            stringMap.put("keywords", keywords);
            stringMap.put("desc", desc);
            stringMap.put("titleMsg", title);

            stringMap.put("keywordsNum", String.valueOf(keywords.length()));
            stringMap.put("descNum", String.valueOf(desc.length()));
            stringMap.put("titleNum", String.valueOf(title.length()));
        } catch (IOException e) {
            stringMap.put("keywords", "--");
            stringMap.put("desc", "--");
            stringMap.put("titleMsg", "--");

            stringMap.put("keywordsNum", "0");
            stringMap.put("descNum", "0");
            stringMap.put("titleNum", "0");
        }
        maps.add(stringMap);
        return maps;
    }

    /**
     * 百度收录、反链查询
     */
    private List<String> getDataInfo(String inquiryUrl, String backlinks, String search) {
        List<String> integers = new ArrayList<>();
        Document doc = null;
        Document docback = null;

        int i = 0;
        String number = "", numberBack = "";
        Element element;
        Elements elements = new Elements(), elementBacks = new Elements();
        while (i < 4) {
            i++;
            try {
                doc = Jsoup.connect(inquiryUrl).get();
                docback = Jsoup.connect(backlinks).get();

                if (search.equals("baidu")) {
                    element = doc.getElementById("container");
                    elements = new Elements();
                    elementBacks = new Elements();
                    if (element != null) {
                        elements = element.getElementsByClass("nums");
                    }
                    Element elementBack = docback.getElementById("container");
                    if (elementBack != null) {
                        elementBacks = elementBack.getElementsByClass("nums");
                    }
                } else if (search.equals("360")) {
                    element = doc.getElementById("page");
                    elements = new Elements();
                    elementBacks = new Elements();
                    if (element != null) {
                        elements = element.getElementsByClass("nums");
                    }
                    Elements totptip = docback.getElementsByClass("so-toptip");
                    if (totptip.size() == 0) {
                        Element elementBack = docback.getElementById("page");
                        if (elementBack != null) {
                            elementBacks = elementBack.getElementsByClass("nums");
                        }
                    }
                } else if (search.equals("sougou")) {
                    elements = new Elements();
                    elementBacks = new Elements();

                    element = doc.getElementById("scd_num");
                    if (element == null) {
                        element = doc.getElementById("main");
                        if (element != null) {
                            elements = element.getElementsByTag("em");
                        }
                    } else {
                        elements.add(element);
                    }
                    Element smart = docback.getElementById("smart_hint_container");
                    if (smart == null) {
                        Element elementBack = docback.getElementById("scd_num");
                        if (elementBack != null) {
                            elementBacks.add(elementBack);
                        }
                    }
                } else if (search.equals("bing")) {
                    element = doc.getElementById("b_results");
                    elements = new Elements();
                    elementBacks = new Elements();
                    if (element != null) {
                        elements = element.getElementsByClass("sb_count");
                    }
                    Element elementBack = docback.getElementById("b_results");
                    if (elementBack != null) {
                        elementBacks = elementBack.getElementsByClass("sb_count");
                    }
                }

                if (elements.size() != 0 && elementBacks.size() != 0) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Pattern pattern = Pattern.compile("[\\d,]+");

        Matcher mss = null, back = null;
        if (elements.size() == 0) {
            number = "0";
        } else {
            if (search.equals("360")) {
                mss = pattern.matcher(elements.get(0).html());
            } else {
                mss = pattern.matcher(elements.get(0).toString());
            }
            if (mss.find()) number = mss.group();
            else number = "0";
        }
        if (elementBacks.size() == 0) {
            numberBack = "0";
        } else {
            if (search.equals("360")) {
                back = pattern.matcher(elementBacks.get(0).html());
            } else {
                back = pattern.matcher(elementBacks.get(0).toString());
            }
            if (back.find()) numberBack = back.group();
            else numberBack = "0";
        }

        String newNumberBD = number.replaceAll(",|，", "");
        String newNumberAiBD = numberBack.replaceAll(",|，", "");
        integers.add(newNumberBD);
        integers.add(newNumberAiBD);
        return integers;
    }
}
