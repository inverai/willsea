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
    /**
     * 增
     * @param bottle
     * @return
     */
    int insert(Bottle bottle);

    /**
     * 删
     * @param bid
     * @return
     */
    //主键删除
    boolean deleteById(Integer bid);
    //删除文字内容
    Integer deleteText(Integer bid);
    //删除音频内容
    Integer deleteAudio(Integer bid);
    //删除视频内容
    Integer deleteVideo(Integer bid);

    /**
     * 查找
     * @param bid
     * @return
     */
    //主键查询
    Bottle queryById(Integer bid);
    //区间查询
    List<Bottle> queryAll(@Param("offset")Integer offset, @Param("limit")Integer limit);
    //根据作者查询
    List<Bottle> queryByAuthor(@Param("aid")Integer aid, @Param("offset")Integer offset, @Param("limit")Integer limit);
    //查询总数
    Integer queryTotalNumber();
    //根据黑白名单查询
    List<Bottle> queryByUserFavoriteAndBlackList(Integer uid);
}
