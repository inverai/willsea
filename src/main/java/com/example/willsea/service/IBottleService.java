package com.example.willsea.service;

import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;

import java.util.ArrayList;
import java.util.List;

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
    List<Bottle> getBottles(Integer row, Integer col);

    /**
     * 根据主键查询漂流瓶
     */

    Bottle getBottle(Integer bid);

    String  addCommentToBottle(Comment comment, Bottle bottle);
    List<Bottle> AdminGetBottles(Integer pageNo, Integer pageRowNumber);


    List<Bottle> queryAll(Integer offset, Integer limit);

    String updateBottle(Bottle bottle);

    String deleteBottle(Integer bid);

    Integer queryTotalNumber();

    String deleteText(Integer bid);
    String deleteAudio(Integer bid);
    String deleteVideo(Integer bid);

}
