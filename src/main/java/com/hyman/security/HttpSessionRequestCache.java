package com.hyman.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.springframework.security.web.savedrequest.DefaultSavedRequest;


/**
 * 自定义网络请求缓存，与 springSecurity 包下的 HttpSessionRequestCache 类完全相同，在进入 springSecurity 登录页之前，将把登录完成
 * 后跳转的页面记录进缓存，以供 springSecurity 在成功登录后跳转。
 */
public class HttpSessionRequestCache implements RequestCache{

    private static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

    //获取一个 log 日志记录器
    protected final Log logger = LogFactory.getLog(this.getClass());

    //PortMapper：是用其自身的解析器对 PortMapper标签进行解析（不同的响应状态，对应不同的 port 端口）。
    //PortResolver：用于解析PortMapper对象的工具对象。
    /**
     * This interface is necessary because ServletRequest.getServerPort() may not return the correct port in certain
     * circumstances. For example, if the browser does not construct the URL correctly after a redirect.
     *
     * 这个接口是必需的，因为 ServletRequest.getServerPort()可能在某些情况下不能返回正确的端口。例如，如果浏览器在重定向后没有正确构造URL。
     */
    private PortResolver portResolver = new PortResolverImpl();

    private boolean sessionAllowed = true;

    /**
     * 请求匹配规则，这是一个功能接口，可以作为lambda表达式或方法引用的赋值目标。
     *
     * 在 spring security的xml配置文件命名空间配置中的<http>元素，它允许对特定的http请求基于安全考虑进行配置。默认情况下，适用于所有的
     * 请求，但可以使用requestMatcher(RequestMatcher)或者其它相似的方法进行限制。
     */
    private RequestMatcher requestMatcher;

    // session属性名字
    private String sessionAttrName;

    ////ajax登录成功后的跳转页面的参数
    private String paramName = "toUrl";

    public HttpSessionRequestCache(){

        // 拿到 RequestMatcher的实现类对象
        this.requestMatcher = AnyRequestMatcher.INSTANCE;
        this.sessionAttrName = "SPRING_SECURITY_SAVED_REQUEST";
    }

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        // 匹配 request请求
        if(this.requestMatcher.matches(request)){

            // XMLHttpRequest对象可以实现客户端与服务器之间的异步请求，AJAX 就是用的这种请求对象，可以把它们看成是一个东西
            // 即如果不是 ajax请求
            if(!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){

                /**
                 * This class is used by AbstractAuthenticationProcessingFilter and SavedRequestAwareWrapper to reproduce
                 * the request after successful authentication. An instance of this class is stored at the time of an
                 * authentication exception by ExceptionTranslationFilter.
                 *
                 * 这个类被 ..Filter和 ..Wrapper（包装器）使用，以在成功的身份验证之后复制 request请求和 port端口信息。这个类的一个
                 * 实例会被 ..Filter 存储，当在一个身份验证异常的时候。
                 */
                DefaultSavedRequest savedRequest = new DefaultSavedRequest(request,this.portResolver);

                /**
                 * getSession(boolean create)意思是返回当前reqeust中的HttpSession，如果当前 reqeust中的HttpSession 为null，
                 * 并且当 create为 true时，就创建一个新的Session。如果 create为 false，则返回null；
                 *
                 * 简而言之：
                 * HttpServletRequest.getSession(ture) 等同于 HttpServletRequest.getSession()
                 * HttpServletRequest.getSession(false) 等同于 如果当前Session没有就为null；
                 */
                if(this.sessionAllowed || request.getSession(false)!=null){
                    request.getSession().setAttribute(this.sessionAttrName,savedRequest);
                    this.logger.debug("请求成功，请求对象被保存到 session："+savedRequest);
                }
            }else{
                //如果 ajax没有上传指定登录完成后跳转的路径参数，则不操作，会默认进入 SpringSecurity的登录结果处理类
                if(request.getParameter(paramName)==null){
                    this.logger.debug("当前为 ajax请求，并且没有传递地址路径！");
                }else{
                    DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, this.portResolver);

                    //将 指定登录完成后跳转的路径 存入 request 对象缓存中
                    savedRequest.setRequestURI(request.getParameter(paramName));
                    if(this.sessionAllowed || request.getSession(false)!=null){
                        request.getSession().setAttribute(this.sessionAttrName,savedRequest);
                        this.logger.debug("ajax 请求路径保存成功，请求对象被保存到 session："+savedRequest);
                    }
                }
            }
        }else{
            this.logger.error("请求失败，非正常请求，匹配失败！");
        }
    }

    @Override
    public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return session!=null?(SavedRequest)session.getAttribute(this.sessionAttrName):null;
    }

    @Override
    public void removeRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            this.logger.debug("当前请求与 request缓存对象相匹配，从 session中移除并进行下一步！");
            session.removeAttribute(this.sessionAttrName);
        }
    }

    @Override
    public HttpServletRequest getMatchingRequest(HttpServletRequest request, HttpServletResponse response) {
        DefaultSavedRequest savedRequest = (DefaultSavedRequest) this.getRequest(request,response);
        if(savedRequest==null){
            return null;
        }else if(!savedRequest.doesRequestMatch(request,this.portResolver)){
            this.logger.error("当前 request请求与 request缓存对象中的对象不匹配！");
            return null;
        }else{
            this.removeRequest(request,response);
            return new SavedRequestAwareWrapper(savedRequest,request);
        }
    }

    public PortResolver getPortResolver() {
        return portResolver;
    }

    public void setPortResolver(PortResolver portResolver) {
        this.portResolver = portResolver;
    }

    public boolean isSessionAllowed() {
        return sessionAllowed;
    }

    public void setSessionAllowed(boolean sessionAllowed) {
        this.sessionAllowed = sessionAllowed;
    }

    public RequestMatcher getRequestMatcher() {
        return requestMatcher;
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public String getSessionAttrName() {
        return sessionAttrName;
    }

    public void setSessionAttrName(String sessionAttrName) {
        this.sessionAttrName = sessionAttrName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
