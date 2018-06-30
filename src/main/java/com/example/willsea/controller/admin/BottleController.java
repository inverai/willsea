package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.exception.SubException;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.ICommentService;
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
import java.util.Map;

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

    @Resource
    private ICommentService commentService;

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
            System.out.println(bottle.getAid() + " ; " + bottle.getBid() + " ; ");
        }

        return "back/wishbottle";
    }

    @PostMapping(value = "/back/wishbottle/save")
    @ResponseBody
    public RestResponse save(@RequestParam(value = "bid")Integer bid, @RequestParam(value = "title")String title,
                             @RequestParam(value = "isPrivate")String isPrivate
                             ) {
        System.out.println("Get the BottleId to be saved: " + bid);
        try {
            Bottle bottle = bottleService.getBottle(bid);
            bottle.setTitle(title);
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

    @PostMapping(value = "/back/wishbottle/deleteContent")
    @ResponseBody
    public RestResponse deleteContent(@RequestParam(value = "bid")Integer bid,
                                      @RequestParam(value = "type")String type) {
        System.out.println("delete the " + type + " in bottle " + bid);
        try{
            if(type.equals("text")) {
                bottleService.deleteText(bid);
            } else if(type.equals("audio")) {
                bottleService.deleteAudio(bid);
            } else if(type.equals("video")) {
                bottleService.deleteVideo(bid);
            }
        } catch (SubException e) {
            String msg = "delete bottle content fialed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @GetMapping(value = "/back/wishbottle/detail/{bid}")
    public String detail(@PathVariable Integer bid, Model model) {
        System.out.println("show the detail message for the bottle" + bid);

        String username = userService.queryById(bottleService.getBottle(bid).getAid()).getUsername();
        List<Comment> comments = commentService.getCommentsByBottle(bid, 0, 8);
        HashMap<Integer, String> authorNames = new HashMap<>();
        for (Comment comment: comments) {
            String name = userService.queryById(comment.getAid()).getUsername();
            authorNames.put(comment.getCid(), name);
        }

        for(Map.Entry<Integer, String> entry: authorNames.entrySet()){
            System.out.println("Key: " +  entry.getKey() + " value: " + entry.getValue());
        }

        Bottle bottle = bottleService.getBottle(bid);
        model.addAttribute("bottle", bottle);
        model.addAttribute("username", username);
        model.addAttribute("authorNames", authorNames);
        model.addAttribute("comments", comments);
        return "back/detail";
    }
}
