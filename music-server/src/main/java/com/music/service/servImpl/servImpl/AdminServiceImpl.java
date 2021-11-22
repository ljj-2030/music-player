package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.adminMap;
import com.music.domain.Admin;
import com.music.service.servImpl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<adminMap, Admin> implements AdminService {
    @Autowired
    private adminMap amp;

    @Override
    public boolean verifyPassword(String name, String password) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("name",name).eq("password",password);
        Admin admin = amp.selectOne(adminQueryWrapper);
        return admin!=null?true:false;
    }

    @Override
    public boolean updateAdminService(Admin admin) {
        return amp.updateById(admin)>0;
    }
}
