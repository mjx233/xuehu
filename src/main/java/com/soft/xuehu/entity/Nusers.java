package com.soft.xuehu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Create By majianxin on 2020/5/30.
 */
@Component
@Entity
@Table(name="nusers")
public class Nusers implements Serializable {

    @Id

    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "nu_no")
    private Integer nuNo;
    @Column(name = "uid")
    private String uid;
    @Column(name = "nid")
    private Integer nid;
    private Integer state;

    public Integer getNuNo() {
        return nuNo;
    }

    public void setNuNo(Integer nuNo) {
        this.nuNo = nuNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
