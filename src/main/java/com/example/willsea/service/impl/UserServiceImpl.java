package com.example.willsea.service.impl;

import com.example.willsea.dao.UserDAO;
import com.example.willsea.entity.User;
import com.example.willsea.exception.SubException;
import com.example.willsea.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */

@Service
public class UserServiceImpl implements IUserService{

    /**
     *  注入DAO对象
     */
    @Resource
    private UserDAO userDAO;


    @Override
    public Integer queryTotalNumber() {
        return userDAO.queryTotalNumber();
    }

    /**
     * 事务管理，
     * @param user
     * @return Integer
     */
    @Override
    @Transactional
    public Integer insertUser(User user) {
        if(!user.getUsername().trim().isEmpty() && !user.getPassword().trim().isEmpty()) {
            userDAO.insert(user);
        }
        return user.getUid();
    }

    @Override
    public User queryById(Integer uid) {
        User user = new User();
        if(uid != null){
            user = userDAO.queryById(uid);
        }

        return user;
    }

    @Override
    public List<User> queryAll(Integer offset, Integer limit) {
        return userDAO.queryAll(offset, limit);
    }

    @Override
    public User login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new SubException("用户名与密码不可为空");
        }

        User user = new User();
        user = userDAO.queryToVerify(username, password);
        if(user == null){
            throw new SubException("用户名或密码错误");
        }
        return user;

    }

    @Override
    public void deleteUser(Integer uid) {
        if(uid == null) {
            throw new SubException("删除用户不存在");
        }
        userDAO.deleteById(uid);
    }

    @Override
    public void updateUser(User user) {
        if(user == null) {
            throw new SubException("更新数据为空");
        }
        userDAO.update(user);
    }

    /**
     * 拉黑用户
     * @param sourceId
     * @param targetId
     */
    @Override
    public void avoidUser(Integer sourceId, Integer targetId) {
        if(sourceId == null || targetId == null) {
            throw new SubException("黑名单条件不成立");
        }
        userDAO.insertBlackRecord(sourceId, targetId);
    }

    @Override
    public void favoriteUser(Integer sourceId, Integer targetId) {
        if(sourceId == null || targetId == null) {
            throw new SubException("关注条件不成立");
        }
        userDAO.insertFavoriteRecord(sourceId, targetId);

    }

    @Override
    public User queryByUsername(String username) {
        return userDAO.queryByUsername(username);
    }

    @Override
    public Integer queryFemaleNumber() {
        return null;
    }

    @Override
    public User queryToVerify(String username, String password) {
        return  userDAO.queryToVerify(username,password);
    }

    @Override
    public List<User> queryFavoriteList(User user) {
        return userDAO.queryFavoriteList(user.getUid());
    }

    @Override
    public List<User> queryBlackList(User user) {
        return  userDAO.queryBlackList(user.getUid());
    }

    @Override
    public Integer freeFromFavoriteList(User source, User target) {
        return null;
    }

    @Override
    public Integer freeFromBlackList(User source, User target) {
        return null;
    }

    @Override
    public void deleteOneBlack(Integer black_id) {
        if(black_id == null){
            throw new SubException("条件错误");
        }
        userDAO.deleteBlackRecord(black_id);

    }

    @Override
    public void deleteOneFavorite(Integer favorite_id) {
        if(favorite_id == null){
            throw new SubException("条件错误");
        }
        userDAO.deleteBlackRecord(favorite_id);
    }




}
