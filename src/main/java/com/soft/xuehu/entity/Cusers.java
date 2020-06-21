package com.soft.xuehu.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Create By majianxin on 2020/5/11.
 */
@Component
@Entity
@Table(name="cusers")
public class Cusers implements Serializable {
    @Id

    //防止因为驼峰命名 或者下划线 导致找不到字段
    @Column(name = "c_no")
    private Integer cno;
    @Column(name = "course_no")
    private String courseNo;

    /**
     * 对一关系
     */
    private Users users;

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
