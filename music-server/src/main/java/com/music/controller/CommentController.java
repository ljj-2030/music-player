package com.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.music.domain.Comment;
import com.music.service.servImpl.CommentService;
import com.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*",methods={RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;


    @PostMapping("add")
    public Object addComment(
            @RequestParam("userId")Integer userId,@RequestParam("type")Integer type,
           @RequestParam(value = "songListId",defaultValue = "0")Integer songListId,@RequestParam(value = "songId",defaultValue = "0")Integer songId,
            @RequestParam("content")String content

    )
    {
        JSONObject jsonObject = new JSONObject();
        Comment comment = new Comment();
        comment.setType(type);
        comment.setUserId(userId);
        if(type==1){
            comment.setSongListId(songListId);
        }else{
            comment.setSongId(songId);
        }
        comment.setContent(content);
        System.out.println(comment);
        if(commentService.insert(comment)>0){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评论成功!");
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"评论失败!");
        }
        return jsonObject;

    }


    @GetMapping("commentOfSongListId")
    public Object commentOfSongListId(@RequestParam("songListId")Integer songListId){
        return commentService.commentOfSongListId(songListId);
    }

    @GetMapping("commentOfSongId")
    public Object commentOfSongId(@RequestParam("songId")Integer songId){
        return commentService.commentOfSongId(songId);
    }


    //更新点赞数
    @PostMapping("like")
    public Object setLike(@RequestParam("id")Integer id,@RequestParam("up")Integer up){
        return commentService.setLike(id,up);
            }
}
