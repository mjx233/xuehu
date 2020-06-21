package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.HworkQuestion;

import java.util.List;

/**
 * Create By majianxin on 2020/5/26.
 */
public interface HworkQuestionMapper {

    //新增一条问题
    void addHworkQuestion(HworkQuestion hworkQuestion);
    //删除一条问题
    void removeHworkQuestion(Integer thworkNo);
    //更新一条问题
    void updateHworkQuestion(HworkQuestion hworkQuestion);
    //根据主键查询一条问题
    HworkQuestion selectByThworkNo(Integer thworkNo);
    //根据作业编号、课程编号,用户编号查询问题
    List<HworkQuestion> selectAll(Integer hid, String courseNo, String uid);

    //删除一份作业下的所有问题
    void removeAll(Integer hid);

    //查询一份作业几个问题
    Integer selectNum(Integer hid);

    //查询一份作业满分
    List<Integer> selectGrade(Integer hid);

}
