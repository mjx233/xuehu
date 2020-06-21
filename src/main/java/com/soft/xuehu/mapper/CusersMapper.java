package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Create By majianxin on 2020/5/11.
 */

public interface CusersMapper {


    //查询用户所有课程
    List<Cusers> loadAll(String uid);

    //更新
    void update(Cusers cusers);

    //获取用户所有选修课程号
    List<String> loadCourseNo(String uid);

    //删除一条课程记录
    void removeCusers(String courseNo,String uid);

    //选修课程
    void addCusers(Cusers cusers);

    //查询选修这门课得人
    List<Cusers> selectUsers(String courseNo);

    //删除用户所有课程记录
    void remove(String uid);

    //删除选修这门课得记录
    void removeByCourseNo(String courseNo);

    //查询所有记录
    List<Cusers> selectAll();

    //查询选修这门课得人
    List<Cusers> selectStu(String courseNo,String uid);


}
