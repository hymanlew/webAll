package spring.controller;

import com.hyman.entity.User;
import com.hyman.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *  控制器，用于处理用户相关的业务功能
 * @author soft01
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService service;
	
	@RequestMapping("/users.do")
	public String users(
			ModelMap map,
			@RequestParam(required=false,
			value="page") Integer page) {
		
		//访问业务层获取全部用户信息
		List<User> list = service.list(page);
		map.put("users", list);
		
		int pages = service.listPages();
		map.put("pages", pages);
		
		System.out.println(list);
		//转发到 JSP 页面
		return "user/list";
	}
	
	@RequestMapping("/useradd.do")
	public String add(ModelMap map) {
		
		return "user/add";		
	}
	
	@RequestMapping("/usersave.do")
	public void save(User user,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		int n = service.save(user);
		System.out.println(n);
		
		String path = request.getContextPath()+"/user/users.do";
		response.sendRedirect(path);
	}
}
