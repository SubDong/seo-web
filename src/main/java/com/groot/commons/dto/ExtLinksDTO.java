package com.groot.commons.dto;

import java.util.Date;

/**
 * Created by XiaoWei on 2015/1/8.
 */
public class ExtLinksDTO extends BaseDTO {
    private String url;
    private Date saveDate;
    private int extLinksCount;

    public int getExtLinksCount() {
        return extLinksCount;
    }

    public void setExtLinksCount(int extLinksCount) {
        this.extLinksCount = extLinksCount;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ExtLinksDTO{" +
                "extLinksCount=" + extLinksCount +
                ", url='" + url + '\'' +
                ", saveDate=" + saveDate +
                '}';
    }
}
