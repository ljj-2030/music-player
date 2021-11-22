package com.music.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.music.domain.Admin;
import com.music.domain.Singer;
import com.music.domain.Song;
import com.music.domain.SongList;
import com.music.service.servImpl.AdminService;
import com.music.service.servImpl.SingerService;
import com.music.service.servImpl.SongListService;
import com.music.service.servImpl.SongService;
import com.music.service.servImpl.servImpl.SongServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@SpringBootTest
class DemoApplicationTests {
  @Autowired
    SongListService songListService;
    @Test
    void contextLoads() {
        System.out.println(songListService.allSonglist().get(0));

    }

}
