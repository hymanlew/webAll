package com.youlu.samples.spring.mybatis.mapper;

import com.youlu.samples.spring.mybatis.AppConfig;
import com.youlu.samples.spring.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static com.youlu.samples.spring.mybatis.mapper.UserDynamicSqlSupport.username;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@ContextConfiguration(classes = AppConfig.class)
public class UserMapperTest extends AbstractTestNGSpringContextTests{

    /**
     *
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;


    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("admin1");
        user.setPassword("x123");
        user.setGmtCreate(LocalDateTime.now());
        assertTrue(userMapper.insertSelective(user) > 0);
    }
//
//    @Test
    public void testFindUser(){
        assertNotNull(userMapper.selectByExample()
                .where(username,isEqualTo("admin")));
    }
//
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setPassword("x1235");
        user.setGmtModified(LocalDateTime.now());
        assertTrue(userMapper.updateByExampleSelective(user).where(username,isEqualTo("admin")).build().execute().intValue() > 0);
    }
}
