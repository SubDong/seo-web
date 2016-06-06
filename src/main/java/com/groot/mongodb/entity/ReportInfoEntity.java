package com.groot.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by SubDong on 2015/2/2.
 */
public class ReportInfoEntity {

    @Id
    private String id;

    @Field("act")
    private String account;

    @Field("uuid")
    private String uuid;

    @Field("date")
    private Date dateTime;

    @Field("url")
    private String url;

    //(0、完成 1、取消 2、爬取中)
    @Field("type")
    private Integer type;

    @Field("data")
    private String userData;

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "ReportInfoEntity{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", uuid='" + uuid + '\'' +
                ", dateTime=" + dateTime +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", userData='" + userData + '\'' +
                '}';
    }
}
