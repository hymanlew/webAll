package test;

import com.hyman.dao.IUserDao;
import com.hyman.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserDaoTest {

	ClassPathXmlApplicationContext ctx;
	IUserDao dao;
	
	// 在所有的案例都执行之前，执行 @Before 注解的方法。
	@Before
	public void init() {
		
		ctx = new ClassPathXmlApplicationContext("config/spring-db.xml",
				"config/spring-mybatis.xml");
		dao = ctx.getBean("userDao",IUserDao.class);
		
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
