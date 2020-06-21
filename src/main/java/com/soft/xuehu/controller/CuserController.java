package com.soft.xuehu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.service.CourseService;
import com.soft.xuehu.service.CusersService;
import com.soft.xuehu.service.UsersService;
import com.soft.xuehu.util.CnameHelper;
import com.soft.xuehu.util.CourseQueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * Create By majianxin on 2020/5/21.
 */

@Controller
@RequestMapping("/cuser")
public class CuserController {


    @Autowired
    private CourseService courseService;

    @Autowired
    private CusersService cusersService;

    @Autowired
    private UsersService usersService;

    /**
     * 判断课程是否已被选修
     */
    @GetMapping(value="/toAdd")
    public String toAdd(@RequestParam String courseNo, @RequestParam String uid,RedirectAttributes attr) {

        List<String> courseNoList = cusersService.loadCourseNo(uid);
        for(int i=0;i<courseNoList.size();i++){
            if(courseNoList.get(i).equals(courseNo)){
                /*    0:无事发生
                      1：选修成功
                      2：重复选修*/
                String tc = "2";
                attr.addAttribute("courseNo", courseNo);
                attr.addAttribute("uid", uid);
                attr.addAttribute("tc", tc);
                return "redirect:/cuser/add";
            }
        }
        attr.addAttribute("courseNo", courseNo);
        attr.addAttribute("uid", uid);
        attr.addAttribute("tc", 1);
        return "redirect:/cuser/add";
    }



    /**
     * 选修课程
     */
    @GetMapping(value="/add")
    public String create(@RequestParam String courseNo, @RequestParam String uid,@RequestParam String tc, @RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,
                         Map<String, Object> map) {

        Users users = usersService.get(uid);
        if(tc.equals("1")){

            Cusers cusers = new Cusers();
            cusers.setCourseNo(courseNo);
            cusers.setUsers(users);

            cusersService.addCusers(cusers);
        }

        int pageNo = 1;

        //对 pageNo 的校验
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }

        PageHelper.startPage(pageNo, 3);

        com.soft.xuehu.util.CourseQueryHelper helper = new CourseQueryHelper();
        helper.setQryCourseNo(null);
        helper.setQryTeacher(null);
        helper.setQryCname(null);

        List<Course> courseList = courseService.loadAll(helper);

        PageInfo<Course> page=new PageInfo<Course>(courseList);

        map.put("page", page);
        map.put("msg",0);
        map.put("users",users);
        map.put("helper", helper);
        map.put("tc", tc);

        return "course/list_course1";
    }

    @RequestMapping("/listStu")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String listAll(@RequestParam(value="uid", required=false, defaultValue="") String uid,
                          Map<String, Object> map) {


        CourseQueryHelper courseQueryHelper = null;

        List<Course> courseList = courseService.loadAll(courseQueryHelper);

        //找出各用户的选修记录
        List<Cusers> cusersList = new ArrayList<>();
        for(int i=0;i<courseList.size();i++){
            List<Cusers> cusersList1 = cusersService.selectStu(courseList.get(i).getCourseNo(),courseList.get(i).getUid());
            cusersList.addAll(cusersList1);
        }
        //利用CnameHelper将选修记录加上教师的名字
        List<CnameHelper> cnameHelperList =new ArrayList<>();
        for(int i=0;i<cusersList.size();i++){
            CnameHelper cnameHelper = new CnameHelper();
            cnameHelper.setCusers(cusersList.get(i));
            cnameHelper.setTeacher(courseService.loadCourseByCourseNo(cusersList.get(i).getCourseNo()).getTeacher());
            cnameHelper.setCname(courseService.loadCourseByCourseNo(cusersList.get(i).getCourseNo()).getCname());
            cnameHelperList.add(i,cnameHelper);
        }
        if(uid!=""){
            for(int i=0;i<cnameHelperList.size();i++)
            {
                if(cnameHelperList.get(i).getCusers().getUsers().getUid()!=uid);
                cnameHelperList.remove(i);

            }
        }

        map.put("cnameHelperList", cnameHelperList);

            return "admin/list_cusersStu";

    }

}