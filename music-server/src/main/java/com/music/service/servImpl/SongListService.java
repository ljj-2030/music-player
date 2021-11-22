package com.music.service.servImpl;

import com.music.domain.Song;
import com.music.domain.SongList;

import java.util.List;

public interface SongListService {
    //增加
    public boolean insert(SongList songList);
    //修改
    public boolean update(SongList songList);

    //删除
    public boolean delete(Integer id);
    //根据id查询对象
    public SongList selectSongById(Integer id);
    //查询所有的歌单
    public List<SongList> allSonglist();
    //根据标题查询所有的列表
    public List<SongList> SongListOfTitle(String title);
    //根据风格模糊查询所有的列表
    public List<SongList> SongListOfStyle(String style);
}
