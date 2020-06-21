package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.HworkQuestion;
import com.soft.xuehu.mapper.HworkQuestionMapper;
import com.soft.xuehu.service.HworkQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By majianxin on 2020/5/26.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HworkQuestionServiceImpl implements HworkQuestionService {

    @Autowired
    HworkQuestionMapper teacherHworkMapper;

    @Override
    //新增一条问题
    public void addHworkQuestion(HworkQuestion hworkQuestion){
        teacherHworkMapper.addHworkQuestion(hworkQuestion);
    }

    @Override
    //删除一条问题
    public void removeHworkQuestion(Integer thworkNo){
        teacherHworkMapper.removeHworkQuestion(thworkNo);
    }

    @Override
    //更新一条问题
    public void updateHworkQuestion(HworkQuestion hworkQuestion){
        teacherHworkMapper.updateHworkQuestion(hworkQuestion);
    }

    @Override
    //根据主键查询一条问题
    public HworkQuestion selectByThworkNo(Integer thworkNo){
        return teacherHworkMapper.selectByThworkNo(thworkNo);
    }

    @Override
    //根据作业编号、课程编号查询所有问题,并根据问题编号排序
    public List<HworkQuestion> selectAll(Integer hid, String courseNo, String uid){
        return teacherHworkMapper.selectAll(hid,courseNo,uid);
    }

    @Override
    //删除一份作业下的所有问题
    public void removeAll(Integer hid){
       teacherHworkMapper.removeAll(hid);
    }

    @Override
    //查询一份作业几个问题
    public Integer selectNum(Integer hid){
        return teacherHworkMapper.selectNum(hid);
    }

    @Override
    //查询一份作业满分
    public List<Integer> selectGrade(Integer hid){
        return teacherHworkMapper.selectGrade(hid);
    }


}
