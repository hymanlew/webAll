package com.hyman.service;

import com.hyman.dao.IUserDao;
import com.hyman.dao.UserDynamicSqlSupport;
import com.hyman.dao.UserInfoMapper;
import com.hyman.dao.UserMapper;
import com.hyman.entity.User;
import com.hyman.entity.User2;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.hyman.dao.UserDynamicSqlSupport.*;
import static com.hyman.dao.UserInfoDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

// 当前类是业务层组件
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService {

	// 因为注解写到接口上没用，所以当前默认是 userDao
	@Resource
	private IUserDao dao;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserInfoMapper userInfoMapper;

	@Override
	public List<User> list(Integer page) {
		
		if(page==null) {
			page=1;
		}
		int size = 4;
		int start = (page-1)*size;
		
		// 调用数据层处理业务
		return dao.findAllUsers(start,size);
	}

	@Override
	public int listPages() {
		
		int rows = dao.countUsers();
		int size = 4;
		int pages = rows/size;
		if(rows%size!=0) {
			pages++;
		}
		return pages;
	}
	
	@Override
	public Integer save(User user) {
		
		int n = dao.insertUser(user);
		return n;
	}


	public void register(User2 userInfo, User user){
		userInfoMapper.insertSelective(userInfo);
		//保存
		userMapper.insertSelective(user);
	}

	/**
	 * 普通查询
	 * @param usernameP
	 * @return
	 */
	public List<User> findByUsername(String usernameP){

		return userMapper.selectByExample().where(substring(username,1,1), isEqualTo(usernameP))
				.build().execute();
	}

	/**
	 * 动态查询 http://www.mybatis.org/mybatis-dynamic-sql/docs/whereClauses.html
	 * @param user
	 * @return
	 */
	public List<User> findByUser(User user){
		return userMapper.selectByExample().where(username,isEqualToWhenPresent(user.getName()))
				.and(password,isGreaterThanOrEqualToWhenPresent(user.getPassword()))
				.build().execute();
	}


	/**
	 * 构建动态SQL 关联查询
	 * @param userP
	 * @return
	 */
	public List<Map<String,Object>> findList(User userP){
		SelectStatementProvider selectStatement = select(count(duser.id),duser.id, duser.username)
				.from(duser, "u")
				.join(duser2, "ui").on(duser.id, equalTo(duser2.id))

				.where(username,isEqualToWhenPresent(userP.getName()))
				//.and(UserDynamicSqlSupport.gmtCreate,isGreaterThanOrEqualToWhenPresent(userP.getGmtCreate()))
				.build()
				.render(RenderingStrategy.MYBATIS3);

		return userMapper.selectManyCustom(selectStatement);
	}
}
