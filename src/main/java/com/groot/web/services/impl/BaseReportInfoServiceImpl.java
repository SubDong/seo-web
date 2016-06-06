package com.groot.web.services.impl;

import com.google.common.primitives.Bytes;
import com.groot.commons.dto.ReportInfoDTO;
import com.groot.mongodb.dao.BaseReportInfoDao;
import com.groot.utils.getProblems;
import com.groot.web.services.BaseReportInfoService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by SubDong on 2015/2/2.
 */
@Service
public class BaseReportInfoServiceImpl implements BaseReportInfoService {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    @Resource
    private BaseReportInfoDao baseReportInfoDao;

    @Override
    public void insertInfo(ReportInfoDTO reportInfoDTO) {
        reportInfoDTO.setUserData("0");
        baseReportInfoDao.insertInfo(reportInfoDTO);
    }

    @Override
    public List<ReportInfoDTO> queryInfo(String userName, int endPer, int startPer) {
        List<ReportInfoDTO> reportInfoDTOs = baseReportInfoDao.queryInfo(userName, endPer, startPer);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ReportInfoDTO reportInfoDTO : reportInfoDTOs) {
            reportInfoDTO.setDateString(dateFormat.format(reportInfoDTO.getDateTime()));
        }
        return reportInfoDTOs;
    }

    @Override
    public long getQueryInfoCount(String userName) {
        return baseReportInfoDao.getQueryInfoCount(userName);
    }

    @Override
    public Map<String, String> getMaxDate(String userName) {
        Map<String, String> stringMap = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = baseReportInfoDao.getMaxDate(userName);
        if (date != null) {
            Date newDate = new Date();

            Calendar nowDate = Calendar.getInstance(), oldDate = Calendar.getInstance();
            nowDate.setTime(date);
            oldDate.setTime(newDate);

            double dateTimeMis = (oldDate.getTimeInMillis() - nowDate.getTimeInMillis()) * 1.0 / (1000 * 60 * 30);
            stringMap.put("date", format.format(date));
            stringMap.put("dateTime", dateTimeMis + "");
        } else {
            stringMap.put("dateTime", "3");
        }
        return stringMap;
    }

    @Override
    public void updateDate(String data, String uuid, String userName) {
        baseReportInfoDao.updateDate(data, uuid, userName);
    }

    @Override
    public ReportInfoDTO getReportByUid(String uid, String userName) {
        return baseReportInfoDao.getDownReport(uid,userName);
    }

    @Override
    public void downReport(OutputStream os, String uid, String userName) {
        ReportInfoDTO reportInfoDTO = baseReportInfoDao.getDownReport(uid, userName);

        if (reportInfoDTO != null && reportInfoDTO.getUserData() != null) {

            try {
                JSONObject jsonObject = new JSONObject(reportInfoDTO.getUserData());
                Map<String, String> stringMap = getProblems.readProperties("seo-problems");
                String bodyhtml = "";
                for (int i = 1; i <= 20; i++) {
                    boolean judge = jsonObject.isNull("SEO1_" + i + "_problems");
                    if (!judge) {
                        JSONArray jsonArray = jsonObject.getJSONArray("SEO1_" + i + "_problems");
                        String problemsHtml = "";
                        StringBuilder buffer = new StringBuilder();
                        for (int j = 0,l = jsonArray.length(); j < l; j++) {
                                buffer.append(jsonArray.get(j)).append(((j == 0) ? "" : ((j % 4 == 0) ? "<br />" : "，")));
                        }

                        bodyhtml = bodyhtml + "<tr cname='clickTr' style='cursor: pointer'><td class='positive'><i class='question circular icon link z-idx' data-content='" + stringMap.get("SEO1_" + i + "_question") + "'></i><b>" + stringMap.get("SEO1_" + i) + "</b></td>" +
                                "<td>" + jsonObject.get("SEO1_" + i) + "</td><td>" + stringMap.get("SEO1_" + i + "_problems") + "</td><td class='textTitle'>点击展开查看问题连接</td>" +
                                "<td>" + jsonArray.length() + "</td><td>" + stringMap.get("SEO1_" + i + "_scores") + "</td></tr>" +
                                "<tr class='noneTr' style='display:none'><td colspan='6'style='padding:0px;margin:0px'><table id='maxTable' width='100%'><tr><td style='text-align:center'>"+buffer.toString()+"</td></tr></table></td></tr>";
                        }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("<html>");
                sb.append("<head>");
                sb.append("<title>报告详细信息</title>");
                sb.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
                sb.append("<link rel='stylesheet' href='http://cdn.bootcss.com/semantic-ui/1.7.3/semantic.min.css'>");
                sb.append("<script src='http://cdn.bootcss.com/jquery/2.1.3/jquery.min.js'></script>");
                sb.append("<script src='http://cdn.bootcss.com/semantic-ui/1.7.3/semantic.min.js'></script>");
                sb.append("<style type='text/css'>");
                sb.append("TABLE{border-collapse:collapse;border-left:solid 1 #000000; border-top:solid 1 #000000;padding:5px;}");
                sb.append("TH{border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
                sb.append("TD{font:normal;border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
                sb.append(".z-idx{z-index: 1000;}");
                sb.append("</style></head>");
                sb.append("<body bgcolor='#FFF8DC'>");
                sb.append("<div class='content'>");
                sb.append("<div class='content'>");
                sb.append("<table class='ui celled table segment'>");
                sb.append("<thead><tr><th class='three wide'>标题</th><th>得分</th><th>存在的问题</th><th>问题连接</th><th>总数量(条)</th><th>分值</th></tr></thead>");
                sb.append("<tbody>");
                sb.append(bodyhtml);
                sb.append("</tbody></table></div></div>");
                sb.append("<script type='text/javascript'>");
                sb.append("$(document).ready(function(){$('#maxTable').attr('style','width: '+document.documentElement.clientWidth+'px');$('.question').popup();$('tr[cname=clickTr]').click(function(){if(!$(this).next().is(':hidden')){$(this).next().hide(200);$(this).find('.textTitle').html('点击展开查看问题连接');}else{$(this).next().show(500);$(this).find('.textTitle').html('点击收起');}})});");
                sb.append("</script></body></html>");
                os.write(Bytes.concat(commonCSVHead, sb.toString().getBytes(StandardCharsets.UTF_8)));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
