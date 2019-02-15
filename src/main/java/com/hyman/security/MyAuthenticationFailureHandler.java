package com.hyman.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * 自定义的登录出错处理，本类相当于重写了 SimpleUrlAuthenticationFailureHandler类。
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler{

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String defaultFailureUrl;
    private String handleFailureUrl = "/security/login?error=error";

    // 设置自动转向为目标地址
    private boolean forwardToDestination = true;
    // 允许创建 session对象
    private boolean allowSessionCreation = true;
    // 声明并且要重写重定向策略
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    // 密码错误提示，但只能用这个值代表，因为框架里面是定死了，这个值代表密码错误
    public static final String PASSWORD_ERROR = "Bad credentials";
    public static final String ACCOUNT_NO_EXIST="账号不存在！";
    public static final String ACCOUNT_BAN="账号被封禁！";

    public MyAuthenticationFailureHandler(){

    }

    public MyAuthenticationFailureHandler(String defaultFailureUrl){
        this.setDefaultFailureUrl(defaultFailureUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(this.defaultFailureUrl == null){
            this.logger.error("没有指定登录失败时的链接url！");
            response.sendError(401,"验证失败：" + exception.getMessage());
        }else {
            this.saveException(request,exception);

            //根据不同的错误，来进行不同的处理逻辑，它会直接返回到登录页面中
            switch (exception.getMessage()){

                /**
                 * getRequestDispatcher.forward 是服务器内部跳转，地址栏信息不变，只能跳转到web应用内的网页。
                 * sendRedirect 是页面重定向，地址栏信息改变，可以跳转到任意网页。
                 *
                 * 其中，getRequestDispatcher分成两种，可以用request调用，也可以用getServletContext()调用，不同的是 request.
                 * getRequestDispatcher(url) 的url可以是相对路径也可以是绝对路径（因为其内部已经封装了 ServletContext.
                 * getRealPath()以获得相应的项目根路径）。
                 * 而 this.getServletContext().getRequestDispatcher(url) 的url只能是绝对路径。
                 *
                 * sendRedirect只能用 response调用，可以用相对路径（直接用），也可以用相对路径（需加应用路径）
                 */
                case PASSWORD_ERROR:
                    this.logger.error("Redirecting to " + this.defaultFailureUrl);
                    request.setAttribute("failureMsg","登录密码错误");
                    request.getRequestDispatcher(this.handleFailureUrl).forward(request, response);
                    break;
                case ACCOUNT_NO_EXIST:
                    this.logger.error("Redirecting to " + this.defaultFailureUrl);
                    request.setAttribute("failureMsg",ACCOUNT_NO_EXIST);
                    request.getRequestDispatcher(this.handleFailureUrl).forward(request, response);
                    break;
                case ACCOUNT_BAN:
                    this.logger.error("Redirecting to " + this.defaultFailureUrl);
                    request.setAttribute("failureMsg",ACCOUNT_BAN);
                    request.getRequestDispatcher(this.handleFailureUrl).forward(request, response);
                    break;
                default:
                    this.logger.error("Redirecting to " + this.defaultFailureUrl);
                    this.redirectStrategy.sendRedirect(request, response, this.defaultFailureUrl);
            }
        }
    }

    /**
     * 缓存 AuthenticationException 用于视图呈现
     * 如果转发目的地设置为true，则将使用请求范围，否则将尝试在会话中存储异常。如果没有会话和允许创建，会话将被创建。否则将不存储异常。
     *
     * @param request
     * @param exception
     */
    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {

        if(this.forwardToDestination) {
            request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        } else {
            HttpSession session = request.getSession(false);
            if(session != null || this.allowSessionCreation) {
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
            }
        }
    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        /**
         * Assert 断言机制:
         * 它是 org.springframework.util 下的抽象类，属于工具类。
         * 在测试代码或者调试程序时，总会做出一些假设，断言就是用于在代码中捕捉这些假设。当要判断一个方法传入的参数时，我们就可以使用断言。
         *
         *  isTrue(boolean expression) / isTrue(boolean expression, String message) ，当 expression 不为 true时抛出异常；
         *
         *  isValidRedirectUrl ，如果重定向 url以 “/”开头，或者是绝对的，则返回true。
         */
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl),
                File.separator + defaultFailureUrl + File.separator + " is not a valid redirect URL：该路径不是有效的重定向 url！");
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public boolean isForwardToDestination() {
        return forwardToDestination;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public boolean isAllowSessionCreation() {
        return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
