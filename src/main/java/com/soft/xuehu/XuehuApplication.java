package com.soft.xuehu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@ComponentScan(basePackages="com.soft.xuehu.*")
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.soft.xuehu.mapper")
public class XuehuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuehuApplication.class, args);
    }

}
