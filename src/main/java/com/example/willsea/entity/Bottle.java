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
     * 心愿标题
     */
    private String title;

    /**
     * 心愿是否公开
     */
    private String isPrivate;

    /**
     * 文本信息
     */
    private String btext;

    /**
     * 音频信息
     */
    private String baudio;

    /**
     * 视频信息
     */
    private String bvideo;




    private static final long serialVersionUID = 1L;


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
        this.bid = bid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBtext() {
        return btext;
    }

    public void setBtext(String btext) {
        this.btext = btext;
    }

    public String getBaudio() {
        return baudio;
    }

    public void setBaudio(String baudio) {
        this.baudio = baudio;
    }

    public String getBvideo() {
        return bvideo;
    }

    public void setBvideo(String bvideo) {
        this.bvideo = bvideo;
    }
}
