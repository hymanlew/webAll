package com.youlu.samples.spring.mybatis.service;


import com.youlu.samples.spring.mybatis.AppConfig;
import com.youlu.samples.spring.mybatis.entity.User;
import com.youlu.samples.spring.mybatis.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDateTime;


@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserService userService;


    @Test
    @Rollback(false)
    public void testRegister(){
        UserInfo userInfo = new UserInfo();
        userInfo.setHobby("编程");
        userInfo.setRealName("任帅鹏");
        userInfo.setGmtCreate(LocalDateTime.now());

        User user = new User();
        user.setGmtCreate(LocalDateTime.now());
        user.setUsername("test101");
        user.setPassword("test");

        userService.register(userInfo,user);
    }

}
