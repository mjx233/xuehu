package com.soft.xuehu.controller;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.service.CourseService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class MyController {
    @Autowired
    CourseService courseService;

    @RequestMapping("/uploadFile")
    public String uploadFile(@RequestParam Integer cid, @RequestParam String uid, RedirectAttributes attr, HttpServletRequest httpServletRequest, Map<String, Object> map){
        /* 1、设置文件到本地的文件夹位置 */
        String realPath=null;
        try {
            /* 这里获得的路径是项目的target/classes/upload*/
            realPath= ResourceUtils.getURL("classpath:").getPath()+"/upload";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /* 如果文件夹不存在，则创建该文件夹 */
        File dir=new File(realPath);
        if(!dir.isDirectory()){
            dir.mkdirs();
        }
        /* 2、获取上传的文件名为uploadFile的list，这个文件名是upload页面上input的name */
        List<MultipartFile> uploadFiles=((MultipartHttpServletRequest)httpServletRequest).getFiles("uploadFile");
        /* 3、开始将文件移动到目标路径下 */
        try {
            for(MultipartFile uploadFile:uploadFiles) {
                String filename = uploadFile.getOriginalFilename();

                //将文件名保存到数据库中
                Course course = courseService.selectMl(cid);
                course.setVideo(filename);
                courseService.updateCourse(course);

                File fileServer = new File(dir, filename);
                uploadFile.transferTo(fileServer);
            }
            map.put("message","上传成功");
            attr.addAttribute("uid",uid);
            attr.addAttribute("cid",cid);
            return "redirect:/course/video";
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("message","上传失败");
        attr.addAttribute("uid",uid);
        attr.addAttribute("cid",cid);
        return "redirect:/course/video";
    }
}
