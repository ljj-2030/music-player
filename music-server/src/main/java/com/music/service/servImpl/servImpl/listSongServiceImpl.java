package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.listSongMapper;
import com.music.domain.ListSong;
import com.music.service.servImpl.listSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class listSongServiceImpl extends ServiceImpl<listSongMapper, ListSong> implements listSongService {
    @Autowired
    private listSongMapper listSongMapper;
    @Override
    public boolean insert(ListSong listSong) {
        return listSongMapper.insert(listSong) >0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return listSongMapper.deleteById(id)>0;
    }

    @Override
    public boolean update(ListSong listSong) {
        return listSongMapper.updateById(listSong)>0;
    }

    @Override
    public List<ListSong> selectBySongId(Integer songId) {
        QueryWrapper<ListSong> listSongQueryWrapper = new QueryWrapper<>();
        listSongQueryWrapper.eq("song_id",songId);
        return listSongMapper.selectList(listSongQueryWrapper);
    }

    @Override
    public List<ListSong> selectBySongListId(Integer songListId) {
        QueryWrapper<ListSong> listSongQueryWrapper = new QueryWrapper<>();
        listSongQueryWrapper.eq("song_list_id",songListId);
        return listSongMapper.selectList(listSongQueryWrapper);
    }


    @Override
    public int deleteSongBySongId(Integer songId) {
        QueryWrapper<ListSong> listSongQueryWrapper = new QueryWrapper<>();
        listSongQueryWrapper.eq("song_id",songId);
        return listSongMapper.delete(listSongQueryWrapper);
    }
}
