package com.soft.xuehu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.soft.xuehu.entity.*;
import com.soft.xuehu.service.CourseService;
import com.soft.xuehu.service.CusersService;
import com.soft.xuehu.service.UsersService;
import com.soft.xuehu.util.Login;
import com.soft.xuehu.util.UploadUsers;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * Create By majianxin on 2020/5/30.
 */
@Controller
@RequestMapping("/users" +
        "")
public class
UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    CourseService courseService;

    @Autowired
    CusersService cusersService;

    @RequestMapping("/preupdate/{uid}")
    public String preupdate(@PathVariable("uid")String uid, Map<String, Object> map) {

        Users users = usersService.get(uid);

        map.put("users",users);
        return "users/update_users";
    }

    @PostMapping(value="/update/{uid}")
    public String Update(@RequestParam("pic1") MultipartFile file, Users users, RedirectAttributes attr, @PathVariable("uid")String uid , Map<String, Object> map) throws Exception{

        //读取多段提交的文件数据，转成字节数组
        if(file.getBytes().length>0){
            users.setPic(file.getBytes());
        }
        else
        {
            users.setPic(usersService.getPic(users.getUid()));
        }
        try{
            usersService.update(users);
            List<Course> courseList = courseService.loadByUid(uid);
            List<Cusers> cusersList = cusersService.loadAll(uid);

            if(!courseList.isEmpty()){
                for(int i=0;i<courseList.size();i++)
                {
                    Course course = courseList.get(i);
                    course.setTeacher(users.getUname());
                    courseService.updateCourse(courseList.get(i));
                }
            }
            if(!cusersList.isEmpty()){
                for(int i=0;i<cusersList.size();i++)
                {
                    Cusers cusers = cusersList.get(i);
                    Users users1 = new Users();
                    users1.setUid(uid);
                    users1.setUname(users.getUname());
                    users1.setRole(users.getRole());
                    cusers.setUsers(users1);
                    cusersService.update(cusers);
                }
            }
        }catch(Exception e){
            map.put("exceptionMessage", e.getMessage());
            map.put("uid",uid);
            System.out.println(e.getMessage());
            return "/users/update_users";
        }

        attr.addAttribute("uid",users.getUid());
        return "redirect:/security/login1";
    }


    @GetMapping("/getPic/{uid}")
    public String getPic(@PathVariable("uid") String uid, HttpServletRequest request, HttpServletResponse response) throws Exception{

        byte[] pic = usersService.getPic(uid);

        if(pic==null){
            String path = request.getSession().getServletContext().getRealPath("/img/touxiang.jpg");
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

    @RequestMapping("/listAll")  //当前台界面调用Controller处理数据时候告诉控制器怎么操作
    //作用：URL映射。
    public String list(@RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr,@RequestParam(value="message", required=false, defaultValue="") String message, Map<String, Object> map) {


        int pageNo = 1;

        //对 pageNo 的校验
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }

        PageHelper.startPage(pageNo, 5);

        List<Users> usersList = usersService.selectAll();

        PageInfo<Users> page=new PageInfo<Users>(usersList);

        map.put("page", page);
        map.put("message",message);


        return "admin/list_users";
    }

    @RequestMapping("/remove/{uid}")
    public String Sremove(@PathVariable("uid") String uid, RedirectAttributes attr,Map<String, Object> map) {

        Users users = usersService.get(uid);

        //删除用户
        usersService.remove(uid);

        if(users.getRole()==0)
        {
            cusersService.remove(uid);
        }
        else{
            //找到教师所教课程
            List<Course> courseList= courseService.loadByUid(uid);
            //删掉这些课程记录
            if(!courseList.isEmpty()){
                for (int i=0;i<courseList.size();i++){
                    Course course = courseList.get(i);
                    System.out.println("sds说的都是"+course.getCourseNo());
                    cusersService.removeByCourseNo(course.getCourseNo());
                }
            }
            courseService.remove(uid);
        }

        return "redirect:/users/listAll";
    }

    /**
     * 导入excel
     */
    @RequestMapping("/import")
    public String excelImport(@RequestParam(value="filename") MultipartFile file,RedirectAttributes attr){

//		String fileName = file.getOriginalFilename();
        UploadUsers uploadUsers = new UploadUsers();
        try {
           uploadUsers  = usersService.add(file);
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        attr.addAttribute("message","成功导入用户："+uploadUsers.getResult()+"个，导入失败的有："+uploadUsers.getFail()+"个\n" +
                "其中，账号id重复导致错误的为以下这些id："+uploadUsers.getUid()+"\n" +
                "其中，账号名重复导致错误的为以下这些账号名："+uploadUsers.getUname());

        return "redirect:/users/listAll";
    }

    /**
     * 导出用户信息
     * @param response
     */
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void goodsExcel(HttpServletResponse response){

        List<Users> usersList = usersService.selectAll();

        XSSFWorkbook wb = usersService.show(usersList);
        String fileName = "用户.xlsx";
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


}
