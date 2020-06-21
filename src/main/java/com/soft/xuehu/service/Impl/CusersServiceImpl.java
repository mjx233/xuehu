package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.mapper.CusersMapper;
import com.soft.xuehu.service.CusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By majianxin on 2020/5/11.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CusersServiceImpl implements CusersService {


    @Autowired
    public CusersMapper cusersMapper;

    //查询用户所有课程
    @Override
    public List<Cusers> loadAll(String uid){
        List<Cusers> list=cusersMapper.loadAll(uid);
        return list;
    }

    @Override
    //更新
    public void update(Cusers cusers){
        cusersMapper.update(cusers);
    }

    //获取用户所有选修课程号
    @Override
    public List<String> loadCourseNo(String uid){
        return cusersMapper.loadCourseNo(uid);
    }

    //删除一条课程记录
    @Override
    public void removeCusers(String courseNo,String uid){
        cusersMapper.removeCusers(courseNo,uid);
    }

    //选修课程
    @Override
    public void addCusers(Cusers cusers){
        cusersMapper.addCusers(cusers);
    }

    @Override
    //查询选修这门课得人
    public List<Cusers> selectUsers(String courseNo){
        return  cusersMapper.selectUsers(courseNo);
    }

    @Override
    //删除用户所有课程记录
        //删除用户所有课程记录
    public void remove(String uid){
        cusersMapper.remove(uid);
    }

    @Override
    //删除选修这门课得记录
    public void removeByCourseNo(String courseNo){
        cusersMapper.removeByCourseNo(courseNo);
    }

    @Override
    //查询所有记录
    public List<Cusers> selectAll(){
        return cusersMapper.selectAll();
    }

    @Override
    //查询选修这门课得人
    public List<Cusers> selectStu(String courseNo,String uid){
        return cusersMapper.selectStu(courseNo,uid);
    }

}
