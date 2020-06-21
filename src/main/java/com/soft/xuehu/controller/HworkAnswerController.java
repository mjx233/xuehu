package com.soft.xuehu.controller;

import com.soft.xuehu.entity.*;
import com.soft.xuehu.service.*;
import com.soft.xuehu.util.AnswerHelper;
import com.soft.xuehu.util.GradeHelper;
import com.soft.xuehu.util.HworkHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * Create By majianxin on 2020/5/27.
 */
@Controller
@RequestMapping("/hworkAnswer")
public class HworkAnswerController {

    @Autowired
    HworkQuestionService hworkQuestionService;

    @Autowired
    CourseService courseService;

    @Autowired
    UsersService usersService;

    @Autowired
    CusersService cusersService;

    @Autowired
    HworkAnswerService hworkAnswerService;

    @Autowired
    HworkService hworkService;



    @RequestMapping("/list")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list(@RequestParam String courseNo, @RequestParam Integer hid,@RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);  //课程
        Users users = usersService.selectByUname(course.getTeacher()); //教师

        List<HworkAnswer> hworkAnswerList = hworkAnswerService.selectAll(hid,courseNo,uid);   //用户作业答案
        List<HworkQuestion> hworkQuestionList = hworkQuestionService.selectAll(hid,courseNo,users.getUid());   //列出作业问题

        //计算题量
        Hwork hwork = hworkService.selectByHid(hid);
        int num = hworkQuestionService.selectNum(hid);


        //计算总分
        List<Integer> gradeList = hworkQuestionService.selectGrade(hid);
        Integer Grade = 0;
        /*Integer size = hworkAnswerList.size();*/
        for(int i=0;i<gradeList.size();i++){
            //计算总分
            Grade += gradeList.get(i);

            //检查是否已经回答问题，没有的话这时候没有记录，需要赋值
            HworkAnswer hworkAnswer = hworkAnswerService.select(hid,courseNo,uid,hworkQuestionList.get(i).getQuestionNo());
            if(hworkAnswer==null){
                hworkAnswer = new HworkAnswer();

                HworkQuestion hworkQuestion = hworkQuestionList.get(i);
                hworkAnswer.setGrade(hworkQuestion.getGrade());
                hworkAnswer.setThworkNo(hworkQuestion.getThworkNo());
                hworkAnswer.setHid(hworkQuestion.getHid());
                hworkAnswer.setCourseNo(hworkQuestion.getCourseNo());
                hworkAnswer.setQuestion(hworkQuestion.getQuestion());
                hworkAnswer.setQuestionNo(hworkQuestion.getQuestionNo());
                hworkAnswer.setUid(hworkQuestion.getUid());
                hworkAnswer.setState("0");
                hworkAnswerList.add(i,hworkAnswer);
            }
        }

        Integer grade = 0;
            for(int j=0;j<hworkAnswerList.size();j++)
            {
                grade += hworkAnswerList.get(j).getGrade();
            }

            List<AnswerHelper> answerHelperList = new ArrayList<>();

            for(int i=0;i<hworkAnswerList.size();i++){
                AnswerHelper answerHelper = new AnswerHelper();
                answerHelper.setHworkAnswer(hworkAnswerList.get(i));
                answerHelper.setRightAnswer(hworkQuestionList.get(i).getAnswer());
                answerHelper.setGrade(hworkQuestionList.get(i).getGrade());
                answerHelperList.add(i,answerHelper);
            }
        map.put("hworkAnswerList",hworkAnswerList);
        map.put("hworkQuestionList",hworkQuestionList);
        map.put("answerHelperList",answerHelperList);
        map.put("course",course);
        map.put("hwork",hwork);
        map.put("num",num);
        map.put("Grade",Grade);
        map.put("grade",grade);
        map.put("uid",users.getUname());
        map.put("users",usersService.get(uid));
        map.put("hid",hid);

