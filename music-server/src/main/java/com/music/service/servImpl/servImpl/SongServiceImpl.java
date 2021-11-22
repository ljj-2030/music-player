package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.SongMap;
import com.music.dao.singerMap;
import com.music.domain.Singer;
import com.music.domain.Song;
import com.music.service.servImpl.SingerService;
import com.music.service.servImpl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl extends ServiceImpl<SongMap, Song> implements SongService {
    @Autowired
    private SongMap songMap;

    @Override
    public boolean insert(Song Song) {

        return songMap.insert(Song)>0;
    }

    @Override
    public boolean update(Song Song) {
        return songMap.updateById(Song)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return songMap.deleteById(id)>0;
    }

    @Override
    public Song selectSongById(Integer id) {
        return songMap.selectById(id);
    }

    @Override
    public List<Song> allSong() {
        List<Song> songs = songMap.selectList(new QueryWrapper<>());

        return songs;
    }

    @Override
    public List<Song> SongOfName(String name) {
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.like("name", name);
        return songMap.selectList(songQueryWrapper);
    }

    @Override
    public List<Song> SongOfSex(Integer se) {
        return null;
    }

    @Override
    public List<Song> SongsBySingerId(Integer singerId) {
        QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
        songQueryWrapper.eq("singer_id",singerId);
        List<Song> songs = songMap.selectList(songQueryWrapper);
        return songs;
    }
}
