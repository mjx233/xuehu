package com.soft.xuehu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Create By majianxin on 2020/5/7.
 */

@Component
@Entity
@Table(name="course")
public class Course implements Serializable {

    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "cid")
    private Integer cid;
    @Column(name = "cname")
    private String cname;
    private Integer zj;
    private Integer ml;
    @Column(name = "c_message")
    private String cMessage;
    private String video;
    private String zjMessage;
    private String mlMessage;
    private String teacher;
    private String courseNo;
    private byte[] pic;
    private String uid;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }



    public Integer getZj() {
        return zj;
    }

    public void setZj(Integer zj) {
        this.zj = zj;
    }

    public Integer getMl() {
        return ml;
    }

    public void setMl(Integer ml) {
        this.ml = ml;
    }

    public String getZjMessage() {
        return zjMessage;
    }

    public void setZjMessage(String zjMessage) {
        this.zjMessage = zjMessage;
    }

    public String getMlMessage() {
        return mlMessage;
    }

    public void setMlMessage(String mlMessage) {
        this.mlMessage = mlMessage;
    }

    public String getcMessage() {
        return cMessage;
    }

    public void setcMessage(String cMessage) {
        this.cMessage = cMessage;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
