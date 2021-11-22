package com.music.service.servImpl;

import com.music.domain.ListSong;

import java.util.List;

public interface listSongService {
//增
    public boolean insert(ListSong listSong);
//删
    public boolean deleteById(Integer id);
// 改
    public boolean update(ListSong listSong);
// 根据song_id查找
    public List<ListSong> selectBySongId(Integer songId);
//根据song_list_id查找
    public List<ListSong> selectBySongListId(Integer songListId);
    //根据songId删除对应的歌曲
    public int deleteSongBySongId(Integer songId);
}
