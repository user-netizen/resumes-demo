package com.fxd.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 设置 前端访问路由
 */
@Controller
@RequestMapping(value = "home",method = RequestMethod.GET)
public class GeneralController {

    @RequestMapping(value = "/test")
    public String Test(){
        System.out.println("个人用户：/test");
        return "index";
    }

    @RequestMapping(value = "/index")
    public String ResumesHomeList(){
        System.out.println("个人用户：person/index");
        return "person/index";
    }

    @RequestMapping(value = "/user")
    public String ResumesUser(){
        System.out.println("个人用户：person/user");
        return "person/user";
    }

    @RequestMapping(value = "/add")
    public String ResumesAddEdit(){
        System.out.println("个人用户：person/addedit");
        return "person/addedit";
    }

    @RequestMapping(value = "/show")
    public String ResumesShow(){
        System.out.println("个人用户：person/show");
        return "person/show";
    }

    @RequestMapping(value = "/login")
    public String ResumesLogin(){
        System.out.println("个人用户：person/login");
        return "person/login";
    }
}
