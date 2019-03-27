package com.hyman.securityDemo;

/**
 * 一个springSecurityFilterChain 的Servlet的过滤器，负责所有安全（例如保护应用程序的URL,验证提交的用户名和密码，重定向到登陆的表单等等）
 * 而其功能的实现是由其默认的配置实现的。
 *
 * 当我们在任意一个类上添加了一个注解 @EnableWebSecurity，就可以创建一个名为 springSecurityFilterChain 的Filter。
 * 需要注意的是，这个过滤器的创建是通过 @EnableWebSecurity完成的，与是否继承这个类无关。
 */

//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//   //@Autowired
//   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//       auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
//       auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
//   }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        /**
//         * 1、http.authorizeRequests()方法有很多子方法，每个子匹配器将会按照声明的顺序起作用。
//         * 2、指定用户可以访问的多个url模式。特别的，任何用户可以访问以"/resources"开头的url资源，或者等于"/signup"或about
//         * 3、任何以"/security"开头的请求限制用户具有 "ROLE_USER"角色。你可能已经注意的，尽管我们调用的hasRole方法，但是不用传入"ROLE_"前缀
//         * 4、任何以"/db"开头的请求同时要求用户具有"ROLE_ADMIN"和"ROLE_DBA"角色。
//         * 5、任何没有匹配上的其他的url请求，只需要用户被验证。
//         */
//       http
//               .authorizeRequests()
//                    .antMatchers("/login.jsp").permitAll()  //访问index.html不要权限验证
//                    .antMatchers( "/resources/**", "/signup" , "/about").permitAll()
//                    .antMatchers( "/security/**").hasRole("USER" )
//                    .antMatchers( "/db/**").access("hasRole('ADMIN') and hasRole('USER')")
//                    .anyRequest().authenticated()           //其他所有路径都需要权限校验
//               .and()
//                    .csrf().disable()                       //默认开启，这里先显式关闭
//               .formLogin()                                 //内部注册 UsernamePasswordAuthenticationFilter
//                    .loginPage("/login.jsp")
//                     // 该路径必须与登录页面方法保持一致（显示在浏览器上），因为它是 security 系统指定的。否则会 302 重定向，或 404.
//                    .loginProcessingUrl("/security/login")
//                    .passwordParameter("password")          //form表单用户名参数名
//                    .usernameParameter("username")          //form表单密码参数名

//
//                // 指定登录成功的连接，并带上数据
//                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/security/login"))
//
//                //也可以不指定登录成功的页面路径，因为它是重定向，所以 request 中的数据就会没有了
//                //.successForwardUrl("/security/welcome")
//                //同样也可以自定义登录成功的页面路径，但它也是重定向，所以 request 中的数据就会没有了
//                //.successHandler(new MyUrlSuccessHandler())
//
//                //如果用户没有访问受保护的页面，默认跳转到页面
//                //.defaultSuccessUrl("/security/success")

//                    .failureForwardUrl("/security/login")
//                    //.failureUrl()
//                    //.failureHandler(AuthenticationFailureHandler)
//                    //.successHandler(AuthenticationSuccessHandler)
//                    //.failureUrl("/login?error")
//                    .permitAll();                           //允许所有用户都有权限访问登录页面
//    }
//}
