package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Nusers;
import com.soft.xuehu.entity.Users;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Create By majianxin on 2020/5/10.
 */
public interface UsersMapper extends Mapper<Users> {

    //获取一条用户数据
    Users get(String uid);
    //新增一条用户数据
    void addUsers(Users users);
    //获取一条用户数据
    Users selectByUname(String uname);

    //查询所有用户
    List<Users> selectAll();

    //  改
    void update(Users users);
    void remove(String uid);
}
