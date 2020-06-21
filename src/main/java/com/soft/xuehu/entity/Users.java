package com.soft.xuehu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Create By majianxin on 2020/5/10.
 */

@Component
public class Users implements Serializable {

    @Id
    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "uid")
    private String uid;
    private String psw;
    @Column(name = "uname")
    private String uname;
    private Integer role;
    private byte[] pic;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
