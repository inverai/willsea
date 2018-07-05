package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.entity.User;
import com.example.willsea.exception.SubException;
import com.example.willsea.service.IBottleService;
import com.example.willsea.service.ICommentService;
import com.example.willsea.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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
        return "back/wishbottle";
    }

    @PostMapping(value = "/back/wishbottle/save")
    @ResponseBody
    public RestResponse save(@RequestParam(value = "bid")Integer bid, @RequestParam(value = "title")String title,
                             @RequestParam(value = "isPrivate")String isPrivate
                             ) {
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

    @PostMapping(value = "/user/wishbottle/save")
    @ResponseBody
    public RestResponse userBottleSave(@RequestParam(value = "bid")Integer bid,
                                 @RequestParam(value = "title")String title,
                             @RequestParam(value = "btext")String btext,
                             @RequestParam(value = "isPrivate")String isPrivate
    ) {
        try {
            Bottle bottle = bottleService.getBottle(bid);
            bottle.setTitle(title);
            bottle.setIsPrivate(isPrivate);
            bottle.setBtext(btext);
            bottleService.updateBottle(bottle);
        } catch (Exception e){
            String msg = "保存心愿瓶失败";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @PostMapping(value = "/user/wishbottle/create")
    @ResponseBody
    public RestResponse userBottleCreate(@RequestParam(value = "aid")Integer aid,
                                 @RequestParam(value = "title")String title,
                                 @RequestParam(value = "btext")String btext,
                                 @RequestParam(value = "isPrivate")String isPrivate
    ) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Bottle bottle = new Bottle();
            bottle.setAid(aid);
            bottle.setTime(df.format(new Date()));
            bottle.setTitle(title);
            bottle.setIsPrivate(isPrivate);
            bottle.setBtext(btext);
            bottleService.createBottle(bottle);
        } catch (Exception e){
            String msg = "创建心愿瓶失败";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }



    @PostMapping(value = "/back/wishbottle/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "bid")Integer bid) {
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
    @GetMapping(value="/user/detail/{bid}")   //客户端展示心愿瓶的详细内容
    public String userdetail(@PathVariable Integer bid, Model model, HttpServletRequest request) {
        Bottle bottle = bottleService.getBottle(bid);
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");  //登录状态，若coolieUser为null则未登录，不为null则是当前已经登录的用户
        if(bottle==null&&cookieUser!=null)
            return "redirect:user/usercenter/"+cookieUser.getUid();
        else if(bottle==null&&cookieUser==null)
            return  "redirect:/user/index";
        String username = userService.queryById(bottleService.getBottle(bid).getAid()).getUsername();
        List<Comment> comments = commentService.getCommentsByBottle(bid, 0, 65535);
        HashMap<Integer, String> authorNames = new HashMap<>();  //评论ID和评论用户名对应的hash表
        for (Comment comment: comments)
        {
            String name = userService.queryById(comment.getAid()).getUsername();
            authorNames.put(comment.getCid(), name);
        }
        if(cookieUser!=null)
        {
            model.addAttribute("isInFavoriteList",userService.isInTypeList(cookieUser.getUid(),bottle.getAid(),"favorite"));
            model.addAttribute("isInBlackList",userService.isInTypeList(cookieUser.getUid(),bottle.getAid(),"black"));
        }
        model.addAttribute("cookieUser",cookieUser);
        model.addAttribute("bottle", bottle);
        model.addAttribute("username", username);
        model.addAttribute("authorNames", authorNames);
        model.addAttribute("comments", comments);
        return "user/detail";
    }
    @GetMapping(value = "/back/wishbottle/detail/{bid}")
    public String backdetail(@PathVariable Integer bid, Model model) {

        String username = userService.queryById(bottleService.getBottle(bid).getAid()).getUsername();
        List<Comment> comments = commentService.getCommentsByBottle(bid, 0, 8);
        HashMap<Integer, String> authorNames = new HashMap<>();
        for (Comment comment: comments) {
            String name = userService.queryById(comment.getAid()).getUsername();
            authorNames.put(comment.getCid(), name);
        }


        Bottle bottle = bottleService.getBottle(bid);
        model.addAttribute("bottle", bottle);
        model.addAttribute("username", username);
        model.addAttribute("authorNames", authorNames);
        model.addAttribute("comments", comments);
        return "back/detail";
    }

}
