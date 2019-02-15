package com.hyman.security;

import com.hyman.dao.DemoDao;
import com.hyman.entity.*;
import com.hyman.service.DemoService;
import com.hyman.service.RoleService;
import com.hyman.util.UserInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 这个类负责的是只是获取登陆用户的详细信息（包括密码、角色等），不负责和前端传过来的密码对比，只需返回User对象，后会有其他类
 * 根据User对象对比密码的正确性（框架帮我们做）。
 */
public class MyUserDetailService implements UserDetailsService{

    @Resource(name = "service")
    private DemoService service;

    @Resource
    private RoleService roleService;

    @Resource
    private transient SqlSessionTemplate sqlSessionTemplate;

    @Resource(name = "supperdao")
    private DemoDao dao;

    /**
     * 登陆验证时，通过 username获取用户的所有权限信息，并返回 User放到 spring的全局缓存 SecurityContextHolder 中，
     * 以供授权器（accessDecisionManager）使用。
     *
     * 通过 MyUserDetailService 拿到用户信息后，authenticationManager对比用户的密码（即验证用户），然后这个 Authentication
     * ProcessingFilter 拦截器就过咯。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * grantedauthority ：权限, 角色；
         * 它是一个接口，表示授予身份验证对象的权限。
         *
         * 该集合表示用户的权限集合
         */
        Collection<GrantedAuthority> auths = new ArrayList<>();

        // 用户相关的所有权限信息
        UserInfo uinfo = null;

        User user = service.getUser(username);
        if(user == null){
            throw new RuntimeException(MyAuthenticationFailureHandler.ACCOUNT_NO_EXIST);
        }

        List<String> roleIds = roleService.getRoleId();
        if(user !=null){
            if(roleIds!=null && roleIds.size()>0){
                for(String id:roleIds){
                    /**
                     * 获得 role的id，每次配置需要重新写
                     * 然后依据已得到的数据，和用户权限关系，去一级级查找比对拿到自定义的权限的名字（已保存到数据库中）
                     *
                     * 以下代码是模拟的 demo，不能执行
                     */
                    Integer roleId = Integer.valueOf(id);

                    // 获取权限名，查找数据库数据然后抽取。并且权限名必须是 ROLE_ 开头的，不然 spring security 是不认账的，
                    // 其实是 spring security里面做了一个判断，必须要ROLE_开头。
                    Role role = sqlSessionTemplate.selectOne("mapping.RoleMapper.selectByPrimaryKey",roleId);
                    String authName = role.getAuthority();

                    // 根据权限名创建权限
				    SimpleGrantedAuthority auth = new SimpleGrantedAuthority(authName);
                    // 把权限添加进权限集合
                    auths.add(auth);
                }
            }
        }

        String userName = "";
        if(user.getName()!=null){
            userName = user.getName();
        }
        uinfo = new UserInfo(user.getId(),userName, user.getPassword(),(ArrayList<GrantedAuthority>) auths,true, true,
                true, true);
        return uinfo;

        /**
         import org.springframework.security.core.userdetails.User;

         GrantedAuthorityImpl auth2=new GrantedAuthorityImpl("ROLE_ADMIN");
         GrantedAuthorityImpl auth1=new GrantedAuthorityImpl("ROLE_USER");

         if(user !=null){
         auths=new ArrayList<GrantedAuthority>();
         auths.add(auth1);
         auths.add(auth2);
         }

         User user = new User(username, "lcy", true, true, true, true, auths);
         return user;
         */

    }

    // 此方法是教程中的 demo案例，用于代替上面的方法。在本项目中没有使用
    public UserDetails loadUserByUsernameDemo(String username) throws UsernameNotFoundException, DataAccessException {

        Collection<GrantedAuthority> auths= new ArrayList<GrantedAuthority>();

        //获取角色标志
        String roletype = username.substring(0,3);
        username = username.substring(3);
        String password = "";

        if("stu".equals(roletype)){
            Student stu = dao.findBysId(username);
            password = stu.getPassword();
            auths.add(new SimpleGrantedAuthority("ROLE_STU"));
        }else if("tea".equals(roletype)){
            Teacher tea = dao.findBytId(username);
            password = tea.getPassword();
            auths.add(new SimpleGrantedAuthority("ROLE_TEA"));
        }else if("adm".equals(roletype)){
            Admin adm = dao.findByaId(username);
            password = adm.getPassword();
            auths.add(new SimpleGrantedAuthority("ROLE_ADM"));
        }

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(username, password, true, true, true, true, auths);
        return user;
    }
}
