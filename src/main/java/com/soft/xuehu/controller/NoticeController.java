package com.soft.xuehu.controller;

import com.soft.xuehu.entity.*;
import com.soft.xuehu.service.*;
import com.soft.xuehu.util.NoticeCourse;
import com.soft.xuehu.util.NoticeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    HworkQuestionService hworkQuestionService;

    @Autowired
    CourseService courseService;

    @Autowired
    UsersService usersService;

    @Autowired
    CusersService cusersService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    NusersService nusersService;

    @GetMapping("toAdd")
    public String toAdd(@RequestParam String courseNo, @RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);

        List<Cusers> cusersList = cusersService.selectUsers(courseNo);

        int sum = cusersList.size();

        Notice notice = new Notice();
        notice.setCourseNo(courseNo);
        notice.setUid(uid);
        notice.setnSum(sum);
        notice.setnNum(0);
        map.put("notice", notice);
        System.out.println(notice.getnSum());

        return "notice/add_notice";
    }

    @PostMapping(value = "/add")
    public String add(Notice notice, RedirectAttributes attr) {

        noticeService.addNotice(notice);

        List<Cusers> cusersList = cusersService.selectUsers(notice.getCourseNo());
        for (int i=0;i<cusersList.size();i++){
            Nusers nusers = new Nusers();
            nusers.setState(0);
            nusers.setNid(notice.getNid());
            nusers.setUid(cusersList.get(i).getUsers().getUid());
            nusersService.addNusers(nusers);
        }

        attr.addAttribute("courseNo", notice.getCourseNo());
        attr.addAttribute("uid", notice.getUid());
        return "redirect:/notice/listAll";
    }

    @RequestMapping("/listAll")
    public String listAll(@RequestParam String courseNo ,@RequestParam String uid, Map<String, Object> map) {

        List<Notice> noticeList = noticeService.selectAll(courseNo);
        List<Nusers> nusersList = nusersService.selectByUid(uid);

        List<NoticeHelper> noticeHelperList = new ArrayList<>();
        if(nusersList.size()!=0){
            for(int i=0;i<noticeList.size();i++){
                NoticeHelper noticeHelper= new NoticeHelper();
                noticeHelper.setNotice(noticeList.get(i));
                noticeHelper.setState(nusersList.get(i).getState());
                noticeHelperList.add(noticeHelper);
            }
        }

        map.put("noticeHelperList",noticeHelperList);
        map.put("users",usersService.get(uid));
        map.put("course",courseService.loadCourseByCourseNo(courseNo));

        return "notice/list_notice";
    }

    @RequestMapping("/list")
    public String list( Map<String, Object> map) {

        List<NoticeCourse> noticeCourseList = new ArrayList<>();

        List<Notice> noticeList = noticeService.select();
        if(noticeList.size()!=0){
            for(int i=0;i<noticeList.size();i++){
                String cname = courseService.loadCourseByCourseNo(noticeList.get(i).getCourseNo()).getCname();
                String uname = usersService.get(noticeList.get(i).getUid()).getUname();
                NoticeCourse noticeCourse = new NoticeCourse();
                noticeCourse.setNotice(noticeList.get(i));
                noticeCourse.setCname(cname);
                noticeCourse.setUname(uname);
                noticeCourseList.add(i,noticeCourse);
            }
        }

        map.put("noticeCourseList",noticeCourseList);
        return "admin/list_notice";
    }

    @RequestMapping("/contentList")
    public String listAll(@RequestParam Integer nid ,@RequestParam String uid, Map<String, Object> map) {

        Notice notice = noticeService.selectByNid(nid);

        map.put("notice",notice);
        map.put("users",usersService.get(uid));
        map.put("name",usersService.get(notice.getUid()).getUname());

        return "notice/list_content";
    }

    @RequestMapping("/content/{nid}")
    public String list(@PathVariable("nid")Integer nid , Map<String, Object> map) {

        Notice notice = noticeService.selectByNid(nid);
        map.put("name",usersService.get(notice.getUid()).getUname());
        map.put("notice",notice);
        return "admin/notice_content";
    }

    @RequestMapping("/state")
    public String state(@RequestParam Integer nid ,@RequestParam String uid, Map<String, Object> map) {

        Nusers nusers = nusersService.select(nid,uid);
        Notice notice = noticeService.selectByNid(nid);
        if(nusers.getState()!=1){
            notice.setnNum(notice.getnNum()+1);
            noticeService.update(notice);
        }
        nusers.setState(1);
        nusersService.updateNusers(nusers);

        map.put("notice",notice);
        map.put("users",usersService.get(uid));
        map.put("name",usersService.get(notice.getUid()).getUname());

        return "notice/list_content";
    }

    @RequestMapping("/Sremove")
    public String Sremove(@RequestParam Integer nid ,@RequestParam String uid,@RequestParam String courseNo, RedirectAttributes attr,Map<String, Object> map) {

        Nusers nusers = nusersService.select(nid,uid);

        nusersService.removeNuno(nusers.getNuNo());

        attr.addAttribute("courseNo",courseNo);
        attr.addAttribute("uid",uid);
        return "redirect:/notice/listAll";
    }

    @RequestMapping("/Tremove")
    public String Tremove(@RequestParam Integer nid ,@RequestParam String uid,@RequestParam String courseNo, RedirectAttributes attr,Map<String, Object> map) {


        nusersService.remove(nid);
        noticeService.removeNotice(nid);

        attr.addAttribute("courseNo",courseNo);
        attr.addAttribute("uid",uid);

        return "redirect:/notice/listAll";
    }

    @RequestMapping("/Tremove1")
    public String Tremove1(@RequestParam Integer nid ,@RequestParam String courseNo, RedirectAttributes attr,Map<String, Object> map) {

        nusersService.removeNuno(nid);
        noticeService.removeNotice(nid);

        return "redirect:/notice/list";
    }


    @RequestMapping("/preupdate")
    public String Tremove(@RequestParam Integer nid ,@RequestParam String uid,Map<String, Object> map) {

        Nusers nusers = nusersService.select(nid, uid);

        Notice notice = noticeService.selectByNid(nid);

        map.put("notice", notice);
        map.put("nusers", nusers);
        map.put("users",usersService.get(uid));
        return "notice/update_notice";
    }
}
