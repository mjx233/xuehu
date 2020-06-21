package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.HworkAnswer;
import com.soft.xuehu.entity.HworkQuestion;

import java.util.List;

/**
 * Create By majianxin on 2020/5/27.
 */
public interface HworkAnswerMapper {

    //新增一条回答
    void addHworkAnswer(HworkAnswer hworkAnswer);
    //删除一条回答
    void removeHworkAnswer(Integer thworkNo);
    //更新一条回答
    void updateHworkAnswer(HworkAnswer hworkAnswer);
    //根据主键查询一条问题
    HworkAnswer selectByAno(Integer Ano);
    //查询一条回答
    HworkAnswer select(Integer hid,String courseNo,String uid,String questionNo);
    //根据作业编号、课程编号,用户编号查询一份作业的Answer
    List<HworkAnswer> selectAll(Integer hid, String courseNo, String uid);

    //查询一份作业的所有Answer
    List<HworkAnswer> loadAll(Integer hid, String courseNo);

    //查询一份作业下的uid
    List<String> selectUid(Integer hid, String courseNo);

    //查询一份作业下未批改的uid
    List<String> selectUid1(Integer hid, String courseNo);

    //多少人提交了作业
    Integer selectUsers(Integer hid, String courseNo);

}
