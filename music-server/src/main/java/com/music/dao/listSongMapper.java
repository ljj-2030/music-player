package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.domain.ListSong;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface listSongMapper  extends BaseMapper<ListSong> {
}
