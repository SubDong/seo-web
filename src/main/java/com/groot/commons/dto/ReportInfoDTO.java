package com.groot.commons.dto;

import java.util.Date;

/**
 * Created by SubDong on 2015/2/2.
 */
public class ReportInfoDTO {

    private String id;

    //用户帐号
    private String account;

    //UUID
    private String uuid;

    //分析URL
    private String url;

    //分析时间
    private Date dateTime;

    //(0、完成 1、爬取中)
    private Integer type;

    private String userData;

    private String dateString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "ReportInfoDTO{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", uuid='" + uuid + '\'' +
                ", url='" + url + '\'' +
                ", dateTime=" + dateTime +
                ", type=" + type +
                ", userData='" + userData + '\'' +
                ", dateString='" + dateString + '\'' +
                '}';
    }
}
