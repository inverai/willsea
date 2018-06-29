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
     *
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

    /**
     *
     * @param cid
     * @return
     */
    boolean deleteById(Integer cid);


    /**
     *
     * @param cid
     * @return
     */
    Comment queryById(Integer cid);
    List<Comment> queryByAuthorId(@Param("aid") Integer aid, @Param("offset")Integer offset, @Param("limit")Integer limit);
    List<Comment> queryByBottleId(@Param("bid") Integer bid, @Param("offset")Integer offset, @Param("limit")Integer limit);
    List<Comment> queryAll(@Param("offset")Integer offset, @Param("limit")Integer limit);

    Integer queryTotalNumber();
}
