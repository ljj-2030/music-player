package com.music.service.servImpl;

import com.music.domain.Singer;

import java.util.List;

public interface SingerService {
    //增加
    public boolean insert(Singer singer);
    //修改
    public boolean update(Singer singer);

    //删除
    public boolean delete(Integer id);
    //根据id查询对象
    public Singer selectSingerById(Integer id);
    //查询所有歌手
    public List<Singer> allSinger();

    //根据歌手名字模糊查询
    public List<Singer> singerOfName(String name);
    //根据性别进行查询
    public List<Singer> singerOfSex(Integer se);

}
