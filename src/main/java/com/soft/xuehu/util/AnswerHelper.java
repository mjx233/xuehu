package com.soft.xuehu.util;

import com.soft.xuehu.entity.HworkAnswer;

/**
 * Create By majianxin on 2020/5/29.
 */
public class AnswerHelper {

    private HworkAnswer hworkAnswer;
    private String rightAnswer;
    private Integer grade;

    public HworkAnswer getHworkAnswer() {
        return hworkAnswer;
    }

    public void setHworkAnswer(HworkAnswer hworkAnswer) {
        this.hworkAnswer = hworkAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

}
