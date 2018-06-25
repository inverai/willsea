package com.example.willsea.controller.admin;

import com.example.willsea.entity.User;
import com.example.willsea.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */
@Controller
@RequestMapping("/back")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @GetMapping(value = "/user")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, Model model){
        List<User> users = userService.queryAll();
        Integer recordNum = users.size();
        model.addAttribute("users", users);
        model.addAttribute("recordNum", recordNum);
        return "back/user";
    }

    @GetMapping(value = "")
    public String index(Model model){
        return "back/index";
    }

    /**
     * 添加用户
     */
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
