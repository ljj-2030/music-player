package com.music.controller;

import com.music.domain.Rank;
import com.music.service.servImpl.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*",methods={RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    RankService rankService;


    //添加
    @PostMapping("add")
    public Object add(@RequestParam("songListId")Integer songListId,
                      @RequestParam("consumerId")Integer consumerId,
                      @RequestParam("score")Integer score){

        Rank rank = new Rank(null, songListId, consumerId, score);
        return rankService.insert(rank);
    }

    //计算评价分
    @GetMapping("avg")
    public Object getAvg(@RequestParam("songListId")Integer songListId){

        return rankService.selectScoreSum(songListId)*1.0/rankService.selectRankNum(songListId);
    }
}
