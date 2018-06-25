package com.example.willsea.service;

import com.example.willsea.entity.Comment;

import java.util.ArrayList;

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
    ArrayList<Comment> getComments(Integer bid);

    /**
     * 根据主键查询评论
     */
    Comment getCommentById(Integer cid);

    /**
     * 删除评论
     */
    void deleteComment(Integer cid);

    /**
     * 更新评论
     */
    void updateComment(Comment comment);
}
