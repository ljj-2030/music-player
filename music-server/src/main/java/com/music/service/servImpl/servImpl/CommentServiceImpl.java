package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.CommentMapper;
import com.music.domain.Comment;
import com.music.service.servImpl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public int insert(Comment comment) {
        return commentMapper.insert(comment);
    }

    @Override
    public int deleteById(Integer id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    @Override
    public List<Comment> commentOfSongId(Integer songId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("song_id",songId);
        return commentMapper.selectList(commentQueryWrapper);
    }

    @Override
    public List<Comment> commentOfSongListId(Integer singerId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("song_list_id",singerId);
        return commentMapper.selectList(commentQueryWrapper);
    }
    //更新点赞数
    @Override
    public int setLike(Integer id, Integer up) {
        Comment comment = commentMapper.selectById(id);
        comment.setUp(up);
        return commentMapper.updateById(comment);
    }
}
