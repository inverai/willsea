package com.example.willsea.entity;

import java.io.Serializable;

/**
 * Created by yt on 2018/6/22.
 */
public class User implements Serializable{


    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户类型
     */
    private String type;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 是否禁用
     */
    private String forbidden;


    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 地区
     */
    private String location;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 头像
     */
    private String headImageUrl;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getForbidden() {
        return forbidden;
    }

    public void setForbidden(String forbidden) {
        this.forbidden = forbidden;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    private static final long serialVersionUID = 1L;
}
