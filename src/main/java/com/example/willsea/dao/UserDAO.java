package com.example.willsea.dao;

import com.example.willsea.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */
@Component
public interface UserDAO {

    // add
    int insert(User user);

    // delete
    int deleteById(Integer uid);

    // update user message
    int update(User user);

    // select
    User queryById(Integer uid);
    User queryToVerify(@Param("username") String username, @Param("password") String password);
    List<User> queryAll(@Param("offset")Integer offset, @Param("limit") Integer limit);

    // make blacklist
    int insertBlackRecord(@Param("source")Integer source, @Param("target")Integer target);
    // cancel blacklist
    int deleteBlackRecord(Integer black_id);

    // make favorite
    int insertFavoriteRecord(@Param("source")Integer source, @Param("target")Integer target);
    // cancel favorite
    int deleteFavoriteRecord(Integer favorite_id);

    int isInBlackList(@Param("source") Integer source,@Param("target")Integer target);

    Integer queryTotalNumber();

    User queryByUsername(String username);
}
