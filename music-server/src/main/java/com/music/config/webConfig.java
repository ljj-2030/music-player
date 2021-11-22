package com.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/****
 * 解决跨域问题
 */
@Configuration
public class webConfig implements WebMvcConfigurer {

    //修改静态文件的存放位置,歌手图片地址
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                                + "img" + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
                );
        /**
         * 歌曲图片位置。
         */
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations("file:" + System.getProperty("user.dir")
                        + System.getProperty("file.separator") + "img" + System.getProperty("file.separator")
                        + "songPic" + System.getProperty("file.separator"));
        /**
         * 歌曲存放位置
         *          */

        registry.addResourceHandler("/song/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "song" + System.getProperty("file.separator")
                );
        /**
         * 歌单图片存放位置
         */
        registry.addResourceHandler("/img/songList/**")
                .addResourceLocations("file:" + System.getProperty("user.dir")
                        + System.getProperty("file.separator") + "img" + System.getProperty("file.separator")
                        + "songList" + System.getProperty("file.separator")
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/song/**");

    }
}
