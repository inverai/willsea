package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yt on 2018/6/26.
 */
@Controller
public class BottleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BottleController.class);

    @Resource
    private IBottleService bottleService;

    @Resource
    private IUserService userService;

    @GetMapping(value = "/back/wishbottle")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "limit", defaultValue = "8") int limit, Model model){
        List<Bottle> bottles = bottleService.queryAll((page - 1) * limit, limit);
        Integer recordNum = bottleService.queryTotalNumber();
        HashMap<Integer, String> usernameMap = new HashMap<>();
        for (Bottle bottle: bottles) {
            String username = userService.queryById(bottle.getAid()).getUsername();
            usernameMap.put(bottle.getAid(), username);
        }
        model.addAttribute("map", usernameMap);
        model.addAttribute("bottles", bottles);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        for (Bottle bottle: bottles) {
            System.out.println(bottle.getAid() + " ; " + bottle.getBid() + " ; " + bottle.getContent());
        }

        return "back/wishbottle";
    }

    @PostMapping(value = "/back/wishbottle/save")
    @ResponseBody
    public RestResponse save(@RequestParam(value = "bid")Integer bid, @RequestParam(value = "title")String title,
                             @RequestParam(value = "content")String content, @RequestParam(value = "isPrivate")String isPrivate
                             ) {
        System.out.println("Get the BottleId to be saved: " + bid);
        try {
            Bottle bottle = bottleService.getBottle(bid);
            bottle.setTitle(title);
            bottle.setContent(content);
            bottle.setIsPrivate(isPrivate);
            bottleService.updateBottle(bottle);
        } catch (Exception e){
            String msg = "保存心愿瓶失败";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @PostMapping(value = "/back/wishbottle/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "bid")Integer bid) {
        System.out.println("Get the bottleId to be deleted: " + bid);
        try{
            Bottle bottle = bottleService.getBottle(bid);
            if(null == bottle) {
                return RestResponse.fail("bottle not exists!");
            }
            bottleService.deleteBottle(bid);
        } catch (Exception e){
            String msg = "deleting bottle failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @GetMapping(value = "/back/wishbottle/detail")
    public String detail(Model model) {
        System.out.println("show the detail message for the bottle");

        return "back/detail";
    }
}
