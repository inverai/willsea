package com.example.willsea.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yt on 2018/6/22.
 */
public class Bottle implements Serializable{

    /**
     * 心愿ID
     */
    private Integer bid;

    /**
     * 作者ID
     */
    private Integer aid;

    /**
     * 创建时间
     */
    private Integer time;

    /**
     * 心愿内容类型
     */
    private String type;

    /**
     * 心愿标题
     */
    private String title;

    /**
     * 心愿内容
     */
    private String content;

    /**
     * 心愿是否公开
     */
    private String isPrivate;
    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        bid = bid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        aid = aid;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
