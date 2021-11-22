package com.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.domain.Singer;
import com.music.domain.Song;
import com.music.service.servImpl.SingerService;
import com.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin(value = "*",methods={RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("/singer")
@RestController
public class SingerController {
    @Autowired
    private SingerService singerService;
    /****
     * 添加歌手
     */
    @PostMapping("add")
    public Object addSinger(@RequestParam("name")String name,
                            @RequestParam("sex")Integer sex,
                            @RequestParam("pic")String pic,
                            @RequestParam("birth")String birth,
                            @RequestParam("introduction")String introduction,
                            @RequestParam("location")String location){
        Singer singer = new Singer(null, name, Byte.parseByte(sex.toString()), pic, birth, location, introduction);
        JSONObject jsonObject = new JSONObject();


        boolean insert=singerService.insert(singer);
        if(insert){
        jsonObject.put(Consts.MSG,"添加成功");
        jsonObject.put(Consts.CODE,1);
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"添加失败");
        }
        return jsonObject;
    }

    @PutMapping("update")
    public Object updateSinger(@RequestParam("id")Integer id,
                               @RequestParam("name")String name,
                               @RequestParam("sex")Integer sex,
                               @RequestParam("pic")String pic,
                               @RequestParam("birth")String birth,
                               @RequestParam("introduction")String introduction,
                               @RequestParam("location")String location){
        Singer singer = new Singer(id, name, Byte.parseByte(sex.toString()), pic, birth, location, introduction);
        JSONObject jsonObject = new JSONObject();
        System.out.println("歌手是:"+singer);
        boolean rst = singerService.update(singer);
        if(rst){
            jsonObject.put(Consts.MSG,"修改成功");
            jsonObject.put(Consts.CODE,1);
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"修改失败");
        }
        return jsonObject;
    }

    @DeleteMapping("delete")
    public Object deleteSingerById(@RequestParam(value = "id",defaultValue = "0") Integer id){
        boolean rst = singerService.delete(id);

        return rst;
    }
    //根据歌手id查找歌手
    @GetMapping("selectByKey")
    public Object selectByPrimaryKey(@RequestParam(value = "id",defaultValue = "0")Integer id){
        return singerService.selectSingerById(id);
    }

    @GetMapping("getAllSinger")
    public Object allSinger(){
        return  singerService.allSinger();
    }

    @GetMapping("singerOfName")
    public Object singerOfName(@RequestParam("name")String name){
        List<Singer> singers = singerService.singerOfName(name);
        return singers;
    }

    @GetMapping("singerOfSex")
    public Object singerOfSex(@RequestParam("sex")Integer  sex){
        return singerService.singerOfSex(sex);
    }

    /**
     * 更改头像->先上传到目录在将
     * @param multipartFile
     * @param id
     * @return
     */
    @PostMapping("updateSingerPic")
    public Object updateSingerPic(@RequestParam("file")MultipartFile multipartFile,@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有提交图片");
        }else{
            String fileName =System.currentTimeMillis()+multipartFile.getOriginalFilename();
            String path=System.getProperty("user.dir")+System.getProperty("file.separator")
                    +"img"+System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator");
            File f1 = new File(path);
            if(f1.exists()){
                f1.mkdirs();
            }
            //准备存放的文件名字
            File f2 = new File(path + "\\" + fileName);
            try {
                multipartFile.transferTo(f2);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                //存储到数据库中相对地址
                String relPath="/img/singerPic/"+fileName;
                System.out.println(relPath);
                //先查到这个id对应的信息
                Singer singer = singerService.selectSingerById(id);
                singer.setPic(relPath);
                //更新数据根据id
                singerService.update(singer);
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败"+e);
            }
        }

        return jsonObject;
    }



}
