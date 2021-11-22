package com.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.domain.Collect;
import com.music.service.servImpl.CollectService;
import com.music.utils.Consts;
import com.music.utils.CurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(value = "*",methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    CollectService collectService;


    //添加搜藏
    @PostMapping("add")
    public Object addCollect(@RequestParam("userId")Integer userId,@RequestParam("type")Integer type,
                             @RequestParam(value = "songId",defaultValue = "0")Integer songId,@RequestParam(value = "songListId",defaultValue = "0")Integer songListId,
                             @RequestParam(value = "createTime",defaultValue = "")String createTime){
        JSONObject jsonObject = new JSONObject();
        //如果没有传入时间
        if(createTime.equals("") || createTime==""){
            createTime= CurrentDate.getCurrentTime();
        }

        if(songId==0){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"收藏歌曲不能为空");
            return jsonObject;
        }
        if(collectService.existSongId(userId,songId)){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.MSG,"已经收藏过该歌曲");
            return jsonObject;
        }
        Collect collect = new Collect(null, userId, type, songId, songListId, createTime);
        if(collectService.insert(collect)>0){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"收藏失败");
            return jsonObject;
        }

    }

    //删除收藏

    @GetMapping("delete")
    public Object deleteCollect(@RequestParam("id")Integer id){
        return  collectService.delete(id);

    }

    //获取所有的收藏
    @GetMapping("all")
    public Object allCollect(){
        return  collectService.allCollect();

    }


    //某个用户的收藏列表
    @GetMapping("collectOfUserId")
    public Object collectOfUserId(@RequestParam("userId")Integer userId){
        return  collectService.collectOfUserId(userId);

    }

    //删除用户的收藏列表
    @DeleteMapping("deleteCollectBySongId")
    public Object deleteSong(@RequestParam("userId")Integer userId,@RequestParam("songId")Integer songId){
        return  collectService.deleteSongBySongId(userId,songId);

    }






}
