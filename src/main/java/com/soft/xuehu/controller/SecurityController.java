package com.soft.xuehu.controller;

import com.soft.xuehu.entity.Users;
import com.soft.xuehu.listener.MySessionListener;
import com.soft.xuehu.service.UsersService;
import com.soft.xuehu.util.Login;
import com.sun.org.apache.xalan.internal.xsltc.dom.StepIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


/**
 * Create By majianxin on 2020/5/10.
 */
@Controller
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private UsersService usersService;


    @GetMapping("toLogin")
    public String toLogin(Map<String,Object> map){
        map.put("users",new Users());
        map.put("flag",0);
        map.put("login",new Login());
        return "login";
    }

    @PostMapping(value = "login")
    public String login(Login login, Map<String,Object>map, HttpServletRequest request){
        if(login.getTorF() == 0)
        {
            map.put("users",new Users());
            map.put("flag",0);
            map.put("login",login);
            return "login";
        }
        if(usersService.get(login.getUid())!=null){
            Users users1=usersService.get(login.getUid());
            if(users1.getPsw().equals(login.getPsw())){
                map.put("users",users1);
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("uid", login.getUid());
                AtomicInteger userCount = MySessionListener.userCount;
                map.put("userCount",userCount);
                if(users1.getRole()==2){
                    return "admin/Index";
                }
                return "main";
            }
            else{
                map.put("users",new Users());
                map.put("flag",5);
                map.put("login",login);
                return "login";
            }
        }
        if(usersService.get(login.getUid())==null)
        {
            map.put("flag",4);
        }
        map.put("users",new Users());
        map.put("login",login);
        return "login";
    }

    @GetMapping(value = "login1")
    public String login1(@RequestParam String uid, Map<String,Object>map, HttpServletRequest request){
            Users users1=usersService.get(uid);
                map.put("users",users1);
                HttpSession httpSession = request.getSession(true);
                httpSession.setAttribute("uid", uid);
                AtomicInteger userCount = MySessionListener.userCount;
                map.put("userCount",userCount);
                System.out.println(userCount);
                return "main";
    }

    @RequestMapping(value = "main/{userCount}")
    public String main(@PathVariable("userCount")String userCount,Map<String,Object>map){
        map.put("userCount",userCount);
        return "index1";
    }

    @GetMapping("/logout/{uid}")
    public String logout(@PathVariable("uid")String uid,HttpServletRequest request){
        HttpSession httpSession = request.getSession(true);
        httpSession.removeAttribute(uid);
        httpSession.invalidate();
        AtomicInteger userCount = MySessionListener.userCount;
        return "redirect:/security/toLogin";
    }


    @PostMapping(value="/zhuce")
    public String zhuce(Users users, Map<String, Object> map) {
        /*    0:无事发生
            1：账号重复注册
            2：用户名已注册！
            3:注册成功！
            4:账号未注册！
            5：密码错误！
        */
        if (usersService.get(users.getUid()) != null)
        {
            String flag="账号重复注册！";
            map.put("flag",1);
            map.put("login",new Login());
            return "login";
        }
        else if(usersService.selectByUname(users.getUname()) != null){
            String flag="用户名已注册！";
            map.put("flag",2);
            map.put("login",new Login());
            return "login";
        }
        else{
            String flag="注册成功！";
            map.put("flag",3);
            usersService.addUsers(users);
            map.put("login",new Login());
            return "login";
        }
    }

}
