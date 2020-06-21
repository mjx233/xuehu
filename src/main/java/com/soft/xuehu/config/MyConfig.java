package com.soft.xuehu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create By majianxin on 2020/5/31.
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/upload.html").setViewName("/upload");
            }
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                /* 其中的路径写的是文件保存的路径，可以直接将file:/后面的内容全部进行替换 */
                registry.addResourceHandler("/Path/**").addResourceLocations("file:/C:/Users/19678/Desktop/xuehu/target/classes/upload/");
            }
        };
        return webMvcConfigurer;
    }
}