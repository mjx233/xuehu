package com.soft.xuehu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Create By majianxin on 2020/5/23.
 */
@Component
@Entity
@Table(name="hwork")
public class Hwork implements Serializable {
    @Id

    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "hid")
    private Integer hid;
    @Column(name = "uid")
    private String uid;
    private String hMessage;
    private String courseNo;
    private String hNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date finishTime;

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String gethMessage() {
        return hMessage;
    }

    public void sethMessage(String hMessage) {
        this.hMessage = hMessage;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String gethNum() {
        return hNum;
    }

    public void sethNum(String hNum) {
        this.hNum = hNum;
    }

/*    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        //获取当前时间
        TimeZone time = TimeZone.getTimeZone("ETC/GMT-8");

        TimeZone.setDefault(time);
        Date date =new Date();
        SimpleDateFormat srtFormat = new SimpleDateFormat("yyyy/m/d h:mm");
        try {
            String nowTime1 = srtFormat.format(date);
            Date time1 = srtFormat.parse(nowTime1);
            this.startTime = time1;
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {

        TimeZone time = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(time);
        //这里是要被转换的字符串格式
        SimpleDateFormat srtFormat = new SimpleDateFormat("yyyy-mm-dd h:mm");
        try {
            Date time1 = srtFormat.parse(finishTime);
            this.finishTime = time1;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}


