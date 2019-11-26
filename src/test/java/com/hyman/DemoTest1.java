package test;

import com.hyman.dao.IUserDao;
import com.hyman.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoTest1 {

	SqlSessionFactory factory;
	
	@Before
	public void init() throws IOException {
		
		// 初始化 SqlSessionFactory
		String path ="config.xml";
		
		// Resources 是 MyBatis提供的 API，SqlSessionFactory 用于创建 SqlSession对象。
		InputStream in = Resources.getResourceAsStream(path);
		// 从 classpath 中读取配置文件 config.xml 为输入流
		factory = new SqlSessionFactoryBuilder().build(in);
	}

	@Test
	public void insert() {
		
		// 测试利用 Mybatis 向数据库中插入数据
		// 打开 session
		SqlSession session = factory.openSession();
		
		// 利用 session 自动创建 IUserDao 的实例
		IUserDao dao = session.getMapper(IUserDao.class);
		System.out.println(dao.getClass());
		
		User user = new User();
		user.setName("lami");
		user.setPassword("123");
		Integer n = dao.insertUser(user);
		System.out.println(n);
		session.commit();
		
		System.out.println();
		
		// 必须关闭 session
		session.close();
	}

	@Test
	public void testinsert2() {
		
		// 利用 sqlsession 直接执行 sql，不访问 DAO 接口、
		// 但与访问 DAO 接口的结果完全一样。
		
		// sqlsession 提供了两种执行 sql 的功能（上面的是第一种，并且第一种方法的
		//	  底层运行原理就是下面的第二种)。
		SqlSession session = factory.openSession();
		
		// session.insert(配置文件中的SQL ID,参数对象)
		User user = new User();
		user.setName("mili");
		user.setPassword("123");
		System.out.println(user); // 返回自动生成的ID
		Integer n = session.insert("insertUser", user);
		System.out.println(user); // 返回自动生成的ID
		System.out.println(n);
		session.commit();
		
		session.close();
	}
	
	@Test
	public void find() {
		
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<User> list = dao.findAllUser();
		for (User user : list) {
			System.out.println(user);
		}
		session.close();
		
	}

	@Test
	public void update() {
		
		/**
		 * 1，查询出用户信息
		 * 2，更改用户信息
		 */
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		User user = dao.findUserById(1);
		System.out.println(user);
		user.setName("fanfan");
		int n = dao.updateUser(user);
		System.out.println(n);
		session.commit();
		
		session.close();
	}
	
	@Test
	public void delete() {
		
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		Integer id = 3;
		Integer in = dao.deleteUserById(id);
		System.out.println(in);
		session.commit();
		session.close();
	}
	
	@Test
	public void findbymanyParam() {
		
		String name = "%a%";
		Integer salary = 7000;
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		
		// 使用 DAO 接口方法执行的底层原理就是利用的，
		//	return	session.selectList("findbymanyParam", params);方法，然后
		//		经过 sqlsession --> JDBC --> 数据库
		List<User> list = dao.findUserByParam(salary, name);
		for (User user : list) {
			System.out.println(user);
		}
		session.close();
	}
	
	@Test
	public void findbymanyParam2() {
		
		String name = "%a%";
		Integer salary = 7000;
		SqlSession session = factory.openSession();
		
		// 将多个参数打包到 map 中，传递到 selectList 方法中执行
		// map 中的 key 值就是传的变量名，value值 才是真正的数据库数据
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", name);
		params.put("salary", salary);
		
		// 第一个参数名字，就是指向了 Userdao 接口内的方法名，而不是本类中的方法名
		List<User> list = session.selectList("findbymanyParam", params);
		for (User user : list) {
			System.out.println(user);
		}
		session.close();
	}
	
	@Test
	public void findbymap() {
		
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<Map<String,Object>> list = dao.findUserByMap();
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
		session.close();
	}
	
	@Test
	public void findUsers() {
		
		String where ="name like ";
		String param ="%an%";
		SqlSession session = factory.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<User> list = dao.findUsers(where, param);
		for (User user : list) {
			System.out.println(user);
		}
		
		session.close();
	}
		
}



