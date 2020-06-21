package com.soft.xuehu.config;

import com.soft.xuehu.listener.MySessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create By majianxin on 2020/5/25.
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer{

    @Bean
    public ServletListenerRegistrationBean getListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MySessionListener());
        return servletListenerRegistrationBean;
    }
}
