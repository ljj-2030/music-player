package com.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.domain.SongList;
import com.music.service.servImpl.SongListService;
import com.music.service.servImpl.listSongService;
import com.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin(value = "*",methods = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("/listSong")
@RestController
public class ListSongController {
    @Autowired
    private SongListService songListService;
    @Autowired
    private listSongService listSongService;

    //增删改查
    //根据歌单id查找所有的歌曲
    @GetMapping("songOfSongList")
    public Object songOfSongList(@RequestParam("songListId")Integer songListId){
        return listSongService.selectBySongListId(songListId);
    }

    @PostMapping(value = "add")
    public Object addSongList(@RequestParam("title")String title,
                          @RequestParam("pic")String pic,
                          @RequestParam("introduction")String introduction,
                          @RequestParam("style")String style){
        SongList songList = new SongList(title, pic, introduction, style);


        System.out.println(songList);
        return songListService.insert(songList);
    }

    /*获取所有歌曲数据*/

    @GetMapping("getAllSongList")
    public Object getAllSongList(){
        return songListService.allSonglist();
    }
    //删除歌单
    @DeleteMapping("deleteSongListById")
    public Object deleteSongListById(@RequestParam("id")Integer id){
        return songListService.delete(id);
    }
//    根据歌曲id删除歌单中对应的信息
        @DeleteMapping("deleteSongBySongId")
        public Object deleteSongListBySongId(@RequestParam("songId")Integer songId){
            return listSongService.deleteSongBySongId(songId);
        }
    //更新歌单信息
    @PutMapping("update")
    public Object updateSongList(@RequestBody SongList songList){
        System.out.println("更新");
        return songListService.update(songList);

    }
    //更新歌单图片
    @PostMapping("updateSongListPic")
    public Object updateSongListPic(@RequestParam("file")MultipartFile multipartFile,@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有提交图片");
        }else{
            String fileName =System.currentTimeMillis()+multipartFile.getOriginalFilename();
            String path=System.getProperty("user.dir")+System.getProperty("file.separator")
                    +"img"+System.getProperty("file.separator")+"songList"+System.getProperty("file.separator");
            File f1 = new File(path);
            if(f1.exists()){
                f1.mkdirs();
            }
            //准备存放的文件名字
            File f2 = new File(path + "\\" + fileName);
            try {
                multipartFile.transferTo(f2);
                //存储到数据库中相对地址
                String relPath="/img/songList/"+fileName;
                //先查到这个id对应的信息
                SongList songList = songListService.selectSongById(id);
                songList.setPic(relPath);
                //更新数据根据id
                songListService.update(songList);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败"+e);
            }
        }

        return jsonObject;

    }


    //根据title模糊查询
    @GetMapping("songListOfTitle")
    public Object SongListOfTitle(@RequestParam("title")String title){
        return songListService.SongListOfTitle(title);
    }
    //根据style模糊查询
    @GetMapping("songListOfStyle")
    public Object SongListOfStyle(@RequestParam("style")String style){
        return songListService.SongListOfTitle(style);
    }


}
