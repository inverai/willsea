package com.example.willsea.dao;

import com.example.willsea.entity.Bottle;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yt on 2018/6/23.
 */
@Component
public interface BottleDAO {
    // add
    int insert(Bottle bottle);

    // update data
    int addLookTime(Integer bid);
    int addCommentSum(Integer bid);
    int subCommentSum(Integer bid);

    //search
    Bottle queryById(Integer bid);
    List<Bottle> queryAll(@Param("offset")Integer offset, @Param("limit")Integer limit);
    List<Bottle> queryByAuthor(@Param("aid")Integer aid, @Param("offset")Integer offset, @Param("limit")Integer limit);

    // delete
    int deleteById(Integer bid);


}
