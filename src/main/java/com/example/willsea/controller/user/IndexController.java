package com.example.willsea.controller.user;

import com.example.willsea.controller.admin.UserController;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.User;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yt on 2018/7/2.
 */

@Controller
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    IBottleService bottleService;
    @Resource
    IUserService userService;

    @GetMapping(value = "/user/regist")
    public String login() {
        return "user/regist";
    }


    @GetMapping(value = "/user/index")
    public String index(HttpServletRequest request, Model model) {
        User cookieUser = (User) request.getSession().getAttribute("cookieUser");
        List<Bottle> bottles = bottleService.getBottlesByUserBlackAndFavoriteList(cookieUser);
        List<Integer> bids = new ArrayList<>();
        for (int i = 0; i < bottles.size(); ++i)
        {
            Bottle curBottle=bottles.get(i);
            if(curBottle.getIsPrivate().equals("false"))
                bids.add(curBottle.getBid());
        }
        model.addAttribute("cookieUser", cookieUser);
        model.addAttribute("bids", bids);
        return "user/index";
    }
}