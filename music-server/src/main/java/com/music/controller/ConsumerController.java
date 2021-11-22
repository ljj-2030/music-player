package com.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.domain.Consumer;
import com.music.service.servImpl.consumerService;
import com.music.utils.Consts;
import com.music.utils.CurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("consumer")
public class ConsumerController {
    @Autowired
    private consumerService consumerService;
    /***
     * 添加用户
     */
    @PostMapping(value = "add")
    public Object addConsumer(@RequestParam("username")String username,@RequestParam("password")String password,
                              @RequestParam("sex")int sex,@RequestParam("phoneNum")String phoneNum,
                              @RequestParam("email")String email,@RequestParam("birth")String birth,
                              @RequestParam("introduction")String introduction,@RequestParam("location")String location,
                              @RequestParam("avator")String avator
                              ){

        Consumer consumer = new Consumer(username, password, sex, phoneNum, email, birth, introduction, location, avator, CurrentDate.getCurrentTime(), CurrentDate.getCurrentTime());
        System.out.println(consumer);
        boolean insert = consumerService.insert(consumer);
        return insert;
    }

        /*获取所有用户数据*/

    @GetMapping("getAllConsumer")
    public Object getAllConsumer(){
        return consumerService.allConsumer();
    }
    @DeleteMapping("deleteConsumerById")
    public Object deleteSongById(@RequestParam("id")Integer id){
        return consumerService.delete(id);

    }

    @PutMapping("update")
    public Object updateSong(@RequestParam("username")String username,@RequestParam("password")String password,
                             @RequestParam("sex")int sex,@RequestParam(value = "phoneNum")String phoneNum,
                             @RequestParam(value = "email")String email,@RequestParam("birth")String birth,
                             @RequestParam(value = "introduction",defaultValue = "null")String introduction,@RequestParam(value = "location",defaultValue = "null")String location,
                             @RequestParam("id")Integer id){
        Consumer consumer = consumerService.selectConsumerById(id);
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(sex);
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(birth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        return consumerService.update(consumer);
    }
    //更新用户图片
    @PostMapping("updateConsumerPic")
    public Object updateSongPic(@RequestParam("file")MultipartFile multipartFile,@RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        if(multipartFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有提交图片");
        }else{
            String fileName =System.currentTimeMillis()+multipartFile.getOriginalFilename();
            String path=System.getProperty("user.dir")+System.getProperty("file.separator")
                    +"img"+System.getProperty("file.separator")+"avator"+System.getProperty("file.separator");
            File f1 = new File(path);
            if(f1.exists()){
                f1.mkdirs();
            }
            //准备存放的文件名字
            File f2 = new File(path + "\\" + fileName);
            try {
                multipartFile.transferTo(f2);
                //存储到数据库中相对地址
                String relPath="/img/avator/"+fileName;
                //先查到这个id对应的信息
                Consumer consumer = consumerService.selectConsumerById(id);
                consumer.setAvator(relPath);
                //更新数据根据id
                consumerService.update(consumer);
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",relPath);
            } catch (IOException e) {
                jsonObject.put(Consts.CODE,0);
                jsonObject.put(Consts.MSG,"上传失败"+e);
            }
        }

        return jsonObject;

    }

    //根据名字模糊查询
    @GetMapping("selectByUserName")
    public Object selectByUserName(@RequestParam("username")String username){
        return consumerService.ConsumerOfName(username);
    }
    @GetMapping("selectBySex")
    public Object selectBySex(@RequestParam("sex")int sex){
        return consumerService.ConsumerOfSex(sex);
    }



    //根据用户名密码查找用户
    @PostMapping("login")
    public Object verifyPassword(@RequestParam("username")String username,@RequestParam("password")String password){
        JSONObject jsonObject = new JSONObject();
        if(username.equals("") || username==null){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空!");
            return jsonObject;
        }
        if(password.equals("") || password==null){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空!");
            return jsonObject;
        }
        Consumer consumer = consumerService.verifyPassword(username, password);

        if(consumer!=null){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put("userId",consumer.getId());
            jsonObject.put("avator",consumer.getAvator());
            jsonObject.put(Consts.MSG,"登录成功!");

            return jsonObject;
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名或密码错误!");
            return jsonObject;
        }

    }

    //根据userId查找用户

    @GetMapping("getUser")
    public Object getUserByUserId(@RequestParam("userId")Integer id)
    {
        Consumer consumer = consumerService.selectConsumerById(id);
        return consumer;
    }
}
