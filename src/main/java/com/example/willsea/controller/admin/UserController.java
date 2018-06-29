package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
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
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;


    @GetMapping(value = "/back/user")
    public RestResponse list(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "limit", defaultValue = "8") int limit, Model model){
        List<User> users = userService.queryAll(page,limit);
        Integer recordNum = userService.queryTotalNumber();
        model.addAttribute("users", users);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return RestResponse.ok();
    }

    /**
     * 添加用户
     */
    @PostMapping(value = "/back/save")
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
    @PostMapping(value = "/back/user/save")
    @ResponseBody
    public  RestResponse save(@RequestParam(value = "uid")Integer uid,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "forbidden")String forbidden) {
        try {
            User user=userService.queryById(uid);
            if(user==null)
            {
                return RestResponse.fail("data not exists");
            }
            user.setEmail(email);
            user.setForbidden(forbidden);
            userService.updateUser(user);
        }catch (Exception e){
            String msg = "deleting comment failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }
    @PostMapping(value = "/back/user/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "uid")Integer uid)
    {
        try {
            User user=userService.queryById(uid);
            if(user==null)
            {
                return RestResponse.fail("data not exists.");
            }
            userService.deleteUser(uid);
        }catch (Exception e){
            String msg = "deleting comment failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }


}
