package com.soft.xuehu.controller;

import com.soft.xuehu.entity.Users;
import com.soft.xuehu.util.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Create By majianxin on 2020/5/31.
 */
@Controller
@RequestMapping("/bbs")
public class BbsController {
    @GetMapping("list")
    public String list(Map<String,Object> map){

        return "bbsList";
    }

}
