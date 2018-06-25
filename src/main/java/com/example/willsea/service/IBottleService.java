package com.example.willsea.service;

import com.example.willsea.entity.Bottle;

import java.util.ArrayList;

/**
 * Created by yt on 2018/6/22.
 */
public interface IBottleService {

    /**
     * 发布心愿瓶
     */
    String publish(Bottle bottle);


    /**
     * 新建心愿瓶
     */
    int createBottle(Bottle bottle);

    /**
     * 批量获取漂流瓶
     */
    ArrayList<Bottle> getBottles(Integer row, Integer col);

    /**
     * 根据主键查询漂流瓶
     */

    Bottle getBottle(Integer bid);


}
