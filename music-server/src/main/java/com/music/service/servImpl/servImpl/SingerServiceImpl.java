package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.singerMap;
import com.music.domain.Singer;
import com.music.service.servImpl.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl extends ServiceImpl<singerMap, Singer> implements SingerService {
    @Autowired
    private singerMap sm;
    @Override
    public boolean insert(Singer singer) {
        int insert = sm.insert(singer);
        return insert>0?true:false;
    }

    @Override
    public boolean update(Singer singer) {

        return sm.updateById(singer)>0?true:false;
    }

    @Override
    public boolean delete(Integer id) {
        return sm.deleteById(id)>0?true:false;
    }

    @Override
    public Singer selectSingerById(Integer id) {
        return sm.selectById(id);
    }

    @Override
    public List<Singer> allSinger() {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        List<Singer> singers = sm.selectList(singerQueryWrapper);
        return singers;
    }

    @Override
    public List<Singer> singerOfName(String name) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.like("name",name);
        List<Singer> singers = sm.selectList(singerQueryWrapper);
        return singers;
    }


    @Override
    public List<Singer> singerOfSex(Integer sex) {
        QueryWrapper<Singer> singerQueryWrapper = new QueryWrapper<>();
        singerQueryWrapper.eq("sex",Byte.parseByte(sex.toString()));
        List<Singer> singers = sm.selectList(singerQueryWrapper);
        return singers;
    }
}
