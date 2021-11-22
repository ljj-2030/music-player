package com.music.service.servImpl.servImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.music.dao.CommentMapper;
import com.music.dao.RankMapper;
import com.music.domain.Comment;
import com.music.domain.Rank;
import com.music.service.servImpl.CommentService;
import com.music.service.servImpl.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl extends ServiceImpl<RankMapper, Rank>
        implements RankService {
    @Autowired
    RankMapper rankMapper;
    @Override
    public boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    @Override
    public int selectScoreSum(Integer songListId) {
        QueryWrapper<Rank> rankQueryWrapper = new QueryWrapper<>();
        rankQueryWrapper.eq("songListId",songListId);

        List<Rank> ranks = rankMapper.selectList(rankQueryWrapper);
        int sum=0;
        for(Rank rank:ranks){
            sum+=rank.getScore();
        }
        return sum;
    }

    @Override
    public int selectRankNum(Integer songListId) {
        QueryWrapper<Rank> rankQueryWrapper = new QueryWrapper<>();
        rankQueryWrapper.eq("songListId",songListId);
        return rankMapper.selectCount(rankQueryWrapper);

    }
}
