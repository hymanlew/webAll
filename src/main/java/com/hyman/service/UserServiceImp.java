package com.hyman.service;

import java.util.List;

import javax.annotation.Resource;

import com.hyman.dao.IUserDao;
import com.hyman.dao.UserInfoMapper;
import com.hyman.dao.UserMapper;
import com.hyman.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")  // 当前类是业务层组件
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService {

	// 因为注解写到接口上没用，所以当前默认是 userDao
	@Resource
	private IUserDao dao;

	@Autowired
	UserMapper userMapper;

	@Autowired
	UserInfoMapper userInfoMapper;
	
	public List<User> list(Integer page) {
		
		if(page==null) {
			page=1;
		}
		int size = 4;
		int start = (page-1)*size;
		
		// 调用数据层处理业务
		return dao.findAllUsers(start,size);
	}

	public int listPages() {
		
		int rows = dao.countUsers();
		int size = 4;
		int pages = rows/size;
		if(rows%size!=0) {
			pages++;
		}
		return pages;
	}
	

	public Integer save(User user) {
		
		int n = dao.insertUser(user);
		return n;
	}

}
