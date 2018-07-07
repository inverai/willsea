package com.example.willsea.service;

import com.example.willsea.entity.User;

import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */
public interface IUserService {

    Integer queryTotalNumber();

    /**
     * 保存新用户
     */
    Integer insertUser(User user);

    /**
     * uid查找用户
     */
    User queryById(Integer uid);

    /**
     * 查找全部用户
     */
    List<User> queryAll(Integer offset, Integer limit);
    /**
     * 用户登陆
     */
    User login(String username, String password);

    /**
     * 删除用户
     */
    void deleteUser(Integer uid);

    /**
     * 修改用户数据
     */
    void updateUser(User user);

    /**
     * 拉黑用户
     */
    void avoidUser(Integer sourceId, Integer targetId);


    /**
     * 关注用户
     */
    void favoriteUser(Integer sourceId, Integer targetId);


    // 查询用户名是否重复
    User queryByUsername(String username);


    //获取sex为female的用户总数
    Integer queryFemaleNumber();

    User queryToVerify(String username,String password);  //登录验证
    //获取user的黑白名单
    List<User> queryFavoriteList(User user);
    List<User> queryBlackList(User user);
    //删除user的黑白名单
    Integer    freeFromFavoriteList(User source,User target);
    Integer    freeFromBlackList(User source,User target);
    //判断是否在黑白名单中
    boolean  isInTypeList(Integer source,Integer target,String type);
}
