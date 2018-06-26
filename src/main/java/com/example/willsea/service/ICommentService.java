package com.example.willsea.service;

import com.example.willsea.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */
public interface ICommentService {

    /**
     * 保存新评论
     */
    String insertComment(Comment comment);

    /**
     * 获取心愿瓶下面的评论
     */
    List<Comment> getCommentsByBottle(Integer bid,Integer offset,Integer limit);

    /**
     * 获取用户的评论
     */
    List<Comment> getCommmentsByUser(Integer uid,Integer offset,Integer limit);
    /**
     *
     * 根据主键查询评论
     */
    Comment getCommentById(Integer cid);

    /**
     * 删除评论
     */
    String deleteComment(Integer cid);

    /**
     * 更新评论
     */
    String  updateComment(Comment comment);



    List<Comment> AdminGetComments(Integer pageNo, Integer pageRowNumber);

    /**
     * 获取全部评论
     */
    List<Comment> queryAll(Integer offset, Integer limit);

}
