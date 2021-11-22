package com.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.domain.Song;
import com.music.service.servImpl.SongService;
import com.music.utils.Consts;
import com.music.utils.CurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("song")
public class SongController {
    @Autowired
    private SongService songService;
    /***
     * 添加歌曲
     */
    @PostMapping(value = "add")
    public Object addSong(@RequestParam("name")String name,
            @RequestParam("introduction")String introduction,
            @RequestParam("lyric")String lyric,
            @RequestParam("singerId")Integer singerId,
            @RequestParam(value = "pic")String pic,
            @RequestParam(value = "createTime",defaultValue = "null")String createTime,
            @RequestParam(value = "updateTime",defaultValue = "null")String updateTime,
            @RequestParam("file")MultipartFile multipartFile){
        Song song = new Song(null, singerId, name, introduction, createTime, updateTime,pic,lyric,null);
        if((song.getCreateTime().equals("null"))&&(song.getUpdateTime().equals("null"))){//如果没有创建时间和更新时间，则就用当前日期
            song.setCreateTime(CurrentDate.getCurrentTime());
            song.setUpdateTime(CurrentDate.getCurrentTime());
        }

        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有上传文件!");
        }else{
            //获取文件的名字,并拼接当前时间戳
            String fileName=System.currentTimeMillis()+multipartFile.getOriginalFilename();
            //获取上传的相对路径
            String fileUrl="/song/"+fileName;
            //将保存到数据库中的数据存到对象里
            song.setUrl(fileUrl);
            //获取真实路径
            String abspath=System.getProperty("user.dir")+System.getProperty("file.separator")+"song"+System.getProperty("file.separator");

            //上传到真实路径
            File file = new File(abspath);
            if(!file.exists()){
                file.mkdirs();
            }
            try {
                multipartFile.transferTo(new File(abspath+fileName));
                boolean rst=songService.insert(song);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功!");
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,e);
            }

        }
        return  jsonObject;
    }

        /*获取所有歌曲数据*/

    @GetMapping("getSongOfSinger")
    public Object getAllSongBySingerId(@RequestParam("singerId")Integer singerId){
        List<Song> songs = songService.SongsBySingerId(singerId);
        return songs;
    }
    @GetMapping("getAllSong")
    public Object getAllSong(){
        return songService.allSong();
    }
    @DeleteMapping("deleteSongById")
    public Object deleteSongById(@RequestParam("id")Integer id){
        return songService.delete(id);
    }
    //根据歌曲id查找歌曲
    @GetMapping("getSong")
    public Object getSongById(@RequestParam("songId")Integer songId){
        return  songService.selectSongById(songId);
    }
    @PutMapping("update")
    public Object updateSong(@RequestBody Song song){
        return songService.update(song);

    }
    //更新歌曲图片
    @PostMapping("updateSongPic")
    public Object updateSongPic(@RequestParam("file")MultipartFile multipartFile,@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有提交图片");
        }else{
            String fileName =System.currentTimeMillis()+multipartFile.getOriginalFilename();
            String path=System.getProperty("user.dir")+System.getProperty("file.separator")
                    +"img"+System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator");
            File f1 = new File(path);
            if(f1.exists()){
                f1.mkdirs();
            }
            //准备存放的文件名字
            File f2 = new File(path + "\\" + fileName);
            try {
                multipartFile.transferTo(f2);
                //存储到数据库中相对地址
                String relPath="/img/songPic/"+fileName;
                //先查到这个id对应的信息
                Song song = songService.selectSongById(id);
                song.setPic(relPath);
                //更新数据根据id
                songService.update(song);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败"+e);
            }
        }

        return jsonObject;

    }

    //更新歌曲
    @PostMapping("updateSong")
    public Object updateSong(@RequestParam("file")MultipartFile multipartFile,@RequestParam("id")Integer id){

        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有提交文件");
        }else{
            String fileName =System.currentTimeMillis()+multipartFile.getOriginalFilename();
            String path=System.getProperty("user.dir")+System.getProperty("file.separator")
                    +"song"+System.getProperty("file.separator");
            File f1 = new File(path);
            if(f1.exists()){
                f1.mkdirs();
            }
            //准备存放的文件名字
            String absName=path + "\\" + fileName;
            File f2 = new File(absName);
            try {
                multipartFile.transferTo(f2);
                //存储到数据库中相对地址
                String relPath="/song/"+fileName;
                //先查到这个id对应的信息
                Song song = songService.selectSongById(id);
                song.setUrl(relPath);
                //更新数据根据id
                songService.update(song);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败"+e);
            }
        }

        return jsonObject;

    }

    //根据歌名模糊查询
    @GetMapping("getSongOfName")
    public Object getSongOfName(@RequestParam("name")String name){
        return songService.SongOfName(name);
    }


}
