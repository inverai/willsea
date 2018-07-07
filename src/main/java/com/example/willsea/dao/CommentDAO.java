package com.example.willsea.dao;

import com.example.willsea.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yt on 2018/6/23.
 */
@Component
public interface CommentDAO {
    /**
     * 增
     * @param comment
     * @return
     */
    //实体插入
    int insertComment(Comment comment);

    /**
     * 删
     * @param cid
     * @return
     */
    //主键删除
    boolean deleteById(Integer cid);


    /**
     * 查
     * @param cid
     * @return
     */
    //主键查询
    Comment queryById(Integer cid);
    //创建者 aid 查询所有评论
    List<Comment> queryByAuthorId(@Param("aid") Integer aid, @Param("offset")Integer offset, @Param("limit")Integer limit);
    //心愿瓶下所有评论
    List<Comment> queryByBottleId(@Param("bid") Integer bid, @Param("offset")Integer offset, @Param("limit")Integer limit);
    //offset和limit查询区间
    List<Comment> queryAll(@Param("offset")Integer offset, @Param("limit")Integer limit);
    //查询总数
    Integer queryTotalNumber();
}
