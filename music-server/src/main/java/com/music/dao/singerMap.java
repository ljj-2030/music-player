package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.domain.Singer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface singerMap  extends BaseMapper<Singer> {
}
