package com.example.willsea.controller.admin;

import com.example.willsea.dto.RestResponse;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.service.ICommentService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/26.
 */
@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private ICommentService commentService;

    @GetMapping(value = "/back/comment")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "limit", defaultValue = "8") int limit, Model model){
        System.out.println("Get Next Time1");
        List<Comment> comments = commentService.queryAll((page - 1) * limit, limit);
        Integer recordNum = commentService.queryTotalNumber();
        model.addAttribute("comments", comments);
        model.addAttribute("recordNum", recordNum);
        model.addAttribute("page",page);
        model.addAttribute("limit",limit);
        return "back/comment";
    }

    @PostMapping(value = "/back/comment/save")
    @ResponseBody
    public RestResponse save(@RequestParam(value = "cid")Integer cid,
                               @RequestParam(value = "content")String content) {
        System.out.println("Get the commentId to be saved: " + cid);
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

    @PostMapping(value = "/back/comment/delete")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "cid")Integer cid,
                               @RequestParam(value = "bid")Integer bid) {
        System.out.println("Get the commentId to be deleted: " + cid);
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
}
