package com.music.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.domain.SongList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongListMap extends BaseMapper<SongList> {
}
