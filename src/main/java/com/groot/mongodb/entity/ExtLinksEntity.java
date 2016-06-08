package com.groot.mongodb.entity;

import com.groot.commons.MongoEntityConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by subDong on 2015/1/8.
 */
@Document(collection = MongoEntityConstants.TBL_EXTLINKS)
public class ExtLinksEntity extends BaseEntity{
    private String url;
    @Field(value = "sd")
    private Date saveDate;
    @Field(value = "elc")
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
        return "ExtLinks{" +
                "extLinksCount=" + extLinksCount +
                ", url='" + url + '\'' +
                ", saveDate=" + saveDate +
                '}';
    }
}
