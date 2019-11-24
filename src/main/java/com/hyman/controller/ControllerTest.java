package com.hyman.controller;

import com.hyman.entity.User;
import com.hyman.util.ExportInputExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/do")
public class ControllerTest {

    //MappingJackson2HttpMessageConverter
    //RequestMappingHandlerAdapter
    //CommonsMultipartResolver
    //ContextLoaderListener
    //SqlSessionTemplate

    // 导出文件时不需要此注解 @ResponseBody
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){

        ExportInputExcel excel = new ExportInputExcel();
        // 导出一张表
        //excel.exportExcel(response,0);
        // 导出多张表
        excel.exportExcel(response,1);
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public String importExcel(HttpServletRequest request,
            @RequestParam(value = "hidd",required = false) List<MultipartFile> file){

        /**
         * ServletRequest的getServletContext方法是Servlet3.0添加的，本地用的是Tomcat9.0，版本比较高，支持request
         * .getServletContext()这种写法，而线上是Tomcat6.0，只支持到Servlet2.5，需要先用request拿到HttpSession或者
         * 通过Servlet自身拿到ServletConfig之后再获取ServletContext。
         *
         * String path = request.getServletContext().getRealPath("uploadFiles");
         */
        String path = request.getSession().getServletContext().getRealPath("uploadFiles");
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        try {
            // name是拿到文件框属性的名字，而 realname才是文件名
            String name = file.get(0).getName();
            String realname = file.get(0).getOriginalFilename();

            File file1 = new File(path+File.separator+realname);
            file.get(0).transferTo(file1);
            return "OK";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @RequestMapping("/importAndShow")
    @ResponseBody
    public Map<String,List<List<String>>> importAndShow(HttpServletRequest request,
                                            @RequestParam(value = "file",required = false) MultipartFile file){

        Map<String,List<List<String>>> map = null;
        try {
            String name = file.getOriginalFilename();
            String path = request.getSession().getServletContext().getRealPath("uploadFiles")+File.separator+name;
            //path = path.replace("","");

            ExportInputExcel excel = new ExportInputExcel();
            List<List<String>> data = excel.importExcel(path);
            map = new HashMap<>();
            map.put("list",data);

        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/manyImages")
    @ResponseBody
    public Map<String,List<String>> uploadManyPic(@RequestParam(value="manyImages",required = false) MultipartFile[] files, HttpServletRequest request){

        Map<String, List<String>> obj = new HashMap<>();
        if(files!=null && files.length>0){
            List<String> list = new ArrayList<>();
            for(int i=0;i<files.length;i++){
                String name = files[i].getOriginalFilename();
                list.add(name);
            }
            obj.put("list",list);
        }

        return obj;
    }


    @RequestMapping("/takejson")
    @ResponseBody
    public User takephoto(){
        User usr = new User();
        usr.setName("lili");
        usr.setPassword("123");

        // 没有重写
        String line = usr.toString();
        return usr;
    }

    // 如果要将字符串以 json 的格式返回，则需要将 jsp 的 ajax dataType设置为 text，否则会直接运行 error函数。
    // 因为直接返回的字符串，在 ajax接收时它是不符合 json规范的，即 success是不带引号的，所以不能运行成功函数。
    public String takejson(){
        return "success";
    }
}
