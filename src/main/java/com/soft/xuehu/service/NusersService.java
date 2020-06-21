package com.soft.xuehu.service;

import com.soft.xuehu.entity.Nusers;

import java.util.List;

/**
 * Create By majianxin on 2020/5/30.
 */
public interface NusersService {

    //    增
    void addNusers(Nusers nusers);
    //  查
    List<Nusers> selectNusers(Integer nid);
    //  改
    void updateNusers(Nusers nusers);

    //删除一份作业
    void removeNuno(Integer nuNo);
    //  查
    Nusers select(Integer nid,String uid);

    //  查
    List<Nusers> selectByUid(String uid);

    //删除一份作业
    void remove(Integer nid);

}
