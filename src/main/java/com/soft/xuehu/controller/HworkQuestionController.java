package com.soft.xuehu.controller;

import com.soft.xuehu.entity.*;
import com.soft.xuehu.service.CourseService;
import com.soft.xuehu.service.CusersService;
import com.soft.xuehu.service.HworkQuestionService;
import com.soft.xuehu.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Create By majianxin on 2020/5/26.
 */

@Controller
@RequestMapping("/hworkQuestion")
public class HworkQuestionController {

    @Autowired
    HworkQuestionService hworkQuestionService;

    @Autowired
    CourseService courseService;

    @Autowired
    UsersService usersService;

    @Autowired
    CusersService cusersService;


    @RequestMapping("/list")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list(@RequestParam String courseNo, @RequestParam Integer hid,@RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);
        Users users = usersService.get(uid);

        List<HworkQuestion> hworkQuestionList = hworkQuestionService.selectAll(hid,courseNo,uid);

        List<Integer> gradeList = hworkQuestionService.selectGrade(hid);

        Integer Grade = 0;
        for(int i=0;i<gradeList.size();i++){
            Grade += gradeList.get(i);
        }

        map.put("hworkQuestionList",hworkQuestionList);
        map.put("course",course);
        map.put("hid",hid);
        map.put("users",usersService.get(uid));

        return "hwork/list";
    }

    @RequestMapping("/adminList")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String adminList(@RequestParam String courseNo, @RequestParam Integer hid,@RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);
        Users users = usersService.get(uid);

        List<HworkQuestion> hworkQuestionList = hworkQuestionService.selectAll(hid,courseNo,uid);

        List<Integer> gradeList = hworkQuestionService.selectGrade(hid);

        Integer Grade = 0;
        for(int i=0;i<gradeList.size();i++){
            Grade += gradeList.get(i);
        }

        map.put("hworkQuestionList",hworkQuestionList);
        map.put("course",course);
        map.put("hid",hid);
        map.put("users",usersService.get(uid));

        return "admin/list_hwork_content";
    }

    @GetMapping("/toadd")
    public String toAdd(@RequestParam String courseNo,@RequestParam String uid,@RequestParam Integer hid, Map<String, Object> map)
    {
        Users users = usersService.get(uid);
        HworkQuestion hworkQuestion = new HworkQuestion();
        hworkQuestion.setCourseNo(courseNo);
        hworkQuestion.setUid(uid);
        hworkQuestion.setHid(hid);

        map.put("hworkQuestion", hworkQuestion);
        map.put("users",users);
        return "hwork/input_hworkQuestion";
    }


    @PostMapping(value="/add")
    public String add(HworkQuestion hworkQuestion, Map<String, Object> map, RedirectAttributes attr) throws Exception{

        try{
            hworkQuestionService.addHworkQuestion(hworkQuestion);
            attr.addAttribute("courseNo",hworkQuestion.getCourseNo());
            attr.addAttribute("hid",hworkQuestion.getHid());
            attr.addAttribute("uid",hworkQuestion.getUid());
            return "redirect:/hworkQuestion/list";

        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());
            return "hwork/input_hworkQuestion";
        }
    }

    @GetMapping(value="/toupdate")
    public String Update(@RequestParam Integer thworkNo,@RequestParam String uid, Map<String, Object> map) {

        HworkQuestion hworkQuestion = hworkQuestionService.selectByThworkNo(thworkNo);

        map.put("hworkQuestion",hworkQuestion);
        map.put("users",usersService.get(uid));

        return "hwork/update_hworkQuestion";
    }

    @PostMapping(value="/update")
    public String update(HworkQuestion hworkQuestion, Map<String, Object> map,RedirectAttributes attr) throws Exception{

        Course course =courseService.loadCourseByCourseNo(hworkQuestion.getCourseNo());
        try{
            hworkQuestionService.updateHworkQuestion(hworkQuestion);
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());

            System.out.println(e.getMessage());
            return "/hwork/update_hworkQuestion";
        }
        attr.addAttribute("courseNo",course.getCourseNo() );
        attr.addAttribute("hid",hworkQuestion.getHid());
        attr.addAttribute("uid", usersService.selectByUname(course.getTeacher()).getUid());
        return "redirect:/hworkQuestion/list";
    }

    //删除课程
    @GetMapping(value="/remove") //作用：对应删除，表明是一个删除URL映射
    public String remove(@RequestParam Integer thworkNo,@RequestParam String uid,RedirectAttributes attr) {

        attr.addAttribute("courseNo", hworkQuestionService.selectByThworkNo(thworkNo).getCourseNo());
        attr.addAttribute("hid", hworkQuestionService.selectByThworkNo(thworkNo).getHid());
        attr.addAttribute("uid", uid);
        hworkQuestionService.removeHworkQuestion(thworkNo);
        return "redirect:/hworkQuestion/list";
    }

}
