package spring.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import spring.entity.User;

//在 MyBatis 中的 UserDao 接口内的方法必须是唯一的，不允许重载
public interface IUserDao {

	/**
	 *    将 user数据保存到数据库中，返回更新的个数
	 * @param user
	 * @return
	 */
	Integer insertUser(User user);
	
	/**
	 * 	查询所有的用户
	 * @return
	 */
	List<User> findAllUser();
	
	/**
	 * 	添加更新方法，更新用户信息
	 * @param user
	 * @return	返回更新的行数
	 */
	public Integer updateUser(User user);
	
	public User findUserById(Integer id);
	
	Integer deleteUserById(Integer id);
	
	/**
	 * 	多参数查询，要使用注解 @Param 绑定参数名，在 SQL中要
	 * 	使用参数名来绑定参数 #{salary}
	 * @param salary
	 * @param name
	 * @return
	 */
	List<User> findUserByParam(
			@Param("salary") Integer salary,
			@Param("name") String name);
	
	/**
	 * 	使用 map 封装查询结果
	 * 	用于只查询一部分列信息时，能够节省流量，例如只获取 id,name
	 */
	List<Map<String,Object>> findUserByMap();
	
	/**
	 * 	动态拼接 SQL 语句
	 * @return
	 */
	List<User> findUsers(
			@Param("where") String where,
			@Param("param") Object param
			);


	List<User> findAllUsers(
			@Param("start") int statr,
			@Param("size") int size);

	int countUsers();

	Integer insertUser(User user);
}





