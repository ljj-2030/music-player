package com.music.service.servImpl;


import com.music.domain.Collect;

import java.util.List;

public interface CollectService {

    //增加
    public int insert(Collect collect);

    //删除
    public int delete(Integer id);

    //查询所有的收藏
    public List<Collect> allCollect();

    //查看用户的收藏列表
    public List<Collect> collectOfUserId(Integer userId);

    //查询某个用户是否已经收藏了某个歌曲
    public boolean existSongId(Integer userId, Integer songId);


    //通过songId将歌曲从该用户的收藏表中删除
    public int deleteSongBySongId(Integer userId,Integer songId);

}