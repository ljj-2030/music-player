package com.music.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("rank")
public class Rank {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "songListId")
    private Integer songListId;
    @TableField(value = "consumerId")
    private Integer consumerId;
    private Integer score;

    @Override
    public String toString() {
        return "Rank{" +
                "id=" + id +
                ", songListId=" + songListId +
                ", consumerId=" + consumerId +
                ", score=" + score +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Rank() {
    }

    public Rank(Integer id, Integer songListId, Integer consumerId, Integer score) {
        this.id = id;
        this.songListId = songListId;
        this.consumerId = consumerId;
        this.score = score;
    }
}
