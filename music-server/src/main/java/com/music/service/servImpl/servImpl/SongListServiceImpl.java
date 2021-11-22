package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.SongListMap;
import com.music.dao.SongMap;
import com.music.domain.Song;
import com.music.domain.SongList;
import com.music.service.servImpl.SongListService;
import com.music.service.servImpl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListServiceImpl extends ServiceImpl<SongListMap, SongList>implements SongListService {
    @Autowired
    private SongListMap songListMap;
    @Override
    public boolean insert(SongList songList) {
        return songListMap.insert(songList)>0;
    }

    @Override
    public boolean update(SongList songList) {
        return songListMap.updateById(songList)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songListMap.deleteById(id)>0;
    }

    @Override
    public SongList selectSongById(Integer id) {
        return songListMap.selectById(id);
    }

    @Override
    public List<SongList> SongListOfTitle(String title) {
        QueryWrapper<SongList> songListQueryWrapper = new QueryWrapper<>();
        songListQueryWrapper.like("title",title);
        return songListMap.selectList(songListQueryWrapper);
    }

    @Override
    public List<SongList> SongListOfStyle(String style) {
        QueryWrapper<SongList> songListQueryWrapper = new QueryWrapper<>();
        songListQueryWrapper.like("style",style);
        return songListMap.selectList(songListQueryWrapper);
    }

    @Override
    public List<SongList> allSonglist() {
        QueryWrapper<SongList> objectQueryWrapper = new QueryWrapper<SongList>();
        return songListMap.selectList(objectQueryWrapper);
    }
}
