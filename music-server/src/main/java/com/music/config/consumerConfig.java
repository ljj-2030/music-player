package com.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class consumerConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/avator/**")
                .addResourceLocations("file:"+System.getProperty("user.dir")
                +System.getProperty("file.separator")+"img"+System.getProperty("file.separator")
                +"avator"+System.getProperty("file.separator"));
    }
}
