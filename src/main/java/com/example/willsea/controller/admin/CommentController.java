package com.example.willsea.controller.admin;

import com.example.willsea.entity.Comment;
import com.example.willsea.service.ICommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/26.
 */
@Controller
@RequestMapping("/back")
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private ICommentService commentService;

    @GetMapping(value = "/comment")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "limit", defaultValue = "10") int limit, Model model){
        List<Comment> comments = commentService.queryAll(page * limit, limit);
        Integer recordNum = comments.size();
        model.addAttribute("comments", comments);
        model.addAttribute("recordNum", recordNum);
        return "back/comment";
    }
}
