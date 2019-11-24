package com.hyman.service;

import com.hyman.entity.User;

import java.util.List;


public interface UserService {

	List<User> list(Integer page);
	
	int listPages();
	
	Integer save(User user); 
}
