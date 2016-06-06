package com.groot.commons.dto;

import java.io.Serializable;

/**
 * Created by XiaoWei on 2015/1/8.
 */
public class BaseDTO implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
