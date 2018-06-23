package com.example.willsea.service.impl;

import com.example.willsea.dao.UserDAO;
import com.example.willsea.entity.User;
import com.example.willsea.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

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
        return null;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public void deleteUser(Integer uid) {

    }

    @Override
    public void updateUser(User user) {

    }
}
