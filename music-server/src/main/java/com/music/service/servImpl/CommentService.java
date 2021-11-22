package com.music.service.servImpl;

import com.music.domain.Comment;

import java.util.LinkedList;
import java.util.List;

public interface CommentService {

    /*
    增加评论
     */
    public int insert(Comment comment);

    /*
    删除评论
     */
    public int deleteById(Integer id);
    /*
    根据主键查询评论
     */
    public Comment selectById(Integer id);

    /*
    查询某个歌曲下的所有评论
     */
    public List<Comment>commentOfSongId(Integer songId);
    /*
        查询某个歌曲下的所有评论
         */
    public List<Comment>commentOfSongListId(Integer singerId);

    /*
    点赞
     */
    public int setLike(Integer id,Integer up);
}
