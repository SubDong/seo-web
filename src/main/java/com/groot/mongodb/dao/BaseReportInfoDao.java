package com.groot.mongodb.dao;

import com.groot.commons.dto.ReportInfoDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2015/2/2.
 */
public interface BaseReportInfoDao {

    /**
     * 添加用户爬取网站基本信息
     * @param reportInfoDTO
     */
    public void insertInfo(ReportInfoDTO reportInfoDTO);

    /**
     * 查询用户除取消以外的说有页面分析信息
     * @param userName
     */
    public List<ReportInfoDTO> queryInfo(String userName,int endPer,int startPer);

    /**
     * 查询用户总条数
     * @param userName
     */
    public long getQueryInfoCount(String userName);
    /**
     * 查询用户最近一条页面分析信息
     * @param userName
     */
    public Date getMaxDate(String userName);
    /**
     * 更新对应的数据（保存爬取下来的完整数据）
     * @param data
     */
    public void updateDate(String data,String uuid, String userName);

    /**
     * 下载数据查询
     * @param uid
     * @param userName
     * @return
     */
    public ReportInfoDTO getDownReport(String uid,String userName);

}
