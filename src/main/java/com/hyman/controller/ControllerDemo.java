package com.hyman.controller;

import com.hyman.entity.PropSet;
import com.hyman.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class ControllerDemo {

    //@Autowired
    @Resource(name= "service")
    private DemoService service;


    /**
     * autowired 与resource 区别：
     *   @Autowired：是默认按照类型装配的，默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false，如：
     *   @Autowired(required=false) ，如果我们想使用名称装配可以结合 @Qualifier注解进行使用，如下：@Autowired() @Qualifier("baseDao")；
     *
     *   @Resource：默认是按照名称byName 自动装配的，当找不到与名称匹配的 bean时才按照类型进行装配。但是需要注意的是，如果name属性一旦指定，就
     *   只会按照名称进行装配。
     *
     *   byName： 通过参数名 自动装配，如果一个bean的name 和另外一个bean的 property 相同，就自动装配。
     *   byType： 通过参数的数据类型自动自动装配，如果一个bean的数据类型和另外一个bean的property属性的数据类型兼容，就自动装配
     *
     *   我们可以通过 @Autowired 或 @Resource 在 Bean 类中使用自动注入功能，但是 Bean 还是在 XML 文件中通过 <bean> 进行定义 —— 也就是说，
     *   在 XML 配置文件中定义 Bean，通过@Autowired 或 @Resource 为 Bean 的成员变量、方法入参或构造函数入参提供自动注入的功能。
     *
     */

    @RequestMapping("/test")
    public String takephoto(){
        return "test";
    }

    @RequestMapping("/login")
    @ResponseBody
    public PropSet login(String username, String password){
        String result = service.login(username, password);
        PropSet propSet = new PropSet();
        if(result!="" && result!=null){
            propSet.setObj1(1);
        }else {
            propSet.setObj1(0);
        }
        return propSet;
    }

    //@RequestMapping("/task")
    //@ResponseBody
    //public String testTask(){
    //    // 不能直接返回 string 类型，前端会接收不到
    //    return "task";
    //}

    @RequestMapping("/takephotos")
    public String takephotos(){
        return "mainFace";
    }

    @RequestMapping("/others")
    public String others(HttpServletRequest request){
        request.setAttribute("user","USER");
        return "other";
    }

    @RequestMapping("/checkXML_allow")
    @ResponseBody
    public void checkXML(){
        service.checkXML();
        System.out.println("=== checkXML ===");
    }
}
