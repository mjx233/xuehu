package com.soft.xuehu.util;

import com.soft.xuehu.entity.HworkAnswer;

import java.util.List;

/**
 * Create By majianxin on 2020/5/28.
 */
public class GradeHelper {

    private List<HworkAnswer> hworkAnswersList;

    private Integer totalGrade;

    private String uname;

    private String uid;

    public List<HworkAnswer> getHworkAnswersList() {
        return hworkAnswersList;
    }

    public void setHworkAnswersList(List<HworkAnswer> hworkAnswersList) {
        this.hworkAnswersList = hworkAnswersList;
    }

    public Integer getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(Integer totalGrade) {
        this.totalGrade = totalGrade;
    }

    public String getUname() {

        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
