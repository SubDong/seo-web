package com.groot.mongodb.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by XiaoWei on 2015/1/8.
 */
public class BaseEntity {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
