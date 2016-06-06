package com.groot.web.controller;

import com.alibaba.fastjson.JSON;
import com.groot.commons.dto.ReportInfoDTO;
import com.groot.commons.dto.SystemUserDTO;
import com.groot.utils.JedisUtil;
import com.groot.utils.getProblems;
import com.groot.utils.json.JSONJudge;
import com.groot.utils.json.JSONUtils;
import com.groot.web.commons.CustomDetailsService;
import com.groot.web.services.BaseReportInfoService;
import com.groot.web.services.DomainScoreItemsService;
import com.groot.web.services.SEOBasicInfoService;
import com.groot.web.services.SystemUserService;
import com.groot.webmagic.seo.score.vo.ScoreItem;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/seo")
public class ScoreController {

    @Resource
    private SEOBasicInfoService seoBasicInfoService;

    @Resource
    private DomainScoreItemsService domainScoreItemsService;

    @Resource
    private BaseReportInfoService baseReportInfoService;

    @Resource
    private SystemUserService systemUserService;

    /**
     * 页面首页访问方法
     *
     * @return
     */
    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView score(HttpServletResponse response,ModelMap map) {
        String loginUserName= CustomDetailsService.getUserName();
        if(loginUserName!=null){
            SystemUserDTO systemUserDTO=systemUserService.findByUserName(loginUserName);
            String userData = JSONUtils.getJsonString(systemUserDTO);
            map.put("userData", userData);
        }
        return new ModelAndView("index",map);
    }

    @RequestMapping("/seo")
    public ModelAndView seo() {
        return new ModelAndView("pingjia");
    }

