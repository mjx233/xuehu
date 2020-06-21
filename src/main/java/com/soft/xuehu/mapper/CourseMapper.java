package com.soft.xuehu.mapper;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.util.CourseQueryHelper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Create By majianxin on 2020/5/7.
 */

public interface CourseMapper {

    //新增课程记录
    void addCourse(Course course);
    //删除一条课程记录
    void removeCourse(String courseNo);

    //删除一个章节目录
    void removeZjml(String courseNo,Integer zj,Integer ml);

    //更新一条课程记录
    void updateCourse(Course course);

    //更新章节目录
    void updateZjml(Course course);

    //获取一条课程记录
    Course loadCourseByCourseNo(String courseNo);

    //根据课程编号、章节目录获取一条课程记录
    Course loadCourse(String courseNo,Integer zj,Integer ml);

    //根据课程编号、章节目录获取课程的条数
    int loadCourse1(String courseNo,Integer zj,Integer ml);

    //获取一节课程的内容
    Course selectMl(Integer cid);

    //顺序查询课程章节内容
    List<Course> loadSortCourses(String courseNo);

    //查询用户所有课程
    List<Course> loadStudy(List<Cusers> cusersList);

    //查询教师所教课程
    List<Course> loadTeach(List<Cusers> cusersList);

    //根据教师id查找所教课程
    List<Course> loadByUid(String uid);

    //分页查询全部课程
    List<Course> loadAll(CourseQueryHelper helper);

    //删除用户所有课程
    void remove(String uid);

}
