package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.CollectMapper;
import com.music.domain.Collect;
import com.music.service.servImpl.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
implements CollectService{
@Autowired
CollectMapper collectMapper;

    @Override
    public int insert(Collect collect) {
        return collectMapper.insert(collect);
    }

    @Override
    public int delete(Integer id) {
        return collectMapper.deleteById(id);
    }

    @Override
    public List<Collect> allCollect() {
        return collectMapper.selectList(null);
    }

    @Override
    public List<Collect> collectOfUserId(Integer userId) {
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id",userId);
        return collectMapper.selectList(collectQueryWrapper);
    }

    @Override
    public boolean existSongId(Integer userId, Integer songId) {
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id",userId)
                .eq("song_id",songId);
        return collectMapper.selectCount(collectQueryWrapper)>0;
    }

    @Override
    public int deleteSongBySongId(Integer userId, Integer songId) {
        QueryWrapper<Collect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("user_id", userId).eq("song_id",songId);
        return collectMapper.delete(collectQueryWrapper);
    }
}
