package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.Nusers;
import com.soft.xuehu.mapper.NusersMapper;
import com.soft.xuehu.service.NusersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By majianxin on 2020/5/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NusersServiceImpl implements NusersService {

    @Autowired
    NusersMapper nusersMapper;

    @Override
    //    增
    public void addNusers(Nusers nusers){
        nusersMapper.addNusers(nusers);
    }
    @Override
    //  查
    public List<Nusers> selectNusers(Integer nid){
        return nusersMapper.selectNusers(nid);
    }
    @Override
    //  改
    public void updateNusers(Nusers nusers){
        nusersMapper.updateNusers(nusers);
    }

    @Override
    //删除一份通知记录
    public void removeNuno(Integer nuNo){
        nusersMapper.removeNuno(nuNo);
    }

    @Override
    //  查
    public Nusers select(Integer nid,String uid){
        return nusersMapper.select(nid,uid);
    }

    @Override
    //  查
    public List<Nusers> selectByUid(String uid){
        return nusersMapper.selectByUid(uid);
    }

    //删除一份作业
    @Override
    public void remove(Integer nid){
        nusersMapper.remove(nid);
    }

}
