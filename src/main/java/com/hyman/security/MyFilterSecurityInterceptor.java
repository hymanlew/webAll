package com.hyman.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

/**
 * AbstractSecurityInterceptor 作用：
 *  1，从安全上下文获取身份验证对象。
 *  2，确定请求是否通过查找安全对象请求（FilterInvocation）与 SecurityMetadataSource关联到安全或公共调用。
 *  3，对于被保护的调用(有一个用于安全对象调用的ConfigAttributes列表):
 *      1，如果身份验证 isauthenticated()返回false，或者 alwaysReauthenticate是正确的，那么就通过配置的AuthenticationManager对
 *         请求进行验证。在身份验证时，用返回的值替换在 securitycontext上的身份验证对象。
 *      2，对配置的 AccessDecisionManager授权请求。
 *      3，通过配置的 RunAsManager执行任何 run-as替换。
 *      4，将控件返回给具体的子类，该子类实际上将继续执行该对象。一个截取程序将被返回，以便在子类完成了对象的执行之后，它最后的子句可以确保
 *         对 AbstractSecurityInterceptor进行重新调用，并正确使用 finallyInvocation(截取程序)。
 *      5，具体的子类将通过 afterInvocation(interceptorstoken, Object)方法重新调用AbstractSecurityInterceptor。
 *      6，如果RunAsManager替换了身份验证对象，则在调用 AuthenticationManager之后将 securitycontext返回到存在的对象。
 *      7，如果定义了 AfterInvocationManager，调用调用管理器，并允许它替换将要返回给调用者的对象。
 *
 *  4，对于公开的调用(安全对象调用但没有 ConfigAttributes权限时):
 *      1，如上所述，具体的子类将被返回一个截取程序，在安全对象被执行之后，它将被重新呈现给 AbstractSecurityInterceptor。
 *         AbstractSecurityInterceptor在调用它的 afterInvocation(interceptorstoken, Object)时不会采取进一步的操作。
 *
 *  5，控件再次返回到具体的子类，以及应该返回给调用者的对象。然后，子类将结果或异常返回给原始调用者。
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{

    /**
     * Invocation ：调用；
     *
     * FilterInvocationSecurityMetadataSource ：该接口被设计用于执行对 FilterInvocations（用于验证的请求，响应对象）的查找。
     * 并且为指定的安全对象类型提供 ConfigAttributes（权限属性）。
     *
     * FilterInvocation 类，安全对象：
     * 它持有与 HTTP过滤器关联的对象。保证请求和响应是 HttpServletRequest 和 HttpServletResponse的实例，并且没有空对象。
     * 它要求安全系统类可以获得对筛选器环境的访问，以及请求和响应。
     * 这个类的作用本身很简单，就是把 doFilter传进来的 request,response 和 FilterChain对象保存起来，供FilterSecurityInterceptor的
     * 处理代码调用。
     */

    //配置文件注入
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Filter：
     * 与Servlet相似，过滤器是一些web应用程序组件，可以绑定到一个web应用程序中。但是与其他web应用程序组件不同的是，过滤器是"链"在容器的
     * 处理过程中的。这就意味着它们会在servlet处理器之前访问一个进入的请求，并且在外发响应信息返回到客户前访问这些响应信息。这种访问使得
     * 过滤器可以检查并修改请求和响应的内容。
     *
     * 过滤器放在web资源之前，可以在请求抵达它所应用的web资源(可以是一个Servlet、一个Jsp页面，甚至是一个HTML页面)之前截获进入的请求，
     * 并且在它返回到客户之前截获输出请求。Filter：用来拦截请求，处于客户端与被请求资源之间，目的是重用代码。Filter链，在web.xml中哪个
     * 先配置，哪个就先调用。在 filter中也可以配置一些初始化参数。
     *
     * Java中的 Filter 并不是一个标准的Servlet ，它不能处理用户请求，也不能对客户端生成响应。 主要用于对HttpServletRequest 进行预处
     * 理，也可以对HttpServletResponse 进行后处理，是个典型的处理链。
     *
     *
     * FilterChain 滤波器接口，即过滤链：
     * 它是两个过滤器，EncodingFilter负责设置编码，SecurityFilter负责控制权限，服务器会按照 web.xml中过滤器定义的先后循序组装成一条链，
     * 然后一次执行其中的 doFilter()方法。执行的顺序就是：执行第一个过滤器的 chain.doFilter()之前的代码，第二个过滤器的chain.doFilter
     * ()之前的代码，请求的资源，第二个过滤器的chain.doFilter()之后的代码，第一个过滤器的chain.doFilter()之后的代码，最后返回响应。
     *
     * 过滤链的好处是，执行过程中任何时候都可以打断，只要不执行chain.doFilter()就不会再执行后面的过滤器和请求的内容。而在实际使用时，就要
     * 特别注意过滤链的执行顺序问题，像EncodingFilter就一定要放在所有Filter之前，这样才能确保在使用请求中的数据前设置正确的编码。
     */

    //登陆后，每次访问资源都通过这个拦截器拦截
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 生成一个请求，响应，过滤的一个对象
        FilterInvocation fi = new FilterInvocation(request,response, filterChain);
        // 过滤器进行权限拦截
        invoker(fi);
    }

    private void invoker(FilterInvocation fi) throws IOException, ServletException {
        /**
         * fi 里面有一个被拦截的 url，里面调用 MyInvocationSecurityMetadataSource 的 getAttributes(Object object) 这个
         * 方法获取 fi 对应的所有权限，再调用 MyAccessDecisionManager 的 decide 方法来校验用户的权限是否足够，弄完这一切
         * 就会执行下一个拦截器。
         */
        /**
         * InterceptorStatusToken ：
         * 这个类反映了安全拦截的状态，所以最后调用 AbstractSecurityInterceptor.afterInvocation（InterceptorStatusToken
         * （截取程序），object返回对象)可以正确地清理。
         *
         * afterInvocation ：完成了在安全对象调用完成之后的 AbstractSecurityInterceptor的工作。
         * beforeInvocation：AbstractSecurityInterceptor 调用 FilterInvocation对象，返回安全状态对象。
         */
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try{
            // 获得 FilterChain 滤波链接口；
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
        }finally {
            super.afterInvocation(token,null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        // 指定安全对象的类型，子类将呈现给抽象的父类以进行处理。
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        /**
         * obtain ：获得
         * SecurityMetadataSource ：用于识别给定安全对象调用的 ConfigAttributes权限属性。
         */
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
