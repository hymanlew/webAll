package com.hyman.controller;

import com.hyman.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @Resource(name="service")
    private DemoService service;

    //@RequestMapping(value = "/login",method = RequestMethod.GET)
    //public String login(@RequestParam(value = "error",required = false) String error){
    //
    //    if (error != null) {
    //        return "login_fail";
    //    }
    //    // 这一行是不运行的，只会根据 xml配置文件运行，并且也只会根据配置文件。
    //    return "";
    //}
    //
    //@RequestMapping(value={"/welcome","/"},method= RequestMethod.GET)
    //public String welcome(){
    //    return "index";
    //}

    @RequestMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error){

        if (error != null) {
            /**
             * 如果登录失败后，要指向登录页面，则必须是重定向的，否则会使用框架默认的路径 login 从而在之后的
             * 操作出异常。 return "redirect:/index";
             *
             * 转发的话，也是不可以的，它还是会用框架的路径。格式为： return "forward:/index";
             */
            return "login_fail";
        }
        // 这一行是不运行的，只会根据 xml配置文件指定的首页运行，并且也只会根据配置文件。只用于测试 post请求。
        return "index";
    }

    @RequestMapping(value={"/welcome","/"})
    public String welcome(){
        return "index";
    }

    // 如果框架中已经设置了 logout属性并且 logout-url与这个地址相同，则不会运行此方法，而由框架自动退出到登录页面
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession session){
        // redirect：只适用于重定向到 controller
        // return "redirect:../logout";

        session.invalidate();

        // 此方法只是移除 session绑定的指定的对象，而其他绑定的对象还会有
        //session.removeAttribute("username");
        return "logout";
    }


    @RequestMapping("/test_allow")
    public String allow(){
        return "test";
    }
}
