package com.groot.commons.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by subDong on 2015/2/13.
 */
public class SystemUserDTO extends BaseDTO implements Serializable {

    private String userName;

    private String password;

    private String companyName;

    private Integer state;      //审核状态: 1审核通过, 0审核未通过

    private Integer access;     //1.admin; 2.user

    private byte[] img;

    private String email;

    private List<BaiduAccountInfoDTO> baiduAccounts;

    //系统帐号状态: 1.启用  0.禁用
    private Integer accountState;

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public List<BaiduAccountInfoDTO> getBaiduAccounts() {
        return baiduAccounts;
    }

    public void setBaiduAccounts(List<BaiduAccountInfoDTO> baiduAccounts) {
        this.baiduAccounts = baiduAccounts;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
