package com.example.willsea.service;

import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.entity.User;

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


    //在bottle下添加评论
    String  addCommentToBottle(Comment comment, Bottle bottle);




    //更新心愿瓶
    String updateBottle(Bottle bottle);





    //删除瓶子，text，audio，video
    String deleteBottle(Integer bid);
    Integer deleteText(Integer bid);
    Integer deleteAudio(Integer bid);
    Integer deleteVideo(Integer bid);
    //查询区间心愿瓶
    List<Bottle> queryAll(Integer offset, Integer limit);
    //查询总数
    Integer queryTotalNumber();
    //主键查询
    Bottle getBottle(Integer bid);
    //作者查询
    List<Bottle>  queryByAuthor(Integer aid,Integer offset,Integer limit);
    //根据user黑白名单获取心愿海中的内容
    List<Bottle> getBottlesByUserBlackAndFavoriteList(User user);
}
