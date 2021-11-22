package com.music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.music.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
