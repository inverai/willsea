package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.entity.User;
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
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yt on 2018/6/26.
 */
@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private ICommentService commentService;

    @Resource
    private IUserService userService;

    @Resource
    private IBottleService bottleService;

    /*
        根据页序号找到数据库中对应的一段心愿瓶
     */
    @GetMapping(value = "/back/comment")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "limit", defaultValue = "8") int limit, Model model){
        List<Comment> comments = commentService.queryAll((page - 1) * limit, limit);

        HashMap<Integer, String> userMap = new HashMap<>();
        HashMap<Integer, String> bottleMap = new HashMap<>();

        //用hash表保存<uid,uername>对应关系
        for (Comment comment: comments) {
            String userName = userService.queryById(comment.getAid()).getUsername();
            String bottleName = bottleService.getBottle(comment.getBid()).getTitle();
            userMap.put(comment.getCid(), userName);
            bottleMap.put(comment.getCid(), bottleName);
        }

        //传到前端
        Integer recordNum = commentService.queryTotalNumber();
        model.addAttribute("comments", comments);
        model.addAttribute("userMap", userMap);
        model.addAttribute("bottleMap", bottleMap);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return "back/comment";
    }
    /*
           对信息心愿瓶的修改进行保存
     */
    @PostMapping(value = "/back/comment/save")
    @ResponseBody
    public RestResponse save(@RequestParam(value = "cid")Integer cid,
                               @RequestParam(value = "content")String content) {
        try {
            Comment comment = commentService.getCommentById(cid);
            comment.setContent(content);
            commentService.updateComment(comment);
        } catch (Exception e){
            String msg = "删除评论失败";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok();
    }

    @PostMapping(value = "/back/comment/delete")  //删除心愿瓶
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "cid")Integer cid,
                               @RequestParam(value = "bid")Integer bid) {
        try{
            Comment comment = commentService.getCommentById(cid);
            if(null == comment) {
                return RestResponse.fail("data not exists.");
            }
            commentService.deleteComment(cid);
        }catch (Exception e){
            String msg = "deleting comment failed.";
            LOGGER.error(msg, e);
            return RestResponse.fail(msg);
        }
        return RestResponse.ok(bid);
    }
    /*
        用户端，在心愿瓶下添加的注释
     */

    @PostMapping(value = "/user/detail/addComment")
    public  String addComment(@Param(value = "bid")Integer bid, @Param(value = "text")String text, HttpServletRequest request)
    {
        //当前的登录状态,cookieUser不为null才能进行评论
        User cookieUser=(User)request.getSession().getAttribute("cookieUser");
        if(cookieUser!=null)
        {
            Bottle curbottle=bottleService.getBottle(bid);
            User bottleAuthor=userService.queryById(curbottle.getAid());
            //如果cookieUser不在心愿瓶作者的黑名单内才能评论
            if(!userService.isInTypeList(bottleAuthor.getUid(),cookieUser.getUid(),"black"))
            {
                Comment comment=new Comment();
                comment.setAid(cookieUser.getUid());
                comment.setBid(curbottle.getBid());
                comment.setContent(text);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new Date());
                comment.setCtime(date);
                bottleService.addCommentToBottle(comment,curbottle);
                return "redirect:/user/detail/"+bid;
            }
        }
        return "error/404";
    }
}
