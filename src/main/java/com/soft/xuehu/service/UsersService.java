package com.soft.xuehu.service;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.util.UploadUsers;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Create By majianxin on 2020/5/10.
 */
public interface UsersService {
    //获取一条用户数据
    Users get(String uid);
    //新增一条用户数据
    void addUsers(Users users);
    //获取一条用户数据
    Users selectByUname(String uname);

    //  改
    void update(Users users);


    /**
     * 根据课程编号，获得图片
     *
     * @param uid
     * @return byte
     *
     */
    byte[] getPic(String uid);

    //查询所有用户
    List<Users> selectAll();

    void remove(String uid);

    /**
     * excel上传
     * @param file
     * @return
     * @throws Exception
     */
    UploadUsers add(MultipartFile file) throws Exception;


    /**
     * 获取章节目录并下载成excel文档
     * @return
     */
    XSSFWorkbook show(List<Users> usersList);

}
