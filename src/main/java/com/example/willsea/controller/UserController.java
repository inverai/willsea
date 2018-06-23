package com.example.willsea.controller;

import com.example.willsea.entity.User;
import com.example.willsea.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by yt on 2018/6/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    /**
     * 添加用户
     */


    @GetMapping(value = "")
    public String index(Model model){
        model.addAttribute("user", new User());
        return "user/sign";
    }

    @PostMapping(value = "save")
    @ResponseBody
    public String saveUser(@RequestParam String type, @RequestParam String username,
                           @RequestParam String password, @RequestParam String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(type);
        user.setEmail(email);
        userService.insertUser(user);

        return "insert success";

    }

}
