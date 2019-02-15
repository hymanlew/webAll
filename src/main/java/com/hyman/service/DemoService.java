package com.hyman.service;

import com.hyman.dao.TestDao;
import com.hyman.entity.PropSet;
import com.hyman.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("service")
public class DemoService{

    @Resource
    private TestDao testDao;

    public String login(String username,String password){
        PropSet propSet = new PropSet();
        propSet.setObj1(username);
        propSet.setObj2(password);
        User user = testDao.login(propSet);
        if(user!=null){
            String name = user.getName();
            return name;
        }else{
            return "";
        }
    }

    public User getUser(String username) {
        PropSet propSet = new PropSet();
        propSet.setObj1(username);
        return testDao.getUser(propSet);
    }

    public void checkXML(){
        testDao.checkXML();
    }
}
