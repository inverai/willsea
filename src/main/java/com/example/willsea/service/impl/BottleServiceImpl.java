package com.example.willsea.service.impl;

import com.example.willsea.dao.BottleDAO;
import com.example.willsea.dao.CommentDAO;
import com.example.willsea.dao.UserDAO;
import com.example.willsea.entity.Bottle;
import com.example.willsea.entity.Comment;
import com.example.willsea.entity.User;
import com.example.willsea.service.IBottleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yt on 2018/6/22.
 */
@Service
public class BottleServiceImpl implements IBottleService{
    @Resource
    private BottleDAO bottleDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private CommentDAO commentDAO;
    @Override
    public String publish(Bottle bottle) {
        bottle.setIsPrivate("false");
        bottleDAO.deleteById(bottle.getBid());
        bottleDAO.insert(bottle);
        return "update success!";
    }

    @Override
    public int createBottle(Bottle bottle) {
        bottleDAO.insert(bottle);
        return 0;
    }

    @Override
    public ArrayList<Bottle> getBottles(Integer row, Integer col) {
        Bottle b=new Bottle();
        b.setIsPrivate("test");
        b.setBid(65535);
        ArrayList<Bottle>  fake=new ArrayList<>();
        fake.add(b);
        return fake;
    }

    @Override
    public Bottle getBottle(Integer bid) {
        return bottleDAO.queryById(bid);
    }

    @Override
    public String addCommentToBottle(Comment comment, Bottle bottle) {
        Integer bottleOwerID=bottle.getAid();
        Integer pushCommentID=comment.getAid();
        if(userDAO.isInBlackList(pushCommentID,bottleOwerID) > 0)
        {
            return "user is forbidden";
        }
        commentDAO.insertComment(comment);
        return "add comment success";
    }

    @Override
    public List<Bottle> AdminGetBottles(Integer pageNo, Integer pageRowNumber) {
        Integer offset=(pageNo-1)*pageRowNumber;
        Integer  limit=pageRowNumber;
        return bottleDAO.queryAll(offset,limit);
    }

    @Override
    public List<Bottle> queryAll(Integer offset, Integer limit) {
        return bottleDAO.queryAll(offset, limit);

    }

    @Override
    public String updateBottle(Bottle bottle) {
        if(bottleDAO.queryById(bottle.getBid()) == null) {
            return "invalid bottle";
        }
        bottleDAO.deleteById(bottle.getBid());
        bottleDAO.insert(bottle);
        return "update success";
    }

    @Override
    public String deleteBottle(Integer bid) {
        if(bottleDAO.deleteById(bid) == true) {
            return "delete success!";
        }

        return "invalid id";
    }

    @Override
    public Integer queryTotalNumber() {
        return bottleDAO.queryTotalNumber();
    }

    @Override
    public Integer deleteText(Integer bid) {
        return bottleDAO.deleteText(bid);
    }

    @Override
    public Integer deleteAudio(Integer bid) {
        return bottleDAO.deleteAudio(bid);
    }

    @Override
    public Integer deleteVideo(Integer bid) {
        return bottleDAO.deleteVideo(bid);
    }

    @Override
    public List<Bottle> queryByAuthor(Integer aid, Integer offset, Integer limit) {
        return bottleDAO.queryByAuthor(aid,offset,limit);
    }

    @Override
    public List<Bottle> getBottlesByUserBlackAndFavoriteList(User user) {
        if(user!=null)
            return  bottleDAO.queryByUserFavoriteAndBlackList(user.getUid());
        Integer totalNumber=bottleDAO.queryTotalNumber();
        Random random=new Random();
        List<Bottle> bottles=new ArrayList<>();
        while(bottles.size()==0) {
            Integer limit = random.nextInt(totalNumber);
            Integer offset = random.nextInt(totalNumber);
            bottles=bottleDAO.queryAll(limit,offset);
        }
        return bottles;
    }


}
