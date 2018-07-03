package com.example.willsea.controller.user;

import com.example.willsea.controller.admin.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by yt on 2018/7/2.
 */

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


    @GetMapping(value = "/front/regist")
    public String login() {
        return "front/regist";
    }

//    @PostMapping(value = "/front/post")
//    public String loginFor() {
//
//        System.out.println("execute post request.");
//        return "forward:/back/user";
//    }

}
