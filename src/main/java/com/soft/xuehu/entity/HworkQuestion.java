package com.soft.xuehu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Create By majianxin on 2020/5/26.
 */
@Component
@Entity
@Table(name="hwork_question")
public class HworkQuestion implements Serializable {
    @Id
    private Integer thworkNo;
    @Column(name = "hid")
    private Integer hid;
    private String courseNo;
    private String questionNo;
    private String question;
    private String answer;
    private Integer grade;
    private String uid;


    public Integer getThworkNo() {
        return thworkNo;
    }

    public void setThworkNo(Integer thworkNo) {
        this.thworkNo = thworkNo;
    }

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
