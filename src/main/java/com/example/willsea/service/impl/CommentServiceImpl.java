package com.example.willsea.service.impl;

import com.example.willsea.dao.CommentDAO;
import com.example.willsea.entity.Comment;
import com.example.willsea.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yt on 2018/6/22.
 */
@Service
public class CommentServiceImpl implements ICommentService{
    @Resource
    private CommentDAO commentDAO;

    @Override
    public String insertComment(Comment comment)
    {
        if(commentDAO.queryById(comment.getCid())==null)
        {
            commentDAO.insertComment(comment);
            return "add comment success!";
        }
        else
        {
            return "duplicated comment id";
        }
    }

    @Override
    public List<Comment> getCommentsByBottle(Integer bid, Integer offset, Integer limit) {
        List<Comment> result=commentDAO.queryByBottleId(bid,offset,limit);
        return result;
    }

    @Override
    public List<Comment> getCommmentsByUser(Integer uid, Integer offset, Integer limit) {

        return commentDAO.queryByAuthorId(uid,offset,limit);
    }

    @Override
    public Comment getCommentById(Integer cid) {
        return commentDAO.queryById(cid);
    }

    @Override
    public String deleteComment(Integer cid)
    {

        if(commentDAO.deleteById(cid)==true)
            return "delete success!";
        return "invalid cid";
    }

    @Override
    public String updateComment(Comment comment) {
        if(commentDAO.queryById(comment.getCid())==null)
        {
            return "invalid comment";
        }
        commentDAO.deleteById(comment.getCid());
        commentDAO.insertComment(comment);
        return  "update success";

    }

    @Override
    public List<Comment> AdminGetComments(Integer pageNo, Integer pageRowNumber) {
        Integer offset=(pageNo-1)*pageRowNumber;   //默认pageNo从1开始
        Integer limit=pageRowNumber;
        return commentDAO.queryAll(offset,limit);
    }

    @Override
    public List<Comment> queryAll(Integer offset, Integer limit) {
        return commentDAO.queryAll(offset, limit);
    }

}
