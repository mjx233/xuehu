package com.soft.xuehu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Create By majianxin on 2020/5/29.
 */

@Component
@Entity
@Table(name="notice")
public class Notice implements Serializable {
    @Id

    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "nid")
    private Integer nid;
    @Column(name = "uid")
    private String uid;
    private String nMessage;
    private String courseNo;
    private String title;
    private Integer nNum;
    private Integer nSum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date startTime;

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getnMessage() {
        return nMessage;
    }

    public void setnMessage(String nMessage) {
        this.nMessage = nMessage;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getnNum() {
        return nNum;
    }

    public void setnNum(Integer nNum) {
        this.nNum = nNum;
    }

    public Integer getnSum() {
        return nSum;
    }

    public void setnSum(Integer nSum) {
        this.nSum = nSum;
    }

}
