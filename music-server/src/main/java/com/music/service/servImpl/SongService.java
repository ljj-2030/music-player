package com.music.service.servImpl;

import com.music.domain.Song;

import java.util.List;

public interface SongService {
    //增加
    public boolean insert(Song Song);
    //修改
    public boolean update(Song Song);

    //删除
    public boolean delete(Integer id);
    //根据id查询对象
    public Song selectSongById(Integer id);
    //查询所有歌手
    public List<Song> allSong();

    //根据歌手名字模糊查询
    public List<Song> SongOfName(String name);
    //根据性别进行查询
    public List<Song> SongOfSex(Integer se);

    //根据singerId获取歌手的数据
    public List<Song> SongsBySingerId(Integer singerId);

}
