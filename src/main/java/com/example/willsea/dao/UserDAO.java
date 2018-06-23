package com.example.willsea.dao;

import com.example.willsea.entity.User;
import org.springframework.stereotype.Component;

/**
 * Created by yt on 2018/6/22.
 */
@Component
public interface UserDAO {

    // add
    int insert(User user);

    // delete
    int delete(Integer uid);

    // update user message
    int update(User user);

    // select
    User queryById(Integer uid);
}
