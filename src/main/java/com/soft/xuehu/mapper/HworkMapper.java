package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.entity.HworkQuestion;

import java.util.List;

/**
 * Create By majianxin on 2020/5/23.
 */
public interface HworkMapper {

    //    增
    int addHwork(com.soft.xuehu.entity.Hwork hword);
    //  查
    int selectHwork(String hnum,String uid);
    //  改
    int updateHwork(com.soft.xuehu.entity.Hwork hwork);

    //按照课程编号查询作业
    List<Hwork> selectBycourseNo(String courseNo);


    //根据主键查询一条问题
    Hwork selectByHid(Integer hid);

    void update(Hwork hwork);

    //查询作业份数
    int selectNum(String courseNo,String uid);

    //删除一份作业
    void removeHwork(Integer hid);

    //查询所有
    List<Hwork> select();

}
