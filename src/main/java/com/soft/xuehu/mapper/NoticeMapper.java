package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.entity.Notice;

import java.util.List;

/**
 * Create By majianxin on 2020/5/29.
 */
public interface NoticeMapper {

    //根据主键查询一条通知
    Notice selectByNid(Integer nid);

    void update(Notice notice);

    //删除一份作业
    void removeNotice(Integer nid);

    //增加一条通知
    void addNotice(Notice notice);

    //找出所有课程发布的通知
    List<Notice> selectAll(String courseNo);

    //找出所有发布的通知
    List<Notice> select();

}
