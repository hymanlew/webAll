package com.hyman;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {

	ClassPathXmlApplicationContext act;
	
	
	// junit 包项目是独立于 web 容器的，即它可以独立运行一套系统而不依赖于容器，
	//  也称为离线测试，就是为了方便测试系统的单元功能。
	
	//  所以在测试时，要注意 xml 配置文件中的类、文件是否存在，或者是否依赖于某些
	//  容器的部分。
	@Before
	public void init() {
		
		act = new ClassPathXmlApplicationContext("config/spring-db.xml",
				"config/spring-mybatis.xml");
		
	}
	
	@Test
	public void testSqlsession() {
		
		SqlSessionFactory factory = act.getBean("factory",SqlSessionFactory.class);
		SqlSession session = factory.openSession();
		System.out.println(session);
		session.close();
	}
}