    /**
     * seo基本信息
     *
     * @param request
     * @param model
     * @param url
     * @param returnNum
     * @return
     */
    @RequestMapping(value = "/score", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView score(HttpServletRequest request, ModelMap model,
                              @RequestParam(value = "url", required = false) String url,
                              @RequestParam(value = "returnNum", required = false, defaultValue = "1") int returnNum) {

        if (url != null) {
            url = url.replaceAll("http://", "").replace(" ", "").toLowerCase();
            Jedis jedis = JedisUtil.get();
            Long jedisKey = jedis.ttl(url + "-seo");
            String jsonData;
            if (jedisKey == -1 || jedisKey == -2) {
                Map<String, List<Map<String, String>>> stringMapMap = new HashMap<>();

                List<Map<String, String>> websiteBasic = seoBasicInfoService.getWebsiteBasic(url);

                List<Map<String, String>> searchInfo = seoBasicInfoService.getSearchInfo(url);

                List<Map<String, String>> contentInfo = seoBasicInfoService.getContentInfo(url);

                stringMapMap.put("websiteBasic", websiteBasic);

                stringMapMap.put("searchInfo", searchInfo);

                stringMapMap.put("contentInfo", contentInfo);

                jsonData = JSONUtils.getJsonString(stringMapMap);

                jedis.set(url + "-seo", jsonData);
                jedis.expire(url + "-seo", 1800);
            } else {
                jsonData = jedis.get(url + "-seo");
            }
            String loginUserName= CustomDetailsService.getUserName();
            if(loginUserName!=null){
                SystemUserDTO systemUserDTO=systemUserService.findByUserName(loginUserName);
                String userData = JSONUtils.getJsonString(systemUserDTO);
                model.put("userData",userData);
            }

            model.put("model", jsonData);
            model.put("reutrnUrl", url);
            if (jedis != null) {
                JedisUtil.returnJedis(jedis);
            }
            if (returnNum == 1) {
                return new ModelAndView("index", model);
            } else if (returnNum == 2) {
                return new ModelAndView("pingjia", model);
            }
        }
        return new ModelAndView("index");
    }

    /**
     * seo 域名详细信息
     *
     * @param request
     * @param model
     * @param url
     * @return
     */
    @RequestMapping(value = "/domain", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView domain(HttpServletRequest request, ModelMap model,
                               @RequestParam(value = "url", required = false) String url,
                               @RequestParam(value = "returnNum", required = false,defaultValue = "1") String search) {
        String jsonData;
        String loginUserName= CustomDetailsService.getUserName();
        SystemUserDTO systemUserDTO=systemUserService.findByUserName(loginUserName);
        String userData = JSONUtils.getJsonString(systemUserDTO);
        model.put("userData",userData);
        if (url != null && !url.equals("")) {

            url = url.replaceAll("http://", "").replace(" ", "").toLowerCase();
            Jedis jedis = JedisUtil.get();

            boolean falg = verify(url);
            if (falg) {
                Long jedisFalg = jedis.ttl(url + "-seo-det");
                if (jedisFalg == -1 || jedisFalg == -2) {
                    List<ScoreItem> scoreItems = domainScoreItemsService.calculate(url);
                    jsonData = JSONUtils.getJsonString(scoreItems);
                    jedis.set(url + "-seo-det", jsonData);
                    jedis.expire(url + "-seo-det", 3600);
                } else {
                    ScoreItem scoreItems = domainScoreItemsService.scoreExtLinksFrequency(url);
                    jsonData = jedis.get(url + "-seo-det");
                    List<ScoreItem> list = JSON.parseArray(jsonData, ScoreItem.class);
                    list.forEach(e->{
                        if(e.getCode().equals("SEO3_5")){
                            e.setScore(scoreItems.getScore());
                            e.setProblem(scoreItems.getProblem());
                            e.setDesc(scoreItems.getDesc());
                        }
                    });
                    jsonData = JSONUtils.getJsonString(list);
                }

                model.put("reutrnUrl", url);
                model.put("search",search);
                model.put("rows", jsonData);

                if (jedis != null) {
                    JedisUtil.returnJedis(jedis);
                }
                return new ModelAndView("pingjia", model);
            } else {
                model.put("search",search);
                return new ModelAndView("index", model);
            }
        } else {
            model.put("search",search);
            model.put("reutrnUrl", url);
            return new ModelAndView("index", model);
        }
    }

    /**
     * seo 深度分析列表信息
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getReportInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView domain(HttpServletRequest request, ModelMap model,
                               @RequestParam(value = "limit", required = false) int endPer,
                               @RequestParam(value = "startPer", required = false) int startPer,
                               @RequestParam(value = "userName", required = false) String userName) {
        AbstractView jsonView = new MappingJackson2JsonView();

        List<ReportInfoDTO> perfect = baseReportInfoService.queryInfo(userName,endPer,startPer);
        long count = baseReportInfoService.getQueryInfoCount(userName);
        Map<String, Object> jsonMapData = JSONUtils.getJsonMapData(perfect);
        jsonMapData.put("countData",count);
        jsonView.setAttributesMap(jsonMapData);

        return new ModelAndView(jsonView);
    }

    /**
     * 报告下载
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/downSeoReport", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void down(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "uid", required = false) String uid,
                               @RequestParam(value = "userName", required = false) String userName) {
        String filename = UUID.randomUUID().toString().replace("-", "") + ".html";
        OutputStream os = null;

        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes("UTF-8"), "ISO8859-1"));
            os = response.getOutputStream();

            baseReportInfoService.downReport(os,uid,userName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * seo 页面分析详细信息
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getReportPageInfo", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getReportPageInfo(HttpServletRequest request, ModelMap model,
                                          @RequestParam(value = "uid", required = false) String uid,
                                          @RequestParam(value = "userName", required = false) String userName) {
        Map<String, List<Object>> stringMap = new HashMap<>();
        AbstractView jsonView = new MappingJackson2JsonView();


        Jedis jedis = JedisUtil.get();
        boolean jedisFalg = jedis.exists("result_" + uid);
        List<Object> objects = new ArrayList<>();
        Map<String, String> stringMap1 = getProblems.readProperties("seo-problems");
        if(jedisFalg){
            String jsonData = jedis.get("result_" + uid);
            baseReportInfoService.updateDate(jsonData,uid,userName);
            //json数据处理
            String _jsonData = JSONJudge.getJsonDge(jsonData);

            jedis.set("seo-"+uid,_jsonData);
            if(jedis.exists("seo-"+uid)){
                jedis.del("result_" + uid);
                jedis.del("tasks_" + uid);
                jedis.del("workers_" + uid);
                jedis.del("duplicate_" + uid);
            }

            objects.add(_jsonData);
            List<Object> objects1 = new ArrayList<>();
            objects1.add(JSONUtils.getJsonString(stringMap1));

            stringMap.put("resultData", objects);
            stringMap.put("resultInfo", objects1);
        }else{
            boolean jedisJudge = jedis.exists("seo-" + uid);

            if(jedisJudge){
                String jsonData = jedis.get("seo-"+uid);
                objects.add(jsonData);
                List<Object> objects1 = new ArrayList<>();
                objects1.add(JSONUtils.getJsonString(stringMap1));
                stringMap.put("resultData", objects);
                stringMap.put("resultInfo", objects1);
            }else{
                ReportInfoDTO reportInfoDTO = baseReportInfoService.getReportByUid(uid, userName);

                if(reportInfoDTO != null && reportInfoDTO.getUserData() != null && !reportInfoDTO.getUserData().equals("0")){
//j                 son数据处理
                    String _jsonData = JSONJudge.getJsonDge(reportInfoDTO.getUserData());

                    jedis.set("seo-"+uid,_jsonData);
                    objects.add(_jsonData);
                    List<Object> objects1 = new ArrayList<>();
                    objects1.add(JSONUtils.getJsonString(stringMap1));

                    stringMap.put("resultData", objects);
                    stringMap.put("resultInfo", objects1);
                }else{
                    Map<String, String> stringMap2 = new HashMap<>();
                    stringMap1.put("flag","0");
                    jsonView.setAttributesMap(stringMap1);
                    return new ModelAndView(jsonView);
                }
            }
        }


        if (jedis != null) {
            JedisUtil.returnJedis(jedis);
        }
        jsonView.setAttributesMap(stringMap);

        return new ModelAndView(jsonView);
    }

    /**
     * seo深度分析信息
     *
     * @param request
     * @param model
     * @param url
     * @return
     */
    @RequestMapping(value = "/domainAjax", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView domainAjax(HttpServletRequest request, ModelMap model,
                                   @RequestParam(value = "urlPath", required = false) String url,
                                   @RequestParam(value = "userName", required = false) String userName) {
        Map<String, String> stringMap = new HashMap<>();
        AbstractView jsonView = new MappingJackson2JsonView();
        if (url != null && !url.equals("")) {

            url = url.replaceAll("http://", "").replace(" ", "").replace("。", ".").toLowerCase();
            Jedis jedis = JedisUtil.get();

            //url正确性判断
            boolean falg = verify(url);
            //获取提交任务相隔时间
            Map<String, String> maxDateMap = baseReportInfoService.getMaxDate(userName);
            //开始处理
            int timefalg = 0;
            if (falg) {
                if (Double.parseDouble(maxDateMap.get("dateTime")) >= 1 || Double.parseDouble(maxDateMap.get("dateTime")) == 1) {
                    //生成UUID
                    String uuid = UUID();
                    jedis.publish("SEO_CHANNEL1", "http://" + url + "|" + uuid);
                    ReportInfoDTO reportInfoDTO = new ReportInfoDTO();
                    reportInfoDTO.setAccount(userName);
                    reportInfoDTO.setDateTime(new Date());
                    reportInfoDTO.setUrl(url);
                    reportInfoDTO.setUuid(uuid);
                    reportInfoDTO.setType(2);
                    baseReportInfoService.insertInfo(reportInfoDTO);
                    timefalg = 1;

                    if (jedis != null) JedisUtil.returnJedis(jedis);

                    stringMap.put("dateFalg", timefalg + "");
                    Map<String, Object> jsonMapData = JSONUtils.getJsonMapData(stringMap);
                    jsonView.setAttributesMap(jsonMapData);
                    return new ModelAndView(jsonView);
                } else {
                    if (jedis != null) JedisUtil.returnJedis(jedis);
                    stringMap.put("dateFalg", timefalg + "");
                    stringMap.put("dateTime", maxDateMap.get("date"));
                    Map<String, Object> jsonMapData = JSONUtils.getJsonMapData(stringMap);
                    jsonView.setAttributesMap(jsonMapData);
                    return new ModelAndView(jsonView);
                }
            } else {
                if (jedis != null) JedisUtil.returnJedis(jedis);
                stringMap.put("dateFalg", "2");
                Map<String, Object> jsonMapData = JSONUtils.getJsonMapData(stringMap);
                jsonView.setAttributesMap(jsonMapData);
                return new ModelAndView(jsonView);
            }

        }
        model.put("reutrnUrl", url);
        return new ModelAndView("index", model);
    }

    /**
     * 正则表达式验证url
     *
     * @param url
     * @return
     */
    private boolean verify(String url) {

        String ip = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        Pattern regexIP = Pattern.compile(ip);

        Matcher matcher = regexIP.matcher(url);
        if (!matcher.find()) {
            Pattern strRegex = Pattern.compile("^(((https|http)?://)?([a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9]))?.+(com|top|cn|wang|net|org|hk|co|cc|me|pw|la|asia|biz|mobi|gov|name|info|tm|tv|tel|us|tw|website|host|press|cm|tw|sh|ws|in|io|vc|sc|ren))$");
            Matcher matcher1 = strRegex.matcher(url);
            if (matcher1.find()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取uuid
     *
     * @return
     */
    private String UUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", "");
        return uuidString;
    }

}