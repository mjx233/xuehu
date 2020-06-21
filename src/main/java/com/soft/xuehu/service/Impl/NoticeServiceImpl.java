package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.entity.Notice;
import com.soft.xuehu.mapper.NoticeMapper;
import com.soft.xuehu.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By majianxin on 2020/5/29.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl implements NoticeService {

@Autowired
NoticeMapper noticeMapper;

    @Override
    //根据主键查询一条通知
    public Notice selectByNid(Integer nid){
        return noticeMapper.selectByNid(nid);
    }

    @Override
    public void update(Notice notice){
        noticeMapper.update(notice);
    }

    @Override
    //删除一份作业
    public void removeNotice(Integer nid){
        noticeMapper.removeNotice(nid);
    }

    @Override
    //增加一条通知
    public void addNotice(Notice notice){
        noticeMapper.addNotice(notice);
    }

    @Override
    //找出所有课程发布的通知
    public List<Notice> selectAll(String courseNo){
        return noticeMapper.selectAll(courseNo);
    }

    @Override
    //找出所有发布的通知
    public List<Notice> select(){
        return noticeMapper.select();
    }

}
