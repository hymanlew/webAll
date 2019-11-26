package com.hyman;

import com.hyman.dao.UserDynamicSqlSupport;
import com.hyman.dao.UserInfoMapper;
import com.hyman.dao.UserMapper;
import com.hyman.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

public class UserMapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;


    @Test
    public void testInsertUser() {
        User user = new User();
        user.setName("admin1");
        user.setPassword("x123");
        Assert.assertTrue(userMapper.insertSelective(user) > 0);
    }

    //
//    @Test
    public void testFindUser() {
        Assert.assertNotNull(userMapper.selectByExample()
                .where(UserDynamicSqlSupport.username, isEqualTo("admin")));
    }

    //
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setPassword("x1235");
        Assert.assertTrue(userMapper.updateByExampleSelective(user).where(UserDynamicSqlSupport.username, isEqualTo("admin")).build().execute().intValue() > 0);
    }
}
