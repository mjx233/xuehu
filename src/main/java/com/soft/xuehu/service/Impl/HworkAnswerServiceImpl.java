package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.HworkAnswer;
import com.soft.xuehu.mapper.HworkAnswerMapper;
import com.soft.xuehu.service.HworkAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By majianxin on 2020/5/27.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HworkAnswerServiceImpl implements HworkAnswerService {


    @Autowired
    HworkAnswerMapper answerMapper;

    @Override
    //新增一条回答
    public void addHworkAnswer(HworkAnswer hworkAnswer){
       answerMapper.addHworkAnswer(hworkAnswer);
    }

    @Override
    //删除一条回答
    public void removeHworkAnswer(Integer thworkNo){
        answerMapper.removeHworkAnswer(thworkNo);
    }

    @Override
    //更新一条回答
    public void updateHworkAnswer(HworkAnswer hworkAnswer){
        answerMapper.updateHworkAnswer(hworkAnswer);
    }

    @Override
    //根据主键查询一条问题
    public HworkAnswer selectByAno(Integer ano){
        return answerMapper.selectByAno(ano);
    }

    @Override
    //根据作业编号、课程编号,用户编号查询Answer
    public List<HworkAnswer> selectAll(Integer hid, String courseNo, String uid){
        return answerMapper.selectAll(hid,courseNo,uid);
    }

    @Override
    //查询一条回答
    public HworkAnswer select(Integer hid,String courseNo,String uid,String questionNo){
        return answerMapper.select(hid,courseNo,uid,questionNo);
    }

    @Override
    //查询一份作业的所有Answer
    public List<HworkAnswer> loadAll(Integer hid, String courseNo){
        return answerMapper.loadAll(hid,courseNo);
    }

    @Override
        //查询一份作业下的uid
    public List<String> selectUid(Integer hid, String courseNo){
        return answerMapper.selectUid(hid,courseNo);
    }

    @Override
    //查询一份作业下的uid
    public List<String> selectUid1(Integer hid, String courseNo){
        return answerMapper.selectUid1(hid,courseNo);
    }

}
