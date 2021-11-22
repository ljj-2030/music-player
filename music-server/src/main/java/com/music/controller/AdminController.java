package com.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.music.domain.Admin;
import com.music.service.servImpl.AdminService;
import com.music.service.servImpl.SingerService;
import com.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    private SingerService singerService;
    @PostMapping("/login/status")
    public Object LoginStatus(HttpSession session, @RequestBody Admin admin){
        System.out.println(admin+"=====");
        JSONObject jsonObject = new JSONObject();
        boolean b = adminService.verifyPassword(admin.getName(), admin.getPassword());
        if(b){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登陆成功!");
            session.setAttribute(Consts.NAME,admin.getName());
        }else{
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名或密码错误");
        }
            return jsonObject;
    }



}
