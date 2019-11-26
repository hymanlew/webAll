package com.youlu.samples.spring.mybatis.service;

import com.hyman.entity.User;
import com.hyman.entity.User2;
import com.hyman.service.UserService;
import com.hyman.util.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import java.time.LocalDateTime;


public class UserServiceTest2 extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    UserService userService;


    @Test
    @Rollback(false)
    public void testRegister(){
        User2 userInfo = new User2();
        userInfo.setHobby("编程");
        userInfo.setRealName("任帅鹏");
        userInfo.setGmtCreate(LocalDateTime.now());

        User user = new User();
        user.setName("test101");
        user.setPassword("test");
        userService.save(user);
    }

}