        if(hworkAnswerList.get(0).getState().equals("1"))
        {
            return "hwork/list_answer1";
        }
        else
        return "hwork/list_answer";
    }

    @PostMapping(value="/add")
    public String add(HworkAnswer hworkAnswer, Map<String, Object> map, RedirectAttributes attr) throws Exception{

        HworkAnswer hworkAnswer1 = hworkAnswerService.select(hworkAnswer.getHid(),hworkAnswer.getCourseNo(),hworkAnswer.getUid(),hworkAnswer.getQuestionNo());

        try{
            if(hworkAnswer1==null){
                hworkAnswerService.addHworkAnswer(hworkAnswer);
            }
            else{
                hworkAnswer1.setAnswer(hworkAnswer.getAnswer());
                hworkAnswerService.updateHworkAnswer(hworkAnswer1);
            }

            attr.addAttribute("courseNo",hworkAnswer.getCourseNo());
            attr.addAttribute("hid",hworkAnswer.getHid());
            attr.addAttribute("uid",hworkAnswer.getUid());
            return "redirect:/hworkAnswer/list";

        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());
            return "hwork/list_answer";
        }
    }

    @GetMapping(value = "/toGrade")
    public String toGrade(@RequestParam String courseNo,@RequestParam String uid,Map<String,Object> map){
        Course course = courseService.loadCourseByCourseNo(courseNo);

        List<Hwork> hworkList = hworkService.selectBycourseNo(courseNo);

        List<HworkHelper> hworkHelperList = new ArrayList<>();

        for(int i=0;i<hworkList.size();i++){
            HworkHelper hworkHelper = new HworkHelper();
            hworkHelper.setHwork(hworkList.get(i));

            List<String> uidList = hworkAnswerService.selectUid1(hworkList.get(i).getHid(),courseNo);
            HashSet h = new HashSet(uidList);
            uidList.clear();
            uidList.addAll(h);
            Integer stateIng = uidList.size();

            hworkHelper.setStateIng(stateIng);
            hworkHelperList.add(i,hworkHelper);
        }

        map.put("hworkHelperList",hworkHelperList);
        map.put("users",usersService.get(uid));
        map.put("courseNo",courseNo);

        return "hwork/toGrade_hwork";
    }

    @GetMapping(value = "/preUpdate")
    public String preGrade(@RequestParam String hid1,@RequestParam String uid,Map<String,Object> map){

        Integer hid = Integer.parseInt(hid1);
        Hwork hwork = hworkService.selectByHid(hid);

        Course course = courseService.loadCourseByCourseNo(hwork.getCourseNo());

        List<Hwork> hworkList = hworkService.selectBycourseNo(hwork.getCourseNo());

        List<HworkAnswer> hworkAnswerList = hworkAnswerService.loadAll(hid,hwork.getCourseNo());

        List<GradeHelper> gradeHelpersList = new ArrayList<>();

        List<String> uidList = hworkAnswerService.selectUid(hid,hwork.getCourseNo());
        HashSet h = new HashSet(uidList);
        uidList.clear();
        uidList.addAll(h);

        List<GradeHelper> gradeHelperList = new ArrayList<>();

        for(int i=0;i<uidList.size();i++){
            System.out.println("uid:"+uidList.get(i));
            String uid1 = uidList.get(i);
            List<HworkAnswer> hworkAnswerList1 = new ArrayList<>();
            GradeHelper gradeHelper = new GradeHelper();
            int grade=0;
            for(int j=0;j<hworkAnswerList.size();j++){
                int r=0;
                System.out.println("huid:"+hworkAnswerList.get(j).getUid());
                if(hworkAnswerList.get(j).getUid().equals(uid1)){
                    grade = grade + hworkAnswerList.get(j).getGrade();
                    hworkAnswerList1.add(r++,hworkAnswerList.get(j));
                }
            }
            gradeHelper.setHworkAnswersList(hworkAnswerList1);
            gradeHelper.setTotalGrade(grade);
            gradeHelper.setUid(uid1);
            gradeHelper.setUname(usersService.get(uid1).getUname());
            gradeHelperList.add(i,gradeHelper);

        }
        map.put("gradeHelperList",gradeHelperList);
        map.put("users",usersService.get(uid));
        map.put("course",course);
        map.put("uidList",uidList);
        map.put("hid",hid);

        return "hwork/preGrade_hwork";
    }

    @GetMapping(value = "/Update")
    public String preGrade(@RequestParam String Suid,  @RequestParam String courseNo, @RequestParam Integer hid,@RequestParam String uid, Map<String,Object> map){

        List<HworkAnswer> hworkAnswerList1 = new ArrayList<>();
        GradeHelper gradeHelper = new GradeHelper();

        List<GradeHelper> gradeHelperList = new ArrayList<>();

        List<HworkAnswer> hworkAnswerList = new ArrayList<>();
        hworkAnswerList = hworkAnswerService.selectAll(hid,courseNo,Suid);
        int grade=0;
        for(int j=0;j<hworkAnswerList.size();j++){
                grade += hworkAnswerList.get(j).getGrade();
        }

        gradeHelper.setHworkAnswersList(hworkAnswerList);
        gradeHelper.setTotalGrade(grade);
        gradeHelper.setUid(Suid);
        gradeHelper.setUname(usersService.get(Suid).getUname());
        gradeHelperList.add(gradeHelper);

        Course course = courseService.loadCourseByCourseNo(hworkAnswerList.get(0).getCourseNo());

        //计算总分
        List<Integer> gradeList = hworkQuestionService.selectGrade(hid);
        Integer Grade = 0;
        /*Integer size = hworkAnswerList.size();*/
        for(int i=0;i<gradeList.size();i++){
            //计算总分
            Grade += gradeList.get(i);
        }


        List<AnswerHelper> answerHelperList =  new ArrayList<>();
        //找出每一题正确答案
        for(int i=0;i<hworkAnswerList.size();i++){
            int thworkNo = hworkAnswerList.get(i).getThworkNo();
            HworkQuestion hworkQuestion = hworkQuestionService.selectByThworkNo(thworkNo);
            AnswerHelper answerHelper = new AnswerHelper();
            answerHelper.setRightAnswer(hworkQuestion.getAnswer());
            answerHelper.setHworkAnswer(hworkAnswerList.get(i));
            answerHelper.setGrade(hworkQuestion.getGrade());
            answerHelperList.add(i,answerHelper);
        }

        map.put("answerHelperList",answerHelperList);
        map.put("uid",uid);
        map.put("course",course);
        map.put("Grade",Grade);
        map.put("num", hworkQuestionService.selectNum(hid));
        map.put("hwork",hworkService.selectByHid(hid));

        return "hwork/update_grade";
    }


    @PostMapping(value="/update")
    public String update(HworkAnswer hworkAnswer, Map<String, Object> map,RedirectAttributes attr) throws Exception{

        try{
            hworkAnswerService.updateHworkAnswer(hworkAnswer);
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());

            System.out.println(e.getMessage());
            return "/hworkAnswer/update_grade";
        }
        attr.addAttribute("Suid", hworkAnswer.getUid());
        attr.addAttribute("courseNo", hworkAnswer.getCourseNo());
        attr.addAttribute("hid",hworkAnswer.getHid().toString());
        attr.addAttribute("uid", hworkAnswer.getHid());
        return "redirect:/hworkAnswer/Update";
    }

}
