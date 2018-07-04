package com.example.willsea.entity;

import java.io.Serializable;

/**
 * Created by yt on 2018/6/22.
 */
public class Comment implements Serializable {
    /**
     * 评论ID
      */
    private Integer cid;
    /**
     * 作者ID
     */
    private Integer aid;
    /**
     * 心愿ID
     */
    private Integer bid;
    /**
     * 创建日期
     */
    private String ctime;
    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
