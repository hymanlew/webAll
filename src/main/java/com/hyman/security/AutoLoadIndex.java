package com.hyman.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义的入口引用结点，位于整个过滤器链的前方
public class AutoLoadIndex extends LoginUrlAuthenticationEntryPoint{

    public AutoLoadIndex(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        //比对请求头信息，然后进行判断
        if("XMLHttpRequest".equals(request.getHeader("X-Requestd-With"))){

            // sendError(int sc, String msg)：向客户端发送一个代表特定错误的HTTP响应状态代码，并且发送具体的错误消息。
            // 对于ajax请求不重定向  而是返回错误代码，Status code (403)
            response.sendError(HttpServletResponse.SC_FORBIDDEN,"Access Denied");
        }else{
            super.commence(request,response,authException);
        }
    }
}
