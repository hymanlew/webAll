package com.hyman;

import com.hyman.entity.User;
import com.hyman.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserserviceTest {

	ClassPathXmlApplicationContext ctx;
	UserService service;
	
	@Before
	public void init() {
		
		ctx = new ClassPathXmlApplicationContext("config/spring-service.xml",
				"config/spring-db.xml","config/spring-mybatis.xml");
		
		service = ctx.getBean("userService",UserService.class);
	}
	
	@After
	public void destory() {
		
		ctx.close();
	}
	
	@Test
	public void testlist() {
		
		List<User> list = service.list(null);
		for (User user : list) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testcout() {
		
		int n = service.listPages();
		System.out.println(n);
	}
	
	@Test
	public void testinsert() {
		User user = new User();
		user.setName("wulu");
		user.setPassword("333");
		int n = service.save(user);
		System.out.println(n);
	}
	
}
