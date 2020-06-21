package com.soft.xuehu.controller;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.entity.HworkAnswer;
import com.soft.xuehu.entity.HworkQuestion;
import com.soft.xuehu.mapper.HworkMapper;
import com.soft.xuehu.service.*;
import com.soft.xuehu.util.AnswerHelper;
import com.soft.xuehu.util.GradeHelper;
import com.soft.xuehu.util.HworkUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static javax.management.Query.attr;

/**
 * Create By majianxin on 2020/5/23.
 */
@Controller
@RequestMapping("/hwork")
public class HworkController {


    @Autowired
    HworkService hworkService;

    @Autowired
    CourseService courseService;

    @Autowired
    UsersService usersService;

    @Autowired
    HworkQuestionService hworkQuestionService;

    @Autowired
    HworkAnswerService hworkAnswerService;

    @GetMapping("toUpload")
    public String toUpload(){
        return "hwork/upload_hwork";
    }

    @GetMapping("toAdd")
    public String toAdd(@RequestParam String courseNo,@RequestParam String uid,Map<String,Object> map){
        Course course = courseService.loadCourseByCourseNo(courseNo);
        Hwork hwork = new Hwork();

        hwork.setUid(uid);
        hwork.setCourseNo(courseNo);

        Integer i= hworkService.selectNum(hwork.getCourseNo(),hwork.getUid())+1;
        String a = i.toString();
        hwork.sethNum(a);
        map.put("hwork",hwork);

        return "hwork/add_hwork";
    }

    @PostMapping(value="/add")
    public String add(Hwork hwork, RedirectAttributes attr){

        hworkService.addHwork(hwork);
        attr.addAttribute("courseNo", hwork.getCourseNo());
        attr.addAttribute("uid", hwork.getUid());
        return "redirect:/hwork/listAll";
    }

/*    *//**
     * 导入excel
     *//*
    @RequestMapping("/import")
    @ResponseBody
    public String excelImport(@RequestParam(value="filename") MultipartFile file, HttpSession session){

//		String fileName = file.getOriginalFilename();

        int result = 0;

        try {
            result = hworkService.addHwork(file);
        } catch (Exception e) {

            e.printStackTrace();
        }

        if(result > 0){
            return "excel文件数据导入成功！";
        }else{
            return "excel数据导入失败！";
        }
    }*/

    @RequestMapping("/listAll")
    public String listAll(@RequestParam String courseNo,@RequestParam String uid, Map<String, Object> map) {

        List<Hwork> hworkList = hworkService.selectBycourseNo(courseNo);

        Date date = new Date();
        map.put("hworkList",hworkList);
        map.put("Date",date);
        map.put("users",usersService.get(uid));
        map.put("course",courseService.loadCourseByCourseNo(courseNo));

        return "hwork/list_hwork";
    }

    @RequestMapping("/list")
    public String list(Map<String, Object> map) {

        List<Hwork> hworkList = hworkService.select();

        List<HworkUsers> hworkUsersList = new ArrayList<>();
        for(int i=0;i<hworkList.size();i++){
            Course course = courseService.loadCourseByCourseNo(hworkList.get(i).getCourseNo());
            HworkUsers hworkUsers = new HworkUsers();
            hworkUsers.setCname(course.getCname());
            hworkUsers.setHwork(hworkList.get(i));
            hworkUsersList.add(i,hworkUsers);
        }
        map.put("hworkUsersList",hworkUsersList);


        return "admin/list_hwork";
    }

    //删除课程
    @GetMapping(value="/remove") //作用：对应删除，表明是一个删除URL映射
    public String remove1(@RequestParam Integer hid,@RequestParam String uid,@RequestParam String courseNo,RedirectAttributes attr) {

        hworkService.removeHwork(hid);
        hworkQuestionService.removeAll(hid);
        attr.addAttribute("courseNo", courseNo);
        attr.addAttribute("uid", uid);
        return "redirect:/hwork/listAll";
    }

    //管理员删除课程
    @GetMapping(value="/adminRemove") //作用：对应删除，表明是一个删除URL映射
    public String adminRemove(@RequestParam Integer hid,@RequestParam String uid,@RequestParam String courseNo,RedirectAttributes attr) {

        hworkService.removeHwork(hid);
        hworkQuestionService.removeAll(hid);

        return "redirect:/hwork/list";
    }

    @GetMapping(value="/toUpdate")
    public String Update(@RequestParam Integer hid,@RequestParam String uid, Map<String, Object> map) {

        Hwork hwork =hworkService.selectByHid(hid);

        map.put("hwork",hwork);
        map.put("course",courseService.loadCourseByCourseNo(hwork.getCourseNo()));
        map.put("uid",uid);

        return "hwork/update_hwork";
    }

    @PostMapping(value="/update")
    public String update(Hwork hwork, Map<String, Object> map,RedirectAttributes attr) throws Exception{

        Course course =courseService.loadCourseByCourseNo(hwork.getCourseNo());
        System.out.println(hwork.gethMessage());
            hworkService.update(hwork);

        attr.addAttribute("courseNo",course.getCourseNo() );
        attr.addAttribute("uid", usersService.selectByUname(course.getTeacher()).getUid());
        return "redirect:/hwork/listAll";
    }

}
