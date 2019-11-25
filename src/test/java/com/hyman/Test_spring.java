package test;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.dao.IUserDao;
import spring.entity.User;

public class Test_spring {

	ClassPathXmlApplicationContext act;
	
	@Before
	public void init() {
		
		act = new ClassPathXmlApplicationContext("ctx.xml");
	}
	
	@Test
	public void testfactory() {
		
		// 测试由 spring 管理的 SqlSessionFactory 对象，
		SqlSessionFactory factory = act.getBean("factory",SqlSessionFactory.class);
		System.out.println(factory);
	}
	
	@Test
	public void testIUserdao() {
		
//		IUserDao 接口内的方法必须是唯一的，不允许重载，
//			并且当类名前两个字母都是大写时，application 的 getbean 的 id 不会转换为小写。
//		IUserDao dao = act.getBean("iUserDao",IUserDao.class);
		IUserDao dao = act.getBean("IUserDao",IUserDao.class);
		System.out.println(dao);
		List<User> list = dao.findAllUser();
		for (User user : list) {
			System.out.println(user);
		}
		
	}
}





