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

    /**
     * 增
     * @param user
     * @return
     */
    //添加用户
    int insert(User user);
    //黑白名单添加
    int insertBlackRecord(@Param("source")Integer source, @Param("target")Integer target);
    int insertFavoriteRecord(@Param("source")Integer source, @Param("target")Integer target);

    /**
     * 删
     * @param uid
     * @return
     */
    //根据uid删除
    int deleteById(Integer uid);

    //source_uid,target_uid删除黑白名单
    Integer freeFromFavoriteList(@Param("source") Integer source,@Param("target")Integer target);
    Integer freeFromBlackList(@Param("source") Integer source,@Param("target")Integer target);

    /**
     * 改
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 查
     * @param
     * @return
     */
    //查询用户总数
    Integer queryTotalNumber();
    //根据主键查询用户
    User queryById(Integer uid);
    //根据用户名查询用户
    User queryByUsername(String username);
    //查询一个用户的黑名单和特别关注
    List<User> queryBlackList(Integer uid);
    List<User> queryFavoriteList(Integer uid);
    //查询是否在黑白名单中
    Integer isInFavoriteList(@Param("source") Integer source,@Param("target")Integer target);
    Integer isInBlackList(@Param("source") Integer source,@Param("target")Integer target);
    //从offset和limit查询所有区间用户
    List<User> queryAll(@Param("offset")Integer offset, @Param("limit") Integer limit);
    //登录验证查询
    User queryToVerify(@Param("username") String username, @Param("password") String password);

}
