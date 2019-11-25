package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.dao.UserDao;
import spring.entity.User;

public class UserDaoTest {

	ClassPathXmlApplicationContext ctx;
	UserDao dao;
	
	// 在所有的案例都执行之前，执行 @Before 注解的方法。
	@Before
	public void init() {
		
		ctx = new ClassPathXmlApplicationContext("config/spring-db.xml",
				"config/spring-mybatis.xml");
		dao = ctx.getBean("userDao",UserDao.class);
		
	}
	
	// 当全部案例都执行结束之后，才执行 @After 注解的方法。
	@After
	public void destory() {
		
		ctx.close();
	}
	
	@Test
	public void testFind() {
		
		List<User> list = dao.findAllUsers(4,4);
		for (User user : list) {
			System.out.println(user);
		}
	}
}
