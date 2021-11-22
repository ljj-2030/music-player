package com.music.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("list_song")
public class ListSong {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer songId;
    private Integer songListId;

    @Override
    public String toString() {
        return "ListSong{" +
                "id=" + id +
                ", songId=" + songId +
                ", songListId=" + songListId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongListId() {
        return songListId;
    }

    public void setSongListId(Integer songListId) {
        this.songListId = songListId;
    }

    public ListSong(Integer songId, Integer songListId) {
        this.songId = songId;
        this.songListId = songListId;
    }
}
