package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface adminMap extends BaseMapper<Admin> {
}
