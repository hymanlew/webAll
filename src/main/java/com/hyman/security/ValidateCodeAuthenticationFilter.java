package com.hyman.security;

import com.hyman.dao.DemoDao;
import com.hyman.entity.Admin;
import com.hyman.entity.Student;
import com.hyman.entity.Teacher;
import com.hyman.entity.User;
import com.hyman.service.DemoService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 自定义验证码验证拦截器，继承了用户验证拦截器，重写其具体功能
 *
 * 执行完这个认证过滤器之后，才会执行 MyUserDetailService。
 * 注：因为 MyUserDetailService类中的 loadUserByUsername(String username) 方法只能接收一个参数 username，而且这个username是从
 * 认证过滤器那里传过来的，所以在测试方法中就通过 username 顺带传递角色类型过来，如下，将角色类型拼在 username中，到
 * MyUserDetailService 类中再解开。
 */
public class ValidateCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Resource(name = "service")
    private DemoService service;
    @Resource(name = "supperdao")
    private DemoDao dao;

    /**
     * SqlSession 是 MyBatis的关键对象（它是一个接口）,是执行持久化操作的独享,类似于JDBC中的Connection.它是应用程序与持久层之间执行
     * 交互操作的一个单线程对象,也是 MyBatis执行持久化操作的关键对象。
     *
     * SqlSession 对象完全包含以数据库为背景的所有执行SQL操作的方法,它的底层封装了JDBC连接,可以用SqlSession实例来直接执行被映射的SQL
     * 语句（即使用其实现类 sqlSessionTemplate）。每个线程都应该有它自己的 SqlSession实例，并且 SqlSession的实例不能被共享,同时
     * SqlSession也是线程不安全的,绝对不能讲 SqlSeesion实例的引用放在一个类的静态字段甚至是实例字段中，也绝不能将SqlSession实例的引
     * 用放在任何类型的管理范围中,比如Servlet当中的 HttpSession对象中。
     *
     * 使用完 SqlSeesion之后关闭 Session很重要，应该确保使用 finally块来关闭它（而 sqlSessionTemplate对象都已经封装好了，是 spring
     * 提供的一个全局唯一的 SqlSessionTemplate示例来完成DefailtSqlSession的功能，因为它不是线程安全的）。
     */
    @Resource(name = "sqlSessionTemplate")
    private transient SqlSessionTemplate sqlSessionTemplate;

    /**
     * SqlSessionFactory 是MyBatis的关键对象（它是一个接口）,它是个单个数据库映射关系经过编译后的内存镜像。SqlSessionFactory对象的实
     * 例可以通过 SqlSessionFactoryBuilder对象类获得，而SqlSessionFactoryBuilder则可以从XML配置文件或一个预先定制的 Configuration
     * 的实例构建出 SqlSessionFactory的实例。
     *
     * 每一个MyBatis的应用程序都以一个 SqlSessionFactory对象的实例为核心，同时 SqlSessionFactory也是线程安全的,SqlSessionFactory
     * 一旦被创建,应该在应用执行期间都存在。
     *
     * 在应用运行期间不要重复创建多次,建议使用单例模式.SqlSessionFactory是创建SqlSession的工厂。
     * 它使用 opensession方法来创建 sqlsession接口对象。
     */
    private SqlSessionFactory sqlSessionFactory;


    // 默认 session中保存的失败验证码
    private static final String DEFAULT_SESSION_VALIDATE_CODE_FIELD = "random";
    // 默认显示的验证码，要让用户输入的值
    public static final String DEFAULT_VALIDATE_CODE_PARAMETER = "code";
    public static final String VALIDATE_CODE_FAILED_MSG_KEY = "验证码错误";

    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeAuthenticationFilter.class);

    public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

    // 只允许 post 方式提交
    private boolean postOnly = true;
    // 默认验证码为空
    private boolean allowEmptyValidateCode = false;

    // 定义验证码区域，以获取值
    private String sessionValidateCodeField = DEFAULT_SESSION_VALIDATE_CODE_FIELD;
    private String validateCodeParameter = DEFAULT_VALIDATE_CODE_PARAMETER;

    public ValidateCodeAuthenticationFilter(SqlSessionFactory sqlSessionFactory) {
    }


    // 具体的验证用户名和密码的业务方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if(postOnly && !request.getMethod().equals("POST")){
            // 抛出验证失败异常
            throw new AuthenticationServiceException("只允许 POST方式请求，本次请求方式为："+request.getMethod());
        }

        // obtainUsername/password，获得用户输入的数据值，即名字和密码
        String username = obtainUsername(request).trim();
        String password = obtainPassword(request);
        if(password==null){
            password = "";
        }

        // 该类用于简单表示用户名和密码的身份验证实现，现在是先定义它，即使用构造方法声明它。用于实现验证
        UsernamePasswordAuthenticationToken authrequest = new UsernamePasswordAuthenticationToken(username,password);

        // Place the last username attempted（作为附件放入） into HttpSession for views，将用户名存入 session中。
        HttpSession session = request.getSession(false);
        if(session!=null || getAllowSessionCreation()){

            // TextEscapeUtils 是 security的内部工具类，作用是用来进行URL编码的，并且做一些用户表单数据的非法输入字符的判断。
            request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));
        }

        // Allow subclasses to set the "details" property，允许子类设置“details”用户详细属性，并交由其父类去处理。
        setDetails(request,authrequest);

        // check validate code，检查并匹配所有的信息
        if (!isAllowEmptyValidateCode()){
            // 由于登录页没有设置验证码及其他相关功能，所以先注释掉
            //checkValidateCode(request);
            //updateLastLoginTime(username, password);
        }

        // 通过AuthenticationManager:ProviderManager完成认证任务，也是通过解析 form-login标签，构造bean时注入的，返回其总父类对象。
        return this.getAuthenticationManager().authenticate(authrequest);
    }


    // 此方法是教程中的 demo案例，用于代替上面的方法。在本项目中没有使用
    public Authentication attemptAuthenticationDemo(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String roletype = request.getParameter("roletype");

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = null;

        if(!"".equals(roletype) || roletype != null){
            if("student".equals(roletype)){
                Student stu = dao.findBysId(username);

                //通过session把用户对象设置到session中
                request.getSession().setAttribute("session_user", stu);

                //将角色标志在username上
                username = "stu"+username;

                try {
                    if (stu == null || !stu.getPassword().equals(password)) {
                        BadCredentialsException exception = new BadCredentialsException("用户名或密码不匹配");
                        throw exception;
                    }
                } catch (Exception e) {
                    BadCredentialsException exception = new BadCredentialsException("没有此用户");
                    throw exception;
                }


            }else if("teacher".equals(roletype)){
                Teacher tea = dao.findBytId(username);

                //通过session把用户对象设置到session中
                request.getSession().setAttribute("session_user", tea);

                //将角色标志在username上
                username = "tea"+username;

                try {
                    if (tea == null || !tea.getPassword().equals(password)) {
                        BadCredentialsException exception = new BadCredentialsException("用户名或密码不匹配");
                        throw exception;
                    }
                } catch (Exception e) {
                    BadCredentialsException exception = new BadCredentialsException("没有此用户");
                    throw exception;
                }

            }else if("admin".equals(roletype)){
                Admin adm = dao.findByaId(username);

                //通过session把用户对象设置到session中
                request.getSession().setAttribute("session_user", adm);

                //将角色标志在username上
                username = "adm"+username;
                try {
                    if (adm == null || !password.equals(adm.getPassword())) {
                        BadCredentialsException exception = new BadCredentialsException("用户名或密码不匹配");
                        throw exception;
                    }
                } catch (Exception e) {
                    BadCredentialsException exception = new BadCredentialsException("没有此用户");
                    throw exception;
                }

            }else{
                BadCredentialsException exception = new BadCredentialsException("系统错误：没有对应的角色！");
                throw exception;
            }
        }

        //实现验证
        authRequest = new UsernamePasswordAuthenticationToken(username, password);
        //允许设置用户详细属性
        setDetails(request, authRequest);
        //运行
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    // 比较 session中的验证码和用户输入的验证码是否相等
    private void checkValidateCode(HttpServletRequest request) {
        // 获取对应的数据
        String sessionValidateCode = obtainSessionValidateCode(request);
        String validateCodeParameter = obtainValidateCodeParameter(request);
        if(validateCodeParameter.isEmpty() || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)){
            throw new AuthenticationServiceException("验证码输入错误" + messages.getMessage(VALIDATE_CODE_FAILED_MSG_KEY));
        }
    }

    private void updateLastLoginTime(String username, String password) {
        SqlSession session = sqlSessionFactory.openSession();
        String sql = "";
        User user = service.getUser(username);
        if(user!=null){
            if(password.equals(user.getPassword())){
                logger.debug("登录成功，所有信息全部匹配！");
            }
        }
    }


    private String obtainSessionValidateCode(HttpServletRequest request) {

        Object obj = request.getSession().getAttribute(sessionValidateCodeField);
        return obj==null ? "":obj.toString();
    }

    private String obtainValidateCodeParameter(HttpServletRequest request) {
        return request.getParameter(validateCodeParameter);
    }


    public boolean isAllowEmptyValidateCode() {
        return allowEmptyValidateCode;
    }

    public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode) {
        this.allowEmptyValidateCode = allowEmptyValidateCode;
    }

    public String getSessionValidateCodeField() {
        return sessionValidateCodeField;
    }

    public void setSessionValidateCodeField(String sessionValidateCodeField) {
        this.sessionValidateCodeField = sessionValidateCodeField;
    }

    public String getValidateCodeParameter() {
        return validateCodeParameter;
    }

    public void setValidateCodeParameter(String validateCodeParameter) {
        this.validateCodeParameter = validateCodeParameter;
    }
}
