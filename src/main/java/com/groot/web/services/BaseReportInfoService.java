package com.groot.web.services;

import com.groot.commons.dto.ReportInfoDTO;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2015/2/2.
 */
public interface BaseReportInfoService {

    /**
     * 添加用户对应爬取信息
     * @param reportInfoDTO
     */
    public void insertInfo(ReportInfoDTO reportInfoDTO);

    /**
     * 查询用户除取消以外的说有页面分析信息
     * @param userName
     */
    public List<ReportInfoDTO> queryInfo(String userName, int endPer, int startPer);

    /**
     * 查询用户总条数
     * @param userName
     */
    public long getQueryInfoCount(String userName);

    /**
     * 查询用户最近一条页面分析信息
     * @param userName
     */
    public Map<String,String> getMaxDate(String userName);
    /**
     * 更新对应的数据（保存爬取下来的完整数据）
     * @param data
     */
    public void updateDate(String data,String uuid, String userName);

    /**
     * 通过 uuid查询数据
     * @param uid
     * @param userName
     * @return
     */
    public ReportInfoDTO getReportByUid(String uid,String userName);

    /**
     * 下载报告数据
     * @param os
     * @param uid
     * @param userName
     */
    public void downReport(OutputStream os,String uid,String userName);
}