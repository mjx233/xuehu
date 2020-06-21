package com.soft.xuehu.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create By majianxin on 2020/5/25.
 */
public class MySessionListener implements HttpSessionListener {

    public static AtomicInteger userCount = new AtomicInteger(0);

    /**
     * 用户登录，创建session，用户数增加
     *
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        userCount.getAndIncrement();
    }

    /**
     * 用户下线，销毁session，用户数减少
     *
     * @param event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        userCount.getAndDecrement();
    }
}