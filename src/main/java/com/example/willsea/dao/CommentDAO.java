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
    // add
    int insert(Comment comment);

    // delete
    int delete(Integer cid);


    // select
    Comment selectById(Integer cid);
    List<Comment> selectByAuthorId(@Param("aid") Integer aid, @Param("offest")Integer offset, @Param("limit")Integer limit);
    List<Comment> selectByBottleId(@Param("bid") Integer bid, @Param("offest")Integer offset, @Param("limit")Integer limit);
}
