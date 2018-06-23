package com.example.willsea.entity;

import java.io.Serializable;

/**
 * Created by yt on 2018/6/22.
 */
public class Comment implements Serializable {
    /**
     * 评论ID
      */
    private Integer Cid;
    /**
     * 作者ID
     */
    private Integer Aid;
    /**
     * 心愿ID
     */
    private Integer Hid;
    /**
     * 创建日期
     */
    private Integer date;
    /**
     * 内容
     */
    private String content;
    /**
     * 评论类型
     */
    private String type;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return Cid;
    }

    public void setCid(Integer cid) {
        Cid = cid;
    }

    public Integer getAid() {
        return Aid;
    }

    public void setAid(Integer aid) {
        Aid = aid;
    }

    public Integer getHid() {
        return Hid;
    }

    public void setHid(Integer hid) {
        Hid = hid;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
