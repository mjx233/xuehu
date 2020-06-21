package com.soft.xuehu.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.service.CourseService;
import com.soft.xuehu.service.CusersService;
import com.soft.xuehu.service.UsersService;
import com.soft.xuehu.util.CourseQueryHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.Operation;
import org.apache.catalina.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import static java.lang.System.out;

/**
 * Create By majianxin on 2020/5/8.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CusersService cusersService;

    @Autowired
    private UsersService usersService;


    @GetMapping("/toAdd/{uid}")
    public String toAdd(Map<String, Object> map,@PathVariable("uid")String uid)
    {
        Users users = usersService.get(uid);
        Course course = new Course();
        course.setTeacher(usersService.get(uid).getUname());
        System.out.println(course.getTeacher());
        map.put("course", course);
        map.put("users",users);
        return "course/input_course";
    }


    @PostMapping(value="/add")
    public String add(@RequestParam("pic1") MultipartFile file, Course course, Map<String, Object> map) throws Exception{

        String courseNo = course.getCourseNo();

            Boolean tf = new Boolean(true);
            while(tf){
                Random rand = new Random();
                Integer no = rand.nextInt(10000);
                courseNo = no.toString();
                //检查生成的随机课程号是否与已有课程号重复
                List<Course> courseList = courseService.loadAll(null);
                for(int i=0;i<courseList.size();i++)
                {
                    if(courseList.get(i).getCourseNo()==courseNo){
                        break;
                    }
                }
                tf=false;
            }
        course.setCourseNo(courseNo);

        //读取文件数据，转成字节数组
        if(file!=null){
            course.setPic(file.getBytes());
        }
        try{
            courseService.addCourse(course);
            //同时要在用户-课程表增加一条记录
            Users users = usersService.selectByUname(course.getTeacher());
            Cusers cusers = new Cusers();
            cusers.setUsers(users);
            cusers.setCourseNo(courseNo);
            cusersService.addCusers(cusers);
            return "redirect:/course/listTeach/"+users.getUid();
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());

            return "course/input_course";
        }
    }


    @GetMapping("/toInput_zjml")
    public String toInput_zjml(@RequestParam String courseNo,@RequestParam String uid,Map<String, Object> map)
    {
        Users users = usersService.get(uid);
        Course course1 = courseService.loadCourseByCourseNo(courseNo);
        Course course = new Course();
        course.setCname(course1.getCname());
        course.setTeacher(course1.getTeacher());
        course.setTeacher(course1.getTeacher());
        course.setCourseNo(course1.getCourseNo());

        map.put("course", course);
        map.put("users",users);
        return "course/input_zjml";
    }

    @PostMapping(value="/add_zjml")
    public String add_zjml(Course course, Map<String, Object> map,RedirectAttributes attr) throws Exception{

        try{
            courseService.addCourse(course);
            Users users = usersService.selectByUname(course.getTeacher());
            attr.addAttribute("courseNo", course.getCourseNo());
            attr.addAttribute("uid", users.getUid());
            return "redirect:/course/list_zjml";

        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());

            return "course/input_zjml";
        }
    }

    //删除课程
    @GetMapping(value="/remove_zjml") //作用：对应删除，表明是一个删除URL映射
    public String remove_zjml(@RequestParam String courseNo,@RequestParam Integer zj,@RequestParam Integer ml,RedirectAttributes attr) {

        Course course = courseService.loadCourse(courseNo,zj,ml);
        System.out.println("ssss"+course.getZj());
        System.out.println("ssss"+course.getMl());
        courseService.removeZjml(courseNo,zj,ml);
        Users users = usersService.selectByUname(course.getTeacher());
        attr.addAttribute("courseNo", courseNo);
        attr.addAttribute("uid", users.getUid());
        return "redirect:/course/list_zjml";
    }

    @GetMapping("toList/{uid}")
    public String toList(@PathVariable("uid")String uid,Map<String,Object> map){
        Users users = usersService.get(uid);
        map.put("users",users);
        return "course/menu";
    }


    @RequestMapping("/listStudy/{uid}")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list(@PathVariable("uid")String uid, @RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,Map<String, Object> map) {


        int pageNo = 1;

        //对 pageNo 的校验
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }

        Users users = usersService.get(uid);

        List<Course> courseList = new ArrayList<Course>();

        List<Cusers> cusersList = cusersService.loadAll(uid);

        PageHelper.startPage(pageNo, 3);

        courseList = courseService.loadStudy(cusersList);

        PageInfo<Course> page=new PageInfo<Course>(courseList);

        map.put("users",users);
        map.put("page", page);
        map.put("msg",0);

        out.println(uid);

        return "course/list_course";
    }

    @RequestMapping("/listTeach/{uid}")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String listTeach(@PathVariable("uid")String uid, @RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,Map<String, Object> map) {

        System.out.println("无语");

        int pageNo = 1;

        //对 pageNo 的校验
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }

        Users users = usersService.get(uid);

        List<Course> courseList = new ArrayList<Course>();

        List<Cusers> cusersList = cusersService.loadAll(uid);

        PageHelper.startPage(pageNo, 3);

        courseList = courseService.loadTeach(cusersList);

        PageInfo<Course> page=new PageInfo<Course>(courseList);

        map.put("users",users);
        map.put("page", page);
        map.put("msg",0);

        out.println(uid);

        return "course/list_course";
    }

    @RequestMapping("/listAll/{uid}")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String listAll(@PathVariable("uid")String uid,@RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,
                          Map<String, Object> map, CourseQueryHelper helper) {

        int pageNo = 1;

        //对 pageNo 的校验
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }

        out.println(helper.getQryCname());
        out.println(helper.getQryTeacher());
        out.println(helper.getQryCourseNo());

        Users users = usersService.get(uid);

        PageHelper.startPage(pageNo, 3);

        List<Course> courseList = courseService.loadAll(helper);

        PageInfo<Course> page=new PageInfo<Course>(courseList);

        map.put("page", page);
        map.put("msg",0);
        map.put("users",users);
        map.put("helper", helper);

        if(users.getRole()==1||users.getRole()==0)
            return "course/list_course1";
        else
            return "admin/list_course";

    }


    @RequestMapping("/list_zjml")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list_zjml(@RequestParam String courseNo,@RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);
        List<Course> courseList = new ArrayList<Course>();

        courseList=courseService.loadSortCourses(courseNo);
        map.put("courseList",courseList);
        map.put("course",course);
        map.put("uid",uid);
        map.put("users",usersService.get(uid));

        return "course/list_zjml";
    }

    //修改课程章节目录
    @RequestMapping("/list_zjml_update")
    public String list_zjml_update(@RequestParam String courseNo,@RequestParam String uid, Map<String, Object> map) {
        Course course = courseService.loadCourseByCourseNo(courseNo);
        List<Course> courseList = new ArrayList<Course>();

        courseList=courseService.loadSortCourses(courseNo);
        map.put("courseList",courseList);
        map.put("course",course);
        map.put("uid",uid);
        map.put("users",usersService.get(uid));

        return "course/list_zjml_update";
    }

    @GetMapping(value="/toUpdate_zjml")
    public String Update(@RequestParam String courseNo,@RequestParam Integer zj,@RequestParam Integer ml, Map<String, Object> map) {

        Course course = courseService.loadCourse(courseNo,zj,ml);
        map.put("course" ,course);

        return "course/update_zjml";
    }

    @PostMapping(value="/update_zjml")
    public String update(Course course, Map<String, Object> map,RedirectAttributes attr) throws Exception{

        try{
            courseService.updateCourse(course);
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());

            return "/course/update_zjml";
        }
        attr.addAttribute("courseNo", course.getCourseNo());
        attr.addAttribute("uid", usersService.selectByUname(course.getTeacher()).getUid());
        return "redirect:/course/list_zjml";
    }

    //删除课程
    @GetMapping(value="/remove1") //作用：对应删除，表明是一个删除URL映射
    public String remove1(@RequestParam String courseNo,@RequestParam String uid) {

        Users users = usersService.get(uid);

        //删掉课程
        courseService.removeCourse(courseNo);
        //删掉对这门课的选修
        cusersService.removeByCourseNo(courseNo);
        if(users.getRole() == 1 || users.getRole()==0){
            return "redirect:/course/listTeach/"+uid;
        }
        else{
            return "redirect:/course/listAll/"+uid;
        }
    }

    //退选
    @GetMapping(value="/remove2") //作用：对应删除，表明是一个删除URL映射
    public String remove2(@RequestParam String courseNo,@RequestParam String uid) {

        cusersService.removeCusers(courseNo,uid);
        return "redirect:/course/listStudy/"+uid;

    }

    //管理员退选
    @GetMapping(value="/remove3") //作用：对应删除，表明是一个删除URL映射
    public String remove3(@RequestParam String courseNo,@RequestParam String uid) {

        cusersService.removeCusers(courseNo,uid);
        return "redirect:/cuser/listStu";

    }

    @GetMapping(value="/preUpdate")
    public String preUpdate(@RequestParam String courseNo,@RequestParam String uid,Map<String, Object> map) {

        map.put("course" ,courseService.loadCourseByCourseNo(courseNo));
        map.put("uid",uid);

        return "course/update_course";
    }

    @PostMapping(value="/update/{uid}")
    public String Update(@RequestParam("pic1") MultipartFile file,Course course,@PathVariable("uid")String uid , Map<String, Object> map) throws Exception{

        //读取多段提交的文件数据，转成字节数组
        if(file.getBytes().length>0){
            course.setPic(file.getBytes());
        }try{
            courseService.updateCourse(course);
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());
            map.put("uid",uid);
            return "/course/update_course";
        }
            return "redirect:/course/listStudy/"+uid;
    }


    @GetMapping(value="/video")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list(@RequestParam Integer cid,@RequestParam String uid,Map<String,Object>map) {

        Course course = courseService.selectMl(cid);
        map.put("course",course);
        map.put("uid",uid);
        return "course/course_content2";
    }

    //管理员退选
    @GetMapping(value="/removeVideo") //作用：对应删除，表明是一个删除URL映射
    public String remove3(@RequestParam Integer cid,@RequestParam String uid,Map<String,Object>map) {

        Course course = courseService.selectMl(cid);
        course.setVideo("");
        courseService.updateCourse(course);
        map.put("course",course);
        map.put("uid",uid);
        return "course/course_content2";

    }

    @GetMapping("/getPic/{courseNo}")
    public String getPic(@PathVariable("courseNo") String courseNo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        byte[] pic = courseService.getPic(courseNo);

        if(pic==null){
            String path = request.getSession().getServletContext().getRealPath("/img/default.jpg");
            FileInputStream fis = new FileInputStream(new File(path));

            pic = new byte[fis.available()];
            fis.read(pic);
        }

        //向浏览器发通知，我要发送是图片
        response.setContentType("image/jpeg");
        ServletOutputStream sos=response.getOutputStream();
        sos.write(pic);
        sos.flush();
        sos.close();

        return null;
    }

    @GetMapping("upload_zjml")
    public String toUpload(@RequestParam String courseNo,@RequestParam String uid,Map<String,Object> map){

        map.put("courseNo",courseNo);
        map.put("uid",uid);
        return "course/upload_zjml";
    }

    /**
     * 导入excel
     */
    @RequestMapping("/import")
    @ResponseBody
    public String excelImport(@RequestParam(value="filename") MultipartFile file, HttpSession session){

//		String fileName = file.getOriginalFilename();

        int result = 0;

        try {
            result = courseService.addZjml(file);
        } catch (Exception e) {

            e.printStackTrace();
        }

        if(result > 0){
            return "excel文件数据导入成功！";
        }else{
            return "excel数据导入失败！";
        }
    }

    /**
     * 导出章节目录excel
     * @param courseNo
     * @param response
     */
    @RequestMapping(value = "/export/goods/{courseNo}",method = RequestMethod.GET)
    public void goodsExcel(@PathVariable("courseNo") String courseNo,HttpServletResponse response){

        List<Course> courseList = new ArrayList<Course>();
        courseList=courseService.loadSortCourses(courseNo);
        XSSFWorkbook wb = courseService.show(courseList);
        String fileName = "章节目录.xlsx";
        OutputStream outputStream =null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/teacher/uploadvideo")
    public void uploadVideo(@RequestParam("file") MultipartFile uploadFile,String opid,HttpServletRequest request) throws IllegalStateException, IOException {
        System.out.println(opid);
        //获取文件初始名称
        String originalFileName = uploadFile.getOriginalFilename();

        String houzhui = originalFileName.substring(originalFileName.lastIndexOf("."));

        //上传文件
        String newFileName = UUID.randomUUID()+houzhui;
        File newFile = new File("videoUrl",newFileName);
        uploadFile.transferTo(newFile);
    }

}
